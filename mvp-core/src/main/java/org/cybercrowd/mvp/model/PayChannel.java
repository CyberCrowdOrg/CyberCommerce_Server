package org.cybercrowd.mvp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 支付通道
 */
public class PayChannel implements Serializable {
    private Long id;

    /**
     * 通道ID
     */
    private String channelId;

    /**
     * 通道名称
     */
    private String channelName;

    /**
     * 通道logo
     */
    private String channelLogo;

    /**
     * 支持币种类型，fiat 法币 cypto
     */
    private String supportCoinType;

    /**
     * 通道币种ID
     */
    private String channelCoinId;

    /**
     * 通道币种名称
     */
    private String channelCoinName;

    /**
     * 币种图标
     */
    private String channelCoinIcon;

    /**
     * 币种ID，系统所属币种ID
     */
    private Long systemCoinId;

    /**
     * 通道api url
     */
    private String channelApiUrl;

    /**
     * 通道api key，通道分配
     */
    private String channelApiKey;

    /**
     * 通道商户ID，通道分配
     */
    private String channelMerchantId;

    /**
     * 是否支持支持退款, 0 不支持 1 
     */
    private String supportRefund;

    /**
     * 退款通道ID
     */
    private Long refundChannelId;

    /**
     * 通道状态 0 不可用 1 可用
     */
    private String channelStatus;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelLogo() {
        return channelLogo;
    }

    public void setChannelLogo(String channelLogo) {
        this.channelLogo = channelLogo;
    }

    public String getSupportCoinType() {
        return supportCoinType;
    }

    public void setSupportCoinType(String supportCoinType) {
        this.supportCoinType = supportCoinType;
    }

    public String getChannelCoinId() {
        return channelCoinId;
    }

    public void setChannelCoinId(String channelCoinId) {
        this.channelCoinId = channelCoinId;
    }

    public String getChannelCoinName() {
        return channelCoinName;
    }

    public void setChannelCoinName(String channelCoinName) {
        this.channelCoinName = channelCoinName;
    }

    public String getChannelCoinIcon() {
        return channelCoinIcon;
    }

    public void setChannelCoinIcon(String channelCoinIcon) {
        this.channelCoinIcon = channelCoinIcon;
    }

    public Long getSystemCoinId() {
        return systemCoinId;
    }

    public void setSystemCoinId(Long systemCoinId) {
        this.systemCoinId = systemCoinId;
    }

    public String getChannelApiUrl() {
        return channelApiUrl;
    }

    public void setChannelApiUrl(String channelApiUrl) {
        this.channelApiUrl = channelApiUrl;
    }

    public String getChannelApiKey() {
        return channelApiKey;
    }

    public void setChannelApiKey(String channelApiKey) {
        this.channelApiKey = channelApiKey;
    }

    public String getChannelMerchantId() {
        return channelMerchantId;
    }

    public void setChannelMerchantId(String channelMerchantId) {
        this.channelMerchantId = channelMerchantId;
    }

    public String getSupportRefund() {
        return supportRefund;
    }

    public void setSupportRefund(String supportRefund) {
        this.supportRefund = supportRefund;
    }

    public Long getRefundChannelId() {
        return refundChannelId;
    }

    public void setRefundChannelId(Long refundChannelId) {
        this.refundChannelId = refundChannelId;
    }

    public String getChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(String channelStatus) {
        this.channelStatus = channelStatus;
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