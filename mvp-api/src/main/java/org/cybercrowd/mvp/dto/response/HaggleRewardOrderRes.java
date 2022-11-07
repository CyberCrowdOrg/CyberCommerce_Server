package org.cybercrowd.mvp.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

public class HaggleRewardOrderRes implements Serializable {

    private BigDecimal haggleAmount;
    private BigDecimal haggleRewardAmount;

    public BigDecimal getHaggleAmount() {
        return haggleAmount;
    }

    public void setHaggleAmount(BigDecimal haggleAmount) {
        this.haggleAmount = haggleAmount;
    }

    public BigDecimal getHaggleRewardAmount() {
        return haggleRewardAmount;
    }

    public void setHaggleRewardAmount(BigDecimal haggleRewardAmount) {
        this.haggleRewardAmount = haggleRewardAmount;
    }
}
