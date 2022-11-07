package org.cybercrowd.mvp.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BaseOrderDto implements Serializable {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单类型, 充值 recharge 、提现 withdraw、付款 payment、退款 refund
     */
    private String orderType;

    /**
     * 币种ID
     */
    private Long orderCoinId;

    /**
     * 币种名称
     */
    private String orderCoinName;

    /**
     * 订单加密币金额
     */
    private BigDecimal orderCoinAmount;

    /**
     * 付款用户ID
     */
    private Long userId;

    /**
     * 付款地址
     */
    private String payerAddress;

    /**
     * 收款地址
     */
    private String payeeAddress;

    /**
     * 订单状态 0 未付款 5 付款中 6 付款成功 7 付款失败 8 退款
     */
    private String orderStatus;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 订单付款成功时间
     */
    private Date successTime;

    /**
     * 退款时间
     */
    private Date refundTime;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPayerAddress() {
        return payerAddress;
    }

    public void setPayerAddress(String payerAddress) {
        this.payerAddress = payerAddress;
    }

    public String getPayeeAddress() {
        return payeeAddress;
    }

    public void setPayeeAddress(String payeeAddress) {
        this.payeeAddress = payeeAddress;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }
}
