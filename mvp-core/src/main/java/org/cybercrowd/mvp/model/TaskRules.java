package org.cybercrowd.mvp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 任务规则
 */
public class TaskRules implements Serializable {
    private Long id;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 规则类型 groupon 拼团  distributor 分销
     */
    private String rulesType;

    /**
     * 任务规则JSON
     */
    private String taskRulesJson;

    /**
     * 任务规则内容(具体描述)
     */
    private String taskRulesContent;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getRulesType() {
        return rulesType;
    }

    public void setRulesType(String rulesType) {
        this.rulesType = rulesType;
    }

    public String getTaskRulesJson() {
        return taskRulesJson;
    }

    public void setTaskRulesJson(String taskRulesJson) {
        this.taskRulesJson = taskRulesJson;
    }

    public String getTaskRulesContent() {
        return taskRulesContent;
    }

    public void setTaskRulesContent(String taskRulesContent) {
        this.taskRulesContent = taskRulesContent;
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