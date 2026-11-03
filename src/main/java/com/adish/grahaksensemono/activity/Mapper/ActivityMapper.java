package com.adish.grahaksensemono.activity.Mapper;

import com.adish.grahaksensemono.activity.Dto.ActivityRequestDto;
import com.adish.grahaksensemono.activity.Dto.ActivityResponseDto;
import com.adish.grahaksensemono.activity.entity.Activity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ActivityMapper {
    public static ActivityResponseDto activityToActivityResponseDto(Activity activity){
        ActivityResponseDto activityResponseDto = new ActivityResponseDto();

        activityResponseDto.setId(activity.getId());
        activityResponseDto.setEvent(activity.getEvent());
        activityResponseDto.setUserId(activity.getUserId());
        activityResponseDto.setProductId(activity.getProductId());
        activityResponseDto.setCreatedAt(activity.getCreatedAt());

        return activityResponseDto;
    }

    public static Activity activityRequestToActivity(ActivityRequestDto activityRequestDto){
        Activity activity = new Activity();
        activity.setUserId(activityRequestDto.getUserId());
        activity.setEvent(activityRequestDto.getEvent());
        activity.setProductId(activityRequestDto.getProductId());
        activity.setCreatedAt(LocalDateTime.now());

        return activity;
    }
}
