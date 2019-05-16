package com.example.cinema.vo;
import com.example.cinema.po.PopularMovies;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PopularMoviesVO {
    private Integer movieId;
    private String name;
    private Integer boxOffice;


    public PopularMoviesVO(PopularMovies popularMovies){
        this.movieId=popularMovies.getMovieId();
        this.name=popularMovies.getName();
        this.boxOffice=popularMovies.getBoxOffice();
    }

}
