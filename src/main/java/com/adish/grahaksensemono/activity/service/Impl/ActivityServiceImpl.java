package com.adish.grahaksensemono.activity.service.Impl;

import com.adish.grahaksensemono.activity.Dto.ActivityRequestDto;
import com.adish.grahaksensemono.activity.Dto.ActivityResponseDto;
import com.adish.grahaksensemono.activity.Mapper.ActivityMapper;
import com.adish.grahaksensemono.activity.entity.Activity;
import com.adish.grahaksensemono.activity.repository.ActivityRepository;
import com.adish.grahaksensemono.activity.service.ActivityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {

    private ActivityRepository activityRepository;

    private ActivityMapper activityMapper;

    public ActivityServiceImpl(ActivityRepository activityRepository, ActivityMapper activityMapper){
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
    }

    @Override
    public ActivityResponseDto createActivity(ActivityRequestDto activityRequestDto) {
        try {
            Activity activity = activityMapper.activityRequestToActivity(activityRequestDto);
            Activity save = activityRepository.save(activity);
            ActivityResponseDto activityResponseDto = activityMapper.activityToActivityResponseDto(save);
            return activityResponseDto;
        } catch (Exception e) {
            throw new RuntimeException("Error in Activity create "+e.getMessage());
        }
    }

    @Override
    public List<ActivityResponseDto> getAllActivity() {

        List<Activity> activities = activityRepository.findAll();

        List<ActivityResponseDto> activityResponseDtoList = activities
                .stream()
                .map(activity -> activityMapper.activityToActivityResponseDto(activity))
                .collect(Collectors.toList());

        return activityResponseDtoList;
    }
}
