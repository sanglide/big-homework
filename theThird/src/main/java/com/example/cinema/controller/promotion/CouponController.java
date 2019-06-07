package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.vo.ResponseVO;
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

    @PostMapping


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
    @PostMapping("/change")
    public ResponseVO updateSpecialCouponDiscount(@PathVariable double discount){
        return couponService.updateSpecialCouponDiscount(discount);
    }

    /**
     *
     * @param consume
     * @return
     */

    @GetMapping("/allMember")
    public ResponseVO getAllUserByConsume(@PathVariable double consume){
        return couponService.getAllUserByConsume(consume);
    }

    /**
     * 为用户方法无门槛优惠券
     * @param userId
     * @return
     */
    @PostMapping("/send")
    public ResponseVO issueCoupon(@RequestParam List<Integer> userId){
        return couponService.issueCoupon(userId);
    }


}
