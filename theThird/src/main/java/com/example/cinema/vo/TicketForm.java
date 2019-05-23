package com.example.cinema.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by liying on 2019/4/16.
 */
@Getter
@Setter
public class TicketForm {

	/**
	 * 用户id
	 */
	private int userId;
	/**
	 * 排片id
	 */
	private int scheduleId;

	private List<SeatForm> seats;

	public TicketForm() {
	}
	
}
