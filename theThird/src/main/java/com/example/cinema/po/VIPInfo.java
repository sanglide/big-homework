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

    private String description;

    private double price;

    public VIPInfoVO getVO(){
        VIPInfoVO vo = new VIPInfoVO();
        vo.setDescription(description);
        vo.setPrice(price);
        return vo;
    }
}
