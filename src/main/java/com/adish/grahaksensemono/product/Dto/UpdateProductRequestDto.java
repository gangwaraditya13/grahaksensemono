package com.adish.grahaksensemono.product.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

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
    private List<String> imageKeys;      // keys returned by the upload step (max 5)
}
