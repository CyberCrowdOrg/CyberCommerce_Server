package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;

public class GrouponGoodsCreateReq implements Serializable {

    private String taskParentId;

    private Long userId;

    public String getTaskParentId() {
        return taskParentId;
    }

    public void setTaskParentId(String taskParentId) {
        this.taskParentId = taskParentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
