package com.example.cinema.controller.management;

import com.example.cinema.bl.management.AdminService;
import com.example.cinema.bl.management.HallService;
import com.example.cinema.vo.UserVO;
import com.example.cinema.vo.AdminForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.AccountBatchDeleteForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "account/get", method = RequestMethod.GET)
    public ResponseVO searchAllAdminAccount(){
        return adminService.searchAllAdminAccount();
    }

    @RequestMapping(value = "account/add", method = RequestMethod.POST)
    public ResponseVO addAdminAccount(@RequestBody UserForm userForm){
        return adminService.addAdminAccount(userForm);
    }

    @PostMapping("/account/update")
    public ResponseVO changeAdminAccount(@RequestBody AdminForm adminForm){
        return adminService.changeAdminAccount(adminForm);
    }

    @RequestMapping(value = "account/delete/batch", method = RequestMethod.DELETE)
    public ResponseVO deleteAdminAccount(@RequestBody AccountBatchDeleteForm accountBatchDeleteForm){
        return adminService.deleteAdminAccount(accountBatchDeleteForm);
    }


}
