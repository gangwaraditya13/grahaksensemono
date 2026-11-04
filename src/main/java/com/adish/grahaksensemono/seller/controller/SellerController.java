package com.adish.grahaksensemono.seller.controller;

import com.adish.grahaksensemono.seller.Dto.SellerDeleteRequestDto;
import com.adish.grahaksensemono.seller.Dto.SellerRequestDto;
import com.adish.grahaksensemono.seller.Dto.SellerResponseDto;
import com.adish.grahaksensemono.seller.Dto.UpdateSellerDto;
import com.adish.grahaksensemono.seller.service.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    private SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping("/create")
    public ResponseEntity<SellerResponseDto> createSeller(@RequestBody SellerRequestDto sellerRequestDto){
        SellerResponseDto seller = sellerService.createSeller(sellerRequestDto);
        if(seller != null){
            return new ResponseEntity<>(seller, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<SellerResponseDto> updateSeller(@RequestBody UpdateSellerDto updateSellerDto){
        SellerResponseDto sellerResponseDto = sellerService.updateSeller(updateSellerDto);
        if(sellerResponseDto != null){
            return new ResponseEntity<>(sellerResponseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<SellerResponseDto> getSellerByUserId(@PathVariable Long userId){
        SellerResponseDto sellerByUserId = sellerService.getSellerByUserId(userId);
        if(sellerByUserId != null){
            return new ResponseEntity<>(sellerByUserId, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /// admin API
    @GetMapping("/all")
    public ResponseEntity<List<SellerResponseDto>> getAllSellers(){
        List<SellerResponseDto> allSellers = sellerService.getAllSellers();
        return new ResponseEntity<>(allSellers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> deleteSeller(@RequestBody SellerDeleteRequestDto deleteRequestDto){
        boolean deleteSellerById = sellerService.deleteSellerById(deleteRequestDto);

        if(deleteSellerById){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
