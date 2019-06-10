package com.example.cinema.controller.router;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author deng
 * @date 2019/03/11
 */
@Controller
public class ViewController {
    @RequestMapping(value = "/index")
    public String getIndex() {
        return "index";
    }

    @RequestMapping(value = "/signUp")
    public String getSignUp() {
        return "signUp";
    }

    @RequestMapping(value = "/admin/movie/manage")
    public String getAdminMovieManage() {
        return "adminMovieManage";
    }

    @RequestMapping(value = "/admin/session/manage")
    public String getAdminSessionManage() {
        return "adminScheduleManage";
    }

    @RequestMapping(value = "/admin/cinema/manage")
    public String getAdminCinemaManage() {
        return "adminCinemaManage";
    }

    @RequestMapping(value = "/admin/promotion/manage")
    public String getAdminPromotionManage() {
        return "adminPromotionManage";
    }

    @RequestMapping(value = "/admin/coupon/manage")
    public String getAdminCouponManage() {
        return "adminCoupons";
    }

    ////////////////////////////////////
    //转到管理会员卡界面
    @RequestMapping(value = "/admin/member/manage")
    public String getAdminMemberManage() {
        return "adminMemberManage";
    }
    //转到管理退票策略界面

    @RequestMapping(value = "/admin/ticket/manage")
    public String getAdminTicketManage() {
        return "adminTicketManage";
    }

    //转到管理账户界面
    @RequestMapping(value = "/admin/account/manage")
    public String getAdminAccountManage() {
        return "adminAccountManage";
    }

    //转到售票员界面
    @RequestMapping(value = "/seller/movie")
    public String getSeller(){return "sellerMovie";}
    //售票员
    @RequestMapping(value = "/seller/movieDetail")
    public String getSellerMovieDetail(@RequestParam int id) {return "sellerMovieDetail";}
    //售票员
    @RequestMapping(value = "/seller/movieDetail/buy")
    public String getSellerMovieBuy(@RequestParam int id) {
        return "sellerSale";
    }

    @RequestMapping(value = "/admin/cinema/statistic")
    public String getAdminCinemaStatistic() {
        return "adminCinemaStatistic";
    }

    @RequestMapping(value = "/admin/movieDetail")
    public String getAdminMovieDetail(@RequestParam int id) { return "adminMovieDetail"; }

    @RequestMapping(value = "/user/home")
    public String getUserHome() {
        return "userHome";
    }

    @RequestMapping(value = "/user/buy")
    public String getUserBuy() {
        return "userBuy";
    }

    @RequestMapping(value = "/user/buyHistory")
    public String getUserBuyHistory() {
        return "userBuyHistory";
    }

    @RequestMapping(value = "/user/chargeHistory")
    public String getUserChargeHistory() {
        return "userChargeHistory";
    }

    @RequestMapping(value = "/user/buyHistoryDetail")
    public String getUserBuyHistoryDetail(@RequestParam int id) {
        return "userBuyHistoryDetail";
    }

    @RequestMapping(value = "/user/movieDetail")
    public String getUserMovieDetail(@RequestParam int id) {
        return "userMovieDetail";
    }

    @RequestMapping(value = "/user/movieDetail/buy")
    public String getUserMovieBuy(@RequestParam int id) {
        return "userMovieBuy";
    }

    @RequestMapping(value = "/user/movie")
    public String getUserMovie() {
        return "userMovie";
    }

    @RequestMapping(value = "/user/member")
    public String getUserMember() {
        return "userMember";
    }
}
