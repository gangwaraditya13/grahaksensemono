package com.adish.grahaksensemono.activity.Dto;

import com.adish.grahaksensemono.activity.entity.Enums.Events;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRequestDto {
    private Long userId;
    private Long productId;
    private Events event;
}
