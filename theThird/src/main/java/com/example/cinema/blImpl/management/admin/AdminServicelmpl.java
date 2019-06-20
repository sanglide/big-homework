package com.example.cinema.blImpl.management.admin;

import com.example.cinema.bl.management.AdminService;
import com.example.cinema.data.management.AdminMapper;
import com.example.cinema.po.User;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServicelmpl implements AdminService{
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public  ResponseVO searchAllAdminAccount(){
        try {
            List<User> allAdminAccount=adminMapper.selectAllAdminAccount();
            List<UserVO> allAdminAccountVO = new ArrayList<>();
            for (User account : allAdminAccount) {
                allAdminAccountVO.add(new UserVO(account));
            }
            return ResponseVO.buildSuccess(allAdminAccountVO);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }
    @Override
    public  ResponseVO addAdminAccount(UserForm userForm){
        try {
            adminMapper.addAdminAccount(userForm.getUsername(),userForm.getPassword());

        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO changeAdminAccount(AdminForm adminForm){
        try {
           adminMapper.changeAdminAccount(adminForm.getId(),adminForm.getUsername(),adminForm.getPassword());
            return ResponseVO.buildSuccess();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public  ResponseVO deleteAdminAccount(AccountBatchDeleteForm accountBatchDeleteForm){
        try {
            for(int m=0;m<accountBatchDeleteForm.getAccountIdList().size();m++){
                adminMapper.deleteAdminAccount(accountBatchDeleteForm.getAccountIdList().get(m));
            }
            return ResponseVO.buildSuccess();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

}
