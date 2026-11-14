package com.adish.grahaksensemono.product.Mapper;

import com.adish.grahaksensemono.product.Dto.ProductRequestDto;
import com.adish.grahaksensemono.product.Dto.ProductResponseDto;
import com.adish.grahaksensemono.product.entity.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class ProductMapper {

    public Product productRequestToProduct(ProductRequestDto dto) {
        Product product = new Product();

        product.setProductName(dto.getProductName().trim());
        product.setProductDescription(
                dto.getProductDescription() == null ? null : dto.getProductDescription().trim());
        product.setDiscount(dto.getDiscount());
        product.setPrice(dto.getPrice());
        product.setSellerId(dto.getSellerId());
        product.setCreatedAt(LocalDateTime.now());
        // images are NOT mapped here: the service verifies the keys in S3 and sets them

        return product;
    }

    public ProductResponseDto productToProductResponseDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();

        dto.setId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setProductDescription(product.getProductDescription());
        dto.setPrice(product.getPrice());
        dto.setDiscount(product.getDiscount());
        dto.setAdminDiscount(product.getAdminDiscount());
        dto.setSellerId(product.getSellerId());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        dto.setImageUrls(new ArrayList<>());   // the service fills this with CloudFront URLs

        return dto;
    }
}