package com.example.cinema.po;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * Created by liying on 2019/4/14.
 */
@Getter
@Setter
@NoArgsConstructor
public class VIPCard {

    /**
     * 用户id
     */
    private int userId;

    /**
     * 会员卡id
     */
    private int id;

    /**
     * 会员卡余额
     */
    private double balance;

    /**
     * 办卡日期
     */
    private Timestamp joinDate;

    public double calculate(double amount) {
        return (int) (amount / 200) * 30 + amount;
    }

}
