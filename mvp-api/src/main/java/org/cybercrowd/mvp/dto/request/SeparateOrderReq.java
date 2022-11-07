package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;

public class SeparateOrderReq implements Serializable {

    private String taskId;

    private Long userId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
