package com.adish.grahaksensemono.product.Dto.S3;

public record UploadUrlResponseDto(String uploadUrl, String key, String imageUrl, int expiresInSeconds) {}