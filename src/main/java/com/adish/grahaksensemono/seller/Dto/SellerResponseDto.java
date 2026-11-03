package com.adish.grahaksensemono.seller.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerResponseDto {

    private Long id;
    private Long userId;
    private String companyName;
    private String businessAddress;
    private String businessPhone;
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
