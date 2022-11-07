package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GrouponRulesDto implements Serializable {

    //团购价格
    private BigDecimal price;
    //最小参与人数
    private int participantsMin;
    //最大参与人数
    private int participantsMax;
    //团长奖励比例
    private BigDecimal ownerRewardRatio;
    //成员奖励比例
    private BigDecimal memberRewardRatio;
    //有效天数
    private int expirationDay;
    //盲盒总奖励金额
    private BigDecimal blindBoxTotalRewardAmount;
    //砍价最小金额
    private BigDecimal haggleMinAmount;
    //砍价最大金额
    private BigDecimal haggleMaxAmount;
    //砍价奖励金额
    private BigDecimal haggleRewardAmount;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getParticipantsMin() {
        return participantsMin;
    }

    public void setParticipantsMin(int participantsMin) {
        this.participantsMin = participantsMin;
    }

    public int getParticipantsMax() {
        return participantsMax;
    }

    public void setParticipantsMax(int participantsMax) {
        this.participantsMax = participantsMax;
    }

    public BigDecimal getOwnerRewardRatio() {
        return ownerRewardRatio;
    }

    public void setOwnerRewardRatio(BigDecimal ownerRewardRatio) {
        this.ownerRewardRatio = ownerRewardRatio;
    }

    public int getExpirationDay() {
        return expirationDay;
    }

    public void setExpirationDay(int expirationDay) {
        this.expirationDay = expirationDay;
    }

    public BigDecimal getBlindBoxTotalRewardAmount() {
        return blindBoxTotalRewardAmount;
    }

    public void setBlindBoxTotalRewardAmount(BigDecimal blindBoxTotalRewardAmount) {
        this.blindBoxTotalRewardAmount = blindBoxTotalRewardAmount;
    }

    public BigDecimal getHaggleMinAmount() {
        return haggleMinAmount;
    }

    public void setHaggleMinAmount(BigDecimal haggleMinAmount) {
        this.haggleMinAmount = haggleMinAmount;
    }

    public BigDecimal getHaggleMaxAmount() {
        return haggleMaxAmount;
    }

    public void setHaggleMaxAmount(BigDecimal haggleMaxAmount) {
        this.haggleMaxAmount = haggleMaxAmount;
    }

    public BigDecimal getHaggleRewardAmount() {
        return haggleRewardAmount;
    }

    public void setHaggleRewardAmount(BigDecimal haggleRewardAmount) {
        this.haggleRewardAmount = haggleRewardAmount;
    }

    public BigDecimal getMemberRewardRatio() {
        return memberRewardRatio;
    }

    public void setMemberRewardRatio(BigDecimal memberRewardRatio) {
        this.memberRewardRatio = memberRewardRatio;
    }
}
