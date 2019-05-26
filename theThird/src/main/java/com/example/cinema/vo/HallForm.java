package com.example.cinema.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class HallForm {
    /**
     * 影院名称
     */
    private String name;
    /**
     * 影院的列
     */
    private Integer column;

    /**
     * 影院的行
     */
    private Integer row;


}
