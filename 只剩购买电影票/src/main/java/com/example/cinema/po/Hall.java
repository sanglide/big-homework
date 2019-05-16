package com.example.cinema.po;

import lombok.Getter;
import lombok.Setter;

/**
 * @author fjj
 * @date 2019/4/28 5:09 PM
 */
@Getter
@Setter
public class Hall {
	private Integer id;
	private String name;
	private Integer row;
	private Integer column;
}
