package org.cybercrowd.mvp.service.impl;

import com.alibaba.fastjson2.JSON;
import org.apache.commons.lang3.StringUtils;
import org.cybercrowd.mvp.constant.AwsS3UploadDirectoryConstants;
import org.cybercrowd.mvp.contract.MvpNFT;
import org.cybercrowd.mvp.dto.*;
import org.cybercrowd.mvp.enums.*;
import org.cybercrowd.mvp.mapper.NftMaterialMapper;
import org.cybercrowd.mvp.mapper.OrderInfoMapper;
import org.cybercrowd.mvp.mapper.TaskEventMapper;
import org.cybercrowd.mvp.mapper.TaskInfoMapper;
import org.cybercrowd.mvp.model.*;
import org.cybercrowd.mvp.service.DaoService;
import org.cybercrowd.mvp.util.HttpClientUtil;
import org.cybercrowd.mvp.util.HttpRequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.*;

@Service
public class CommonService {

    private Logger logger = LoggerFactory.getLogger(CommonService.class);

    @Value("${twitter.oauth.token-url}")
    private String twitterOauthTokenUrl;
    @Value("${twitter.oauth.client-id}")
    private String twitterOauthClientId;
    @Value("${twitter.user.url}")
    private String twitterOauthUserUrl;
    @Value("${twitter.redirect}")
    private String twitterRedirect;

    @Value("${google.oauth.token-url}")
    private String googleOauthTokenUrl;
    @Value("${google.oauth.client-id}")
    private String googleOauthClientId;
    @Value("${google.user.url}")
    private String googleOauthUserUrl;
    @Value("${google.redirect}")
    private String googleRedirect;

    @Value("${facebook.oauth.token-url}")
    private String facebookOauthTokenUrl;
    @Value("${facebook.oauth.client-id}")
    private String facebookOauthClientId;
    @Value("${facebook.user.url}")
    private String facebookOauthUserUrl;
    @Value("${facebook.redirect}")
    private String facebookRedirect;

    @Value("${web3j.server.rinkeby}")
    private String web3jRinkebyServer;
    @Value("${web3j.server.goerli}")
    private String web3jGoerliServer;
    @Value("${contract.owner_private}")
    private String contractOwnerPrivate;


    @Autowired
    TaskInfoMapper taskInfoMapper;
    @Autowired
    TaskEventMapper taskEventMapper;
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    NftMaterialMapper nftMaterialMapper;

    @Autowired
    AwsS3StorageService awsS3StorageService;

    @Autowired
    DaoService daoService;



    public DaoEvent createDaoEvent(Long userId, DaoOrganize daoOrganize,
                                   String proposalNo, DaoEventEnum daoEventEnum) {
        DaoEvent daoEvent = new DaoEvent();
        daoEvent.setDaoNo(daoOrganize.getDaoNo());
        daoEvent.setTaskId(daoOrganize.getTaskId());
        daoEvent.setEventType(daoEventEnum.getCode());
        daoEvent.setUserId(userId);
        daoEvent.setCreateTime(new Date());
        daoEvent.setUpdateTime(new Date());
        daoEvent.setProposalNo(proposalNo);
        return daoEvent;
    }

    public TaskEvent createTaskEvent(TaskInfo taskInfo, TaskEventEnum taskEventEnum) {
        TaskEvent taskEvent = new TaskEvent();
        taskEvent.setTaskId(taskInfo.getTaskId());
        taskEvent.setEventType(taskEventEnum.getCode());
        taskEvent.setTaskParentId(taskInfo.getTaskParentId());
        taskEvent.setUserId(taskInfo.getTaskOwnerId());
        taskEvent.setCreateTime(new Date());
        taskEvent.setUpdateTime(new Date());
        return taskEvent;
    }

