package com.example.cinema.data.statistics;

import com.example.cinema.po.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/16 1:43 PM
 */
@Mapper
public interface StatisticsMapper {
    /**
     * 查询date日期每部电影的排片次数
     * @param date
     * @return
     */
    List<MovieScheduleTime> selectMovieScheduleTimes(@Param("date") Date date, @Param("nextDate") Date nextDate);

    /**
     * 查询所有电影的总票房（包括已经下架的，降序排列）
     * @return
     */
    List<MovieTotalBoxOffice> selectMovieTotalBoxOffice();

    /**
     * 查询某天每个客户的购票金额
     * @param date
     * @param nextDate
     * @return
     */
    List<AudiencePrice> selectAudiencePrice(@Param("date") Date date, @Param("nextDate") Date nextDate);

    /**
     * 返回一个list里面是hall的一些东西
     */
    List<Hall> searchHall();
    /**
     * 查询某一天所有电影的上座率（这里返回的其实是那一天所有电影的购票人数和该电影的放映场次，也就是排了几场）
     * @param date
     * @param nextDate
     * @return
     */
    List<MoviePlacingRateByDate> getMoviePlacingRateByDate(@Param("date")Date date, @Param("nextDate") Date nextDate);

    List<PopularMovies> selectPopularMovie(@Param("startDate")Date startDate, @Param("endDate")Date endDate);
}
