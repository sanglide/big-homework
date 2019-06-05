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

    private int state;

    private double originCost;

    private double refund;

    private boolean canRefund;

    private List<Ticket> ticketList;

}
