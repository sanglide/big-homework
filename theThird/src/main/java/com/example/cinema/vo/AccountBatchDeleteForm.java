package com.example.cinema.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**@author zyc
 * @data 2019/5/28 10:44
 */

@Getter
@Setter
public class AccountBatchDeleteForm {
    /**
     * 要删除的员工账户信息id列表
     */
    private List<Integer> accountIdList;

//    public List<Integer> getAccountIdList() {
//        return accountIdList;
//    }
//
//    public void setAccountIdList(List<Integer> accountIdList) {
//        this.accountIdList = accountIdList;
//    }

}
