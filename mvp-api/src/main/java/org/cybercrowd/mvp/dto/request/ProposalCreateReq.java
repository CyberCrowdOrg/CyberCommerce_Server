package org.cybercrowd.mvp.dto.request;

import org.cybercrowd.mvp.dto.BaseOptionDto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ProposalCreateReq implements Serializable {

    private Long userId;

    private String daoNo;
    private String optionType;
    private String proposalTitle;
    private String proposalContext;

    private String proposalStartTime;
    private String proposalEndTime;

    private Object fileMap;

    private String optionList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDaoNo() {
        return daoNo;
    }

    public void setDaoNo(String daoNo) {
        this.daoNo = daoNo;
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

    public String getOptionList() {
        return optionList;
    }

    public void setOptionList(String optionList) {
        this.optionList = optionList;
    }

    public String getProposalStartTime() {
        return proposalStartTime;
    }

    public void setProposalStartTime(String proposalStartTime) {
        this.proposalStartTime = proposalStartTime;
    }

    public String getProposalEndTime() {
        return proposalEndTime;
    }

    public void setProposalEndTime(String proposalEndTime) {
        this.proposalEndTime = proposalEndTime;
    }

    public Object getFileMap() {
        return fileMap;
    }

    public void setFileMap(Object fileMap) {
        this.fileMap = fileMap;
    }
}
