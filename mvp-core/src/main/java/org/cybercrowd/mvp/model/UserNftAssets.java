package org.cybercrowd.mvp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 用户NFT资产
 */
public class UserNftAssets implements Serializable {
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * nft编号
     */
    private String nftNo;

    /**
     * nft名称
     */
    private String nftName;

    /**
     * nft合约
     */
    private String nftContract;

    /**
     * nft数量
     */
    private Long nftNumber;

    /**
     * 创建时间
     */
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

    public String getNftNo() {
        return nftNo;
    }

    public void setNftNo(String nftNo) {
        this.nftNo = nftNo;
    }

    public String getNftName() {
        return nftName;
    }

    public void setNftName(String nftName) {
        this.nftName = nftName;
    }

    public String getNftContract() {
        return nftContract;
    }

    public void setNftContract(String nftContract) {
        this.nftContract = nftContract;
    }

    public Long getNftNumber() {
        return nftNumber;
    }

    public void setNftNumber(Long nftNumber) {
        this.nftNumber = nftNumber;
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