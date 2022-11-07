package org.cybercrowd.mvp.service;

import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.ProposalCreateReq;
import org.cybercrowd.mvp.dto.request.ProposalDetailsReq;

public interface ProposalService {

    BaseResponse createProposal(ProposalCreateReq proposalCreateReq);

    BaseResponse proposalDetails(ProposalDetailsReq proposalDetailsReq);
}
