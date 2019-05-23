package com.example.cinema.po;

import lombok.Getter;
import lombok.Setter;

/**
 * @author fjj
 * @date 2019/4/28 6:09 PM
 */
@Getter
@Setter
public class MovieScheduleTime {
	private Integer movieId;
	/**
	 * 排片次数
	 */
	private Integer time;
	private String name;

}
