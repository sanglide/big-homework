package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.ActivityService;
import com.example.cinema.vo.ActivityForm;
import com.example.cinema.vo.ActivityUpdateVO;
import com.example.cinema.vo.ActivityBatchDeleteForm;
import com.example.cinema.vo.ActivityVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Created by liying on 2019/4/20.
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @PostMapping("/publish")
    public ResponseVO publishActivity(@RequestBody ActivityForm activityForm){
        return activityService.publishActivity(activityForm);
    }
    @GetMapping("/get")
    public ResponseVO getActivities(){
        return activityService.getActivities();
    }

    @PostMapping( "/update")
    public ResponseVO updateActivity(@RequestBody ActivityUpdateVO activityUpdateVO){
        return activityService.updateActivity(activityUpdateVO);
    }

    @RequestMapping(value = "/delete/batch", method = RequestMethod.DELETE)
    public ResponseVO deleteActivity(@RequestBody ActivityBatchDeleteForm activityBatchDeleteForm){
        return activityService.deleteActivity(activityBatchDeleteForm);
    }



}
