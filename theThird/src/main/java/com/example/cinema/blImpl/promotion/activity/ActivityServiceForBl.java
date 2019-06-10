package com.example.cinema.blImpl.promotion.activity;

import java.util.List;

import com.example.cinema.po.Activity;
import com.example.cinema.po.Coupon;

public interface ActivityServiceForBl {
	
	/**
	 * 通过movieId获取对应的Activities
	 * @param movieId
	 * @return
	 */
	List<Activity> getActivitiesByMovieId(Integer movieId);


	/**
	 * 通过CouponId获取对应的Activity
	 */
	Activity getActivityByCouponId(Integer couponId);
}
