package org.cybercrowd.mvp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 * 交易订单
 */
public class OrderInfo implements Serializable {
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 支付单号
     */
    private String payNo;

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
     * 订单法币ID
     */
    private Long orderFiatId;

    /**
     * 订单法币金额
     */
    private BigDecimal orderFiatAmount;

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
     * 任务ID
     */
    private String taskId;

    /**
     * 订单状态 0 未付款 5 付款中 6 付款成功 7 付款失败 8 退款
     */
    private String orderStatus;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 订单更新时间
     */
    private Date updateTime;

    /**
     * 订单付款成功时间
     */
    private Date successTime;

    /**
     * 退款时间
     */
    private Date refundTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getOrderFiatId() {
        return orderFiatId;
    }

    public void setOrderFiatId(Long orderFiatId) {
        this.orderFiatId = orderFiatId;
    }

    public BigDecimal getOrderFiatAmount() {
        return orderFiatAmount;
    }

    public void setOrderFiatAmount(BigDecimal orderFiatAmount) {
        this.orderFiatAmount = orderFiatAmount;
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }
}