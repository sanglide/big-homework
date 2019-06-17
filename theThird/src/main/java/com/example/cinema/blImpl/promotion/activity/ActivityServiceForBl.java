package com.example.cinema.blImpl.promotion.activity;

import com.example.cinema.po.Activity;

import java.util.List;

public interface ActivityServiceForBl {
	
	/**
	 * 通过movieId获取对应的Activities
	 * @param movieId
	 * @return
	 */
	List<Activity> getActivitiesByMovieId(Integer movieId);

	List<Activity> getActivitiesWithoutMovie();


	/**
	 * 通过CouponId获取对应的Activity
	 */
	Activity getActivityByCouponId(Integer couponId);
}
