package com.example.cinema.blImpl.promotion.vipcard;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.po.VIPCard;
import com.example.cinema.po.VIPCardChargeHistory;
import com.example.cinema.po.VIPInfo;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPCardChargeHistoryVO;
import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.VIPInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liying on 2019/4/14.
 */
@Service
public class VIPServiceImpl implements VIPService, VIPCardServiceForBl {
    @Autowired
    VIPCardMapper vipCardMapper;

    @Override
    public ResponseVO addVIPCard(int userId) {
        VIPCard vipCard = new VIPCard();
        vipCard.setUserId(userId);
        vipCard.setBalance(0);
        try {
            int id = vipCardMapper.insertOneCard(vipCard);
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardById(int id) {
        try {
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO charge(VIPCardForm vipCardForm) {
        try {
            VIPCard vipCard = vipCardMapper.selectCardById(vipCardForm.getVipId());
            if (vipCard == null) {
                return ResponseVO.buildFailure("会员卡不存在");
            }
            VIPInfo vipInfo = vipCardMapper.selectVIPInfo();
            //当前余额
            double balance = vipCard.getBalance();
            //充值金额
            double amount = vipCardForm.getAmount();
            //充值后余额
            amount = (int)(amount / vipInfo.getCharge()) * vipInfo.getBonus() + amount;
            balance += amount;
            vipCardMapper.updateCardBalance(vipCard.getId(), balance);
            //插入历史记录
            VIPCardChargeHistory history = new VIPCardChargeHistory();
            history.setUserId(vipCard.getUserId());
            history.setBalance(balance);
            history.setCharge(amount);
            history.setTime(new Timestamp(System.currentTimeMillis()));
            vipCardMapper.insertChargeHistory(history);
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("充值失败");
        }
    }

    @Override
    public ResponseVO getChargeHistoryByUserId(int userId) {
        try {
            List<VIPCardChargeHistory> vipCardChargeHistoryList = vipCardMapper.selectChargeHistoryByUserId(userId);
            List<VIPCardChargeHistoryVO> voList = new ArrayList<>();
            for (VIPCardChargeHistory history : vipCardChargeHistoryList){
                voList.add(history.getVO());
            }
            return ResponseVO.buildSuccess(voList);
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseVO.buildFailure("获取充值记录失败");
        }

    }

    @Override
    public ResponseVO getCardByUserId(int userId) {
        try {
            VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
            if (vipCard == null) {
                return ResponseVO.buildFailure("用户卡不存在");
            }
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }


    @Override
    public ResponseVO getVIPInfo() {
        try {
            VIPInfo vipInfo = vipCardMapper.selectVIPInfo();
            return ResponseVO.buildSuccess(vipInfo.getVO());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获取会员卡信息失败");
        }
    }

    @Override
    public ResponseVO updateVIPInfo(VIPInfoVO vipInfoVO) {
        try {
            vipCardMapper.updateVIPInfo(vipInfoVO.getPrice(), vipInfoVO.getCharge(), vipInfoVO.getBonus());
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("更新会员卡信息失败");
        }
    }

    @Override
    public void updateVIPCardByIdAndBanlance(int id, double balance) {
        vipCardMapper.updateCardBalance(id, balance);
    }

    @Override
    public VIPCard getVIPCardByUserId(int userId) {
        return vipCardMapper.selectCardByUserId(userId);
    }


}
