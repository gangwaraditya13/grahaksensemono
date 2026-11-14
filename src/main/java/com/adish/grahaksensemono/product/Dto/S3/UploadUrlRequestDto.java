package com.adish.grahaksensemono.product.Dto.S3;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UploadUrlRequestDto {
    @NotBlank
    private String contentType;       // image/jpeg | image/png | image/webp

    @Min(1) @Max(2 * 1024 * 1024)
    private long size;                // bytes
}
