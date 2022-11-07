package org.cybercrowd.mvp.dto;

import java.io.Serializable;

public class BasePayChannelDto implements Serializable {

    private String channelId;
    private String channelName;
    private String channelLogo;
    // fiat cypto
    private String supportCoinType;
    private String coinName;
    private String coinIcon;

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

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getCoinIcon() {
        return coinIcon;
    }

    public void setCoinIcon(String coinIcon) {
        this.coinIcon = coinIcon;
    }
}
