package com.example.cinema.data.management;

import com.example.cinema.po.Hall;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/11 3:46 PM
 */
@Mapper
public interface HallMapper {
    /**
     * 查询所有影厅信息
     * @return
     */
    List<Hall> selectAllHall();

    /**
     * 根据id查询影厅
     * @return
     */
    Hall selectHallById(@Param("hallId") int hallId);

    /**
     * 根据名字查询影厅
     * @return
     */
    Hall selectHallByName(@Param("hallName") String hallName);

    Hall selectHallHasSchedule(@Param("hallName") String hallName, @Param("today") Date today);

    /**
     * 增加影厅
     * @param name
     * @param column
     * @param row
     * @return
     */
    int addHall(@Param("hall_name") String name,@Param("hall_column") Integer column, @Param("hall_row") Integer row);

    /**
     * 修改影厅
     * @param name
     * @param column
     * @param row
     * @return
     */
    int changeHall(@Param("hall_name") String name,@Param("hall_column") Integer column, @Param("hall_row") Integer row);


    /**
     * 删除影厅
     * @param name

     * @return
     */
    int deleteHall(@Param("hall_name") String name);
}
