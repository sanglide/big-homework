package com.example.cinema.vo;

import com.example.cinema.po.ScheduleItem;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by liying on 2019/4/21.
 */
@Getter
@Setter
public class ScheduleWithSeatVO {
	/**
	 * 排片
	 */
	private ScheduleItem scheduleItem;
	/**
	 * 座位
	 */
	private int[][] seats;
}
