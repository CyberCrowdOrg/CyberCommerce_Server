package org.cybercrowd.mvp.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.cybercrowd.mvp.dto.BaseOptionDto;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.ProposalCreateReq;
import org.cybercrowd.mvp.enums.OptionTypeEnum;
import org.cybercrowd.mvp.enums.ReturnCodeEnum;
import org.cybercrowd.mvp.service.ProposalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class ProposalController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(ProposalController.class);

    @Autowired
    ProposalService proposalService;


    @PostMapping(value = "/proposal/v1/create")
    @ResponseBody
    public BaseResponse createProposal(ProposalCreateReq proposalCreateReq, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        proposalCreateReq.setUserId(getUserId(request));
        logger.info("DAO提案创建接口,请求入参:{}", JSON.toJSONString(proposalCreateReq));
        //验证参数
        String daoNo = proposalCreateReq.getDaoNo();
        if(StringUtils.isEmpty(daoNo)){
            logger.info("DAO提案创建接口, dao no 不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        String proposalTitle = proposalCreateReq.getProposalTitle();
        if(StringUtils.isEmpty(proposalTitle)){
            logger.info("DAO提案创建接口,proposal title 不能为空", JSON.toJSONString(proposalCreateReq));
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        String proposalContext = proposalCreateReq.getProposalContext();
        if(StringUtils.isEmpty(proposalContext)){
            logger.info("DAO提案创建接口,proposal context 不能为空", JSON.toJSONString(proposalCreateReq));
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        String optionList = proposalCreateReq.getOptionList();
        if(StringUtils.isEmpty(optionList)){
            logger.info("DAO提案创建接口, option list 不能为空 或者 size 小于2", JSON.toJSONString(proposalCreateReq));
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        String optionType = proposalCreateReq.getOptionType();
        if(StringUtils.isEmpty(optionType) || null == OptionTypeEnum.toEnum(optionType)){
            logger.info("DAO提案创建接口, option type 不能为空或者参数错误");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        String proposalStartTime = proposalCreateReq.getProposalStartTime();
        if(StringUtils.isEmpty(proposalStartTime)){
            logger.info("DAO提案创建接口,start time 不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        String proposalEndTime = proposalCreateReq.getProposalEndTime();
        if(StringUtils.isEmpty(proposalEndTime)){
            logger.info("DAO提案创建接口,end time 不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }

        if(proposalEndTime.compareTo(proposalStartTime) <= 0){
            logger.info("DAO提案创建接口,end time 不能小于等于 start time ");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }

        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
//        if (null == fileMap || fileMap.size() == 0) {
//            logger.info("DAO提案创建接口,图片不能为空");
//            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
//            return baseResponse;
//        }
        proposalCreateReq.setFileMap(fileMap);

        baseResponse = proposalService.createProposal(proposalCreateReq);
        logger.info("DAO提案创建接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }
}
