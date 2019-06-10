package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liying on 2019/4/14.
 */
@RestController()
@RequestMapping("/vip")
public class VIPCardController {
    @Autowired
    VIPService vipService;

    /**
     * 用户购买会员卡
     * @param userId
     * @return
     */
    @PostMapping("/add")
    public ResponseVO addVIP(@RequestParam int userId) {
        return vipService.addVIPCard(userId);
    }

    /**
     * 获得用户的会员卡信息
     * @param userId
     * @return
     */
    @GetMapping("{userId}/get")
    public ResponseVO getVIP(@PathVariable int userId) {
        return vipService.getCardByUserId(userId);
    }

    /**
     * 为会员卡充值
     * @param vipCardForm
     * @return
     */
    @PostMapping("/charge")
    public ResponseVO charge(@RequestBody VIPCardForm vipCardForm) {
        return vipService.charge(vipCardForm);
    }

    /**
     * 获得当前会员卡策略
     * @return
     */
    @GetMapping("/getVIPInfo")
    public ResponseVO getVIPInfo() {
        return vipService.getVIPInfo();
    }

    /**
     * 更新会员卡策略
     * @param vipInfoVO
     * @return
     */
    @PostMapping("/update")
    public ResponseVO updateVIPInfo(@RequestBody VIPInfoVO vipInfoVO){
        return vipService.updateVIPInfo(vipInfoVO);
    }

    /**
     * 获得用户的充值记录
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/getChargeHistory")
    public ResponseVO getChargeHistoryByUserId(@PathVariable int userId){
        return vipService.getChargeHistoryByUserId(userId);
    }


}
