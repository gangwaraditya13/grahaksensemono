package com.adish.grahaksensemono.cart.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequestDto {
    private Long userId;
    private Long productId;
    private Integer quantity;
    private BigDecimal priceSnapshot;
}
