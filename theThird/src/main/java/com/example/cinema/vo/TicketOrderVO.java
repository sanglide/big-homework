package com.example.cinema.vo;

import com.example.cinema.po.Ticket;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class TicketOrderVO {

    private Timestamp time;
    //状态0未付款，1已付款，2已失效，3已退款
    private int state;

    //实付款
    private double originCost;

    //
    private double refund;


    private boolean canRefund;

    //ticketVO
    private List<Ticket> ticketList;

}
