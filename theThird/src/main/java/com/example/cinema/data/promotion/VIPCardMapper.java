package com.example.cinema.data.promotion;

import com.example.cinema.po.VIPCard;
import com.example.cinema.po.VIPCardChargeHistory;
import com.example.cinema.po.VIPInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liying on 2019/4/14.
 */
@Mapper
public interface VIPCardMapper {

	/**
	 * 往数据库插入一条VIPCard数据
	 * @param vipCard
	 * @return 
	 */
    int insertOneCard(VIPCard vipCard);

    /**
     * 根据id(key)返回一个VIPCard
     * @param id VIPCardId
     * @return VIPCard对象
     */
    VIPCard selectCardById(int id);

    void updateCardBalance(@Param("id") int id,@Param("balance") double balance);

    VIPCard selectCardByUserId(int userId);

    VIPInfo selectVIPInfo();

    void updateVIPInfo(@Param("price") double price,@Param("charge") double charge, @Param("bonus") double bonus);

    void insertChargeHistory(VIPCardChargeHistory history);

    List<VIPCardChargeHistory> selectChargeHistoryByUserId(int userId);
}
