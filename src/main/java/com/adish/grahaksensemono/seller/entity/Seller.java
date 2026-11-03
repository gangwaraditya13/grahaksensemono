package com.adish.grahaksensemono.seller.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "sellers")
@Data
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String businessAddress;

    private String businessPhone;

    private boolean status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}