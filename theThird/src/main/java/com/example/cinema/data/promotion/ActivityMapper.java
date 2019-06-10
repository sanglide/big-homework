package com.example.cinema.data.promotion;

import com.example.cinema.po.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by liying on 2019/4/20.
 */
@Mapper
public interface ActivityMapper {

    int insertActivity(Activity activity);

    int insertActivityAndMovie(@Param("activityId") int activityId,@Param("movieId") List<Integer> movieId);

    List<Activity> selectActivities();

    Activity selectActivityByCoupon(Integer couponId);

    List<Activity> selectActivitiesByMovie(int movieId);

    Activity selectById(int id);

    /**
     * 所有电影都有的无差别活动
     * @return
     */
    List<Activity> selectActivitiesWithoutMovie();

    //这里是更新之前activity里面的对应的coupon的内容
    int updateCoupon(@Param("name")String name,@Param("description") String description,@Param("targetAmount")  double targetAmount,@Param("discountAmount") double discountAmount,
                     @Param("startTime") Timestamp startTime,  @Param("endTime") Timestamp endTime);
    //这里是更新之前的activity里面的内容
    int updateActivity(@Param("id")int id,@Param("name")String name,@Param("description") String description,@Param("startTime") Timestamp startTime,  @Param("endTime") Timestamp endTime);


    //根据活动的名字返回
    Activity selectByName(@Param("name")String name);
    //根据activity的名字删除对应的activity_movie活动电影
    int deleteById(@Param("id")int id);

    //根据coupon的id删除对应的coupon
    int deleteCouponById(@Param("id")int id);

    //根据activity的id删除对应的activity
    int deleteActivityById(@Param("id")int id);


}
