package com.example.cinema.blImpl.sales;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.schedule.MovieServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.blImpl.promotion.activity.ActivityServiceForBl;
import com.example.cinema.blImpl.promotion.coupon.CouponServiceForBl;
import com.example.cinema.blImpl.promotion.vipcard.VIPCardServiceForBl;
import com.example.cinema.data.sales.TicketMapper;

/**
 * Created by liying on 2019/4/16.
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    ScheduleServiceForBl scheduleService;
    @Autowired
    CouponServiceForBl couponService;
    @Autowired
    VIPCardServiceForBl vipCardService;
    @Autowired
    HallServiceForBl hallService;
    @Autowired
    MovieServiceForBl movieService;
    @Autowired
    ActivityServiceForBl activityService;

    @Override
    @Transactional
    public ResponseVO addTicket(TicketForm ticketForm) {

        try {
            Ticket ticket;

            List<Integer> ticketId = new ArrayList<>();
            List<TicketVO> ticketVOList = new ArrayList<>();
            for (SeatForm seatForm : ticketForm.getSeats()) {
                ticket = new Ticket();
                ticket.setUserId(ticketForm.getUserId());
                ticket.setScheduleId(ticketForm.getScheduleId());
                ticket.setState(0);
                ticket.setColumnIndex(seatForm.getColumnIndex());
                ticket.setRowIndex(seatForm.getRowIndex());
                ticket.setTime(new Timestamp(System.currentTimeMillis()));
                ticketMapper.insertTicket(ticket);
                ticket = ticketMapper.selectTicketByScheduleIdAndSeat(ticket.getScheduleId(),
                        ticket.getColumnIndex(), ticket.getRowIndex());
                ticketVOList.add(ticket.getVO());
                ticketId.add(ticket.getId());
            }

            TicketWithCouponVO ticketWithCouponVO = new TicketWithCouponVO();

            ScheduleItem scheduleItem = scheduleService.getScheduleItemById(ticketForm.getScheduleId());
            Movie movie = movieService.getMovieById(scheduleItem.getMovieId());
            // 该排片电影可用活动
            List<Activity> activityList = activityService.getActivitiesByMovieId(movie.getId());
            List<ActivityVO> activityVOList = new ArrayList<>();
            // 小于总价的可用优惠券
            List<Coupon> couponList = couponService.getCouponByUserAndAmount(ticketForm.getUserId(),
                    scheduleItem.getFare());
            // 所有电影票的总价
            double total = scheduleItem.getFare() * ticketVOList.size();
            for (Activity activity : activityList) {
                activityVOList.add(activity.getVO());
            }

            //创建订单并向数据库插入记录
            ticketMapper.insertTicketOrder(ticketId);

            ticketWithCouponVO.setTicketVOList(ticketVOList);
            ticketWithCouponVO.setActivities(activityVOList);
            ticketWithCouponVO.setCoupons(couponList);
            ticketWithCouponVO.setTotal(total);

            return ResponseVO.buildSuccess(ticketWithCouponVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("锁座失败");
        }
    }

    @Override
    @Transactional
    public ResponseVO completeTicket(TicketsWithCouponForm ticketsWithCouponForm) {
        try {
            List<Integer> ticketIdList = ticketsWithCouponForm.getTicketIdList();
            int couponId = ticketsWithCouponForm.getCouponId();

            Ticket ticket = ticketMapper.selectTicketById(ticketIdList.get(0));
            ScheduleItem scheduleItem = scheduleService.getScheduleItemById(ticket.getScheduleId());
            List<Activity> activityList = activityService.getActivitiesByMovieId(scheduleItem.getMovieId());
            int userId = ticket.getUserId();
            // 根据该电影可用活动为user赠送优惠券
            for (Activity activity : activityList) {
                couponService.insertCouponUser(activity.getCoupon().getId(), userId);
            }
            // 删除使用的优惠券
            couponService.deleteCouponUser(couponId, userId);

            // 票state改为"已购买"
            for (Integer ticketId : ticketIdList) {
                ticketMapper.updateTicketState(ticketId, 1);
            }

            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("购票失败");
        }
    }

    @Override
    @Transactional
    public ResponseVO completeByVIPCard(TicketsWithCouponForm ticketsWithCouponForm) {
        try {
            List<Integer> ticketIdList = ticketsWithCouponForm.getTicketIdList();
            int couponId = ticketsWithCouponForm.getCouponId();

            Coupon coupon = couponService.getCouponById(couponId);

            Ticket ticket = ticketMapper.selectTicketById(ticketIdList.get(0));

            ScheduleItem scheduleItem = scheduleService.getScheduleItemById(ticket.getScheduleId());
            List<Activity> activityList = activityService.getActivitiesByMovieId(scheduleItem.getMovieId());
            int userId = ticket.getUserId();
            // 根据该电影可用活动为user赠送优惠券
            for (Activity activity : activityList) {
                couponService.insertCouponUser(activity.getCoupon().getId(), userId);
            }
            // 删除使用的优惠券
            couponService.deleteCouponUser(couponId, userId);

            // 扣除VIPCard余额
            VIPCard vipCard = vipCardService.getVIPCardByUserId(ticket.getUserId());

            double amount = scheduleService.getScheduleItemById(ticket.getScheduleId()).getFare();

            double total = amount * ticketIdList.size();
            // 扣除后的余额
            double discount;
            if (coupon == null) {
                discount = 0;
            } else {
                discount = coupon.getDiscountAmount();
            }
            double balance = vipCard.getBalance() - total + discount;
            if (balance < 0) {
                return (ResponseVO.buildFailure("余额不足"));
            }
            vipCardService.updateVIPCardByIdAndBanlance(vipCard.getId(), balance);

            // 票state改为"已购买"
            for (Integer ticketId : ticketIdList) {
                ticketMapper.updateTicketState(ticketId, 1);
            }

            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            return ResponseVO.buildFailure("购票失败");
        }
    }

    @Override
    public ResponseVO getBySchedule(int scheduleId) {
        try {
            List<Ticket> tickets = ticketMapper.selectTicketsBySchedule(scheduleId);
            ScheduleItem schedule = scheduleService.getScheduleItemById(scheduleId);
            Hall hall = hallService.getHallById(schedule.getHallId());
            int[][] seats = new int[hall.getRow()][hall.getColumn()];
            tickets.stream().forEach(ticket -> {
                seats[ticket.getRowIndex()][ticket.getColumnIndex()] = 1;
            });
            ScheduleWithSeatVO scheduleWithSeatVO = new ScheduleWithSeatVO();
            scheduleWithSeatVO.setScheduleItem(schedule);
            scheduleWithSeatVO.setSeats(seats);
            return ResponseVO.buildSuccess(scheduleWithSeatVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTicketByUser(int userId) {
        try {
            List<Ticket> ticketList = ticketMapper.selectTicketBoughtByUser(userId);
            return ResponseVO.buildSuccess(ticketList2ticketVOList(ticketList));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获得电影票失败");
        }
    }

    @Override
    @Transactional
    public ResponseVO cancelTicket(List<Integer> ticketIdList) {
        try {
            for (Integer ticketId : ticketIdList) {
                ticketMapper.updateTicketState(ticketId, 2);
            }
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("取消失败");
        }
    }

    @Override
    public ResponseVO addRefund(TicketRefundVO ticketRefundVO) {
        try {
            TicketRefund ticketRefund = new TicketRefund();
            ticketRefund.setRate(ticketRefund.getRate());
            ticketRefund.setLimitHours(ticketRefund.getLimitHours());
            ticketMapper.insertTicketRefund(ticketRefund);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("添加退票策略失败");
        }
    }

    @Override
    public ResponseVO updateRefund(TicketRefundVO ticketRefundVO) {
        try {
            ticketMapper.updateTicketRefund(ticketRefundVO.getRate(), ticketRefundVO.getLimitHours());
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("修改退票策略失败");
        }
    }

    @Override
    public ResponseVO getRefundInfo() {
        try {
            return ResponseVO.buildSuccess(ticketMapper.selectRefundInfo().getVO());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获取退票策略失败");
        }
    }

    @Override
    public ResponseVO refundByTicketId(int id) {
        try {
            Ticket ticket = ticketMapper.selectTicketById(id);
            TicketRefund ticketRefund = ticketMapper.selectRefundInfo();


            ticketMapper.updateTicketState(id, 3);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("退票失败");
        }
    }

    private Object ticketList2ticketVOList(List<Ticket> ticketList) {
        List<TicketVO> ticketVOList = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            ticketVOList.add(ticket.getVO());
        }
        return ticketVOList;
    }


}
