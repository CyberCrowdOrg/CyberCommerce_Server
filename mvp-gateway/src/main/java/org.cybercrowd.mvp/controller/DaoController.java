package org.cybercrowd.mvp.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.*;
import org.cybercrowd.mvp.enums.ReturnCodeEnum;
import org.cybercrowd.mvp.service.DaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DaoController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(DaoController.class);

    @Autowired
    DaoService daoService;

    @PostMapping(value = "/dao/v1/list")
    @ResponseBody
    public BaseResponse userDaoList(@RequestBody UserDaoListReq userDaoListReq, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        userDaoListReq.setUserId(getUserId(request));
        logger.info("用户DAO列表查询接口,请求入参:{}", JSON.toJSONString(userDaoListReq));
        baseResponse = daoService.userDaoList(userDaoListReq);
        logger.info("用户DAO列表查询接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @PostMapping(value = "/dao/v1/details")
    @ResponseBody
    public BaseResponse daoDetails(@RequestBody UserDaoDetailsReq userDaoDetailsReq, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        userDaoDetailsReq.setUserId(getUserId(request));
        logger.info("用户DAO列表查询接口,请求入参:{}", JSON.toJSONString(userDaoDetailsReq));
        String daoNo = userDaoDetailsReq.getDaoNo();
        if(StringUtils.isEmpty(daoNo)){
            logger.info("用户DAO列表查询接口, dao no 不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        baseResponse = daoService.userDaoDetails(userDaoDetailsReq);
        logger.info("用户DAO列表查询接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @PostMapping(value = "/dao/v1/all")
    @ResponseBody
    public BaseResponse daoAll(@RequestBody DaoAllReq daoAllReq){
        BaseResponse baseResponse = new BaseResponse();
        logger.info("所有DAO查询接口,请求入参:{}",JSON.toJSONString(daoAllReq));
        baseResponse = daoService.daoAll(daoAllReq);
        logger.info("所有DAO查询接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @PostMapping(value = "/dao/v2/details")
    @ResponseBody
    public BaseResponse daoDetailsV2(@RequestBody UserDaoDetailsReq userDaoDetailsReq, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        userDaoDetailsReq.setUserId(getUserId(request));
        logger.info("用户DAO列表查询接口V2,请求入参:{}", JSON.toJSONString(userDaoDetailsReq));
        String daoNo = userDaoDetailsReq.getDaoNo();
        if(StringUtils.isEmpty(daoNo)){
            logger.info("用户DAO列表查询接口V2, dao no 不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        baseResponse = daoService.userDaoDetailsV2(userDaoDetailsReq);
        logger.info("用户DAO列表查询接口V2,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @PostMapping(value = "/dao/v1/nftMinit")
    @ResponseBody
    public BaseResponse daoNftMinit(DaoNftMintReq daoNftMintReq, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        daoNftMintReq.setUserId(getUserId(request));
        logger.info("DAO NFT铸造接口,请求入参:{}", JSON.toJSONString(daoNftMintReq));
        String daoNo = daoNftMintReq.getDaoNo();
        if(StringUtils.isEmpty(daoNo)){
            logger.info("DAO NFT铸造接口, dao no 不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        //处理NFT文件
        baseResponse = setFileMap(request);
        if(!ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
            return baseResponse;
        }else {
            daoNftMintReq.setNftFileMap(baseResponse.getData());
            baseResponse.setData(null);
        }

        baseResponse = daoService.daoNftMint(daoNftMintReq);
        logger.info("DAO NFT铸造接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }


    private BaseResponse setFileMap(HttpServletRequest request) {
        BaseResponse baseResponse = new BaseResponse();
        MultipartRequest multipartRequest = (MultipartRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        if(null == fileMap || fileMap.size() == 0){
            logger.info("NFT铸造,上传文件不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }else {
            Map<String,MultipartFile> nftFileMap = new HashMap<>();
            for (String key : fileMap.keySet()) {
                //NFT文件
                nftFileMap.put(key, fileMap.get(key));
            }
            baseResponse.setData(nftFileMap);
        }
        return baseResponse;
    }
}
