package org.cybercrowd.mvp.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.*;
import org.cybercrowd.mvp.enums.ReturnCodeEnum;
import org.cybercrowd.mvp.enums.TaskRulesTypeEnum;
import org.cybercrowd.mvp.service.GoodsService;
import org.cybercrowd.mvp.service.TaskRulesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

@RestController
public class GoodsController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    GoodsService goodsService;
    @Autowired
    TaskRulesService taskRulesService;

    @RequestMapping(value = "/goods/v1/create")
    @ResponseBody
    public BaseResponse createGoods(GoodsCreateReq goodsCreateReq, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        goodsCreateReq.setUserId(getUserId(request));
        logger.info("商品信息保存接口,请求入参:{}",JSON.toJSONString(goodsCreateReq));
        try {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
            if (null == fileMap || fileMap.size() == 0) {
                logger.info("商品信息保存接口,商品图片不能为空");
                baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
                return baseResponse;
            }
            //参数校验
            goodsCreateReq.setFileMap(fileMap);
            baseResponse = goodsService.createGoods(goodsCreateReq);
            logger.info("商品信息保存接口,响应结果:{}", JSON.toJSONString(baseResponse));
        }catch (Exception e){
            logger.error("商品信息保存接口,执行异常:{}", e.getMessage(),e);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            return baseResponse;
        }
        return baseResponse;
    }

    @PostMapping(value = "/goods/v1/rules.create")
    @ResponseBody
    public BaseResponse createTaskRules(@RequestBody TaskRulesCreateReq taskRulesCreateReq, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        taskRulesCreateReq.setUserId(getUserId(request));
        logger.info("任务规则创建接口,请求入参:{}", com.alibaba.fastjson2.JSON.toJSONString(taskRulesCreateReq));
        if(StringUtils.isEmpty(taskRulesCreateReq.getTaskRulesType())){
            logger.info("任务规则创建接口,task rules type 不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }else if(null == TaskRulesTypeEnum.toEnum(taskRulesCreateReq.getTaskRulesType())){
            logger.info("任务规则创建接口,task rules type 参数错误");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        if(StringUtils.isEmpty(taskRulesCreateReq.getTaskId())){
            logger.info("任务规则创建接口,task id 不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        baseResponse = taskRulesService.createTaskRules(taskRulesCreateReq);
        logger.info("任务规则创建接口,响应结果:{}", com.alibaba.fastjson2.JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @RequestMapping(value = "/goods/v1/publish")
    @ResponseBody
    public BaseResponse publishGoods(@RequestBody PublishGoodsReqDto publishGoodsReqDto, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        publishGoodsReqDto.setUserId(getUserId(request));
        logger.info("商品发布接口,请求入参:{}",JSON.toJSONString(publishGoodsReqDto));
        Long goodsId = publishGoodsReqDto.getGoodsId();
        String taskId = publishGoodsReqDto.getTaskId();
        if(StringUtils.isEmpty(taskId)){
            logger.info("商品发布接口,task id 不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        if(null == goodsId || 0 == goodsId.intValue()){
            logger.info("商品发布接口,goods id:{} 不能为空" ,goodsId);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        baseResponse = goodsService.publishGoods(publishGoodsReqDto);
        logger.info("商品发布接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @RequestMapping(value = "/goods/v1/list")
    @ResponseBody
    public BaseResponse goodsList(@RequestBody GoodsListReq goodsListReq,HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        goodsListReq.setUserId(getUserId(request));
        logger.info("商品列表查询接口,请求入参:{}",JSON.toJSONString(goodsListReq));
        baseResponse = goodsService.goodsList(goodsListReq);
        logger.info("商品列表查询接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @RequestMapping(value = "/goods/v1/details")
    @ResponseBody
    public BaseResponse goodsDetails(@RequestBody GoodsDetailsReq detailsReq,HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        detailsReq.setUserId(getUserId(request));
        logger.info("商品详情查询接口,请求入参:{}",JSON.toJSONString(detailsReq));
        String taskId = detailsReq.getTaskId();
        if(StringUtils.isEmpty(taskId)){
            logger.info("商品详情查询接口,task id 不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        baseResponse = goodsService.goodsDetails(detailsReq);
        logger.info("商品详情查询接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @PostMapping(value = "/goods/v1/distribution.create")
    @ResponseBody
    public BaseResponse createDistributionGoods(@RequestBody DistributionGoodsCreateReq distributionGoodsCreateReq,HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        distributionGoodsCreateReq.setUserId(getUserId(request));
        logger.info("创建分销商品接口,请求入参:{}",JSON.toJSONString(distributionGoodsCreateReq));

        String taskParentId = distributionGoodsCreateReq.getTaskParentId();
        if(StringUtils.isEmpty(taskParentId)){
            logger.info("创建分销商品接口,task parent id 不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }

        BigDecimal goodsPrice = distributionGoodsCreateReq.getGoodsPrice();
        if(null == goodsPrice || BigDecimal.ZERO.compareTo(goodsPrice)>=0){
            logger.info("创建分销商品接口,goods price 参数错误");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        BigDecimal goodsSalePrice = distributionGoodsCreateReq.getGoodsSalePrice();
        if(null == goodsSalePrice || BigDecimal.ZERO.compareTo(goodsSalePrice)>=0){
            logger.info("创建分销商品接口,goods sale price 参数错误");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }

        if(goodsPrice.compareTo(goodsSalePrice) <= 0){
            logger.info("创建分销商品接口,市场价格 <= 销售价格,参数错误");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }

        baseResponse = goodsService.createDistributionGoods(distributionGoodsCreateReq);
        logger.info("创建分销商品接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @PostMapping(value = "/goods/v1/groupon.create")
    @ResponseBody
    public BaseResponse createGrouponGoods(@RequestBody GrouponGoodsCreateReq grouponGoodsCreateReq,HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        grouponGoodsCreateReq.setUserId(getUserId(request));
        logger.info("创建拼团商品接口,请求入参:{}",JSON.toJSONString(grouponGoodsCreateReq));
        String taskParentId = grouponGoodsCreateReq.getTaskParentId();
        if(StringUtils.isEmpty(taskParentId)){
            logger.info("创建拼团商品接口,task parent id 不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        baseResponse = goodsService.createGrouponGoods(grouponGoodsCreateReq);
        logger.info("创建拼团商品接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }
}
