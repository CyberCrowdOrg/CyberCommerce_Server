package org.cybercrowd.mvp.dto;

import java.io.Serializable;
import java.util.Date;

public class ProgressGroupDto implements Serializable {

    private Long userId;

    private String userAvatar;

    private String taskId;

    private String nickName;

    private Long grouponPeople;

    private Long grouponPeopleLimit;

    private Date taskEndTime;

    private Long taskEndTimeMillisecond;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getGrouponPeople() {
        return grouponPeople;
    }

    public void setGrouponPeople(Long grouponPeople) {
        this.grouponPeople = grouponPeople;
    }

    public Long getGrouponPeopleLimit() {
        return grouponPeopleLimit;
    }

    public void setGrouponPeopleLimit(Long grouponPeopleLimit) {
        this.grouponPeopleLimit = grouponPeopleLimit;
    }

    public Date getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public Long getTaskEndTimeMillisecond() {
        return taskEndTimeMillisecond;
    }

    public void setTaskEndTimeMillisecond(Long taskEndTimeMillisecond) {
        this.taskEndTimeMillisecond = taskEndTimeMillisecond;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}
