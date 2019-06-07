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

    @PostMapping("/buy")
    public ResponseVO buyTicket(@RequestBody TicketsWithCouponForm ticketsWithCouponForm) {
        return ticketService.completeTicket(ticketsWithCouponForm);
    }

    @PostMapping("/vip/buy")
    public ResponseVO buyTicketByVIPCard(@RequestBody TicketsWithCouponForm ticketsWithCouponForm) {
        return ticketService.completeByVIPCard(ticketsWithCouponForm);
    }

    @PostMapping("/lockSeat")
    public ResponseVO lockSeat(@RequestBody TicketForm ticketForm) {
        return ticketService.addTicket(ticketForm);
    }

    @GetMapping("/get/{userId}")
    public ResponseVO getTicketByUserId(@PathVariable int userId) {
        return ticketService.getTicketByUser(userId);
    }

    @GetMapping("/get/occupiedSeats")
    public ResponseVO getOccupiedSeats(@RequestParam int scheduleId) {
        return ticketService.getBySchedule(scheduleId);
    }

    @PostMapping("/cancel")
    public ResponseVO cancelTicket(@RequestParam List<Integer> ticketId) {
        return ticketService.cancelTicket(ticketId);
    }

    @PostMapping("/refund/add")
    public ResponseVO addRefund(@RequestBody TicketRefundVO ticketRefundVO){
        return ticketService.addRefund(ticketRefundVO);
    }

    @PostMapping("/refund/update")
    public ResponseVO updateRefund(@RequestBody TicketRefundVO ticketRefundVO){
        return ticketService.updateRefund(ticketRefundVO);
    }

    @GetMapping("/refund/getInfo")
    public ResponseVO getRefund(){
        return ticketService.getRefundInfo();
    }

    @GetMapping("/refund")
    public ResponseVO refundBySaleTime(@PathVariable Timestamp time){
        return ticketService.refundBySaleTime(time);
    }

    @GetMapping("/{userId}/getSaleHistory}")
    private ResponseVO getSaleHistory(@PathVariable int userId){
        return ticketService.getSaleHistory(userId);
    }

}
