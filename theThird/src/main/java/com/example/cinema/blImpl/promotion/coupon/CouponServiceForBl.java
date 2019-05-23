package com.example.cinema.blImpl.promotion.coupon;

import java.util.List;

import com.example.cinema.po.Coupon;

public interface CouponServiceForBl {

	List<Coupon> getCouponByUserAndAmount(int userId, double amount);

	Coupon getCouponById(int couponId);

	void insertCouponUser(int couponId, int userId);

	void deleteCouponUser(int couponId, int userId);
}
