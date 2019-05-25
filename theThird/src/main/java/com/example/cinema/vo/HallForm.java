package com.example.cinema.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HallForm {
    /**
     * 影院名称
     */
    private String name;
    /**
     * 影院的行、列
     */
    private String row;

    private String column;

    public HallForm() {
    }
}
