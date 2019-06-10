package com.example.cinema.vo;

import java.util.*;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefundVO {
    /**
     * 退票时订单的时间戳
     */
    private List<Timestamp> times;


}
