package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

public class DistributorTaskPublishReq implements Serializable {

    private String taskParentId;
    private boolean enableNft;
    private BigDecimal goodsPrice;
    private BigDecimal goodsSalePrice;

    private GrouponRulesDto grouponRulesDto;

    private DistributionRulesDto distributionRulesDto;

    private Long userId;

    private int distributionExpirationDay;

    private Object nftFileMap;

    private Object fileMap;

    public String getTaskParentId() {
        return taskParentId;
    }

    public void setTaskParentId(String taskParentId) {
        this.taskParentId = taskParentId;
    }

    public boolean isEnableNft() {
        return enableNft;
    }

    public void setEnableNft(boolean enableNft) {
        this.enableNft = enableNft;
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

    public GrouponRulesDto getGrouponRulesDto() {
        return grouponRulesDto;
    }

    public void setGrouponRulesDto(GrouponRulesDto grouponRulesDto) {
        this.grouponRulesDto = grouponRulesDto;
    }

    public DistributionRulesDto getDistributionRulesDto() {
        return distributionRulesDto;
    }

    public void setDistributionRulesDto(DistributionRulesDto distributionRulesDto) {
        this.distributionRulesDto = distributionRulesDto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Object getNftFileMap() {
        return nftFileMap;
    }

    public void setNftFileMap(Object nftFileMap) {
        this.nftFileMap = nftFileMap;
    }

    public int getDistributionExpirationDay() {
        return distributionExpirationDay;
    }

    public void setDistributionExpirationDay(int distributionExpirationDay) {
        this.distributionExpirationDay = distributionExpirationDay;
    }

    public Object getFileMap() {
        return fileMap;
    }

    public void setFileMap(Object fileMap) {
        this.fileMap = fileMap;
    }
}
