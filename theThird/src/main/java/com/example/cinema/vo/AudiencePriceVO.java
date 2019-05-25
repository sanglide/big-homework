package com.example.cinema.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @author fjj
 * @date 2019/4/21 3:00 PM
 */
@Setter
@Getter
public class AudiencePriceVO{
    private Date date;
    /**
     * 客单价
     */
    private Double price;


}
