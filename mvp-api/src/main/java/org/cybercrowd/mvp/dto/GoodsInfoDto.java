package org.cybercrowd.mvp.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 * 商品信息
 */
public class GoodsInfoDto implements Serializable {
    private Long id;

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

    /**
     * 商品库存数量
     */
    private Long goodsStock;

    /**
     * 商品已售数量
     */
    private Long goodsSold;

    /**
     * 商品图片JSON数据
     */
    private String goodsImageJson;

    /**
     * 发布用户ID
     */
    private Long publishUserId;

    /**
     * 发布用户地址
     */
    private String publishUserAddr;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(Long goodsStock) {
        this.goodsStock = goodsStock;
    }

    public Long getGoodsSold() {
        return goodsSold;
    }

    public void setGoodsSold(Long goodsSold) {
        this.goodsSold = goodsSold;
    }

    public String getGoodsImageJson() {
        return goodsImageJson;
    }

    public void setGoodsImageJson(String goodsImageJson) {
        this.goodsImageJson = goodsImageJson;
    }

    public Long getPublishUserId() {
        return publishUserId;
    }

    public void setPublishUserId(Long publishUserId) {
        this.publishUserId = publishUserId;
    }

    public String getPublishUserAddr() {
        return publishUserAddr;
    }

    public void setPublishUserAddr(String publishUserAddr) {
        this.publishUserAddr = publishUserAddr;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}