package com.example.cinema.po;

import com.example.cinema.vo.VIPInfoVO;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jlz on 2019/5/29
 */
@Getter
@Setter
public class VIPInfo {

    private double price;

    private double charge;

    private double bonus;

    public VIPInfoVO getVO(){
        VIPInfoVO vo = new VIPInfoVO();
        vo.setCharge(charge);
        vo.setBonus(bonus);
        vo.setPrice(price);
        return vo;
    }
}
