package com.adish.grahaksensemono.seller.Mapper;

import com.adish.grahaksensemono.seller.Dto.SellerRequestDto;
import com.adish.grahaksensemono.seller.Dto.SellerResponseDto;
import com.adish.grahaksensemono.seller.entity.Seller;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SellerMapper {
    public Seller sellerRequestDtoToSeller(SellerRequestDto sellerRequestDto){
        Seller seller = new Seller();
        seller.setUserId(sellerRequestDto.getUserId());
        seller.setStatus(false);
        seller.setBusinessAddress(sellerRequestDto.getBusinessAddress());
        seller.setCompanyName(sellerRequestDto.getCompanyName());
        seller.setBusinessPhone(sellerRequestDto.getBusinessPhone());
        seller.setCreatedAt(LocalDateTime.now());
        seller.setUpdatedAt(LocalDateTime.now());

        return seller;
    }

    public SellerResponseDto sellerToSellerResponseDto(Seller seller){
        SellerResponseDto sellerResponseDto = new SellerResponseDto();
        sellerResponseDto.setId(seller.getId());
        sellerResponseDto.setStatus(seller.isStatus());
        sellerResponseDto.setCompanyName(seller.getCompanyName());
        sellerResponseDto.setBusinessAddress(seller.getBusinessAddress());
        sellerResponseDto.setBusinessPhone(seller.getBusinessPhone());
        sellerResponseDto.setUserId(seller.getUserId());
        sellerResponseDto.setCreatedAt(seller.getCreatedAt());
        sellerResponseDto.setUpdatedAt(seller.getUpdatedAt());

        return sellerResponseDto;
    }
}
