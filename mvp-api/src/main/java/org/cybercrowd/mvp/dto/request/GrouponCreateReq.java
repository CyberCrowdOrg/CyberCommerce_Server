package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;

public class GrouponCreateReq implements Serializable {

    private String taskParentId;

    private Long userId;

    private Long goodsId;

    private GrouponRulesDto grouponRulesDto;

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

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public GrouponRulesDto getGrouponRulesDto() {
        return grouponRulesDto;
    }

    public void setGrouponRulesDto(GrouponRulesDto grouponRulesDto) {
        this.grouponRulesDto = grouponRulesDto;
    }
}
