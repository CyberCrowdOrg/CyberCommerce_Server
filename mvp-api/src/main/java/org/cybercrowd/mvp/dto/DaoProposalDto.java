package org.cybercrowd.mvp.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DaoProposalDto implements Serializable {

    private String daoNo;
    private String proposalNo;
    private String optionType;
    private String proposalTitle;
    private String proposalContext;
    private String proposalAddr;
    private Date proposalEndTime;
    private String proposalStatus;
    private String userId;
    private String userAddr;
    private String userAvatar;
    private Long endTimeMillisecond;

    List<BaseOptionDto> optionList;

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


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public List<BaseOptionDto> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<BaseOptionDto> optionList) {
        this.optionList = optionList;
    }

    public Long getEndTimeMillisecond() {
        return endTimeMillisecond;
    }

    public void setEndTimeMillisecond(Long endTimeMillisecond) {
        this.endTimeMillisecond = endTimeMillisecond;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }
}
