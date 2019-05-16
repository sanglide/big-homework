package com.example.cinema.vo;

import java.util.List;

import com.example.cinema.po.Coupon;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by liying on 2019/4/21.
 */
@Getter
@Setter
public class TicketWithCouponVO {
	
	//需要结算ticketVO
    private List<TicketVO> ticketVOList;
    //有票据计算出的总价
    private double total;
    //可使用的优惠券
    private List<Coupon> coupons;
    //优惠券对应的活动
    private List<ActivityVO> activities;
}
