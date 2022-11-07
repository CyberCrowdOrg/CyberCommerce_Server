package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;
import java.util.List;

public class ProposalVoteReq implements Serializable {

    private String proposalNo;
    private Long userId;
    private List<String> optionCodeList;

    public String getProposalNo() {
        return proposalNo;
    }

    public void setProposalNo(String proposalNo) {
        this.proposalNo = proposalNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<String> getOptionCodeList() {
        return optionCodeList;
    }

    public void setOptionCodeList(List<String> optionCodeList) {
        this.optionCodeList = optionCodeList;
    }
}
