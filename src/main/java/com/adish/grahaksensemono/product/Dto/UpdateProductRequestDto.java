package com.adish.grahaksensemono.product.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequestDto {
    private Long id;
    private String productName;
    private String productDescription;
    private BigDecimal price;
    private BigDecimal discount;
    private BigDecimal adminDiscount;
}
