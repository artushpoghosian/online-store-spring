package com.example.onlinestorespring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchCriteria {
    private String title;
    private Double minPrice;
    private Double maxPrice;
}
