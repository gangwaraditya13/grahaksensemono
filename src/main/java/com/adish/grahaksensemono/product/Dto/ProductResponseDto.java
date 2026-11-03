package com.adish.grahaksensemono.product.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private Long id;
    private Long sellerId;
    private String productName;
    private String productDescription;
    private BigDecimal price;
    private BigDecimal discount;
    private BigDecimal adminDiscount;
    private Set<String> imageUrl = new HashSet<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
