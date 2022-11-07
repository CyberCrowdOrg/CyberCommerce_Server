package org.cybercrowd.mvp.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserWalletBalanceRes implements Serializable {

    private Long coinId;

    private String coinName;

    private String coinImageUrl;

    private String walletAddr;

    private BigDecimal balance;

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getWalletAddr() {
        return walletAddr;
    }

    public void setWalletAddr(String walletAddr) {
        this.walletAddr = walletAddr;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public String getCoinImageUrl() {
        return coinImageUrl;
    }

    public void setCoinImageUrl(String coinImageUrl) {
        this.coinImageUrl = coinImageUrl;
    }
}
