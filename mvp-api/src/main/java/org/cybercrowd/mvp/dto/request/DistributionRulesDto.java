package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

public class DistributionRulesDto implements Serializable {

    //采购量
    private int purchaseVolume;
    //分销最少数量
    private int distributionMin;
    //分销价格
    private BigDecimal distributionPrice;
    //质押金额
    private BigDecimal pledgeAmount;
    //分销商奖励比例
    private BigDecimal distributionRewardRatio;
    //有效天数
    private int expirationDay;
    //未完成分销任务罚金比例
    private BigDecimal penaltyRatio;

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

    public BigDecimal getPledgeAmount() {
        return pledgeAmount;
    }

    public void setPledgeAmount(BigDecimal pledgeAmount) {
        this.pledgeAmount = pledgeAmount;
    }

    public BigDecimal getDistributionRewardRatio() {
        return distributionRewardRatio;
    }

    public void setDistributionRewardRatio(BigDecimal distributionRewardRatio) {
        this.distributionRewardRatio = distributionRewardRatio;
    }

    public int getExpirationDay() {
        return expirationDay;
    }

    public void setExpirationDay(int expirationDay) {
        this.expirationDay = expirationDay;
    }

    public BigDecimal getPenaltyRatio() {
        return penaltyRatio;
    }

    public void setPenaltyRatio(BigDecimal penaltyRatio) {
        this.penaltyRatio = penaltyRatio;
    }

    public int getPurchaseVolume() {
        return purchaseVolume;
    }

    public void setPurchaseVolume(int purchaseVolume) {
        this.purchaseVolume = purchaseVolume;
    }
}
