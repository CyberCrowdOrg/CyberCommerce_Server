package org.cybercrowd.mvp.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.cybercrowd.mvp.constant.AwsS3UploadDirectoryConstants;
import org.cybercrowd.mvp.dto.BaseOptionDto;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.ProposalCreateReq;
import org.cybercrowd.mvp.dto.request.ProposalDetailsReq;
import org.cybercrowd.mvp.enums.ProposalStatusEnum;
import org.cybercrowd.mvp.enums.ReturnCodeEnum;
import org.cybercrowd.mvp.mapper.*;
import org.cybercrowd.mvp.model.*;
import org.cybercrowd.mvp.service.DaoService;
import org.cybercrowd.mvp.service.ProposalService;
import org.cybercrowd.mvp.util.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("proposalService")
public class ProposalServiceImpl implements ProposalService {

    private Logger logger = LoggerFactory.getLogger(ProposalServiceImpl.class);

    @Autowired
    ProposalMapper proposalMapper;
    @Autowired
    ProposalOptionsMapper proposalOptionsMapper;
    @Autowired
    DaoMemberMapper daoMemberMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    DaoOrganizeMapper organizeMapper;

    @Autowired
    AwsS3StorageService awsS3StorageService;

    @Autowired
    NftMintService nftMintService;

    @Override
    public BaseResponse createProposal(ProposalCreateReq proposalCreateReq) {
        BaseResponse baseResponse = new BaseResponse();
        Long userId = proposalCreateReq.getUserId();
        String daoNo = proposalCreateReq.getDaoNo();
        try {
            //检查创建提案的用户是否 DAO成员
            DaoMember member = daoMemberMapper.findMember(userId, daoNo);
            if (null == member) {
                logger.info("DAO提案创建业务,创建用户不是DAO成员,没有提案创建权限");
                baseResponse.setReturnCodeEnum(ReturnCodeEnum.PROPOSAL_CREATE_PERMISSION_DENIED_ERROR);
                return baseResponse;
            }
            //检查选项是否重复
            List<BaseOptionDto> optionList = JSON.parseArray(proposalCreateReq.getOptionList(),BaseOptionDto.class);
            BaseResponse checkResponse = checkRepeatOption(optionList);
            if (null != checkResponse) {
                logger.info("DAO提案创建业务,创建提案失败,存在重复提案选项");
                return checkResponse;
            }
            //创建提案
            Proposal proposal = createDaoProposal(proposalCreateReq);
            //创建提案选项
            List<ProposalOptions> options = createProposalOptions(proposal.getProposalNo(), optionList);
            //保存数据
            proposalOptionsMapper.batchInsertOptions(options);
            proposalMapper.insertSelective(proposal);

//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
                    //NFT铸造
                    Map<String, MultipartFile> fileMap = (Map<String, MultipartFile>) proposalCreateReq.getFileMap();
                    if (null != fileMap && fileMap.size() > 0) {
                        DaoOrganize daoOrganize = organizeMapper.selectDaoOrganize(daoNo);
                        nftMintService.daoNftMint(fileMap, daoNo, daoOrganize.getTaskId(), "0x34BB184bFb7B7795cc1Ded050deA1344c392A7C1");

                    }
//                }
//            });
//            thread.start();
        }catch (Exception e){
            logger.error("DAO提案创建业务,执行异常:{}",e.getMessage(),e);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            return baseResponse;
        }
        logger.info("DAO提案创建业务,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @Override
    public BaseResponse proposalDetails(ProposalDetailsReq proposalDetailsReq) {

        return null;
    }

    private List<ProposalOptions> createProposalOptions(String proposalNo, List<BaseOptionDto> optionList) {
        List<ProposalOptions> proposalOptionsList = new ArrayList<>();
        for(BaseOptionDto baseOptionDto:optionList){
            ProposalOptions proposalOptions = new ProposalOptions();
            proposalOptions.setProposalNo(proposalNo);
            proposalOptions.setOptionCode(baseOptionDto.getOptionCode());
            proposalOptions.setOptionName(baseOptionDto.getOptionName());
            proposalOptions.setVoteCount(0l);
            proposalOptions.setCreateTime(new Date());
            proposalOptions.setUpdateTime(new Date());
            proposalOptionsList.add(proposalOptions);
        }
        return proposalOptionsList;
    }

    private Proposal createDaoProposal(ProposalCreateReq proposalCreateReq) {
        Long userId = proposalCreateReq.getUserId();
        Proposal proposal = new Proposal();
        proposal.setDaoNo(proposalCreateReq.getDaoNo());
        proposal.setProposalNo(IDUtils.getProposalNo());
        proposal.setProposalTitle(proposalCreateReq.getProposalTitle());
        proposal.setProposalContext(proposalCreateReq.getProposalContext());
        proposal.setProposalStartTime(new Date(Long.valueOf(proposalCreateReq.getProposalStartTime())));
        proposal.setProposalEndTime(new Date(Long.valueOf(proposalCreateReq.getProposalEndTime())));
        proposal.setProposalStatus(ProposalStatusEnum.NOT_START.getCode());
        proposal.setOptionType(proposalCreateReq.getOptionType());
        proposal.setOwnerUserId(userId);
        proposal.setCreateTime(new Date());
        proposal.setUpdateTime(new Date());
        UserInfo userInfo = userInfoMapper.queryUserByUserId(userId);
        proposal.setOwnerUserAddr(userInfo.getUserWalletAddr());
        return proposal;
    }

    private BaseResponse checkRepeatOption(List<BaseOptionDto> optionList) {
        BaseResponse baseResponse = new BaseResponse();
        List<String> options = new ArrayList<>();
        for(BaseOptionDto baseOptionDto:optionList){
            if(!options.contains(baseOptionDto.getOptionCode())){
                options.add(baseOptionDto.getOptionCode());
            }else {
                baseResponse.setReturnCodeEnum(ReturnCodeEnum.PROPOSAL_OPTION_REPEAT_ERROR);
                return baseResponse;
            }
        }
        return null;
    }
}
