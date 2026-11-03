package com.adish.grahaksensemono.cart.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private BigDecimal priceSnapshot;
}
