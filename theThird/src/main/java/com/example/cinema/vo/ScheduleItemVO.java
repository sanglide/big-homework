package com.example.cinema.vo;

import com.example.cinema.po.ScheduleItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author fjj
 * @date 2019/4/28 5:43 PM
 */
@Getter
@Setter
@NoArgsConstructor
public class ScheduleItemVO {
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 影厅id
	 */
	private Integer hallId;
	/**
	 * 影厅名称
	 */
	private String hallName;
	/**
	 * 电影id
	 */
	private Integer movieId;
	/**
	 * 电影名
	 */
	private String movieName;
	/**
	 * 开始放映时间
	 */
	private Date startTime;
	/**
	 * 结束放映时间
	 */
	private Date endTime;
	/**
	 * 票价
	 */
	private double fare;

	public ScheduleItemVO(ScheduleItem scheduleItem) {
		this.id = scheduleItem.getId();
		this.hallId = scheduleItem.getHallId();
		this.hallName = scheduleItem.getHallName();
		this.movieId = scheduleItem.getMovieId();
		this.movieName = scheduleItem.getMovieName();
		this.startTime = scheduleItem.getStartTime();
		this.endTime = scheduleItem.getEndTime();
		this.fare = scheduleItem.getFare();
	}
}
