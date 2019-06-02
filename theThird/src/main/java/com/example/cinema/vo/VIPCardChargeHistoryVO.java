package com.example.cinema.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class VIPCardChargeHistoryVO {

    private double balance;

    private double charge;

    private Timestamp date;

}
