package com.adish.grahaksensemono.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDateTime;

@Embeddable
@Data
public class ImageData {
    @Column(nullable = false)
    private String imageKey;      // e.g. products/uuid.png

    private String imageType;     // image/png

    private LocalDateTime createdAt;
}
