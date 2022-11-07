package org.cybercrowd.mvp.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GoodsStoreDto implements Serializable {

    private String goodsTitle;

    private String goodsImage;

    private BigDecimal grouponPrice;
    /**
     * 拼团人数
     */
    private int grouponPeople;
    /**
     * 拼团人数上限
     */
    private int grouponPeopleLimit;
    /**
     * 最少分销数量
     */
    private int distributionMin;
    /**
     * 分销价格
     */
    private BigDecimal distributionPrice;

    private Date publishTime;

    private Long dealQuantity;

    private Long grouponQuantity;

    private Long distributionQuantity;

    private String storeLink;

    private String nftAssetsLink;

    private String taskStatus;

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public BigDecimal getGrouponPrice() {
        return grouponPrice;
    }

    public void setGrouponPrice(BigDecimal grouponPrice) {
        this.grouponPrice = grouponPrice;
    }

    public int getGrouponPeople() {
        return grouponPeople;
    }

    public void setGrouponPeople(int grouponPeople) {
        this.grouponPeople = grouponPeople;
    }

    public int getGrouponPeopleLimit() {
        return grouponPeopleLimit;
    }

    public void setGrouponPeopleLimit(int grouponPeopleLimit) {
        this.grouponPeopleLimit = grouponPeopleLimit;
    }

    public int getDistributionMin() {
        return distributionMin;
    }

    public void setDistributionMin(int distributionMin) {
        this.distributionMin = distributionMin;
    }

    public BigDecimal getDistributionPrice() {
        return distributionPrice;
    }

    public void setDistributionPrice(BigDecimal distributionPrice) {
        this.distributionPrice = distributionPrice;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
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

    public Long getDistributionQuantity() {
        return distributionQuantity;
    }

    public void setDistributionQuantity(Long distributionQuantity) {
        this.distributionQuantity = distributionQuantity;
    }

    public String getStoreLink() {
        return storeLink;
    }

    public void setStoreLink(String storeLink) {
        this.storeLink = storeLink;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getNftAssetsLink() {
        return nftAssetsLink;
    }

    public void setNftAssetsLink(String nftAssetsLink) {
        this.nftAssetsLink = nftAssetsLink;
    }
}
