package org.cybercrowd.mvp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 投票记录
 */
public class BallotRecord implements Serializable {
    private Long id;

    private String proposalNo;

    private String ballotUserId;

    private String ballotOptionCode;

    private Long vote;

    private Date ballotTime;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProposalNo() {
        return proposalNo;
    }

    public void setProposalNo(String proposalNo) {
        this.proposalNo = proposalNo;
    }

    public String getBallotUserId() {
        return ballotUserId;
    }

    public void setBallotUserId(String ballotUserId) {
        this.ballotUserId = ballotUserId;
    }

    public String getBallotOptionCode() {
        return ballotOptionCode;
    }

    public void setBallotOptionCode(String ballotOptionCode) {
        this.ballotOptionCode = ballotOptionCode;
    }

    public Long getVote() {
        return vote;
    }

    public void setVote(Long vote) {
        this.vote = vote;
    }

    public Date getBallotTime() {
        return ballotTime;
    }

    public void setBallotTime(Date ballotTime) {
        this.ballotTime = ballotTime;
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