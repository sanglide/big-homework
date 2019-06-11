package com.example.cinema.vo;

import com.example.cinema.po.Ticket;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class TicketOrderVO {


    private int id;
    //创建订单时间
    private Timestamp time;
    //状态
    /**
     * 0：未付款
     * 1：已付款
     * 2：已失效
     * 3：已退款
     */
    private String state;
    //实付款
    private double originCost;
    //如果可退款的退款金额
    private double refund;
    //是否可退款
    private boolean canRefund;
    //订单中的票据
    private List<TicketVO> ticketVOList;

}
