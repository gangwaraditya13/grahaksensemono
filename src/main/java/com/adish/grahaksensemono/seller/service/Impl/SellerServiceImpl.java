package com.adish.grahaksensemono.seller.service.Impl;

import com.adish.grahaksensemono.product.Dto.ProductResponseDto;
import com.adish.grahaksensemono.product.service.ProductService;
import com.adish.grahaksensemono.seller.Dto.SellerRequestDto;
import com.adish.grahaksensemono.seller.Dto.SellerResponseDto;
import com.adish.grahaksensemono.seller.Dto.UpdateSellerDto;
import com.adish.grahaksensemono.seller.Mapper.SellerMapper;
import com.adish.grahaksensemono.seller.entity.Seller;
import com.adish.grahaksensemono.seller.repository.SellerRepository;
import com.adish.grahaksensemono.seller.service.SellerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerServiceImpl implements SellerService {

    private SellerRepository sellerRepository;

    private SellerMapper sellerMapper;

    private ProductService productService;

    public SellerServiceImpl(SellerRepository sellerRepository, SellerMapper sellerMapper, ProductService productService){
        this.sellerRepository = sellerRepository;
        this.sellerMapper = sellerMapper;
        this.productService = productService;
    }

    @Override
    public SellerResponseDto createSeller(SellerRequestDto sellerRequestDto) {
        Seller seller = sellerMapper.sellerRequestDtoToSeller(sellerRequestDto);
        try{
            Seller save = sellerRepository.save(seller);
            SellerResponseDto sellerResponseDto = sellerMapper.sellerToSellerResponseDto(save);
            return sellerResponseDto;
        }catch (Exception e){
//            throw new RuntimeException("seller not created : "+e.getMessage());
            return null;
        }
    }

    @Override
    public SellerResponseDto updateSeller(UpdateSellerDto updateSellerDto) {
        try {
            Seller seller = sellerRepository.findByCompanyName(updateSellerDto.getCompanyName()).orElseThrow();
            boolean change = false;
            if(!updateSellerDto.getCompanyName().trim().equals("")
                    && updateSellerDto.getCompanyName() != null
                    && !updateSellerDto.getCompanyName().trim().equals(seller.getCompanyName())){
                seller.setCompanyName(updateSellerDto.getCompanyName());
                change = true;
            }
            if(!updateSellerDto.getBusinessAddress().trim().equals("")
                    && updateSellerDto.getBusinessAddress() != null
                    && !updateSellerDto.getBusinessAddress().trim().equals(seller.getBusinessAddress())){
                seller.setBusinessAddress(updateSellerDto.getBusinessAddress());
                change = true;
            }
            if(!updateSellerDto.getBusinessPhone().trim().equals("")
                    && updateSellerDto.getBusinessPhone() != null
                    && !updateSellerDto.getBusinessPhone().trim().equals(seller.getBusinessPhone())){
                seller.setBusinessPhone(updateSellerDto.getBusinessPhone());
                change = true;
            }

            if(change){
                seller.setUpdatedAt(LocalDateTime.now());
                sellerRepository.save(seller);
            }

            return sellerMapper.sellerToSellerResponseDto(seller);

        }catch(Exception e){
            return null;
        }

    }

    @Override
    public SellerResponseDto getSellerByUserId(Long UserId) {
        try {
            Seller seller = sellerRepository.findByUserId(UserId).orElseThrow();
            SellerResponseDto sellerResponseDto = sellerMapper.sellerToSellerResponseDto(seller);
            return sellerResponseDto;
        } catch (Exception e) {

            return null;
        }
    }

    @Override
    public List<SellerResponseDto> getAllSellers() {
        List<Seller> sellerList = sellerRepository.findAll();

        List<SellerResponseDto> sellerResponseDtoList = sellerList.stream().map(seller -> sellerMapper.sellerToSellerResponseDto(seller)).collect(Collectors.toList());
        return sellerResponseDtoList;
    }

    @Transactional
    @Override
    public boolean deleteSellerById(Long sellerId) {
        if(sellerRepository.existsById(sellerId)){
            try {
                List<ProductResponseDto> productById = productService.getAllProductByUserId(sellerId);
                productById.stream().forEach(productResponseDto -> productService.deleteProductById(productResponseDto.getId()));
                sellerRepository.deleteById(sellerId);
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
