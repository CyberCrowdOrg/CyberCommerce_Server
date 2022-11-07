package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;

public class PublishGoodsReqDto implements Serializable {

    private String taskId;
    private Long goodsId;
    private Long userId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
