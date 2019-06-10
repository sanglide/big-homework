package com.example.cinema.data.sales;

import com.example.cinema.po.Ticket;
import com.example.cinema.po.TicketOrder;
import com.example.cinema.po.TicketRefund;
import com.example.cinema.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@Mapper
public interface TicketMapper {

    void insertTicket(Ticket ticket);

    int insertTickets(List<Ticket> tickets);

    void deleteTicket(int ticketId);

    void updateTicketState(@Param("ticketId") int ticketId, @Param("state") int state);

    List<Ticket> selectTicketsBySchedule(int scheduleId);

    Ticket selectTicketByScheduleIdAndSeat(@Param("scheduleId") int scheduleId, @Param("column") int columnIndex, @Param("row") int rowIndex);

    Ticket selectTicketById(int id);

    List<Ticket> selectTicketByUser(int userId);

    @Scheduled(cron = "0/1 * * * * ?")
    void cleanExpiredTicket();

    void insertTicketRefund(TicketRefund ticketRefund);

    void updateTicketRefund(Double rate, Integer limitHours);

    TicketRefund selectRefundInfo();

    void insertTicketOrder(TicketOrder ticketOrder);

    void updateTicketOrder(@Param("couponId") Integer couponId, @Param("ticketId") Integer ticketId);

    List<TicketOrder> selectTicketOrdersByUserId(int userId);

    TicketOrder selectTicketOrderById(Integer id);

}

