package org.cybercrowd.mvp.dto;

import java.io.Serializable;
import java.util.Date;

public class BaseDaoDto implements Serializable {

    private String daoNo;

    private String daoName;

    private Long daoOwnerId;

    private String taskId;

    private String taskType;

    private String userNickName;

    private String userAvatar;

    private String userWalletAddr;

    private Date createTime;

    private boolean owner;

    private int memberNumber;

    public String getDaoNo() {
        return daoNo;
    }

    public void setDaoNo(String daoNo) {
        this.daoNo = daoNo;
    }

    public String getDaoName() {
        return daoName;
    }

    public void setDaoName(String daoName) {
        this.daoName = daoName;
    }

    public Long getDaoOwnerId() {
        return daoOwnerId;
    }

    public void setDaoOwnerId(Long daoOwnerId) {
        this.daoOwnerId = daoOwnerId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public int getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(int memberNumber) {
        this.memberNumber = memberNumber;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}
