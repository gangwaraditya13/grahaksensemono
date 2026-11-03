package com.adish.grahaksensemono.activity.Dto;

import com.adish.grahaksensemono.activity.entity.Enums.Events;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityResponseDto {
    private Long id;
    private Long userId;
    private Long productId;
    private Events event;
    private LocalDateTime createdAt;
}
