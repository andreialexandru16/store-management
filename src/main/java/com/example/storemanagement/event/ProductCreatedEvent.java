package com.example.storemanagement.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductCreatedEvent {
    private Long id;
    private String name;
    private BigDecimal price;
}
