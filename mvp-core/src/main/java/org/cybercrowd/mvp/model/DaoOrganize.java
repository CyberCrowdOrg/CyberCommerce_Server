package org.cybercrowd.mvp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * Dao组织
 */
public class DaoOrganize implements Serializable {
    private Long id;

    /**
     * Dao编号
     */
    private String daoNo;

    /**
     * Dao名称
     */
    private String daoName;

    /**
     * Dao logo
     */
    private String daoLogo;

    /**
     * Dao 介绍
     */
    private String daoIntro;

    /**
     * Dao 主人地址
     */
    private String daoOwnerAddr;

    /**
     * Dao 主人ID
     */
    private Long daoOwnerId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

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

    public String getDaoName() {
        return daoName;
    }

    public void setDaoName(String daoName) {
        this.daoName = daoName;
    }

    public String getDaoLogo() {
        return daoLogo;
    }

    public void setDaoLogo(String daoLogo) {
        this.daoLogo = daoLogo;
    }

    public String getDaoIntro() {
        return daoIntro;
    }

    public void setDaoIntro(String daoIntro) {
        this.daoIntro = daoIntro;
    }

    public String getDaoOwnerAddr() {
        return daoOwnerAddr;
    }

    public void setDaoOwnerAddr(String daoOwnerAddr) {
        this.daoOwnerAddr = daoOwnerAddr;
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
}