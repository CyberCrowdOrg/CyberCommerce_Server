package org.cybercrowd.mvp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 * 
 */
public class PayOrder implements Serializable {
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 原支付单号,退款时使用
     */
    private String orgPayNo;

    /**
     * 支付单号
     */
    private String payNo;

    /**
     * 通道ID
     */
    private String channelId;

    /**
     * 上游流水号
     */
    private String channelFlowNo;

    /**
     * 付款方式 balance 余额 chain 链上付款 online 在线支付
     */
    private String payMethod;

    /**
     * 支付订单类型 pay 付款  refund 退款
     */
    private String payOrderType;

    /**
     * 收款地址
     */
    private String receiveAddress;

    /**
     * 订单币种ID
     */
    private Long orderCoinId;

    /**
     * 订单币种名称
     */
    private String orderCoinName;

    /**
     * 收款币种ID
     */
    private Long receiveCoinId;

    /**
     * 收款币种名称
     */
    private String receiveCoinName;

    /**
     * 收款金额
     */
    private BigDecimal receiveAmount;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单汇率
     */
    private BigDecimal orderExchangeRate;

    /**
     * 支付状态, 0 待付款 5 付款中  6 付款成功 7付款失败 8 已退款
     */
    private String payStatus;

    /**
     * 付款成功时间
     */
    private Date paySuccessTime;

    private Date createTime;

    private Date updateTime;

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

    public String getOrgPayNo() {
        return orgPayNo;
    }

    public void setOrgPayNo(String orgPayNo) {
        this.orgPayNo = orgPayNo;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelFlowNo() {
        return channelFlowNo;
    }

    public void setChannelFlowNo(String channelFlowNo) {
        this.channelFlowNo = channelFlowNo;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getPayOrderType() {
        return payOrderType;
    }

    public void setPayOrderType(String payOrderType) {
        this.payOrderType = payOrderType;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
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

    public Long getReceiveCoinId() {
        return receiveCoinId;
    }

    public void setReceiveCoinId(Long receiveCoinId) {
        this.receiveCoinId = receiveCoinId;
    }

    public String getReceiveCoinName() {
        return receiveCoinName;
    }

    public void setReceiveCoinName(String receiveCoinName) {
        this.receiveCoinName = receiveCoinName;
    }

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getOrderExchangeRate() {
        return orderExchangeRate;
    }

    public void setOrderExchangeRate(BigDecimal orderExchangeRate) {
        this.orderExchangeRate = orderExchangeRate;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public Date getPaySuccessTime() {
        return paySuccessTime;
    }

    public void setPaySuccessTime(Date paySuccessTime) {
        this.paySuccessTime = paySuccessTime;
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
}