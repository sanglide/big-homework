package com.example.cinema.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class VIPCardVO {

    /**
     * 会员卡优惠描述
     */
    private String discription;

    /**
     * 会员卡购买价格
     */
    private double price;

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
