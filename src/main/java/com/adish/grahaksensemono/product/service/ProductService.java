package com.adish.grahaksensemono.product.service;

import com.adish.grahaksensemono.product.Dto.ProductRequestDto;
import com.adish.grahaksensemono.product.Dto.ProductResponseDto;
import com.adish.grahaksensemono.product.Dto.UpdateProductRequestDto;

import java.util.List;

public interface ProductService {


    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    ProductResponseDto updateProduct(UpdateProductRequestDto productRequestDto);

    ProductResponseDto getProductById(Long productId);

    List<ProductResponseDto> getAllProduct();

    List<ProductResponseDto> getAllProductByUserId(Long sellerId);

    //todo: add image url by adding image server service

    //todo: add pagination letter

    boolean deleteProductById(Long productId);
}
