package org.cybercrowd.mvp.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserDistributionDto implements Serializable {

    private Long taskOwnerId;

    private String taskId;

    private Long goodsId;

    private Long inviteesNumber;
    //商品图片
    private String goodsImage;
    //商品名称
    private String goodsTitle;
    //拼团价格
    private BigDecimal grouponPrice;
    //最少参与人数
    private int people;
    //参与人数上限
    private int peopleLimit;
    //开始时间
    private Date startTime;

    private Date endTime;
    //结束时间毫秒
    private Long endTimeMilliSecond;
    //分销商品链接
    private String distributionLink;
    //任务状态
    private String taskStatus;
    //成交数量
    private Long dealQuantity;
    //拼团数量
    private Long grouponQuantity;
    //最小分销量
    private int distributionMin;
    //分销价格
    private BigDecimal distributionPrice;

    private String nftAssetsLink;

    public Long getTaskOwnerId() {
        return taskOwnerId;
    }

    public void setTaskOwnerId(Long taskOwnerId) {
        this.taskOwnerId = taskOwnerId;
    }

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

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
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

    public void setPeople(int people) {
        this.people = people;
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

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public BigDecimal getDistributionPrice() {
        return distributionPrice;
    }

    public void setDistributionPrice(BigDecimal distributionPrice) {
        this.distributionPrice = distributionPrice;
    }

    public String getDistributionLink() {
        return distributionLink;
    }

    public void setDistributionLink(String distributionLink) {
        this.distributionLink = distributionLink;
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

    public Long getInviteesNumber() {
        return inviteesNumber;
    }

    public void setInviteesNumber(Long inviteesNumber) {
        this.inviteesNumber = inviteesNumber;
    }

    public int getPeople() {
        return people;
    }

    public int getPeopleLimit() {
        return peopleLimit;
    }

    public void setPeopleLimit(int peopleLimit) {
        this.peopleLimit = peopleLimit;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getDistributionMin() {
        return distributionMin;
    }

    public void setDistributionMin(int distributionMin) {
        this.distributionMin = distributionMin;
    }

    public String getNftAssetsLink() {
        return nftAssetsLink;
    }

    public void setNftAssetsLink(String nftAssetsLink) {
        this.nftAssetsLink = nftAssetsLink;
    }
}
