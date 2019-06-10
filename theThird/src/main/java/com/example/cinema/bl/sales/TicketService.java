package com.example.cinema.bl.sales;

import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.TicketForm;
import com.example.cinema.vo.TicketRefundVO;
import com.example.cinema.vo.TicketsWithCouponForm;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
public interface TicketService {
    /**
     *
     *
     * @param ticketForm
     * @return ResponseVO.buildSuccess(List<TicketVO>)
     */
    ResponseVO addTicket(TicketForm ticketForm);

    /**
     *
     * @return 是否成功
     */
    ResponseVO completeTicket(TicketsWithCouponForm ticketsWithCouponForm);

    /**
     *
     * @return 是否成功
     */
    ResponseVO completeByVIPCard(TicketsWithCouponForm ticketsWithCouponForm);

    /**
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
     *
     * @param userId
     * @return List<TicketVO>
     */
    ResponseVO getTicketByUser(int userId);

    ResponseVO addRefund(TicketRefundVO ticketRefundVO);

    ResponseVO updateRefund(TicketRefundVO ticketRefundVO);

    ResponseVO getRefundInfo();

    ResponseVO getSaleHistory(int userId);

    ResponseVO refundById(List<Integer> idList);

    ResponseVO getUserByConsume(double consume);
}
