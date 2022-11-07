package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

public class PayOrderUpdateReq implements Serializable {

    private String payNo;
    private Long payCoinId;
    private BigDecimal payAmount;
    private String payStatus;

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public Long getPayCoinId() {
        return payCoinId;
    }

    public void setPayCoinId(Long payCoinId) {
        this.payCoinId = payCoinId;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }
}
