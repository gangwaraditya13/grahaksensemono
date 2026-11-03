package com.adish.grahaksensemono.activity.entity;

import com.adish.grahaksensemono.activity.entity.Enums.Events;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
@Data
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long productId;

    @Enumerated(EnumType.STRING)
    private Events event;

    private LocalDateTime createdAt;
}