    public BaseResponse updateOrderAndEvent(OrderInfo orderInfo, OrderStatusEnum orderStatusEnum){
        BaseResponse baseResponse = new BaseResponse();
        Long userId = orderInfo.getUserId();
        String taskId = orderInfo.getTaskId();
        orderInfo.setOrderStatus(orderStatusEnum.getCode());
        if(OrderStatusEnum.SUCCESS == orderStatusEnum){
            orderInfo.setSuccessTime(new Date());
            String orderType = orderInfo.getOrderType();
            TaskInfo taskInfo = taskInfoMapper.selectTaskByTaskId(taskId);
            if(OrderTypeEnum.TRADE.getCode().equals(orderType)){
                //任务的商品销量更新
                taskInfo.setDealQuantity(taskInfo.getDealQuantity()+1);
                taskInfoMapper.updateByPrimaryKeySelective(taskInfo);
            }else if(OrderTypeEnum.GROUPON_BUY.getCode().equals(orderType)){
                //创建事件
                TaskEvent taskEvent = this.createTaskEvent(taskInfo, TaskEventEnum.GROUPON_BUY);
                taskEventMapper.insertSelective(taskEvent);
                //任务的商品销量更新s
                taskInfo.setDealQuantity(taskInfo.getDealQuantity()+1);
                taskInfoMapper.updateByPrimaryKeySelective(taskInfo);
                //加入Dao
                daoService.joinDaoOrganize(userId,taskId);
            }
        }else{
        }
        orderInfo.setUpdateTime(new Date());
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
        return baseResponse;
    }

