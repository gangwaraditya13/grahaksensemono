package com.adish.grahaksensemono.seller.service.Impl;

import com.adish.grahaksensemono.seller.Dto.SellerRequestDto;
import com.adish.grahaksensemono.seller.Dto.SellerResponseDto;
import com.adish.grahaksensemono.seller.Dto.UpdateSellerDto;
import com.adish.grahaksensemono.seller.Mapper.SellerMapper;
import com.adish.grahaksensemono.seller.entity.Seller;
import com.adish.grahaksensemono.seller.repository.SellerRepository;
import com.adish.grahaksensemono.seller.service.SellerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerServiceImpl implements SellerService {

    private SellerRepository sellerRepository;

    private SellerMapper sellerMapper;
    public SellerServiceImpl(SellerRepository sellerRepository, SellerMapper sellerMapper){
        this.sellerRepository = sellerRepository;
        this.sellerMapper = sellerMapper;
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

    @Override
    public boolean deleteSellerById(Long SellerId) {
        if(sellerRepository.existsById(SellerId)){
            sellerRepository.deleteById(SellerId);
            return true;
        }
        return false;
    }
}
