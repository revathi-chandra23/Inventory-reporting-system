package com.revathi.inventory.service;

import com.revathi.inventory.dto.*;
import com.revathi.inventory.exceptionHanding.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockAlertScheduler {

    @Autowired
    private ReportService reportService;

    @Autowired
    private EmailService emailService;


    @Scheduled(cron = "5 * * * * *")
    public void checkAndSendLowStockAlert() {
        List<LowStockDTO> lowStockItems = reportService.getLowStockReport();
        if (lowStockItems.isEmpty()) return;

        StringBuilder emailContent = new StringBuilder(" Low Stock Alert:\n\n");
        for (LowStockDTO item : lowStockItems) {
            emailContent.append(String.format("• %s — Current: %d, Reorder Level: %d\n",
                    item.getProductName(),
                    item.getCurrentStock(),
                    item.getReorderLevel()));
        }


        emailService.sendLowStockEmail(
              new String[]{"revathichandra2302@gmail.com","thotaatchyuth@gmail.com"
              },"Low Stock Report - Daily Alert",
                emailContent.toString()
        );
    }
}

