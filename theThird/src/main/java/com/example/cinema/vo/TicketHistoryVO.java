package com.example.cinema.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class TicketHistoryVO {

    private int tickeId;

    private String movieName;

    private double account;

    private Timestamp date;

    /**
     * "已退款"或"已支付"
     */
    private String state;

    private String hallName;

    private int conlumnIndex;

    private int rowIndex;



}
