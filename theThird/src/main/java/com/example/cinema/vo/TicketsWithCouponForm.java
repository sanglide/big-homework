package com.example.cinema.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketsWithCouponForm {
	
	private List<Integer> ticketIdList;

	private int couponId;
}
