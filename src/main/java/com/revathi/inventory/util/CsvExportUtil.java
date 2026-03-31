package com.revathi.inventory.util;

import com.revathi.inventory.dto.*;
import com.opencsv.CSVWriter;

import java.io.PrintWriter;
import java.util.List;

public class CsvExportUtil {

    public static void writeCurrentStockToCsv(PrintWriter writer, List<StockReportDTO> data) {
        CSVWriter csvWriter = new CSVWriter(writer);
        csvWriter.writeNext(new String[]{"Product Name", "Warehouse Name", "Current Stock"});

        for (StockReportDTO dto : data) {
            csvWriter.writeNext(new String[]{
                    dto.getProductName(),
                    dto.getWarehouseName(),
                    String.valueOf(dto.getCurrentStock())
            });
        }

        csvWriter.flushQuietly();
    }

    public static void writeLowStockToCsv(PrintWriter writer, List<LowStockDTO> data) {
        CSVWriter csvWriter = new CSVWriter(writer);
        csvWriter.writeNext(new String[]{"Product Name", "Current Stock", "Reorder Level"});

        for (LowStockDTO dto : data) {
            csvWriter.writeNext(new String[]{
                    dto.getProductName(),
                    String.valueOf(dto.getCurrentStock()),
                    String.valueOf(dto.getReorderLevel())
            });
        }

        csvWriter.flushQuietly();
    }

    public static void writeTrendToCsv(PrintWriter writer, List<StockTrendDTO> data) {
        CSVWriter csvWriter = new CSVWriter(writer);
        csvWriter.writeNext(new String[]{"Product Name", "Warehouse Name", "Transaction Date", "Type", "Quantity"});

        for (StockTrendDTO dto : data) {
            csvWriter.writeNext(new String[]{
                    dto.getProductName(),
                    dto.getWarehouseName(),
                    dto.getTransactionDate().toString(),
                    dto.getType(),
                    String.valueOf(dto.getQuantity())
            });
        }

        csvWriter.flushQuietly();
    }
}
