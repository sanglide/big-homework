package com.example.cinema.controller.sales;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.TicketForm;
import com.example.cinema.vo.TicketRefundVO;
import com.example.cinema.vo.TicketsWithCouponForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;

    /**
     * 普通购票
     * @param ticketsWithCouponForm
     * @return
     */
    @PostMapping("/buy")
    public ResponseVO buyTicket(@RequestBody TicketsWithCouponForm ticketsWithCouponForm) {
        return ticketService.completeTicket(ticketsWithCouponForm);
    }

    /**
     * 使用会员卡购票
     * @param ticketsWithCouponForm
     * @return
     */
    @PostMapping("/vip/buy")
    public ResponseVO buyTicketByVIPCard(@RequestBody TicketsWithCouponForm ticketsWithCouponForm) {
        return ticketService.completeByVIPCard(ticketsWithCouponForm);
    }

    /**
     * 锁座
     * @param ticketForm
     * @return
     */
    @PostMapping("/lockSeat")
    public ResponseVO lockSeat(@RequestBody TicketForm ticketForm) {
        return ticketService.addTicket(ticketForm);
    }

    /**
     * 获取用户的订单
     * @param userId
     * @return
     */
    @GetMapping("/get/{userId}")
    public ResponseVO getTicketOrderByUserId(@PathVariable int userId) {
        return ticketService.getTicketByUser(userId);
    }

    /**
     * 获取历史订单
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/getSaleHistory")
    public ResponseVO getSaleHistory(@PathVariable("userId") int userId){
        return ticketService.getSaleHistory(userId);
    }

    /**
     * 获取排片的座位表
     * @param scheduleId
     * @return
     */
    @GetMapping("/get/occupiedSeats")
    public ResponseVO getOccupiedSeats(@RequestParam int scheduleId) {
        return ticketService.getBySchedule(scheduleId);
    }

    /**
     * 取消锁座
     * @param ticketId
     * @return
     */
    @PostMapping("/cancel")
    public ResponseVO cancelTicket(@RequestParam("ticketId") List<Integer> ticketId) {
        return ticketService.cancelTicket(ticketId);
    }

    /**
     * 添加退票策略
     * @param ticketRefundVO
     * @return
     */
    @PostMapping("/refund/add")
    public ResponseVO addRefund(@RequestBody TicketRefundVO ticketRefundVO){
        return ticketService.addRefund(ticketRefundVO);
    }

    /**
     * 更新退票策略
     * @param ticketRefundVO
     * @return
     */
    @PostMapping("/refund/update")
    public ResponseVO updateRefund(@RequestBody TicketRefundVO ticketRefundVO){
        return ticketService.updateRefund(ticketRefundVO);
    }

    /**
     * 获取退票策略
     * @return
     */
    @GetMapping("/refund/getInfo")
    public ResponseVO getRefund(){
        return ticketService.getRefundInfo();
    }

    /**
     * 退票
     * @param idList
     * @return
     */
    @GetMapping("/refund")
    public ResponseVO refundById(@RequestParam List<Integer> idList){
        return ticketService.refundById(idList);
    }

    /**
     * 筛选消费达到一定金额的用户
     */
    @GetMapping("/allMember")
    public ResponseVO getUserByConsume(@RequestParam("consume") double consume){
        return ticketService.getUserByConsume(consume);
    }
}
