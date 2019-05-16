package com.example.cinema.vo;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by liying on 2019/4/16.
 */
@Getter
@Setter
public class TicketVO {

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
	private int scheduleId;
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
	 * "未完成","已完成","已失效","未完成"
	 */
	private String state;

	private Timestamp time;

	public TicketVO() {
	}

}
