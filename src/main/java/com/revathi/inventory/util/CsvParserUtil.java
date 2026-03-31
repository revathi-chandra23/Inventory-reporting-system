package com.revathi.inventory.util;



import com.revathi.inventory.model.*;

import com.revathi.inventory.repositories.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvParserUtil {

    public List<Product> parseProductsCSV(InputStream inputStream) throws IOException {
        List<Product> products = new ArrayList<>();
        try (Reader reader = new InputStreamReader(inputStream);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                Product product = new Product();
                product.setName(record.get("name"));
                product.setSku(record.get("sku"));
                product.setReorderLevel(Integer.parseInt(record.get("reorderLevel")));
                products.add(product);
            }
        }
        return products;
    }

    public List<Warehouse> parseWarehousesCSV(InputStream inputStream) throws IOException {
        List<Warehouse> warehouses = new ArrayList<>();
        try (Reader reader = new InputStreamReader(inputStream);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                Warehouse warehouse = new Warehouse();
                warehouse.setName(record.get("name"));
                warehouse.setLocation(record.get("location"));
                warehouses.add(warehouse);
            }
        }
        return warehouses;
    }

    public List<StockTransaction> parseStockTransactionsCSV(InputStream inputStream,
                                                            ProductRepository productRepo,
                                                            WarehouseRepository warehouseRepo) throws IOException {
        List<StockTransaction> transactions = new ArrayList<>();
        try (Reader reader = new InputStreamReader(inputStream);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                StockTransaction tx = new StockTransaction();
                tx.setProduct(productRepo.findById(Long.parseLong(record.get("productId"))).orElse(null));
                tx.setWarehouse(warehouseRepo.findById(Long.parseLong(record.get("warehouseId"))).orElse(null));
                tx.setQuantity(Integer.parseInt(record.get("quantity")));
                tx.setTransactionDate(LocalDate.parse(record.get("transactionDate")));
                tx.setType(record.get("type"));
                transactions.add(tx);
            }
        }
        return transactions;
    }
}
