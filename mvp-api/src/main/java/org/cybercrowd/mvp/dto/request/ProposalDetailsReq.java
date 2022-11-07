package org.cybercrowd.mvp.dto.request;


import java.io.Serializable;

public class ProposalDetailsReq implements Serializable {

    private String proposalNo;
    private Long userId;

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
}
