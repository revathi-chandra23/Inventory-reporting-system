package com.revathi.inventory.controller;

import com.revathi.inventory.dto.*;

import com.revathi.inventory.model.ReportJobStatus;
import com.revathi.inventory.util.CsvExportUtil;
import com.revathi.inventory.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/current-stock")
    public ResponseEntity<List<StockReportDTO>> getCurrentStock() {
        return ResponseEntity.ok(reportService.getCurrentStockReport());
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<LowStockDTO>> getLowStock() {
        return ResponseEntity.ok(reportService.getLowStockReport());
    }
    @GetMapping("/trend")
    public ResponseEntity<List<StockTrendDTO>> getStockTrend(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(reportService.getMovementTrend(from, to));
    }



    @PostMapping("/upload")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file,
                                            @RequestParam("type") String type) {
        reportService.processCSV(file, type);
        return ResponseEntity.ok("CSV uploaded and processed: " + type);
    }

    @GetMapping("/status/{jobId}")
    public ResponseEntity<ReportJobStatus> getStatus(@PathVariable Long jobId) {
        return ResponseEntity.ok(reportService.getJobStatus(jobId));
    }
    @GetMapping("/download/current-stock")
    public void downloadCurrentStock(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=current_stock.csv");

        List<StockReportDTO> stockList = reportService.getCurrentStockReport();
        CsvExportUtil.writeCurrentStockToCsv(response.getWriter(), stockList);
    }

    @GetMapping("/download/low-stock")
    public void downloadLowStock(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=low_stock.csv");

        List<LowStockDTO> lowStockList = reportService.getLowStockReport();
        CsvExportUtil.writeLowStockToCsv(response.getWriter(), lowStockList);
    }

    @GetMapping("/download/trend")
    public void downloadTrendCsv(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            HttpServletResponse response) throws IOException {

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=stock_trend.csv");

        List<StockTrendDTO> trendList = reportService.getMovementTrend(from, to);
        CsvExportUtil.writeTrendToCsv(response.getWriter(), trendList);
    }

}
