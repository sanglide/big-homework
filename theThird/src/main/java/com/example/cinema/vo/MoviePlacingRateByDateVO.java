package com.example.cinema.vo;

import com.example.cinema.po.MoviePlacingRateByDate;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MoviePlacingRateByDateVO {
    private Integer movieId;
    //电影的名字
    private String name;
    //电影在那一天的上座率
    private double placingRate;

    /**public MoviePlacingRateByDateVO(MoviePlacingRateByDate moviePlacingRateByDate ){
        this.movieId=moviePlacingRateByDate.getMovieId();
        this.name=moviePlacingRateByDate.getName();
        this.placingRate=moviePlacingRateByDate.getPlacingRate();
    }**/



}
