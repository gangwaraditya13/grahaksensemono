package com.adish.grahaksensemono.product.service.Impl;

import com.adish.grahaksensemono.product.Dto.ProductRequestDto;
import com.adish.grahaksensemono.product.Dto.ProductResponseDto;
import com.adish.grahaksensemono.product.Dto.UpdateProductRequestDto;
import com.adish.grahaksensemono.product.Mapper.ProductMapper;
import com.adish.grahaksensemono.product.entity.ImageData;
import com.adish.grahaksensemono.product.entity.Product;
import com.adish.grahaksensemono.product.repository.ProductRepository;
import com.adish.grahaksensemono.product.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final int MAX_IMAGES = 5;

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final S3FileService s3FileService;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper,
                              S3FileService s3FileService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.s3FileService = s3FileService;
    }

    //CREATE
    @Transactional
    @Override
    public ProductResponseDto createProduct(ProductRequestDto dto) {
        Product product = productMapper.productRequestToProduct(dto);
        product.setCreatedAt(LocalDateTime.now());
        product.setImages(syncImages(dto.getImageKeys(), List.of()));
        return toResponse(productRepository.save(product));
    }

    //UPDATE
    @Transactional
    @Override
    public ProductResponseDto updateProduct(UpdateProductRequestDto dto) {
        Product product = productRepository.findById(dto.getId()).orElse(null);
        if (product == null) return null;

        boolean changed = false;

        if (dto.getProductName() != null && !dto.getProductName().isBlank()
                && !dto.getProductName().trim().equals(product.getProductName())) {
            product.setProductName(dto.getProductName().trim());
            changed = true;
        }

        if (dto.getProductDescription() != null && !dto.getProductDescription().isBlank()
                && !dto.getProductDescription().trim().equals(product.getProductDescription())) {
            product.setProductDescription(dto.getProductDescription().trim());
            changed = true;
        }

        if (dto.getPrice() != null && dto.getPrice().compareTo(BigDecimal.ZERO) > 0
                && dto.getPrice().compareTo(product.getPrice()) != 0) {
            product.setPrice(dto.getPrice());
            changed = true;
        }

        if (dto.getDiscount() != null && dto.getDiscount().compareTo(BigDecimal.ZERO) >= 0
                && (product.getDiscount() == null || dto.getDiscount().compareTo(product.getDiscount()) != 0)) {
            product.setDiscount(dto.getDiscount());
            changed = true;
        }

        List<String> removedKeys = List.of();
        if (dto.getImageKeys() != null) {
            List<ImageData> old = new ArrayList<>(product.getImages());
            List<ImageData> updated = syncImages(dto.getImageKeys(), old);

            Set<String> keep = updated.stream().map(ImageData::getImageKey).collect(Collectors.toSet());
            removedKeys = old.stream()
                    .map(ImageData::getImageKey)
                    .filter(k -> !keep.contains(k))
                    .toList();

            product.getImages().clear();
            product.getImages().addAll(updated);
            changed = true;
        }

        if (changed) {
            product.setUpdatedAt(LocalDateTime.now());
            productRepository.save(product);
            deleteFromS3AfterCommit(removedKeys);
        }
        return toResponse(product);
    }

    //READ
    @Transactional(readOnly = true)
    @Override
    public ProductResponseDto getProductById(Long productId) {
        return productRepository.findById(productId).map(this::toResponse).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponseDto> getAllProduct() {
        return productRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponseDto> getAllProductByUserId(Long sellerId) {
        return productRepository.findBySellerId(sellerId).stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    //DELETE
    @Transactional
    @Override
    public boolean deleteProductById(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return false;

        List<String> keys = product.getImages().stream().map(ImageData::getImageKey).toList();
        productRepository.delete(product);
        deleteFromS3AfterCommit(keys);
        return true;
    }

    //helpers

    /** Returns the final image list. Existing keys are kept; new keys are verified in S3. */
    private List<ImageData> syncImages(List<String> requestedKeys, List<ImageData> existing) {
        List<String> keys = requestedKeys == null ? List.of() : requestedKeys.stream().distinct().toList();
        if (keys.size() > MAX_IMAGES) {
            throw new IllegalArgumentException("Maximum " + MAX_IMAGES + " images per product");
        }

        Map<String, ImageData> existingByKey = existing.stream()
                .collect(Collectors.toMap(ImageData::getImageKey, i -> i, (a, b) -> a));

        List<ImageData> result = new ArrayList<>();
        for (String key : keys) {
            ImageData current = existingByKey.get(key);
            if (current != null) {
                result.add(current);
                continue;
            }
            if (productRepository.existsByImagesImageKey(key)) {
                throw new IllegalArgumentException("Image is already used by another product");
            }
            String type = s3FileService.confirmUpload(key);

            ImageData image = new ImageData();
            image.setImageKey(key);
            image.setImageType(type);
            image.setCreatedAt(LocalDateTime.now());
            result.add(image);
        }
        return result;
    }

    private ProductResponseDto toResponse(Product product) {
        ProductResponseDto dto = productMapper.productToProductResponseDto(product);
        dto.setImageUrls(product.getImages().stream()
                .map(i -> s3FileService.getUrl(i.getImageKey()))
                .toList());
        return dto;
    }

    /** Delete S3 files only after the DB change is committed, so a rollback never loses images. */
    private void deleteFromS3AfterCommit(List<String> keys) {
        if (keys.isEmpty()) return;
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                for (String key : keys) {
                    try {
                        s3FileService.delete(key);
                    } catch (Exception ignored) {
                        // leftover file is harmless; a lifecycle rule can clean it later
                    }
                }
            }
        });
    }
}