package org.cybercrowd.mvp.service;

import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.*;
import org.cybercrowd.mvp.enums.DaoEventEnum;

public interface DaoService {

    BaseResponse createDaoOrganize(Long userId,String taskId, DaoEventEnum daoEventEnum);

    BaseResponse joinDaoOrganize(Long userId,String taskId);

    BaseResponse updateDaoOrganize(DaoOrganizeUpdateReq daoOrganizeUpdateReq);

    BaseResponse createDaoEvent(Long userId, String taskId,String proposalNo, DaoEventEnum daoEventEnum);

    BaseResponse userDaoList(UserDaoListReq userDaoListReq);

    BaseResponse userDaoDetails(UserDaoDetailsReq userDaoDetailsReq);

    BaseResponse daoAll(DaoAllReq daoAllReq);

    BaseResponse userDaoDetailsV2(UserDaoDetailsReq userDaoDetailsReq);

    BaseResponse daoNftMint(DaoNftMintReq daoNftMintReq);
}
