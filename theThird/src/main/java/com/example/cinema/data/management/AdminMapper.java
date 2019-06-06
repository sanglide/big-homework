package com.example.cinema.data.management;

import com.example.cinema.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fjj
 * @date 2019/4/11 3:46 PM
 */
@Mapper
public interface AdminMapper {
    /**
     * 查询所有售票员信息
     * @return
     */
    List<User> selectAllAdminAccount();

    /**
     * 增加售票员信息

     * @param username
     * @param username
     * @return
     */
    int addAdminAccount(@Param("username") String username, @Param("password") String password);

    /**
     * 修改售票员信息
     * @param id
     * @param username
     * @param password
     * @return
     */
    int changeAdminAccount(@Param("id") int id,@Param("username") String username, @Param("password") String password);

    /**
     * 删除售票员信息
     * @param id

     * @return
     */
    int deleteAdminAccount(@Param("id") int id);

}
