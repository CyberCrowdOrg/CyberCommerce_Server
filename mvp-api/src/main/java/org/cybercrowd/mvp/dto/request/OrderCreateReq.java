package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;

public class OrderCreateReq implements Serializable {

    private String taskId;

    private String orderType;

    private Long userId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
