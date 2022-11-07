package org.cybercrowd.mvp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 基础币种
 */
public class BaseCoin implements Serializable {
    private Long id;

    /**
     * 币种ID
     */
    private Long coinId;

    /**
     * 父币种ID
     */
    private Long parentCoinId;

    /**
     * 币种名称
     */
    private String coinName;

    /**
     * 币种全称
     */
    private String coinFullName;

    /**
     * 币类型,token blockchain
     */
    private String coinType;

    /**
     * 合约地址
     */
    private String contractAddr;

    /**
     * 币种小数位精度
     */
    private Long coinDecimal;

    /**
     * 是否默认钱包币种 0 不是1 是 
     */
    private String defaultWallet;

    /**
     * 是否默认支持充值的币种, 0 否 1 是
     */
    private String defaultRecharge;

    /**
     * 币种图片
     */
    private String coinImage;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public Long getParentCoinId() {
        return parentCoinId;
    }

    public void setParentCoinId(Long parentCoinId) {
        this.parentCoinId = parentCoinId;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getCoinFullName() {
        return coinFullName;
    }

    public void setCoinFullName(String coinFullName) {
        this.coinFullName = coinFullName;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public String getContractAddr() {
        return contractAddr;
    }

    public void setContractAddr(String contractAddr) {
        this.contractAddr = contractAddr;
    }

    public Long getCoinDecimal() {
        return coinDecimal;
    }

    public void setCoinDecimal(Long coinDecimal) {
        this.coinDecimal = coinDecimal;
    }

    public String getDefaultWallet() {
        return defaultWallet;
    }

    public void setDefaultWallet(String defaultWallet) {
        this.defaultWallet = defaultWallet;
    }

    public String getDefaultRecharge() {
        return defaultRecharge;
    }

    public void setDefaultRecharge(String defaultRecharge) {
        this.defaultRecharge = defaultRecharge;
    }

    public String getCoinImage() {
        return coinImage;
    }

    public void setCoinImage(String coinImage) {
        this.coinImage = coinImage;
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