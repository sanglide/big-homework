package com.example.cinema.blImpl.promotion.activity;

import com.example.cinema.bl.promotion.ActivityService;
import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.data.promotion.ActivityMapper;
import com.example.cinema.po.Activity;
import com.example.cinema.po.Coupon;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liying on 2019/4/20.
 */
@Service
public class ActivityServiceImpl implements ActivityService, ActivityServiceForBl {

    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    CouponService couponService;

    @Override
    @Transactional
    public ResponseVO publishActivity(ActivityForm activityForm) {
        try {
            Coupon coupon = (Coupon) couponService.addCoupon(activityForm.getCouponForm()).getContent();
            Activity activity = new Activity();
            activity.setName(activityForm.getName());
            activity.setDescription(activityForm.getDescription());
            activity.setStartTime(activityForm.getStartTime());
            activity.setEndTime(activityForm.getEndTime());
            activity.setCoupon(coupon);
            activityMapper.insertActivity(activity);
            if (activityForm.getMovieList() != null && activityForm.getMovieList().size() != 0) {
                activityMapper.insertActivityAndMovie(activity.getId(), activityForm.getMovieList());
            }
            return ResponseVO.buildSuccess(activityMapper.selectById(activity.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO updateActivity(ActivityUpdateVO activityUpdateVO) {
        try {
            CouponForm cou = activityUpdateVO.getCouponForm();

            activityMapper.updateCoupon(cou.getName(), cou.getDescription(), cou.getTargetAmount(), cou.getDiscountAmount(), cou.getStartTime(), cou.getEndTime());
            activityMapper.updateActivity(activityUpdateVO.getId(), activityUpdateVO.getName(), activityUpdateVO.getDescription(), activityUpdateVO.getStartTime(), activityUpdateVO.getEndTime());
            activityMapper.deleteById(activityUpdateVO.getId());
            if (activityUpdateVO.getMovieList() != null && activityUpdateVO.getMovieList().size() != 0) {
                activityMapper.insertActivityAndMovie(activityUpdateVO.getId(), activityUpdateVO.getMovieList());
            }

            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO deleteActivity(ActivityBatchDeleteForm activityBatchDeleteForm) {
        try {
            List<Integer> de = activityBatchDeleteForm.getActivityIdList();
            for (int i = 0; i < de.size(); i++) {
                int id = de.get(i);
                Activity acti = activityMapper.selectById(id);
                activityMapper.deleteById(id);
                activityMapper.deleteActivityById(id);
                activityMapper.deleteCouponById(acti.getCoupon().getId());

            }

            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getActivities() {
        try {
            return ResponseVO.buildSuccess(activityMapper.selectActivities().stream().map(Activity::getVO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public List<Activity> getActivitiesByMovieId(Integer movieId) {
        return activityMapper.selectActivitiesByMovie(movieId);
    }

    @Override
    public List<Activity> getActivitiesWithoutMovie() {
        return activityMapper.selectActivitiesWithoutMovie();
    }

    @Override
    public Activity getActivityByCouponId(Integer couponId) {
        return activityMapper.selectActivityByCoupon(couponId);
    }


}
