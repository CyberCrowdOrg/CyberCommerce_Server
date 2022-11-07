package org.cybercrowd.mvp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 提案信息
 */
public class Proposal implements Serializable {
    private Long id;

    /**
     * dao编号
     */
    private String daoNo;

    /**
     * 提案编号
     */
    private String proposalNo;

    /**
     * 提案类型,社区提案 community、任务Dao提案 task
     */
    private String proposalType;

    /**
     * 提案选项类型, 单选 0 多选 1
     */
    private String optionType;

    /**
     * 提案标题
     */
    private String proposalTitle;

    /**
     * 提案内容
     */
    private String proposalContext;

    /**
     * 提案地址
     */
    private String proposalAddr;

    private Date proposalStartTime;

    private Date proposalEndTime;

    /**
     * 提案状态，0 未开始 1 进行中 2 已结束
     */
    private String proposalStatus;

    /**
     * 提案用户
     */
    private Long ownerUserId;

    /**
     * 提案用户钱包地址
     */
    private String ownerUserAddr;

    private String imageUrl;

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

    public String getProposalNo() {
        return proposalNo;
    }

    public void setProposalNo(String proposalNo) {
        this.proposalNo = proposalNo;
    }

    public String getProposalType() {
        return proposalType;
    }

    public void setProposalType(String proposalType) {
        this.proposalType = proposalType;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getProposalTitle() {
        return proposalTitle;
    }

    public void setProposalTitle(String proposalTitle) {
        this.proposalTitle = proposalTitle;
    }

    public String getProposalContext() {
        return proposalContext;
    }

    public void setProposalContext(String proposalContext) {
        this.proposalContext = proposalContext;
    }

    public String getProposalAddr() {
        return proposalAddr;
    }

    public void setProposalAddr(String proposalAddr) {
        this.proposalAddr = proposalAddr;
    }

    public Date getProposalStartTime() {
        return proposalStartTime;
    }

    public void setProposalStartTime(Date proposalStartTime) {
        this.proposalStartTime = proposalStartTime;
    }

    public Date getProposalEndTime() {
        return proposalEndTime;
    }

    public void setProposalEndTime(Date proposalEndTime) {
        this.proposalEndTime = proposalEndTime;
    }

    public String getProposalStatus() {
        return proposalStatus;
    }

    public void setProposalStatus(String proposalStatus) {
        this.proposalStatus = proposalStatus;
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getOwnerUserAddr() {
        return ownerUserAddr;
    }

    public void setOwnerUserAddr(String ownerUserAddr) {
        this.ownerUserAddr = ownerUserAddr;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}