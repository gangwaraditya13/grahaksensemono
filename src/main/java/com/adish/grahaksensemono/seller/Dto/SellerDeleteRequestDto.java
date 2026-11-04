package com.adish.grahaksensemono.seller.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerDeleteRequestDto {
    private Long userId;
    private String password;
}
