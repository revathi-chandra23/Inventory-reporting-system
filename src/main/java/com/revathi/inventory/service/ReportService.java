package com.revathi.inventory.service;

import com.revathi.inventory.dto.LowStockDTO;
import com.revathi.inventory.dto.StockReportDTO;

import com.revathi.inventory.dto.StockTrendDTO;
import com.revathi.inventory.exceptionHanding.ResourceNotFoundException;
import com.revathi.inventory.model.*;
import com.revathi.inventory.repositories.*;
import com.revathi.inventory.util.CsvParserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class ReportService {

    @Autowired
    private CsvParserUtil csvParserUtil;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private WarehouseRepository warehouseRepo;

    @Autowired
    private StockTransactionRepository transactionRepo;

    @Autowired
    private ReportJobStatusRepository reportJobStatusRepository;

    public void processCSV(MultipartFile file, String type) {
        try {
            switch (type.toLowerCase()) {
                case "product":
                    List<Product> products = csvParserUtil.parseProductsCSV(file.getInputStream());
                    for (Product product : products) {
                        productRepo.findBySku(product.getSku())
                                .ifPresentOrElse(
                                        existing -> {
                                    existing.setName(product.getName());
                                    existing.setReorderLevel(product.getReorderLevel());
                                    productRepo.save(existing);
                                    },
                                    () -> productRepo.save(product)
                                );
                    }
                    break;

                case "warehouse":
                    List<Warehouse> warehouses = csvParserUtil.parseWarehousesCSV(file.getInputStream());
                    for (Warehouse warehouse : warehouses) {
                        Optional<Warehouse> existing = warehouseRepo.findByLocation(warehouse.getLocation());
                        if (existing.isPresent()) {
                            Warehouse existingWarehouse = existing.get();
                            existingWarehouse.setName(warehouse.getName());
                            warehouseRepo.save(existingWarehouse);
                        } else {
                            warehouseRepo.save(warehouse);
                        }
                    }
                    break;

                case "transaction":
                    List<StockTransaction> transactions = csvParserUtil.parseStockTransactionsCSV(
                            file.getInputStream(), productRepo, warehouseRepo);
                    transactionRepo.saveAll(transactions);
                    break;
                default:
                    throw new IllegalArgumentException("unknown CSV type: " + type);
            }
        } catch (IOException e) {
            throw new RuntimeException("failed to process CSV file", e);
        }
    }

    public List<StockReportDTO> getCurrentStockReport() {
        List<StockTransaction> transactions = transactionRepo.findAll();
        Map<String, StockReportDTO> stockMap = new HashMap<>();

        for (StockTransaction tx : transactions) {
            String productName = tx.getProduct().getName();
            String warehouseName = tx.getWarehouse().getName();
            String key = productName + "-" + warehouseName;

            stockMap.putIfAbsent(key, new StockReportDTO(productName, warehouseName, 0));
            StockReportDTO dto = stockMap.get(key);

            if ("IN".equalsIgnoreCase(tx.getType())) {
                dto.setCurrentStock(dto.getCurrentStock() + tx.getQuantity());
            } else if ("OUT".equalsIgnoreCase(tx.getType())) {
                dto.setCurrentStock(dto.getCurrentStock() - tx.getQuantity());
            }
        }

        return new ArrayList<>(stockMap.values());
    }

    public List<LowStockDTO> getLowStockReport() {
        List<Product> products = productRepo.findAll();
        List<LowStockDTO> lowStockList = new ArrayList<>();

        for (Product product : products) {
            int totalStock = transactionRepo.findTotalStockByProductId(product.getId());
            if (totalStock < product.getReorderLevel()) {
                lowStockList.add(new LowStockDTO(
                        product.getName(),
                        totalStock,
                        product.getReorderLevel()
                ));
            }
        }

        return lowStockList;
    }
    public List<StockTrendDTO> getMovementTrend(LocalDate from, LocalDate to) {
        List<StockTransaction> transactions = transactionRepo.findByTransactionDateBetween(from, to);
        List<StockTrendDTO> trendList = new ArrayList<>();

        for (StockTransaction tx : transactions) {
            if (tx.getProduct() == null || tx.getWarehouse() == null) continue;

            trendList.add(new StockTrendDTO(
                    tx.getProduct().getName(),
                    tx.getWarehouse().getName(),
                    tx.getTransactionDate(),
                    tx.getType(),
                    tx.getQuantity()
            ));
        }

        return trendList;
    }


    public ReportJobStatus getJobStatus(Long jobId) {
        return reportJobStatusRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
    }
}
