package com.adish.grahaksensemono.product.controller;

import com.adish.grahaksensemono.product.Dto.ProductRequestDto;
import com.adish.grahaksensemono.product.Dto.ProductResponseDto;
import com.adish.grahaksensemono.product.Dto.S3.UploadUrlRequestDto;
import com.adish.grahaksensemono.product.Dto.S3.UploadUrlResponseDto;
import com.adish.grahaksensemono.product.Dto.UpdateProductRequestDto;
import com.adish.grahaksensemono.product.service.Impl.S3FileService;
import com.adish.grahaksensemono.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    private S3FileService s3FileService;

    public ProductController(ProductService productService, S3FileService s3FileService) {
        this.productService = productService;
        this.s3FileService = s3FileService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto){

        ProductResponseDto product = productService.createProduct(productRequestDto);
        if(product != null){
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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


    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<ProductResponseDto>> getAllProductOfSeller(@PathVariable Long sellerId) {
        return ResponseEntity.ok(productService.getAllProductByUserId(sellerId));
    }

    @PutMapping("/update")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody UpdateProductRequestDto productRequestDto){
        ProductResponseDto productResponseDto = productService.updateProduct(productRequestDto);
        if(productResponseDto != null){
            return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteById(@PathVariable("productId") Long productId){
        boolean deleted = productService.deleteProductById(productId);
        if(deleted){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // product image uploaded

    // CREATE step 1: get a signed upload URL (used for new upload AND for replacing an image)
    @PostMapping("/upload-url")
    public ResponseEntity<UploadUrlResponseDto> uploadUrl(@Valid @RequestBody UploadUrlRequestDto request) {
        return ResponseEntity.ok(s3FileService.createUploadUrl(request));
    }

    // DELETE
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String key) {
        s3FileService.delete(key);
        return ResponseEntity.noContent().build();
    }

}
