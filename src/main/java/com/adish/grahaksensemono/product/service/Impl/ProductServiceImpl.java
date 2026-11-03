package com.adish.grahaksensemono.product.service.Impl;

import com.adish.grahaksensemono.product.Dto.ProductRequestDto;
import com.adish.grahaksensemono.product.Dto.ProductResponseDto;
import com.adish.grahaksensemono.product.Dto.UpdateProductRequestDto;
import com.adish.grahaksensemono.product.Mapper.ProductMapper;
import com.adish.grahaksensemono.product.entity.Product;
import com.adish.grahaksensemono.product.repository.ProductRepository;
import com.adish.grahaksensemono.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    private ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper){

        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {

        try{
            Product product = productMapper.productRequestToProduct(productRequestDto);
            Product save = productRepository.save(product);
            ProductResponseDto productResponseDto = productMapper.productToProductResponseDto(save);
            return productResponseDto;
        }catch (Exception e){
            throw new RuntimeException("product not created : "+ e.getMessage());
        }
    }

    @Override
    public ProductResponseDto updateProduct(UpdateProductRequestDto productRequestDto) {
        boolean update = false;
        if(productRepository.existsById(productRequestDto.getId())) {
            Product product = productRepository.findById(productRequestDto.getId()).orElseThrow();

            if(!productRequestDto.getProductName().trim().equals("")
                    && productRequestDto.getProductName().trim() != null
                    && !productRequestDto.getProductName().trim().equals(product.getProductName())){
                product.setProductName(productRequestDto.getProductName());
                update = true;
            }

            if(!productRequestDto.getProductDescription().trim().equals("")
                    && productRequestDto.getProductDescription().trim() != null
                    && !productRequestDto.getProductDescription().trim().equals(product.getProductDescription())){
                product.setProductDescription(productRequestDto.getProductDescription());
                update = true;
            }

            if(productRequestDto.getPrice().compareTo(BigDecimal.ZERO) != 0
                    && productRequestDto.getPrice().compareTo(BigDecimal.ZERO) < 0
                    && productRequestDto.getPrice().compareTo(product.getPrice()) != 0){
                product.setPrice(productRequestDto.getPrice());
                update = true;
            }

            if(productRequestDto.getDiscount().compareTo(BigDecimal.ZERO) != 0
                    && productRequestDto.getDiscount().compareTo(BigDecimal.ZERO) < 0
                    && productRequestDto.getDiscount().compareTo(product.getDiscount()) != 0){
                product.setDiscount(productRequestDto.getDiscount());
                update = true;
            }

            if(update){
                ProductResponseDto productResponseDto = productMapper.productToProductResponseDto(product);
                return productResponseDto;
            }

        }
        return null;
    }

    @Override
    public ProductResponseDto getProductById(Long productId) {

        if(productRepository.existsById(productId)){
            Product product = productRepository.findById(productId).orElseThrow();
            ProductResponseDto productResponseDto = productMapper.productToProductResponseDto(product);
            return productResponseDto;
        }
        return null;
    }

    @Override
    public List<ProductResponseDto> getAllProduct() {
        List<Product> productList = productRepository.findAll();
        List<ProductResponseDto> productResponseDtoList = productList
                .stream()
                .map(product -> productMapper.productToProductResponseDto(product))
                .collect(Collectors.toList());
        return productResponseDtoList;
    }

    @Override
    public boolean deleteProductById(Long productId) {
        if(productRepository.existsById(productId)){
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }
}
