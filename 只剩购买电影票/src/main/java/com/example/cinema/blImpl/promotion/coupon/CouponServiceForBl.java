package com.example.cinema.blImpl.promotion.coupon;

import java.util.List;

import com.example.cinema.po.Coupon;

public interface CouponServiceForBl {

	Coupon getCouponByUserAndAmountAndActivity(int userId, double amount, int activityId);

	Coupon getCouponById(int couponId);
}
