package com.example.cinema.po;


import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

/**
 * Created by liying on 2019/3/23.
 */
@Setter
@Getter
public class DateLike {
    /**
     * 喜爱人数
     */
    private int likeNum;

    /**
     * 喜爱时间
     */
    private Date likeTime;

}
