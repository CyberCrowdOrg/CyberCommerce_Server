package org.cybercrowd.mvp.dto.response;

import java.io.Serializable;

public class DistributionGoodsCreateRes implements Serializable {

    private String taskId;

    private Long goodsId;

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
}
