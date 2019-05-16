package com.example.cinema.vo;

import com.example.cinema.po.ScheduleItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * Created by liying on 2019/4/16.
 */
@Getter
@Setter
@NoArgsConstructor
public class TicketWithScheduleVO {

	/**
	 * 电影票id
	 */
	private int id;
	/**
	 * 用户id
	 */
	private int userId;
	/**
	 * 排片id
	 */
	private ScheduleItem schedule;
	/**
	 * 列号
	 */
	private int columnIndex;
	/**
	 * 排号
	 */
	private int rowIndex;

	/**
	 * 订单状态
	 */
	private String state;

	private Timestamp time;

}
