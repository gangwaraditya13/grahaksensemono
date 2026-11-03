package com.adish.grahaksensemono.product.Mapper;

import com.adish.grahaksensemono.product.Dto.ProductRequestDto;
import com.adish.grahaksensemono.product.Dto.ProductResponseDto;
import com.adish.grahaksensemono.product.entity.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductMapper {

    public static Product productRequestToProduct(ProductRequestDto productRequestDto){
        Product product = new Product();

        product.setProductName(productRequestDto.getProductName().trim());
        product.setProductDescription(productRequestDto.getProductDescription().trim());
        product.setDiscount(productRequestDto.getDiscount());
        product.setPrice(productRequestDto.getPrice());
        product.setSellerId(productRequestDto.getSellerId());
        product.setCreatedAt(LocalDateTime.now());

        return product;

    }

    public static ProductResponseDto productToProductResponseDto(Product product){
        ProductResponseDto productResponseDto = new ProductResponseDto();

        productResponseDto.setId(product.getId());
        productResponseDto.setProductName(product.getProductName());
        productResponseDto.setProductDescription(product.getProductDescription());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setDiscount(product.getDiscount());
        productResponseDto.setImageUrl(product.getImageUrl());
        productResponseDto.setSellerId(product.getSellerId());
        productResponseDto.setAdminDiscount(product.getAdminDiscount());
        productResponseDto.setCreatedAt(product.getCreatedAt());
        productResponseDto.setUpdatedAt(product.getUpdatedAt());

        return productResponseDto;
    }

}
