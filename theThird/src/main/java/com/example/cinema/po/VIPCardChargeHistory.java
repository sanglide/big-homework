package com.example.cinema.po;

import com.example.cinema.vo.VIPCardChargeHistoryVO;

import java.security.Timestamp;

public class VIPCardChargeHistory {

    private int userId;

    private double charge;

    private double balance;

    private Timestamp date;

    public VIPCardChargeHistoryVO getVO(){
        VIPCardChargeHistoryVO vo = new VIPCardChargeHistoryVO();
        vo.setCharge(charge);
        vo.setBalance(balance);
        vo.setDate(date);
        return vo;
    }
}
