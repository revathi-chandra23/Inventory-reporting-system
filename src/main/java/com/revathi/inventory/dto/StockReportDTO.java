package com.revathi.inventory.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class StockReportDTO {
    private String productName;
    private String warehouseName;
    private int currentStock;
}
