package org.cybercrowd.mvp.dto.response;

import java.io.Serializable;

public class GoodsCreateRes implements Serializable {

    private Long goodsId;

    private String taskId;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
