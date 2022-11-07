package org.cybercrowd.mvp.controller;


import com.alibaba.fastjson2.JSON;
import org.apache.commons.lang3.StringUtils;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.UploadNftFileReq;
import org.cybercrowd.mvp.enums.ReturnCodeEnum;
import org.cybercrowd.mvp.service.NftMaterialService;
import org.cybercrowd.mvp.service.impl.NftMintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class NftMaterialController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(NftMaterialController.class);

    @Autowired
    NftMaterialService nftMaterialService;

    @Autowired
    NftMintService nftMintService;


    @RequestMapping(value = "/nftmaterial/v1/upload")
    @ResponseBody
    public BaseResponse uploadNftMaterial(UploadNftFileReq uploadNftFileReq, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        Long userId = getUserId(request);
        uploadNftFileReq.setUserId(userId);
        logger.info("NFT素材文件上传接口,请求入参:{}", JSON.toJSONString(uploadNftFileReq));
        String taskId = uploadNftFileReq.getTaskId();
        if(StringUtils.isEmpty(taskId)){
            logger.info("NFT素材文件上传接口, task id 不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
        if (null == fileMap || fileMap.size() == 0) {
            logger.info("NFT素材文件上传接口,NFT图片不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            return baseResponse;
        }
        uploadNftFileReq.setFileMap(fileMap);
        baseResponse = nftMaterialService.uploadNftFile(uploadNftFileReq);
        logger.info("NFT素材文件上传接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @PostMapping(value = "/nft/v1/mint")
    @ResponseBody
    public BaseResponse nftMint(HttpServletRequest request){
        MultipartRequest multipartRequest = (MultipartRequest) request;
        BaseResponse baseResponse = nftMintService.daoNftMint(multipartRequest.getFileMap(),
                "D15623519221224611", "T156234857020247654", "0x34BB184bFb7B7795cc1Ded050deA1344c392A7C1");
        return baseResponse;
    }
}
