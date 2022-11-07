package org.cybercrowd.mvp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * NFT素材
 */
public class NftMaterial implements Serializable {
    private Long id;

    private String taskId;

    /**
     * nft文件类型 image 图片 audio 音频 video 视频
     */
    private String nftFileType;

    /**
     * 文件路径
     */
    private String filePath;

    private Date createTime;

    private Date updateTime;

    /**
     * 铸造状态 0 未铸造 1 已铸造 2 铸造中
     */
    private String castingStatus;

    /**
     * 铸造成功时间
     */
    private Date castionSuccTime;

    /**
     * nft所属钱包地址
     */
    private String nftOwnerAddr;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    public String getCastingStatus() {
        return castingStatus;
    }

    public void setCastingStatus(String castingStatus) {
        this.castingStatus = castingStatus;
    }

    public Date getCastionSuccTime() {
        return castionSuccTime;
    }

    public void setCastionSuccTime(Date castionSuccTime) {
        this.castionSuccTime = castionSuccTime;
    }

    public String getNftOwnerAddr() {
        return nftOwnerAddr;
    }

    public void setNftOwnerAddr(String nftOwnerAddr) {
        this.nftOwnerAddr = nftOwnerAddr;
    }
}