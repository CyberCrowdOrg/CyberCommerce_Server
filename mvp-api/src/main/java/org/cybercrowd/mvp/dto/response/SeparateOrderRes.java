package org.cybercrowd.mvp.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

public class SeparateOrderRes implements Serializable {

    private String orderNo;
    private Long orderCoinId;
    private String orderCoinName;
    private BigDecimal orderCoinAmount;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getOrderCoinId() {
        return orderCoinId;
    }

    public void setOrderCoinId(Long orderCoinId) {
        this.orderCoinId = orderCoinId;
    }

    public String getOrderCoinName() {
        return orderCoinName;
    }

    public void setOrderCoinName(String orderCoinName) {
        this.orderCoinName = orderCoinName;
    }

    public BigDecimal getOrderCoinAmount() {
        return orderCoinAmount;
    }

    public void setOrderCoinAmount(BigDecimal orderCoinAmount) {
        this.orderCoinAmount = orderCoinAmount;
    }
}
