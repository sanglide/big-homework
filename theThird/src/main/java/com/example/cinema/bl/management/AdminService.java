package com.example.cinema.bl.management;

import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.AdminForm;
import com.example.cinema.vo.AccountBatchDeleteForm;
import com.example.cinema.vo.UserVO;

public interface AdminService {
    /**
     * 搜索所有售票员的账户
     * @return
     */
    ResponseVO searchAllAdminAccount();


    /**
     * 添加售票员账户信息
     * @param userForm
     * @return
     */
    ResponseVO addAdminAccount(UserForm userForm);

    /**
     * 修改售票员账户信息
     * @param adminForm
     * @return
     */

    ResponseVO changeAdminAccount(AdminForm adminForm);

    /**
     * 删除售票员账户信息
     * @param accountBatchDeleteForm
     * @return
     */

    ResponseVO deleteAdminAccount(AccountBatchDeleteForm accountBatchDeleteForm);
}
