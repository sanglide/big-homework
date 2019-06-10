package com.example.cinema.po;

import com.example.cinema.vo.TicketOrderVO;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter@Setter
public class TicketOrder {

    private int orderId;

    private int couponId;

    private List<Ticket> ticketList;

}
