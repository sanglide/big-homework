package com.example.cinema.po;

import com.example.cinema.vo.TicketRefundVO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TicketRefund {

    /**
     * id
     */
    private int id;

    /**
     * 收取的手续费占实付款的比例
     */
    private Double rate;

    /**
     * 规定在电影放映多少小时之前可以退票
     */
    private Integer limitHours;

    public TicketRefundVO getVO(){
        TicketRefundVO vo = new TicketRefundVO();
        vo.setRate(rate);
        vo.setLimitHours(limitHours);
        return vo;
    }
}
