package com.example.cinema.blImpl.promotion.coupon;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.po.Coupon;
import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liying on 2019/4/17.
 */
@Service
public class CouponServiceImpl implements CouponService, CouponServiceForBl {

	@Autowired
	CouponMapper couponMapper;

	@Override
	public ResponseVO getCouponsByUser(int userId) {
		try {
			List<Coupon> couponList = couponMapper.selectCouponByUser(userId);
			if(couponList.size() == 0){
				return ResponseVO.buildFailure("无优惠券");
			}
			return ResponseVO.buildSuccess(couponList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("失败");
		}
	}

	@Override
	public ResponseVO addCoupon(CouponForm couponForm) {
		try {
			Coupon coupon = new Coupon();
			coupon.setName(couponForm.getName());
			coupon.setDescription(couponForm.getDescription());
			coupon.setTargetAmount(couponForm.getTargetAmount());
			coupon.setDiscountAmount(couponForm.getDiscountAmount());
			coupon.setStartTime(couponForm.getStartTime());
			coupon.setEndTime(couponForm.getEndTime());
			couponMapper.insertCoupon(coupon);
			return ResponseVO.buildSuccess(coupon);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("失败");
		}
	}

	@Override
	public ResponseVO issueCoupon(List<Integer> userId) {
		try {
			int couponId = couponMapper.selectSpecialCoupon().getId();
			for(Integer id: userId){
				couponMapper.insertCouponUser(couponId, id);
			}
			return ResponseVO.buildSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("发放优惠券失败");
		}
	}

	@Override
	public ResponseVO getSpecialCoupon() {
		try {
			Coupon specialCoupon = couponMapper.selectSpecialCoupon();
			double discount = specialCoupon.getDiscountAmount();
			return ResponseVO.buildSuccess(discount);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("获取无门槛优惠券失败");
		}
	}

	@Override
	public ResponseVO updateSpecialCouponDiscount(double discount) {
		try {
			couponMapper.updateSpecialCoupon(discount);
			return ResponseVO.buildSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("更改无门槛优惠券失败");
		}
	}


	@Override
	public Coupon getCouponById(int couponId) {
		return couponMapper.selectById(couponId);
	}

	@Override
	public List<Coupon> getCouponByUserAndAmount(int userId, double amount) {
		return couponMapper.selectCouponByUserAndAmount(userId, amount);
	}

	@Override
	public void insertCouponUser(int couponId, int userId) {
		couponMapper.insertCouponUser(couponId, userId);
	}

	@Override
	public void deleteCouponUser(int couponId, int userId) {
		couponMapper.deleteCouponUser(couponId, userId);
	}

}
