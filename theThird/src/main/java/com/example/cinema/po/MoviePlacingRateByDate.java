package com.example.cinema.po;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MoviePlacingRateByDate {
    private  Integer movieId;
    private String  name;
    //当天的放映次数
    private Integer scheduleTime;
    //当天的观影人数，也就是卖出去的票数
    private Integer ticketNumber;

}
