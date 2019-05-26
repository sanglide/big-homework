package com.example.cinema.vo;

import java.util.List;

/**@author zyc
 * @data 2019/5/22 22:22
 */
public class ActivityBatchDeleteForm {
    /**
     * 要删除的活动信息id列表
     */
    private List<Integer> activityIdList;

    public List<Integer> getActivityIdList() {
        return activityIdList;
    }

    public void setActivityIdList(List<Integer> activityIdList) {
        this.activityIdList = activityIdList;
    }
}
