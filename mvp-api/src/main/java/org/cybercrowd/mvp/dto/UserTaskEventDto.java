package org.cybercrowd.mvp.dto;

import java.io.Serializable;
import java.util.Date;

public class UserTaskEventDto implements Serializable {

    private Long eventId;

    private String taskId;

    private String taskParentId;

    private String eventType;

    private String eventAmount;

    private Long userId;

    private String userNickName;

    private String userWalletAddr;

    private Date createTime;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskParentId() {
        return taskParentId;
    }

    public void setTaskParentId(String taskParentId) {
        this.taskParentId = taskParentId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventAmount() {
        return eventAmount;
    }

    public void setEventAmount(String eventAmount) {
        this.eventAmount = eventAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserWalletAddr() {
        return userWalletAddr;
    }

    public void setUserWalletAddr(String userWalletAddr) {
        this.userWalletAddr = userWalletAddr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