    public BaseResponse getTwitterAccount(String loginKey,String redirectUri){
        BaseResponse baseResponse = new BaseResponse();
        //获取oauth token
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("code",loginKey);
        paramMap.put("grant_type","authorization_code");
        paramMap.put("client_id",twitterOauthClientId);
        paramMap.put("redirect_uri",redirectUri);
        paramMap.put("code_verifier","challenge");
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Authorization","Basic YWtVakphZ1hLd3lpRUxFdnJxdjJnek9ORjplcFdhTENKQVF1eE84RmZuQlVIMGRLb25FUlJJSTNqZDZVeDZ6OUhsc1BDTmZ1VVpUcw==");

        String oauthTokenResult = HttpClientUtil.postWithParamsForString(twitterOauthTokenUrl,paramMap,headerMap);
        if(StringUtils.isEmpty(oauthTokenResult)){
            logger.info("获取Twitter第三方用户信息,OAUTH Token获取失败");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            return baseResponse;
        }

        TwitterOauthTokenResDto twitterOauthTokenResDto = JSON.parseObject(oauthTokenResult, TwitterOauthTokenResDto.class);
        headerMap.put("Authorization","Bearer "+ twitterOauthTokenResDto.getAccess_token());

        String result = HttpClientUtil.sendHttp(HttpRequestMethod.HttpRequestMethodEnum.HttpGet, twitterOauthUserUrl, null, headerMap);
        if(StringUtils.isEmpty(result)){
            logger.info("获取Twitter第三方用户信息失败");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            return baseResponse;
        }

        TwitterAccountDto twitterAccountDto = JSON.parseObject(result, TwitterAccountDto.class);
        SocialAccountDto accountDto = new SocialAccountDto();
        accountDto.setAccount(twitterAccountDto.getData().getId());
        accountDto.setNickName(twitterAccountDto.getData().getUsername());
        accountDto.setUserAvatar(twitterAccountDto.getData().getProfile_image_url());
        baseResponse.setData(accountDto);
        logger.info("获取Twitter第三方用户信息,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    public BaseResponse getGoogleAccount(String loginKey,String redirectUri){
        BaseResponse baseResponse = new BaseResponse();
        //获取oauth token
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("code",loginKey);
        paramMap.put("grant_type","authorization_code");
        paramMap.put("client_id",googleOauthClientId);
        paramMap.put("redirect_uri",redirectUri);
        paramMap.put("client_secret","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");

        String oauthTokenResult = HttpClientUtil.postWithParamsForString(googleOauthTokenUrl,paramMap,null);
        if(StringUtils.isEmpty(oauthTokenResult)){
            logger.info("获取Google第三方用户信息,OAUTH Token获取失败");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            return baseResponse;
        }

        GoogleOauthTokenResDto googleOauthTokenResDto = JSON.parseObject(oauthTokenResult, GoogleOauthTokenResDto.class);
        if(StringUtils.isEmpty(googleOauthTokenResDto.getAccess_token())){
            logger.info("获取Google第三方用户信息,OAUTH Token获取失败");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            return baseResponse;
        }

        Map<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+googleOauthTokenResDto.getAccess_token());
        String result = HttpClientUtil.sendHttp(HttpRequestMethod.HttpRequestMethodEnum.HttpGet, googleOauthUserUrl, null, header);
        if(StringUtils.isEmpty(result)){
            logger.info("获取Google第三方用户信息失败");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            return baseResponse;
        }

        GoogleAccountDto googleAccountDto = JSON.parseObject(result,GoogleAccountDto.class);
        SocialAccountDto accountDto = new SocialAccountDto();
        accountDto.setAccount(googleAccountDto.getSub());
        accountDto.setNickName(googleAccountDto.getName());
        accountDto.setUserAvatar(googleAccountDto.getPicture());
        baseResponse.setData(accountDto);
        logger.info("获取Google第三方用户信息,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    public BaseResponse getFaceBookAccount(String loginKey,String redirectUri){
        BaseResponse baseResponse = new BaseResponse();
       String oauthTokenUrl = MessageFormat.format(facebookOauthTokenUrl,facebookOauthClientId,redirectUri,loginKey);
       String oauthTokenResult = HttpClientUtil.sendHttp(HttpRequestMethod.HttpRequestMethodEnum.HttpGet,oauthTokenUrl,null,null);

        if(StringUtils.isEmpty(oauthTokenResult)){
            logger.info("获取Facebook第三方用户信息,OAUTH Token获取失败");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            return baseResponse;
        }
        FacebookOauthTokenResDto faceBookOauthTokenResDto = JSON.parseObject(oauthTokenResult, FacebookOauthTokenResDto.class);
        if(StringUtils.isEmpty(faceBookOauthTokenResDto.getAccess_token())){
            logger.info("获取Facebook第三方用户信息,OAUTH Token获取失败");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            return baseResponse;
        }
        String oauthUserUrl = MessageFormat.format(facebookOauthUserUrl, faceBookOauthTokenResDto.getAccess_token());
        String result = HttpClientUtil.sendHttp(HttpRequestMethod.HttpRequestMethodEnum.HttpGet, oauthUserUrl, null, null);
        if(StringUtils.isEmpty(result)){
            logger.info("获取Facebook第三方用户信息失败");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            return baseResponse;
        }

        FacebookAccountDto facebookAccountDto = JSON.parseObject(result,FacebookAccountDto.class);
        SocialAccountDto accountDto = new SocialAccountDto();
        accountDto.setAccount(facebookAccountDto.getId());
        accountDto.setNickName(facebookAccountDto.getName());
        accountDto.setUserAvatar(facebookAccountDto.getPicture().getData().getUrl());
        baseResponse.setData(accountDto);
        logger.info("获取Facebook第三方用户信息,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    public BaseResponse uploadNftFile(Object fileMap,Long userId,String taskId){
        List<String> filePathMap = new ArrayList<>();
        BaseResponse baseResponse = new BaseResponse();

        try {
            Map<String, MultipartFile> nftFileMap = (Map<String, MultipartFile>) fileMap;
            for (String fileKey : nftFileMap.keySet()) {
                logger.info("fileKey:{}",fileKey);
                String fileUrl = awsS3StorageService.upload(nftFileMap.get(fileKey), AwsS3UploadDirectoryConstants.NFT_FILE_DIR);
                if (StringUtils.isEmpty(fileUrl)) {
                    //文件上传失败
                    logger.info("任务所属NFT素材文件上传业务,上传商品图片文件失败....");
                    baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
                    return baseResponse;
                }
                filePathMap.add(fileUrl);
            }
            //批量保存NFT素材数据
            List<NftMaterial> nftMaterialsList = batchSaveNftMaterial(
                    userId, taskId, filePathMap);
        }catch (Exception e){
            logger.error("任务所属NFT素材文件上传业务,上传异常:{}",e.getMessage(),e);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
        }
        return baseResponse;
    }


    private List<NftMaterial> batchSaveNftMaterial(Long userId, String taskId, List<String> filePathMap) {
        List<NftMaterial> nftMaterialList = new ArrayList<>();
        for(String url:filePathMap){
            NftMaterial nftMaterial = new NftMaterial();
            nftMaterial.setTaskId(taskId);
            nftMaterial.setNftFileType(getNftFileType(url.substring(url.lastIndexOf(".")+1)));
            nftMaterial.setCreateTime(new Date());
            nftMaterial.setCastingStatus(NftCastionStatusEnum.UNCAST.getCode());
            nftMaterial.setFilePath(url);
            nftMaterial.setUpdateTime(new Date());
            nftMaterialMapper.insertSelective(nftMaterial);
            nftMaterialList.add(nftMaterial);
        }
        return nftMaterialList;
    }

    private String getNftFileType(String fileSuffix) {

        if("jpeg".equalsIgnoreCase(fileSuffix) || "jpg".equalsIgnoreCase(fileSuffix)
                || "png".equalsIgnoreCase(fileSuffix) || "svg".equalsIgnoreCase(fileSuffix)
                || "gif".equalsIgnoreCase(fileSuffix) || "tif".equalsIgnoreCase(fileSuffix)
                || "tag".equalsIgnoreCase(fileSuffix) || "bmp".equalsIgnoreCase(fileSuffix)
                || "dds".equalsIgnoreCase(fileSuffix) || "eps".equalsIgnoreCase(fileSuffix)){
            return NftFileTypeEnum.IMAGE.getCode();
        }else if("dsd".equalsIgnoreCase(fileSuffix)
                || "wav".equalsIgnoreCase(fileSuffix) || "ape".equalsIgnoreCase(fileSuffix)
                || "flac".equalsIgnoreCase(fileSuffix) || "tif".equalsIgnoreCase(fileSuffix)
                || "tag".equalsIgnoreCase(fileSuffix) || "bmp".equalsIgnoreCase(fileSuffix)
                || "dds".equalsIgnoreCase(fileSuffix) || "mp3".equalsIgnoreCase(fileSuffix)
                || "acc".equalsIgnoreCase(fileSuffix) || "amr".equalsIgnoreCase(fileSuffix)
                || "dds".equalsIgnoreCase(fileSuffix) || "opus".equalsIgnoreCase(fileSuffix)
                || "ogg".equalsIgnoreCase(fileSuffix) || "wma".equalsIgnoreCase(fileSuffix)
                || "ac3".equalsIgnoreCase(fileSuffix)){
            return NftFileTypeEnum.AUDIO.getCode();
        }else if("MP4".equalsIgnoreCase(fileSuffix)
                || "MOV".equalsIgnoreCase(fileSuffix) || "WMV".equalsIgnoreCase(fileSuffix)
                || "FLV".equalsIgnoreCase(fileSuffix) || "AVI".equalsIgnoreCase(fileSuffix)
                || "AVCHD".equalsIgnoreCase(fileSuffix) || "WebM".equalsIgnoreCase(fileSuffix)
                || "MKV".equalsIgnoreCase(fileSuffix)){
            return NftFileTypeEnum.VIDEO.getCode();
        }
        return NftFileTypeEnum.FILE.getCode();
    }

    public BaseResponse uploadGoodsImages(Map<String, MultipartFile> fileMap){
        BaseResponse baseResponse = new BaseResponse();
        //上传图片
        Map<String,String> filePathMap = new HashMap<>();
        try {
            int index = 0;
            for (String fileKey : fileMap.keySet()) {
                String fileUrl = awsS3StorageService.
                        upload(fileMap.get(fileKey), AwsS3UploadDirectoryConstants.GOODS_FILE_DIR);
                if (StringUtils.isEmpty(fileUrl)) {
                    //文件上传失败
                    logger.info("商品图片上传业务,上传文件失败....");
                    baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
                    return baseResponse;
                }
                filePathMap.put("file" + index, fileUrl);
                index++;
            }
            baseResponse.setData(filePathMap);
        }catch (Exception e){
            logger.error("商品图片上传业务,上传文件异常:{}",e.getMessage(),e);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
        }
        return baseResponse;
    }

    /**
     * 发布NFT合约
     * @param name
     * @param symbol
     * @return
     */
    public BaseResponse deployNftContract(String name,String symbol) {
        BaseResponse baseResponse = new BaseResponse();
        try{
            Web3j web3j = Web3j.build(new HttpService(web3jGoerliServer));
            Credentials credentials = Credentials.create(contractOwnerPrivate);
            StaticGasProvider staticGasProvider = new StaticGasProvider(new BigInteger("2500000008"), BigInteger.valueOf(3000000));
            MvpNFT mvpNft = MvpNFT.deploy(web3j, credentials, staticGasProvider, name, symbol).send();
            baseResponse.setData(mvpNft.getContractAddress());
        }catch (Exception e){
            logger.error("NFT合约发布异常:{}",e.getMessage(),e);
            baseResponse.setData(ReturnCodeEnum.REQUEST_FAILED);
            return baseResponse;
        }
        return baseResponse;
    }

//    public BaseResponse nftMultiMint(String contractAddr,List<String> ipfsFileList,String nftReceiveAddr){
//        BaseResponse baseResponse = new BaseResponse();
//        try {
//            Web3j web3j = Web3j.build(new HttpService(web3jGoerliServer));
//            Credentials credentials = Credentials.create(contractOwnerPrivate);
//            StaticGasProvider staticGasProvider = new StaticGasProvider(web3j.ethGasPrice().send().getGasPrice(), BigInteger.valueOf(3000000));
//            MvpNFT mvpNFT = MvpNFT.load(contractAddr, web3j, credentials, staticGasProvider);
//            TransactionReceipt transactionReceipt = mvpNFT.multiMint(nftReceiveAddr, ipfsFileList).send();
//            logger.info("");
//        }catch (Exception e){
//            baseResponse.setData(ReturnCodeEnum.REQUEST_FAILED);
//            logger.error("NFT铸造异常:{}",e.getMessage(),e);
//        }
//        return baseResponse;
//    }

    public BaseResponse nftMultiMint(String contractAddr,List<String> nftJsonList,String nftReceiveAddr){
        BaseResponse baseResponse = new BaseResponse();
        List<String> tokenIds = new ArrayList<>();
        try {
            Web3j web3j = Web3j.build(new HttpService(web3jGoerliServer));
            Credentials credentials = Credentials.create(contractOwnerPrivate);
            StaticGasProvider staticGasProvider = new StaticGasProvider(web3j.ethGasPrice().send().getGasPrice(), BigInteger.valueOf(3500000));
            MvpNFT mvpNFT = MvpNFT.load(contractAddr, web3j, credentials, staticGasProvider);
            int i = 1;
            for(String nftJson:nftJsonList) {
                long startTime = System.currentTimeMillis();
                TransactionReceipt transactionReceipt = mvpNFT.mint(nftReceiveAddr, nftJson).send();
                logger.info("NFT铸造链上响应数据:{}",JSON.toJSONString(transactionReceipt));
                long endTime = System.currentTimeMillis();
                logger.info("NFT: {} 铸造耗时:{}",i,(endTime - startTime));
                List<Log> logs = transactionReceipt.getLogs();
                List<String> topics = logs.get(0).getTopics();
//                String tokenId = Integer.valueOf(topics.get(3).replace("0x", "")).toString();
                String tokenId = new BigInteger(topics.get(3).replace("0x", ""),16).toString();
                tokenIds.add(tokenId);
                i++;
            }
            baseResponse.setData(tokenIds);
        }catch (Exception e){
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            logger.error("NFT铸造异常:{}",e.getMessage(),e);
        }
        return baseResponse;
    }
}
