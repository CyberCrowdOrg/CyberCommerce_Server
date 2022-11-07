package org.cybercrowd.mvp.controller;

import com.alibaba.fastjson2.JSON;
import org.apache.commons.lang3.StringUtils;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.*;
import org.cybercrowd.mvp.enums.ReturnCodeEnum;
import org.cybercrowd.mvp.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TaskController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    TaskService taskService;


    @PostMapping(value = "/task/user.task.list")
    @ResponseBody
    public BaseResponse userTaskList(@RequestBody UserTaskListReq userTaskListReq, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        userTaskListReq.setUserId(getUserId(request));
        logger.info("用户任务列表查询接口,请求入参:{}", JSON.toJSONString(userTaskListReq));
        baseResponse = taskService.userTaskList(userTaskListReq);
        logger.info("用户任务列表查询接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @PostMapping(value = "/task/v1/sale.publish")
    @ResponseBody
    public BaseResponse publishSaleTask(SaleTaskPublishReq saleTaskPublishReq,
                                        GrouponRulesDto grouponRulesDto,
                                        DistributionRulesDto distributionRulesDto, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        saleTaskPublishReq.setUserId(getUserId(request));
        distributionRulesDto.setExpirationDay(saleTaskPublishReq.getDistributionExpirationDay());
        saleTaskPublishReq.setDistributionRulesDto(distributionRulesDto);
        saleTaskPublishReq.setGrouponRulesDto(grouponRulesDto);
        logger.info("商品销售任务发布接口,请求入参:{}",JSON.toJSONString(saleTaskPublishReq));

        String goodsTitle = saleTaskPublishReq.getGoodsTitle();
        if(StringUtils.isEmpty(goodsTitle)){
            logger.info("商品销售任务发布接口, 商品标题不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        String goodsDetailsIntro = saleTaskPublishReq.getGoodsDetailsIntro();
        if(StringUtils.isEmpty(goodsDetailsIntro)){
            logger.info("商品销售任务发布接口, 商品详情不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        String goodsIntro = saleTaskPublishReq.getGoodsIntro();
        if(StringUtils.isEmpty(goodsIntro)){
            logger.info("商品销售任务发布接口, 商品介绍不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }

        BigDecimal goodsPrice = saleTaskPublishReq.getGoodsPrice();
        BigDecimal goodsSalePrice = saleTaskPublishReq.getGoodsSalePrice();
        if(null == goodsPrice || goodsPrice.compareTo(BigDecimal.ZERO) <= 0){
            logger.info("商品销售任务发布接口, 市场价格不能为空或者小于等于0");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }else if(null == goodsSalePrice || goodsSalePrice.compareTo(BigDecimal.ZERO) <= 0){
            logger.info("商品销售任务发布接口, 销售价格不能为空或者小于等于0");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }else if(goodsSalePrice.compareTo(goodsPrice) >=0){
            logger.info("商品销售任务发布接口,销售价格不能大于或者等于市场价格");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }

        DistributionRulesDto distributionRulesDto2 = saleTaskPublishReq.getDistributionRulesDto();
        if(null == distributionRulesDto2){
            logger.info("商品销售任务发布接口,分销规则不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }

        GrouponRulesDto grouponRulesDto2 = saleTaskPublishReq.getGrouponRulesDto();
        if(null == grouponRulesDto2){
            logger.info("商品销售任务发布接口,拼团规则不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }

        baseResponse = setFileMap(request, saleTaskPublishReq);
        if(!ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
            return baseResponse;
        }

        baseResponse = taskService.publishSaleTask(saleTaskPublishReq);
        logger.info("商品销售任务发布接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @PostMapping(value = "/task/v1/groupon.publish")
    @ResponseBody
    public BaseResponse publishGrouponTask(GrouponTaskPublishReq grouponTaskPublishReq,HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        grouponTaskPublishReq.setUserId(getUserId(request));
        logger.info("拼团任务发布接口,请求入参:{}",JSON.toJSONString(grouponTaskPublishReq));

        String taskParentId = grouponTaskPublishReq.getTaskParentId();
        if(StringUtils.isEmpty(taskParentId)){
            logger.info("拼团任务发布接口,父级任务ID 不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        //检查是否上传了NFT文件
        boolean enableNft = grouponTaskPublishReq.isEnableNft();
        if(enableNft) {
            MultipartRequest multipartRequest = (MultipartRequest) request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            if(null == fileMap || fileMap.size() == 0){
                logger.info("拼团任务发布接口,相关NFT文件未上传");
                baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
                return baseResponse;
            }
            grouponTaskPublishReq.setNftFileMap(fileMap);
        }
        baseResponse = taskService.publishGrouponTask(grouponTaskPublishReq);
        logger.info("拼团任务发布接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }


    @PostMapping(value = "/task/v1/distributor.publish")
    @ResponseBody
    public BaseResponse publishDistributorTask(DistributorTaskPublishReq distributorTaskPublishReq,
                                               GrouponRulesDto grouponRulesDto,
                                               DistributionRulesDto distributionRulesDto, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        distributorTaskPublishReq.setUserId(getUserId(request));
        distributionRulesDto.setExpirationDay(distributorTaskPublishReq.getDistributionExpirationDay());
        distributorTaskPublishReq.setDistributionRulesDto(distributionRulesDto);
        distributorTaskPublishReq.setGrouponRulesDto(grouponRulesDto);
        logger.info("分销任务发布接口,请求入参:{}",JSON.toJSONString(distributorTaskPublishReq));

        String taskParentId = distributorTaskPublishReq.getTaskParentId();
        if(StringUtils.isEmpty(taskParentId)){
            logger.info("分销任务发布接口,父级任务ID 不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        //检查是否上传了NFT文件
        boolean enableNft = distributorTaskPublishReq.isEnableNft();
        if(enableNft) {
            MultipartRequest multipartRequest = (MultipartRequest) request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            if(null == fileMap || fileMap.size() == 0){
                logger.info("分销任务发布接口,相关NFT文件未上传");
                baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
                return baseResponse;
            }
            distributorTaskPublishReq.setNftFileMap(fileMap);
        }

        BigDecimal goodsPrice = distributorTaskPublishReq.getGoodsPrice();
        BigDecimal goodsSalePrice = distributorTaskPublishReq.getGoodsSalePrice();
        if(null == goodsPrice || goodsPrice.compareTo(BigDecimal.ZERO) <= 0){
            logger.info("分销任务发布接口, 市场价格不能为空或者小于等于0");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }else if(null == goodsSalePrice || goodsSalePrice.compareTo(BigDecimal.ZERO) <= 0){
            logger.info("分销任务发布接口, 销售价格不能为空或者小于等于0");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }else if(goodsSalePrice.compareTo(goodsPrice) >=0){
            logger.info("分销任务发布接口,销售价格不能大于或者等于市场价格");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }

        DistributionRulesDto distributionRulesDto2 = distributorTaskPublishReq.getDistributionRulesDto();
        if(null == distributionRulesDto2){
            logger.info("分销任务发布接口,分销规则不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        GrouponRulesDto grouponRulesDto2 = distributorTaskPublishReq.getGrouponRulesDto();
        if(null == grouponRulesDto2){
            logger.info("分销任务发布接口,拼团规则不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        baseResponse = setFileMap(request, distributorTaskPublishReq);
        if(!ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
            return baseResponse;
        }
        baseResponse = taskService.publishDistributorTask(distributorTaskPublishReq);
        logger.info("分销任务发布接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    private BaseResponse setFileMap(HttpServletRequest request, SaleTaskPublishReq saleTaskPublishReq) {
        BaseResponse baseResponse = new BaseResponse();
        MultipartRequest multipartRequest = (MultipartRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        if(null == fileMap || fileMap.size() == 0){
            logger.info("商品销售任务发布接口,上传文件不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }else {
            Map<String,MultipartFile> goodsFileMap = new HashMap<>();
            Map<String,MultipartFile> nftFileMap = new HashMap<>();
            boolean enableNft = saleTaskPublishReq.isEnableNft();
            if(enableNft){
                //区分NFT文件和商品图片
                for (String key : fileMap.keySet()) {
                    if(key.indexOf("nft")> -1 || key.indexOf("NFT") > -1){
                        //NFT文件
                        nftFileMap.put(key,fileMap.get(key));
                    }else {
                        //商品图片文件
                        goodsFileMap.put(key,fileMap.get(key));
                    }
                }
                saleTaskPublishReq.setFileMap(goodsFileMap);
                saleTaskPublishReq.setNftFileMap(nftFileMap);
                if(null == nftFileMap || nftFileMap.size() == 0){
                    logger.info("商品销售任务发布接口,NFT上传文件不能为空");
                    baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
                    return baseResponse;
                }
            }else{
                saleTaskPublishReq.setFileMap(fileMap);
            }
        }
        return baseResponse;
    }

    private BaseResponse setFileMap(HttpServletRequest request, DistributorTaskPublishReq distributorTaskPublishReq) {
        BaseResponse baseResponse = new BaseResponse();
        MultipartRequest multipartRequest = (MultipartRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        if(null == fileMap || fileMap.size() == 0){
            logger.info("分销任务发布接口,上传文件不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }else {
            Map<String,MultipartFile> goodsFileMap = new HashMap<>();
            Map<String,MultipartFile> nftFileMap = new HashMap<>();
            boolean enableNft = distributorTaskPublishReq.isEnableNft();
            if(enableNft){
                //区分NFT文件和商品图片
                for (String key : fileMap.keySet()) {
                    if(key.indexOf("nft")> -1 || key.indexOf("NFT") > -1){
                        //NFT文件
                        nftFileMap.put(key,fileMap.get(key));
                    }else {
                        //商品图片文件
                        goodsFileMap.put(key,fileMap.get(key));
                    }
                }
                distributorTaskPublishReq.setFileMap(goodsFileMap);
                distributorTaskPublishReq.setNftFileMap(nftFileMap);
                if(null == nftFileMap || nftFileMap.size() == 0){
                    logger.info("分销任务发布接口,NFT上传文件不能为空");
                    baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
                    return baseResponse;
                }
            }else{
                distributorTaskPublishReq.setFileMap(fileMap);
            }
        }
        return baseResponse;
    }

}
