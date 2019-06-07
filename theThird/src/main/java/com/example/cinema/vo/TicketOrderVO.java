package com.example.cinema.vo;

import com.example.cinema.po.Ticket;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class TicketOrderVO {

    /**
     * 买票的具体时间
     */
    private Timestamp time;
    /**
     * 0：未付款
     * 1：已付款
     * 2：已失效
     * 3：已退款
     */

    private int state;

    /**
     * 实付款
     */

    private double originCost;

    /**
     * 退票，用户收到的钱
     */

    private double refund;

    /**
     * 是否能退款
     */

    private boolean canRefund;

    private List<Ticket> ticketList;

}
