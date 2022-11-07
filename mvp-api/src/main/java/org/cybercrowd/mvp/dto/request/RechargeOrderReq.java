package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

public class RechargeOrderReq implements Serializable {

    private Long coinId;

    private Long userId;

    private BigDecimal amount;

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
