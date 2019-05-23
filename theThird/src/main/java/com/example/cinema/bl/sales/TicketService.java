package com.example.cinema.bl.sales;

import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.TicketForm;
import com.example.cinema.vo.TicketsWithCouponForm;

import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
public interface TicketService {
    /**
     * TODO:锁座【增加票但状态为未付款】
     *
     * @param ticketForm
     * @return ResponseVO.buildSuccess(List<TicketVO>)
     */
    ResponseVO addTicket(TicketForm ticketForm);

    /**
     * TODO:完成购票【不使用会员卡】流程包括校验优惠券和根据优惠活动赠送优惠券
     *
     * @param id
     * @param couponId
     * @return 是否成功
     */
    ResponseVO completeTicket(TicketsWithCouponForm ticketsWithCouponForm);

    /**
     * TODO:完成购票【使用会员卡】流程包括会员卡扣费、校验优惠券和根据优惠活动赠送优惠券
     *
     * @param id
     * @param couponId
     * @return 是否成功
     */
    ResponseVO completeByVIPCard(TicketsWithCouponForm ticketsWithCouponForm);

    /**
     * TODO:取消锁座（只有状态是"锁定中"的可以取消）
     *
     * @param id TicketId列表
     * @return 是否成功
     */
    ResponseVO cancelTicket(List<Integer> id);
    
    /**
     * 获得该场次的被锁座位和场次信息
     *
     * @param scheduleId
     * @return 
     */
    ResponseVO getBySchedule(int scheduleId);
    
    /**
     * TODO:获得用户买过的票
     *
     * @param userId
     * @return List<TicketVO>
     */
    ResponseVO getTicketByUser(int userId);
}
