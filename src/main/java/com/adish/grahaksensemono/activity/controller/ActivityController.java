package com.adish.grahaksensemono.activity.controller;

import com.adish.grahaksensemono.activity.Dto.ActivityRequestDto;
import com.adish.grahaksensemono.activity.Dto.ActivityResponseDto;
import com.adish.grahaksensemono.activity.service.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    private ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/create")
    public ResponseEntity<ActivityResponseDto> createActivity(@RequestBody ActivityRequestDto activityRequestDto){
        ActivityResponseDto activity = activityService.createActivity(activityRequestDto);
        if(activity!=null){
        return new ResponseEntity<>(activity, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/activities")
    public ResponseEntity<List<ActivityResponseDto>> getAllActivity(){
        List<ActivityResponseDto> allActivity = activityService.getAllActivity();

        return new ResponseEntity<>(allActivity, HttpStatus.OK);
    }
}
