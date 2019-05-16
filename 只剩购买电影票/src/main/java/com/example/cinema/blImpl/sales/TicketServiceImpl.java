package com.example.cinema.blImpl.sales;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
import com.example.cinema.po.Activity;
import com.example.cinema.po.Coupon;
import com.example.cinema.po.Hall;
import com.example.cinema.po.Movie;
import com.example.cinema.po.ScheduleItem;
import com.example.cinema.po.Ticket;
import com.example.cinema.po.VIPCard;
import com.example.cinema.vo.ActivityVO;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.ScheduleWithSeatVO;
import com.example.cinema.vo.SeatForm;
import com.example.cinema.vo.TicketForm;
import com.example.cinema.vo.TicketVO;
import com.example.cinema.vo.TicketWithCouponVO;

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
			List<Ticket> ticketList = new ArrayList<>();
			for (SeatForm seatForm : ticketForm.getSeats()) {
				Ticket ticket = new Ticket();
				ticket.setUserId(ticketForm.getUserId());
				ticket.setScheduleId(ticketForm.getScheduleId());
				ticket.setState(0);
				ticket.setColumnIndex(seatForm.getColumnIndex());
				ticket.setRowIndex(seatForm.getRowIndex());
				ticket.setTime(new Timestamp(System.currentTimeMillis()));
				ticketList.add(ticket);
			}
			
			List<TicketVO> ticketVOList = ticketList2ticketVOList(ticketList);
			ticketMapper.insertTickets(ticketList);
			
			TicketWithCouponVO ticketWithCouponVO = new TicketWithCouponVO();

			ScheduleItem scheduleItem = scheduleService.getScheduleItemById(ticketForm.getScheduleId());
			Movie movie = movieService.getMovieById(scheduleItem.getMovieId());
			// 该排片电影可用活动
			List<Activity> activityList = activityService.getActivitiesByMovieId(movie.getId());
			List<ActivityVO> activityVOList = new ArrayList<>();
			// 小于总价的可用优惠券
			List<Coupon> couponList = new ArrayList<>();
			// 所有电影票的总价
			double total = scheduleItem.getFare() * ticketVOList.size();
			for (Activity activity : activityList) {
				activityVOList.add(activity.getVO());
				couponList.add(couponService.getCouponByUserAndAmountAndActivity(ticketForm.getUserId(), total,
						activity.getId()));
			}

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
	public ResponseVO completeTicket(List<Integer> ticketIdList, int couponId) {
		try {
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
	@Transactional
	public ResponseVO completeByVIPCard(List<Integer> ticketIdList, int couponId) {
		try {
			Coupon coupon = couponService.getCouponById(couponId);
			Ticket ticket = ticketMapper.selectTicketById(ticketIdList.get(0));
			VIPCard vipCard = vipCardService.getVIPCardByUserId(ticket.getUserId());
			double amount = scheduleService.getScheduleItemById(ticket.getScheduleId()).getFare();
			double total = amount * ticketIdList.size();
			vipCardService.updateVIPCardByIdAndBanlance(vipCard.getId(),
					vipCard.getBalance() - total + coupon.getDiscountAmount());
			
			for (Integer ticketId : ticketIdList) {
				ticketMapper.updateTicketState(ticketId, 1);
			} 
			return ResponseVO.buildSuccess();
		} catch (Exception e) {
			return ResponseVO.buildFailure("购票失败");
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
	
	/**
	 * 将List<Ticket>转换为对应的List<TicketVO>
	 * @param ticketList
	 * @return
	 */
	private List<TicketVO> ticketList2ticketVOList(List<Ticket> ticketList) {
		List<TicketVO> ticketVOList = new ArrayList<>();
		for (Ticket ticket : ticketList) {
			ticketVOList.add(ticket.getVO());
		}
		return ticketVOList;
	}

}
