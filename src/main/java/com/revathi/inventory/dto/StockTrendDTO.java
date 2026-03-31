package com.revathi.inventory.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockTrendDTO {
    private String productName;
    private String warehouseName;
    private LocalDate transactionDate;
    private String type;
    private int quantity;
}

