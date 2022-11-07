package org.cybercrowd.mvp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 * 用户钱包
 */
public class UserWalletInfo implements Serializable {
    private Long id;

    private Long userId;

    private String walletAddr;

    private Long coinId;

    private String coinName;

    /**
     * 累计充值金额
     */
    private BigDecimal totalRechargeAmt;

    /**
     * 累计入账金额
     */
    private BigDecimal totalInAmt;

    /**
     * 累计出账金额
     */
    private BigDecimal totalOutAmt;

    /**
     * 累计提现金额
     */
    private BigDecimal totalWithdrawalAmt;

    /**
     * 可用金额
     */
    private BigDecimal availableAmt;

    /**
     * 冻结金额
     */
    private BigDecimal frozenAmt;

    /**
     * 账户状态 1 正常 2 冻结
     */
    private String accountStatus;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getWalletAddr() {
        return walletAddr;
    }

    public void setWalletAddr(String walletAddr) {
        this.walletAddr = walletAddr;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public BigDecimal getTotalRechargeAmt() {
        return totalRechargeAmt;
    }

    public void setTotalRechargeAmt(BigDecimal totalRechargeAmt) {
        this.totalRechargeAmt = totalRechargeAmt;
    }

    public BigDecimal getTotalInAmt() {
        return totalInAmt;
    }

    public void setTotalInAmt(BigDecimal totalInAmt) {
        this.totalInAmt = totalInAmt;
    }

    public BigDecimal getTotalOutAmt() {
        return totalOutAmt;
    }

    public void setTotalOutAmt(BigDecimal totalOutAmt) {
        this.totalOutAmt = totalOutAmt;
    }

    public BigDecimal getTotalWithdrawalAmt() {
        return totalWithdrawalAmt;
    }

    public void setTotalWithdrawalAmt(BigDecimal totalWithdrawalAmt) {
        this.totalWithdrawalAmt = totalWithdrawalAmt;
    }

    public BigDecimal getAvailableAmt() {
        return availableAmt;
    }

    public void setAvailableAmt(BigDecimal availableAmt) {
        this.availableAmt = availableAmt;
    }

    public BigDecimal getFrozenAmt() {
        return frozenAmt;
    }

    public void setFrozenAmt(BigDecimal frozenAmt) {
        this.frozenAmt = frozenAmt;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
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