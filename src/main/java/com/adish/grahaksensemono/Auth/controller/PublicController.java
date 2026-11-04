package com.adish.grahaksensemono.Auth.controller;

import com.adish.grahaksensemono.activity.Dto.ActivityRequestDto;
import com.adish.grahaksensemono.activity.Dto.ActivityResponseDto;
import com.adish.grahaksensemono.activity.service.ActivityService;
import com.adish.grahaksensemono.product.Dto.ProductResponseDto;
import com.adish.grahaksensemono.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    private ActivityService activityService;

    private ProductService productService;

    public PublicController(ActivityService activityService, ProductService productService) {
        this.activityService = activityService;
        this.productService = productService;
    }

    @PostMapping("/activity")
    public ResponseEntity<ActivityResponseDto> createActivity(@RequestBody ActivityRequestDto activityRequestDto){
        ActivityResponseDto activity = activityService.createActivity(activityRequestDto);
        if(activity!=null){
            return new ResponseEntity<>(activity, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("productId") Long productId){
        ProductResponseDto product = productService.getProductById(productId);
        if(product != null){
            return new ResponseEntity<>(product,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponseDto>> getAllProduct(){
        List<ProductResponseDto> productResponseDtoList = productService.getAllProduct();
        return new ResponseEntity<>(productResponseDtoList,HttpStatus.OK);
    }

    @GetMapping("/{sellerId}")
    private ResponseEntity<ProductResponseDto> getAllProductOfSeller(@PathVariable Long sellerId){
        ProductResponseDto productById = productService.getProductById(sellerId);
        return new ResponseEntity<>(productById, HttpStatus.OK);
    }

}
