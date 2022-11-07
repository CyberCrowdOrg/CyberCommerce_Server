package org.cybercrowd.mvp.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserGrouponDto implements Serializable {

    private Long taskOwnerId;

    private String taskId;

    private Long goodsId;

    //邀请人数
    private Long inviteesNumber;
    //是不是拼团发起人
    private boolean grouponOwner;
    //商品图片
    private String goodsImage;
    //商品名称
    private String goodsTitle;
    //拼团价格
    private BigDecimal grouponPrice;
    //当前参与人数
    private int currentPeople;
    //参与人数上限
    private int peopleLimit;

    private Date endTime;
    //结束时间毫秒
    private Long endTimeMilliSecond;
    //团链接
    private String grouponLink;

    private String nftAssetsLink;
    //任务状态
    private String taskStatus;

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

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public BigDecimal getGrouponPrice() {
        return grouponPrice;
    }

    public void setGrouponPrice(BigDecimal grouponPrice) {
        this.grouponPrice = grouponPrice;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getEndTimeMilliSecond() {
        return endTimeMilliSecond;
    }

    public void setEndTimeMilliSecond(Long endTimeMilliSecond) {
        this.endTimeMilliSecond = endTimeMilliSecond;
    }

    public String getGrouponLink() {
        return grouponLink;
    }

    public void setGrouponLink(String grouponLink) {
        this.grouponLink = grouponLink;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public boolean isGrouponOwner() {
        return grouponOwner;
    }

    public void setGrouponOwner(boolean grouponOwner) {
        this.grouponOwner = grouponOwner;
    }

    public Long getTaskOwnerId() {
        return taskOwnerId;
    }

    public void setTaskOwnerId(Long taskOwnerId) {
        this.taskOwnerId = taskOwnerId;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Long getInviteesNumber() {
        return inviteesNumber;
    }

    public void setInviteesNumber(Long inviteesNumber) {
        this.inviteesNumber = inviteesNumber;
    }

    public int getCurrentPeople() {
        return currentPeople;
    }

    public void setCurrentPeople(int currentPeople) {
        this.currentPeople = currentPeople;
    }

    public int getPeopleLimit() {
        return peopleLimit;
    }

    public void setPeopleLimit(int peopleLimit) {
        this.peopleLimit = peopleLimit;
    }

    public String getNftAssetsLink() {
        return nftAssetsLink;
    }

    public void setNftAssetsLink(String nftAssetsLink) {
        this.nftAssetsLink = nftAssetsLink;
    }
}
