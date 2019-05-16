package com.example.cinema.vo;

import com.example.cinema.po.MovieScheduleTime;

import lombok.Getter;
import lombok.Setter;

/**
 * @author fjj
 * @date 2019/4/16 2:48 PM
 */
@Setter
@Getter
public class MovieScheduleTimeVO {
	private Integer movieId;
	/**
	 * 排片次数
	 */
	private Integer time;
	private String name;

	public MovieScheduleTimeVO(MovieScheduleTime movieScheduleTime) {
		this.movieId = movieScheduleTime.getMovieId();
		this.time = movieScheduleTime.getTime();
		this.name = movieScheduleTime.getName();
	}

}
