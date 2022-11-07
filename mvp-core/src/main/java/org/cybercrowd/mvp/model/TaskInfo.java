package org.cybercrowd.mvp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 
 */
public class TaskInfo implements Serializable {
    private Long id;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 父任务ID
     */
    private String taskParentId;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 任务类型，groupon 拼团 distributor 分销商 
     */
    private String taskType;

    /**
     * 任务主人ID
     */
    private Long taskOwnerId;

    /**
     * 任务主人地址
     */
    private String taskOwnerAddr;

    /**
     * 任务关键字,便于搜索
     */
    private String taskKeyWord;

    /**
     * 任务来源
     */
    private String taskSource;

    /**
     * 成交数量
     */
    private Long dealQuantity;

    /**
     * 拼团数量
     */
    private Long grouponQuantity;

    /**
     * 分销数量
     */
    private Long distributorQuantity;

    /**
     * 邀请人数
     */
    private Long inviteesNumber;

    /**
     * 拼团人数
     */
    private Long grouponPeople;

    /**
     * 拼团人数上限
     */
    private Long grouponPeopleLimit;

    /**
     * 任务状态,未开始、进行中、已完成待确认、未通过、已完成
     */
    private String taskStatus;

    /**
     * 任务开始时间
     */
    private Date taskStartTime;

    /**
     * 任务结束时间
     */
    private Date taskEndTime;

    /**
     * 任务完成时间
     */
    private Date taskCompletionTime;

    /**
     * 启用NFT功能 0 否 1 是
     */
    private String enableNft;

    /**
     * 启用分销功能 0 未开启 1已开启
     */
    private String enableDistributor;

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

    public String getTaskParentId() {
        return taskParentId;
    }

    public void setTaskParentId(String taskParentId) {
        this.taskParentId = taskParentId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Long getTaskOwnerId() {
        return taskOwnerId;
    }

    public void setTaskOwnerId(Long taskOwnerId) {
        this.taskOwnerId = taskOwnerId;
    }

    public String getTaskOwnerAddr() {
        return taskOwnerAddr;
    }

    public void setTaskOwnerAddr(String taskOwnerAddr) {
        this.taskOwnerAddr = taskOwnerAddr;
    }

    public String getTaskKeyWord() {
        return taskKeyWord;
    }

    public void setTaskKeyWord(String taskKeyWord) {
        this.taskKeyWord = taskKeyWord;
    }

    public String getTaskSource() {
        return taskSource;
    }

    public void setTaskSource(String taskSource) {
        this.taskSource = taskSource;
    }

    public Long getDealQuantity() {
        return dealQuantity;
    }

    public void setDealQuantity(Long dealQuantity) {
        this.dealQuantity = dealQuantity;
    }

    public Long getGrouponQuantity() {
        return grouponQuantity;
    }

    public void setGrouponQuantity(Long grouponQuantity) {
        this.grouponQuantity = grouponQuantity;
    }

    public Long getDistributorQuantity() {
        return distributorQuantity;
    }

    public void setDistributorQuantity(Long distributorQuantity) {
        this.distributorQuantity = distributorQuantity;
    }

    public Long getInviteesNumber() {
        return inviteesNumber;
    }

    public void setInviteesNumber(Long inviteesNumber) {
        this.inviteesNumber = inviteesNumber;
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

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(Date taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public Date getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public Date getTaskCompletionTime() {
        return taskCompletionTime;
    }

    public void setTaskCompletionTime(Date taskCompletionTime) {
        this.taskCompletionTime = taskCompletionTime;
    }

    public String getEnableNft() {
        return enableNft;
    }

    public void setEnableNft(String enableNft) {
        this.enableNft = enableNft;
    }

    public String getEnableDistributor() {
        return enableDistributor;
    }

    public void setEnableDistributor(String enableDistributor) {
        this.enableDistributor = enableDistributor;
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