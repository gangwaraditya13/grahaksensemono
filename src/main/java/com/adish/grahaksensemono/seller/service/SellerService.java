package com.adish.grahaksensemono.seller.service;

import com.adish.grahaksensemono.seller.Dto.SellerRequestDto;
import com.adish.grahaksensemono.seller.Dto.SellerResponseDto;
import com.adish.grahaksensemono.seller.Dto.UpdateSellerDto;

import java.util.List;

public interface SellerService{

    SellerResponseDto createSeller(SellerRequestDto sellerRequestDto);

    SellerResponseDto updateSeller(UpdateSellerDto updateSellerDto);

    SellerResponseDto getSellerByUserId(Long UserId);

    List<SellerResponseDto> getAllSellers();

    boolean deleteSellerById(Long SellerId);

}
