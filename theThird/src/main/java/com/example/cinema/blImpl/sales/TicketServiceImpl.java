package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.blImpl.promotion.activity.ActivityServiceForBl;
import com.example.cinema.blImpl.promotion.coupon.CouponServiceForBl;
import com.example.cinema.blImpl.promotion.vipcard.VIPCardServiceForBl;
import com.example.cinema.blImpl.user.AccountServiceForBl;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    ActivityServiceForBl activityService;
    @Autowired
    AccountServiceForBl accountService;

    @Override
    @Transactional
    public ResponseVO addTicket(TicketForm ticketForm) {

        try {
            //通过ticketForm获得List<TicketVO>
            List<TicketVO> ticketVOList = new ArrayList<>();
            Ticket ticket;
            List<Ticket> ticketList = new ArrayList<>();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            for (SeatForm seatForm : ticketForm.getSeats()) {
                ticket = new Ticket();
                ticket.setUserId(ticketForm.getUserId());
                ticket.setScheduleId(ticketForm.getScheduleId());
                ticket.setState(0);
                ticket.setColumnIndex(seatForm.getColumnIndex());
                ticket.setRowIndex(seatForm.getRowIndex());
                ticket.setTime(now);
                ticketMapper.insertTicket(ticket);
                ticket = ticketMapper.selectTicketByScheduleIdAndSeat(ticket.getScheduleId(),
                        ticket.getColumnIndex(), ticket.getRowIndex());
                ticketList.add(ticket);
                ticketVOList.add(ticket.getVO());
            }

            TicketWithCouponVO ticketWithCouponVO = new TicketWithCouponVO();
            ScheduleItem scheduleItem = scheduleService.getScheduleItemById(ticketForm.getScheduleId());
            // 该排片电影可用活动
            List<ActivityVO> activityVOList = new ArrayList<>();
            // 小于总价的可用优惠券
            List<Coupon> couponList = couponService.getCouponByUserAndAmount(ticketForm.getUserId(),
                    scheduleItem.getFare());
            //活动
            List<Activity> activityList = new ArrayList<>();
            for (Coupon coupon : couponList) {
                activityList.add(activityService.getActivityByCouponId(coupon.getId()));
            }
            // 所有电影票的总价
            double total = scheduleItem.getFare() * ticketVOList.size();
            for (Activity activity : activityList) {
                if (activity != null) {
                    activityVOList.add(activity.getVO());
                }
            }

            ticketWithCouponVO.setTicketVOList(ticketVOList);
            ticketWithCouponVO.setActivities(activityVOList);
            ticketWithCouponVO.setCoupons(couponList);
            ticketWithCouponVO.setTotal(total);

            //创建订单并向数据库插入
            TicketOrder ticketOrder = new TicketOrder();
            ticketOrder.setCouponId(0);
            ticketOrder.setTicketList(ticketList);
            ticketMapper.insertTicketOrder(ticketOrder);

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
            // 根据该电影可用活动为user赠送优惠券
            List<Activity> activityList = activityService.getActivitiesByMovieId(scheduleItem.getMovieId());
            List<Activity> activityList2 = activityService.getActivitiesWithoutMovie();
            activityList.addAll(activityList2);
            int userId = ticket.getUserId();
            for (Activity activity : activityList) {
                couponService.insertCouponUser(activity.getCoupon().getId(), userId);
            }
            // 删除使用的优惠券
            couponService.deleteCouponUser(couponId, userId);
            // 订单使用优惠券
            for (Integer id : ticketIdList) {
                ticketMapper.updateTicketOrder(couponId, id);
            }
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
            // 根据该电影可用活动为user赠送优惠券
            List<Activity> activityList = activityService.getActivitiesByMovieId(scheduleItem.getMovieId());
            List<Activity> activityList2 = activityService.getActivitiesWithoutMovie();
            activityList.addAll(activityList2);
            int userId = ticket.getUserId();
            for (Activity activity : activityList) {
                couponService.insertCouponUser(activity.getCoupon().getId(), userId);
            }
            // 删除使用的优惠券
            couponService.deleteCouponUser(couponId, userId);
            // 订单使用优惠券
            for (Integer id : ticketIdList) {
                ticketMapper.updateTicketOrder(couponId, id);
            }

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
            List<TicketOrder> ticketOrderList = ticketMapper.selectTicketOrdersByUserId(userId);
            if (ticketOrderList.size() == 0) {
                return ResponseVO.buildFailure("暂无订单");
            }
            return ResponseVO.buildSuccess(ticketOrderList2VOList(ticketOrderList));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获得电影票失败");
        }
    }

    @Override
    public ResponseVO getUserByConsume(double consume) {
        try {
            List<User> userList = accountService.getUsers();
            List<UserVO> userVOList = new ArrayList<>();
            for (User user : userList) {
                double userConsume = 0;
                List<TicketVO> ticketVOList = ticketList2VOList(ticketMapper.selectTicketByUser(user.getId()));
                for (TicketVO ticketVO : ticketVOList) {
                    ScheduleItem scheduleItem = scheduleService.getScheduleItemById(ticketVO.getScheduleId());
                    if ("已完成".equals(ticketVO.getState()))
                        userConsume += scheduleItem.getFare();
                }
                if (userConsume >= consume && !"root".equals(user.getUsername()) && !"/".equals(user.getUsername().substring(0, 1))) {
                    userVOList.add(new UserVO(user));
                }
            }
            return ResponseVO.buildSuccess(userVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获取用户失败");
        }
    }

    @Override
    public ResponseVO getSaleHistory(int userId) {
        try {
            List<TicketVO> ticketVOList = ticketList2VOList(ticketMapper.selectTicketByUser(userId));
            if (ticketVOList.size() == 0) {
                return ResponseVO.buildFailure("暂无订单");
            }
            return ResponseVO.buildSuccess(ticketVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获取消费记录失败");
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
    public ResponseVO refundById(List<Integer> idList) {
        try {
            TicketOrder ticketOrder;
            Ticket ticket;
            VIPCard vipCard;
            for (Integer id : idList) {
                ticketOrder = ticketMapper.selectTicketOrderById(id);
                ticket = ticketOrder.getTicketList().get(0);
                if (ticket.getState() != 1) {
                    return ResponseVO.buildFailure("未付款，无法退票");
                } else if ((scheduleService.getScheduleItemById(ticket.getScheduleId()).getStartTime().getTime() -
                        System.currentTimeMillis()) <= ticketMapper.selectRefundInfo().getLimitHours() * 3600000) {
                    return ResponseVO.buildFailure("距离上映时间过近，无法退票");
                }

                double[] prices = caculatePrice(ticketOrder);
                vipCard = vipCardService.getVIPCardByUserId(ticket.getUserId());
                if (vipCard != null)
                    vipCardService.updateVIPCardByIdAndBanlance(vipCard.getId(), vipCard.getBalance() + prices[1]);
                for (Ticket ticket2 : ticketOrder.getTicketList()) {
                    ticketMapper.updateTicketState(ticket2.getId(), 3);
                }
            }
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("退票失败");
        }

    }

    private List<TicketVO> ticketList2VOList(List<Ticket> ticketList) {
        List<TicketVO> ticketVOList = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            ticketVOList.add(ticket.getVO());
        }
        return ticketVOList;
    }

    private List<TicketOrderVO> ticketOrderList2VOList(List<TicketOrder> ticketOrderList) {
        List<TicketOrderVO> ticketOrderVOList = new ArrayList<>();
        TicketOrderVO ticketOrderVO;
        Ticket ticket;
        int state;
        for (TicketOrder ticketOrder : ticketOrderList) {
            ticket = ticketOrder.getTicketList().get(0);
            ticketOrderVO = new TicketOrderVO();
            ticketOrderVO.setId(ticketOrder.getOrderId());
            ticketOrderVO.setTime(ticket.getTime());
            state = ticket.getState();
            if (state == 0) {
                ticketOrderVO.setState("未付款");
            } else if (state == 1) {
                ticketOrderVO.setState("已付款");
            } else if (state == 2) {
                ticketOrderVO.setState("已失效");
            } else if (state == 3) {
                ticketOrderVO.setState("已退款");
            }

            ticketOrderVO.setTicketVOList(ticketList2VOList(ticketOrder.getTicketList()));
            ticketOrderVO.setCanRefund(state == 1 &&
                    (scheduleService.getScheduleItemById(ticket.getScheduleId()).getStartTime().getTime() -
                            System.currentTimeMillis()) > ticketMapper.selectRefundInfo().getLimitHours() * 3600000);
            double[] prices = caculatePrice(ticketOrder);
            ticketOrderVO.setOriginCost(prices[0]);
            ticketOrderVO.setRefund(prices[1]);
            ticketOrderVOList.add(ticketOrderVO);
        }
        return ticketOrderVOList;
    }

    /**
     * 计算订单的优惠价和应退款
     *
     * @param ticketOrder
     * @return
     */
    private double[] caculatePrice(TicketOrder ticketOrder) {
        double[] prices = new double[2];
        prices[0] = scheduleService.getScheduleItemById(ticketOrder.getTicketList().get(0).getScheduleId()).getFare() *
                ticketOrder.getTicketList().size();
        //优惠价(实付款)
        Coupon coupon = couponService.getCouponById(ticketOrder.getCouponId());
        if (coupon != null) {
            prices[0] = prices[0] - coupon.getDiscountAmount();
        }
        //差价
        double discount = prices[0] * ticketMapper.selectRefundInfo().getRate();
        //退款
        prices[1] = prices[0] - discount;
        return prices;
    }

}

