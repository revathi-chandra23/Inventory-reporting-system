package com.revathi.inventory.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LowStockDTO {
    private String productName;
    private int currentStock;
    private int reorderLevel;
}
