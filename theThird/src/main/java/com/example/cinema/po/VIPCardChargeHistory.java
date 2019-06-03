package com.example.cinema.po;

import com.example.cinema.vo.VIPCardChargeHistoryVO;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
public class VIPCardChargeHistory {

    private int id;

    private int userId;

    private double charge;

    private double balance;

    private Timestamp date;

    public VIPCardChargeHistoryVO getVO() {
        VIPCardChargeHistoryVO vo = new VIPCardChargeHistoryVO();
        vo.setCharge(charge);
        vo.setBalance(balance);
        vo.setDate(date);
        return vo;
    }
}
