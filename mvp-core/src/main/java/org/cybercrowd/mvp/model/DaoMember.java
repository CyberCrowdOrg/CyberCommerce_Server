package org.cybercrowd.mvp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * Dao成员
 */
public class DaoMember implements Serializable {
    private Long id;

    /**
     * Dao编号
     */
    private String daoNo;

    /**
     * Dao主人标签 ，0 不是主人 1 是主人
     */
    private String ownerTag;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户钱包地址
     */
    private String userWalletAddr;

    private Date createTime;

    private Date updateTime;

    /**
     * 删除状态 1 已删除 0 未删除 ，已删除代表用户已经不是Dao成员
     */
    private String deleteStatus;

    /**
     * 删除时间
     */
    private Date deleteTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDaoNo() {
        return daoNo;
    }

    public void setDaoNo(String daoNo) {
        this.daoNo = daoNo;
    }

    public String getOwnerTag() {
        return ownerTag;
    }

    public void setOwnerTag(String ownerTag) {
        this.ownerTag = ownerTag;
    }

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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}