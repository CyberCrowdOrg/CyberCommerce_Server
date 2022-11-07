package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleTaskPublishReq implements Serializable {

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

    private boolean enableNft;

    private int distributionExpirationDay;

    private DistributionRulesDto distributionRulesDto;

    private GrouponRulesDto grouponRulesDto;

    private Object fileMap;

    private Object nftFileMap;

    private Long userId;

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


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Object getFileMap() {
        return fileMap;
    }

    public void setFileMap(Object fileMap) {
        this.fileMap = fileMap;
    }

    public int getDistributionExpirationDay() {
        return distributionExpirationDay;
    }

    public void setDistributionExpirationDay(int distributionExpirationDay) {
        this.distributionExpirationDay = distributionExpirationDay;
    }

    public DistributionRulesDto getDistributionRulesDto() {
        return distributionRulesDto;
    }

    public void setDistributionRulesDto(DistributionRulesDto distributionRulesDto) {
        this.distributionRulesDto = distributionRulesDto;
    }

    public GrouponRulesDto getGrouponRulesDto() {
        return grouponRulesDto;
    }

    public void setGrouponRulesDto(GrouponRulesDto grouponRulesDto) {
        this.grouponRulesDto = grouponRulesDto;
    }

    public Object getNftFileMap() {
        return nftFileMap;
    }

    public void setNftFileMap(Object nftFileMap) {
        this.nftFileMap = nftFileMap;
    }

    public boolean isEnableNft() {
        return enableNft;
    }

    public void setEnableNft(boolean enableNft) {
        this.enableNft = enableNft;
    }
}
