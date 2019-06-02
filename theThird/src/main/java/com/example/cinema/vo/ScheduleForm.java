package com.example.cinema.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author fjj
 * @date 2019/4/11 4:09 PM
 */
@Getter
@Setter
public class ScheduleForm {
    /**
     * id
     */
    private Integer id;
    /**
     * 影厅id
     */
    private Integer hallId;
    /**
     * 电影id
     */
    private Integer movieId;
    /**
     * 电影开始放映的时间
     */
    private Date startTime;
    /**
     * 电影结束放映的时间
     */
    private Date endTime;
    /**
     * 电影票价
     */
    private Double fare;
}
