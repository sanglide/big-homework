package com.example.cinema.vo;

import com.example.cinema.po.Ticket;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class TicketOrderVO {

    private int state;

    private Timestamp time;

    private List<Ticket> ticketList;

}
