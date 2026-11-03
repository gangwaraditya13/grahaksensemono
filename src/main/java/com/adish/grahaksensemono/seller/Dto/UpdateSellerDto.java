package com.adish.grahaksensemono.seller.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSellerDto {
    private String companyName;
    private String businessAddress;
    private String businessPhone;
}
