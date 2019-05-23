package com.example.cinema.blImpl.promotion.activity;

import java.util.List;

import com.example.cinema.po.Activity;

public interface ActivityServiceForBl {
	
	/**
	 * 通过movieId获取对应的Activities
	 * @param movieId
	 * @return
	 */
	List<Activity> getActivitiesByMovieId(Integer movieId);
}
