package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.vo.ResponseVO;
//import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    /**
     * 通过用户id得到优惠券
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/get")
    public ResponseVO getCoupons(@PathVariable int userId){
        return couponService.getCouponsByUser(userId);
    }

    /**
     * 获取无门槛优惠券的优惠金额
     * @return
     */
    @GetMapping("/getNum")
    public ResponseVO getSpecialCoupon(){
        return couponService.getSpecialCoupon();
    }

    /**
     * 更改无门槛优惠券的优惠金额
     * @param discount
     * @return
     */
    @GetMapping("/change")
    public ResponseVO updateSpecialCouponDiscount(@RequestParam("discount") double discount){
        return couponService.updateSpecialCouponDiscount(discount);
    }


    /**
     * 为用户发放无门槛优惠券
     * @param userId
     * @return
     */
    @GetMapping("/send")
    public ResponseVO issueCoupon(@RequestParam List<Integer> userId){
        return couponService.issueCoupon(userId);
    }


}
