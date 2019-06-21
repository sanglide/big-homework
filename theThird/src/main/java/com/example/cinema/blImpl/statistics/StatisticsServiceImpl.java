package com.example.cinema.blImpl.statistics;

import com.example.cinema.bl.statistics.StatisticsService;
import com.example.cinema.data.statistics.StatisticsMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/16 1:34 PM
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
	@Autowired
	private StatisticsMapper statisticsMapper;

	@Override
	public ResponseVO getScheduleRateByDate(Date date) {
		try {
			Date requireDate = date;
			if (requireDate == null) {
				requireDate = new Date();
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			requireDate = simpleDateFormat.parse(simpleDateFormat.format(requireDate));

			Date nextDate = getNumDayAfterDate(requireDate, 1);
			return ResponseVO.buildSuccess(movieScheduleTimeList2MovieScheduleTimeVOList(
					statisticsMapper.selectMovieScheduleTimes(requireDate, nextDate)));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("失败");
		}
	}

	@Override
	public ResponseVO getTotalBoxOffice() {
		try {
			return ResponseVO.buildSuccess(
					movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(statisticsMapper.selectMovieTotalBoxOffice()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("失败");
		}
	}

	@Override
	public ResponseVO getAudiencePriceSevenDays() {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
			Date startDate = getNumDayAfterDate(today, -6);
			List<AudiencePriceVO> audiencePriceVOList = new ArrayList<>();
			for (int i = 0; i < 7; i++) {
				AudiencePriceVO audiencePriceVO = new AudiencePriceVO();
				Date date = getNumDayAfterDate(startDate, i);
				audiencePriceVO.setDate(date);
				List<AudiencePrice> audiencePriceList = statisticsMapper.selectAudiencePrice(date,
						getNumDayAfterDate(date, 1));
				double totalPrice = audiencePriceList.stream().mapToDouble(item -> item.getTotalPrice()).sum();
				audiencePriceVO.setPrice(Double.parseDouble(String.format("%.2f",
						audiencePriceList.size() == 0 ? 0 : totalPrice / audiencePriceList.size())));
				audiencePriceVOList.add(audiencePriceVO);
			}
			return ResponseVO.buildSuccess(audiencePriceVOList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("失败");
		}
	}

	// 获得当天所有电影的上座率（自己写的方法）
	@Override
	public ResponseVO getMoviePlacingRateByDate(Date date) {
		try {
			Date requireDate = date;
			if (requireDate == null) {
				requireDate = new Date();
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			requireDate = simpleDateFormat.parse(simpleDateFormat.format(requireDate));
			Date nextDate = getNumDayAfterDate(requireDate, 1);
			List<Hall> hall = statisticsMapper.searchHall();
			int m = hall.size();
			int n = 0;
			for (int j = 0; j < m; j++) {
				n = n + hall.get(j).getColumn() * hall.get(j).getRow();
			}
			List<MoviePlacingRateByDate> moviePlacingRateByDate = statisticsMapper.getMoviePlacingRateByDate(date,
					nextDate);
			List<MoviePlacingRateByDateVO> moviePlacingRateByDateVOList = new ArrayList<>();
			for (int i = 0; i < moviePlacingRateByDate.size(); i++) {
				MoviePlacingRateByDateVO moviePlacingRateByDateVO = new MoviePlacingRateByDateVO();
				moviePlacingRateByDateVO.setMovieId(moviePlacingRateByDate.get(i).getMovieId());
				moviePlacingRateByDateVO.setName(moviePlacingRateByDate.get(i).getName());
				int ticketNum = moviePlacingRateByDate.get(i).getTicketNumber();
				int scheduleNum = moviePlacingRateByDate.get(i).getScheduleTime();
				double rate = 0.00;
				if ((scheduleNum != 0 && ticketNum != 0)) {
					double rate1 = Double.parseDouble(String.format("%.4f", (double) n / (double) m));
					double rate2 = Double.parseDouble(String.format("%.4f", (double) ticketNum / (double) scheduleNum));
					rate = Double.parseDouble(String.format("%.2f", rate2 / rate1));
				}
				// double rate=Double.parseDouble(String.format("%.2f",
				// (moviePlacingRateByDate.get(i).getTicketNumber()/moviePlacingRateByDate.get(i).getScheduleTime()/(m/n))
				// )) ;

				moviePlacingRateByDateVO.setPlacingRate(rate);
				moviePlacingRateByDateVOList.add(moviePlacingRateByDateVO);
			}
			return ResponseVO.buildSuccess(moviePlacingRateByDateVOList);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("失败");
		}

	}

	// 最近几天内最受欢迎的电影，也就是电影的票房最高
	@Override
	public ResponseVO getPopularMovies(int days, int movieNum) {
		// 要求见接口说明
		try {
			Date endDate = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			endDate = simpleDateFormat.parse(simpleDateFormat.format(endDate));

			Date startDate = getNumDayAfterDate(endDate, -1 * days);
			List<PopularMovies> popularMovies = (statisticsMapper.selectPopularMovie(startDate, endDate));
			List<PopularMoviesVO> popularMoviesVOList = new ArrayList<>();
			if(movieNum>=popularMovies.size()){
				movieNum=popularMovies.size();
			}
			for (int i = 0; i < movieNum; i++) {
				PopularMoviesVO popularMoviesVO = new PopularMoviesVO(popularMovies.get(i));
				popularMoviesVOList.add(popularMoviesVO);

//				System.out.println(popularMoviesVO.getName());
//				System.out.println(popularMoviesVO.getBoxOffice());
			}
			return ResponseVO.buildSuccess(popularMoviesVOList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("失败");
		}

	}

	/**
	 * 获得num天后的日期
	 *
	 * @param oldDate
	 * @param num
	 * @return
	 */
	Date getNumDayAfterDate(Date oldDate, int num) {
		Calendar calendarTime = Calendar.getInstance();
		calendarTime.setTime(oldDate);
		calendarTime.add(Calendar.DAY_OF_YEAR, num);
		return calendarTime.getTime();
	}

	private List<MovieScheduleTimeVO> movieScheduleTimeList2MovieScheduleTimeVOList(
			List<MovieScheduleTime> movieScheduleTimeList) {
		List<MovieScheduleTimeVO> movieScheduleTimeVOList = new ArrayList<>();
		for (MovieScheduleTime movieScheduleTime : movieScheduleTimeList) {
			movieScheduleTimeVOList.add(new MovieScheduleTimeVO(movieScheduleTime));
		}
		return movieScheduleTimeVOList;
	}

	private List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(
			List<MovieTotalBoxOffice> movieTotalBoxOfficeList) {
		List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeVOList = new ArrayList<>();
		for (MovieTotalBoxOffice movieTotalBoxOffice : movieTotalBoxOfficeList) {
			movieTotalBoxOfficeVOList.add(new MovieTotalBoxOfficeVO(movieTotalBoxOffice));
		}
		return movieTotalBoxOfficeVOList;
	}

	/**
	 * private List<PopularMoviesVO>
	 * popularMoviesList2PopularMoviesVOList(List<PopularMovies>popularMoviesList){
	 * List<PopularMoviesVO>popularMoviesVOList=new ArrayList<>(); for(PopularMovies
	 * popularMovies : popularMoviesList){ popularMoviesVOList.add(new
	 * PopularMoviesVO(popularMovies)); } return popularMoviesVOList; }
	 **/

	/**
	 * private List<MoviePlacingRateByDateVO>
	 * moviePlacingRateByDateList2MoviePlacingRateByDateVOList(List<MoviePlacingRateByDate>moviePlacingRateByDateList){
	 * List<MoviePlacingRateByDateVO> moviePlacingRateByDateVOList=new
	 * ArrayList<>(); for(MoviePlacingRateByDate moviePlacingRateByDate :
	 * moviePlacingRateByDateList){ moviePlacingRateByDateVOList.add(new
	 * MoviePlacingRateByDateVO(moviePlacingRateByDate)); } return
	 * moviePlacingRateByDateVOList; }
	 **/
}
