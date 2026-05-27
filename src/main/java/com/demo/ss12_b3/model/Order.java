package com.demo.ss12_b3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private Long id;
    private String customerName;
    private String product;
    private Integer quantity;
    private Double totalAmount;
}
