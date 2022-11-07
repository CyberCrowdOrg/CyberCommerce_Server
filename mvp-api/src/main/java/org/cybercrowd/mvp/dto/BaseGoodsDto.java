package org.cybercrowd.mvp.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BaseGoodsDto implements Serializable {

    private String taskId;

    private String taskType;

    private Long goodsId;
    /**
     * 商品标题
     */
    private String goodsTitle;

    /**
     * 商品简介
     */
    private String goodsIntro;

    /**
     * 商品详细介绍
     */
    private String goodsDetailsIntro;

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 商品销售价格
     */
    private BigDecimal goodsSalePrice;


    private String goodsImages;

    private Long ownerUserId;

    private String userNickName;

    private String userAvatar;

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

    public String getGoodsIntro() {
        return goodsIntro;
    }

    public void setGoodsIntro(String goodsIntro) {
        this.goodsIntro = goodsIntro;
    }

    public String getGoodsDetailsIntro() {
        return goodsDetailsIntro;
    }

    public void setGoodsDetailsIntro(String goodsDetailsIntro) {
        this.goodsDetailsIntro = goodsDetailsIntro;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getGoodsSalePrice() {
        return goodsSalePrice;
    }

    public void setGoodsSalePrice(BigDecimal goodsSalePrice) {
        this.goodsSalePrice = goodsSalePrice;
    }

    public String getGoodsImages() {
        return goodsImages;
    }

    public void setGoodsImages(String goodsImages) {
        this.goodsImages = goodsImages;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}
