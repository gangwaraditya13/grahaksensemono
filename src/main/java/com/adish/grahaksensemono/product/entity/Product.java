package com.adish.grahaksensemono.product.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long sellerId;

    @Column(nullable = false)
    private String productName;

    @Column(columnDefinition = "TEXT")
    private String productDescription;
    @Column(nullable = false)
    private BigDecimal price;

    private BigDecimal discount;

    private BigDecimal adminDiscount;

    @ElementCollection
    private Set<String> imageUrl = new HashSet<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
