package com.example.cinema.blImpl.management.hall;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.data.management.HallMapper;
import com.example.cinema.po.Hall;
import com.example.cinema.vo.HallForm;
import com.example.cinema.vo.HallVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/12 2:01 PM
 */
@Service
public class HallServiceImpl implements HallService, HallServiceForBl {
    private final static String HALL_EXIST = "影厅已存在";
    private final static String HALL_USED = "该影厅已经排片，暂时不可以删除";
    @Autowired
    private HallMapper hallMapper;

    @Override
    public ResponseVO searchAllHall() {
        try {
            return ResponseVO.buildSuccess(hallList2HallVOList(hallMapper.selectAllHall()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public Hall getHallById(int id) {
        try {
            return hallMapper.selectHallById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public ResponseVO addHall(HallForm hallForm) {
        try {
            List<Hall> allHall = hallMapper.selectAllHall();
            for (int i = 0; i < allHall.size(); i++) {
                if (allHall.get(i).getName().equals(hallForm.getName())) {
                    return ResponseVO.buildFailure(HALL_EXIST);
                }
            }
            hallMapper.addHall(hallForm.getName(), hallForm.getColumn(), hallForm.getRow());
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }


    }

    @Override
    public ResponseVO changeHall(HallForm hallForm) {
        try {
            hallMapper.changeHall(hallForm.getName(), hallForm.getColumn(), hallForm.getRow());
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO deleteHall(HallForm hallForm) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Hall hallHasSchedule = hallMapper.selectHallHasSchedule(hallForm.getName(), today);
            if (hallHasSchedule != null) {
                return ResponseVO.buildFailure(HALL_USED);
            }
            hallMapper.deleteHall(hallForm.getName());
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    private List<HallVO> hallList2HallVOList(List<Hall> hallList) {
        List<HallVO> hallVOList = new ArrayList<>();
        for (Hall hall : hallList) {
            hallVOList.add(new HallVO(hall));
        }
        return hallVOList;
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
}
