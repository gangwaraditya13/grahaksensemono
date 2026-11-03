package com.adish.grahaksensemono.activity.service;

import com.adish.grahaksensemono.activity.Dto.ActivityRequestDto;
import com.adish.grahaksensemono.activity.Dto.ActivityResponseDto;

import java.util.List;

public interface ActivityService {

    ActivityResponseDto createActivity(ActivityRequestDto activityRequestDto);

    List<ActivityResponseDto> getAllActivity();

}
