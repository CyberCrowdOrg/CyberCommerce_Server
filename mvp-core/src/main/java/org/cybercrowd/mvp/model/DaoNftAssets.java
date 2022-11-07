package org.cybercrowd.mvp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * DAO NFT资产
 */
public class DaoNftAssets implements Serializable {
    private Long id;

    /**
     * DAO id
     */
    private String daoNo;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * nft文件类型 image 图片 audio 音频 video 视频
     */
    private String nftFileType;

    /**
     * nft 源文件路径
     */
    private String nftFilePath;

    /**
     * nft 名称
     */
    private String nftName;

    /**
     * nft描述
     */
    private String nftDescription;

    /**
     * nft tokenid
     */
    private String nftTokenId;

    /**
     * nft合约地址
     */
    private String nftContract;

    /**
     * nft数量
     */
    private Long nftNumber;

    /**
     * nft市场链接
     */
    private String nftMarketLink;

    /**
     * nft持有者
     */
    private String nftHolderAddress;

    /**
     * nft铸造者
     */
    private String nftMintAddress;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDaoNo() {
        return daoNo;
    }

    public void setDaoNo(String daoNo) {
        this.daoNo = daoNo;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getNftFileType() {
        return nftFileType;
    }

    public void setNftFileType(String nftFileType) {
        this.nftFileType = nftFileType;
    }

    public String getNftFilePath() {
        return nftFilePath;
    }

    public void setNftFilePath(String nftFilePath) {
        this.nftFilePath = nftFilePath;
    }

    public String getNftName() {
        return nftName;
    }

    public void setNftName(String nftName) {
        this.nftName = nftName;
    }

    public String getNftDescription() {
        return nftDescription;
    }

    public void setNftDescription(String nftDescription) {
        this.nftDescription = nftDescription;
    }

    public String getNftTokenId() {
        return nftTokenId;
    }

    public void setNftTokenId(String nftTokenId) {
        this.nftTokenId = nftTokenId;
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

    public String getNftMarketLink() {
        return nftMarketLink;
    }

    public void setNftMarketLink(String nftMarketLink) {
        this.nftMarketLink = nftMarketLink;
    }

    public String getNftHolderAddress() {
        return nftHolderAddress;
    }

    public void setNftHolderAddress(String nftHolderAddress) {
        this.nftHolderAddress = nftHolderAddress;
    }

    public String getNftMintAddress() {
        return nftMintAddress;
    }

    public void setNftMintAddress(String nftMintAddress) {
        this.nftMintAddress = nftMintAddress;
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