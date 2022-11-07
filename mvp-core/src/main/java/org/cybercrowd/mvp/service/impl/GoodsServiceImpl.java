package org.cybercrowd.mvp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.cybercrowd.mvp.constant.AwsS3UploadDirectoryConstants;
import org.cybercrowd.mvp.dto.*;
import org.cybercrowd.mvp.dto.request.*;
import org.cybercrowd.mvp.dto.response.*;
import org.cybercrowd.mvp.enums.*;
import org.cybercrowd.mvp.mapper.*;
import org.cybercrowd.mvp.model.GoodsInfo;
import org.cybercrowd.mvp.model.TaskInfo;
import org.cybercrowd.mvp.model.TaskRules;
import org.cybercrowd.mvp.service.DaoService;
import org.cybercrowd.mvp.service.GoodsService;
import org.cybercrowd.mvp.service.TaskRulesService;
import org.cybercrowd.mvp.service.TaskService;
import org.cybercrowd.mvp.util.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

    private Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Autowired
    AwsS3StorageService awsS3StorageService;
    @Autowired
    TaskRulesService taskRulesService;
    @Autowired
    TaskService taskService;
    @Autowired
    DaoService daoService;
    @Autowired
    GoodsInfoMapper goodsInfoMapper;
    @Autowired
    TaskInfoMapper taskInfoMapper;
    @Autowired
    TaskEventMapper taskEventMapper;
    @Autowired
    TaskRulesMapper taskRulesMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    DaoOrganizeMapper daoOrganizeMapper;

    @Override
    public BaseResponse createGoods(GoodsCreateReq goodsCreateReq) {
        BaseResponse baseResponse = new BaseResponse();
        //上传图片
        Map<String,MultipartFile> fileMap = (Map<String,MultipartFile>) goodsCreateReq.getFileMap();
        Map<String,String> filePathMap = new HashMap<>();
        try{
            int index = 0;
            for(String fileKey:fileMap.keySet()){
                String fileUrl = awsS3StorageService.
                        upload(fileMap.get(fileKey), AwsS3UploadDirectoryConstants.GOODS_FILE_DIR);
                if(StringUtils.isEmpty(fileUrl)){
                    //文件上传失败
                    logger.info("创建商品业务,上传商品图片文件失败....");
                    baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
                    return baseResponse;
                }
                filePathMap.put("file"+index,fileUrl);
                index++;
            }
            GoodsCreateRes goodsCreateRes = new GoodsCreateRes();
            //创建商品数据
            GoodsInfo goodsInfo = createGoodsInfo(goodsCreateReq,filePathMap);
            goodsInfoMapper.insertSelective(goodsInfo);
            goodsCreateRes.setGoodsId(goodsInfo.getId());
            logger.info("创建商品业务, 保存商品信息:{}",JSON.toJSONString(goodsInfo));
            //创建商品销售任务
            TaskInfo taskInfo = createGoodsTask(goodsInfo,TaskTypeEnum.SALES);
            taskInfoMapper.insertSelective(taskInfo);
            goodsCreateRes.setTaskId(taskInfo.getTaskId());
            logger.info("创建商品业务, 保存商品销售任务信息:{}",JSON.toJSONString(goodsInfo));
            baseResponse.setData(goodsCreateRes);
        }catch (Exception e){
            logger.info("创建商品业务,执行异常:{}",e.getMessage(),e);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
        }
        return baseResponse;
    }

    @Override
    public BaseResponse publishGoods(PublishGoodsReqDto publishGoodsReqDto) {
        BaseResponse baseResponse = new BaseResponse();
        logger.info("商品发布业务,请求入参:{}",JSON.toJSONString(publishGoodsReqDto));
        Long goodsId = publishGoodsReqDto.getGoodsId();
        Long userId = publishGoodsReqDto.getUserId();
        String taskId = publishGoodsReqDto.getTaskId();
        //查询商品信息
        GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(goodsId);
        if(null == goodsInfo) {
            logger.info("商品发布业务,商品ID:{}商品信息未查询到...", goodsId);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            return baseResponse;
        }
        //创建Dao组织和相关事件
        baseResponse = daoService.createDaoOrganize(userId, taskId,DaoEventEnum.SALES);
        if(ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())) {
            //发布任务,创建任务事件
            TaskPublishReq taskPublishReq = new TaskPublishReq();
            taskPublishReq.setUserId(userId);
            taskPublishReq.setTaskId(taskId);
            baseResponse = taskService.publishTask(taskPublishReq);
        }
        logger.info("商品发布业务,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @Override
    public BaseResponse goodsList(GoodsListReq goodsListReq) {
        BaseResponse baseResponse = new BaseResponse();
        GoodsListRes goodsListRes = new GoodsListRes();
        logger.info("商品列表查询接口,请求入参:{}",JSON.toJSONString(goodsListReq));

        PageHelper.startPage(goodsListReq.getPageNum(), goodsListReq.getPageSize()," gi.create_time asc");
        List<BaseGoodsDto> baseGoodsDtos = goodsInfoMapper.selectGoodsList(TaskStatusEnum.STARTED.getCode());
        if(null != baseGoodsDtos && baseGoodsDtos.size() > 0){
            logger.info("商品列表查询接口,查询到符合条件的数据:{}条",baseGoodsDtos.size());
            PageInfo pageInfo = new PageInfo(baseGoodsDtos);
            goodsListRes.setTotalPage(pageInfo.getPages());
            for(BaseGoodsDto baseGoodsDto:baseGoodsDtos){
                Map<String,String> filePathMap = JSON.parseObject(baseGoodsDto.getGoodsImages(),Map.class);
                baseGoodsDto.setGoodsImages(filePathMap.get("file0"));
            }
            goodsListRes.setBaseGoodsDtos(baseGoodsDtos);
        }
        goodsListRes.setPageNum(goodsListReq.getPageNum());
        goodsListRes.setPageSize(goodsListReq.getPageSize());
        baseResponse.setData(goodsListRes);
        logger.info("商品列表查询接口,响应结果{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @Override
    public BaseResponse goodsDetails(GoodsDetailsReq goodsDetailsReq) {
        BaseResponse baseResponse = new BaseResponse();
        GoodsDetailsRes goodsDetailsRes = new GoodsDetailsRes();
        logger.info("商品详情查询业务,请求入参:{}",JSON.toJSONString(goodsDetailsReq));
        String taskId = goodsDetailsReq.getTaskId();
        Long userId = goodsDetailsReq.getUserId();
        //查询任务信息
        TaskInfo taskInfo = taskInfoMapper.selectTaskByTaskId(taskId);
        //查询商品信息
        GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(taskInfo.getGoodsId());
        //查询规则信息
        List<TaskRules> taskRulesList = taskRulesMapper.selectTaskRulesByTaskId(taskId);
        //查询已开团信息
        List<ProgressGroupDto> progressGroupList = taskInfoMapper.selectProgressGroupList(taskId);
        //查询DAO NO
        BaseDaoDto baseDaoDto = daoOrganizeMapper.findDaoDetailsByTaskId(taskId);
        //处理响应数据
        setGoodsDetails(taskRulesList,taskInfo,goodsInfo,progressGroupList,userId,goodsDetailsRes,baseDaoDto);
        baseResponse.setData(goodsDetailsRes);
        logger.info("商品详情查询业务,响应结果:{}",JSON.toJSONString(goodsDetailsRes));
        return baseResponse;
    }

    @Override
    public BaseResponse createDistributionGoods(DistributionGoodsCreateReq distributionGoodsCreateReq) {
        BaseResponse baseResponse = new BaseResponse();
        logger.info("创建分销商品业务,请求入参:{}",JSON.toJSONString(distributionGoodsCreateReq));
        String taskParentId = distributionGoodsCreateReq.getTaskParentId();
        TaskInfo taskInfo = taskInfoMapper.selectTaskByTaskId(taskParentId);
        if(null == taskInfo){
            logger.info("创建分销商品业务,父任务ID:{} 信息未查询到...",taskParentId);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            return baseResponse;
        }
        //查询父商品信息
        GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(taskInfo.getGoodsId());
        //品继续使用任务商品基本信息，删除 goods id 修改价格、时间信息
        goodsInfo.setId(null);
        goodsInfo.setPublishUserId(distributionGoodsCreateReq.getUserId());
        goodsInfo.setGoodsPrice(distributionGoodsCreateReq.getGoodsPrice());
        goodsInfo.setGoodsSalePrice(distributionGoodsCreateReq.getGoodsSalePrice());
        goodsInfo.setLastUpdateTime(new Date());
        goodsInfo.setCreateTime(new Date());
        goodsInfo.setPublishTime(new Date());
        goodsInfoMapper.insertSelective(goodsInfo);
        //创建任务
        TaskInfo goodsTask = createGoodsTask(goodsInfo, TaskTypeEnum.DISTRIBUTOR);
        goodsTask.setTaskParentId(taskParentId);
        taskInfoMapper.insertSelective(goodsTask);
        DistributionGoodsCreateRes distributionGoodsCreateRes = new DistributionGoodsCreateRes();
        distributionGoodsCreateRes.setGoodsId(goodsInfo.getId());
        distributionGoodsCreateRes.setTaskId(goodsTask.getTaskId());
        baseResponse.setData(distributionGoodsCreateRes);
        logger.info("创建分销商品业务,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @Override
    public BaseResponse createGrouponGoods(GrouponGoodsCreateReq grouponGoodsCreateReq) {
        BaseResponse baseResponse = new BaseResponse();
        logger.info("创建拼团商品业务,请求入参:{}",JSON.toJSONString(grouponGoodsCreateReq));
        String taskParentId = grouponGoodsCreateReq.getTaskParentId();
        Long userId = grouponGoodsCreateReq.getUserId();

        try {
            //查询上级任务是否存在
            TaskInfo taskInfo = taskInfoMapper.selectTaskByTaskId(taskParentId);
            if (null == taskInfo) {
                logger.info("创建拼团业务,未找到上级任务,创建拼团失败....");
                baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
                return baseResponse;
            }
            if (taskInfo.getTaskOwnerId().intValue() == userId.intValue()) {
                logger.info("创建拼团业务,拼团创建发起用户和上级任务用户不能是同一人,创建拼团失败....");
                baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
                return baseResponse;
            }
            //查询父商品信息
            GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(taskInfo.getGoodsId());
            //查询拼团规则数据
            TaskRules taskRules = taskRulesMapper.selectTaskRulesByType(taskParentId, TaskRulesTypeEnum.GROUPON.getCode());
            GrouponRulesDto grouponRulesDto = JSON.parseObject(taskRules.getTaskRulesJson(), GrouponRulesDto.class);
            //拼团商品继续使用任务商品基本信息，删除 goods id 修改价格、时间信息
            goodsInfo.setId(null);
            goodsInfo.setPublishUserId(userId);
            goodsInfo.setGoodsSalePrice(grouponRulesDto.getPrice());
            goodsInfo.setLastUpdateTime(new Date());
            goodsInfo.setCreateTime(new Date());
            goodsInfo.setPublishTime(new Date());
            goodsInfoMapper.insertSelective(goodsInfo);
            //去创建拼团任务和事件
            GrouponCreateReq grouponCreateReq = new GrouponCreateReq();
            grouponCreateReq.setGoodsId(goodsInfo.getId());
            grouponCreateReq.setGrouponRulesDto(grouponRulesDto);
            grouponCreateReq.setTaskParentId(taskParentId);
            grouponCreateReq.setUserId(userId);
            baseResponse = taskService.createGroupon(grouponCreateReq);
            if(ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
                //更新拼团数量
                taskInfo.setGrouponQuantity(taskInfo.getGrouponQuantity()+1);
                taskInfoMapper.updateByPrimaryKeySelective(taskInfo);
            }

        }catch (Exception e){
            logger.error("创建拼团业务,执行异常:{}",e.getMessage(),e);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
        }
        return baseResponse;
    }

    @Override
    public BaseResponse userGoodsStore(UserGoodsStoreReq userGoodsStoreReq) {
        BaseResponse baseResponse = new BaseResponse();
        UserGoodsStoreRes userGoodsStoreRes = new UserGoodsStoreRes();
        logger.info("用户商品店铺,请求入参:{}",JSON.toJSONString(userGoodsStoreReq));
        //查询执行中的销售商品任务
        Long userId = userGoodsStoreReq.getUserId();
        int pageNum = userGoodsStoreReq.getPageNum();
        int pageSize = userGoodsStoreReq.getPageSize();
        PageHelper.startPage(pageNum,pageSize);
        List<TaskInfo> taskInfos = taskInfoMapper.selectListByUserId(userId);
        if(null != taskInfos && taskInfos.size() > 0){
            PageInfo pageInfo = new PageInfo(taskInfos);
            userGoodsStoreRes.setTotalPage(pageInfo.getPages());
            userGoodsStoreRes.setPageNum(pageNum);
            userGoodsStoreRes.setPageSize(pageSize);
            List<GoodsStoreDto> goodsStoreDtos = new ArrayList<>();
            for(TaskInfo taskInfo:taskInfos){
                //查询商品
                GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(taskInfo.getGoodsId());
                //查询任务相关规则
                List<TaskRules> taskRules = taskRulesMapper.selectTaskRulesByTaskId(taskInfo.getTaskId());
                //set 商品店铺信息
                GoodsStoreDto goodsStoreDto = setGoodsStoreRes(taskInfo,goodsInfo,taskRules);
                if("1".equals(taskInfo.getEnableNft())){
                    goodsStoreDto.setNftAssetsLink("https://testnets.opensea.io/collection/cybercrowd-mvp-nft");
                }
                goodsStoreDtos.add(goodsStoreDto);
            }
            userGoodsStoreRes.setGoodsStoreDtos(goodsStoreDtos);
            baseResponse.setData(userGoodsStoreRes);
        }
        logger.info("用户商品店铺,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @Override
    public BaseResponse userGroupon(UserGrouponReq userGrouponReq) {
        BaseResponse baseResponse = new BaseResponse();
        UserGrouponRes userGrouponRes = new UserGrouponRes();
        Long userId = userGrouponReq.getUserId();
        int pageNum = userGrouponReq.getPageNum();
        int pageSize = userGrouponReq.getPageSize();
        PageHelper.startPage(pageNum,pageSize);
        List<UserGrouponDto> userGrouponDtoList = taskInfoMapper.selectUserGroupList(userId);
        if(null != userGrouponDtoList && userGrouponDtoList.size() >0){
            PageInfo pageInfo = new PageInfo(userGrouponDtoList);
            userGrouponRes.setTotalPage(pageInfo.getPages());
            userGrouponRes.setPageNum(pageNum);
            userGrouponRes.setPageSize(pageSize);

            for(UserGrouponDto userGrouponDto:userGrouponDtoList){
                userGrouponDto.setGrouponLink("https://cybercrowd.store/mvp/goods/v1/detail/"+userGrouponDto.getTaskId());

                //判断是不是owner
                userGrouponDto.setGrouponOwner(
                        userGrouponDto.getTaskOwnerId().intValue() == userId.intValue() ? true:false);
                //当前参团人数
                userGrouponDto.setCurrentPeople(taskEventMapper.countGrouponPeopleNumber(userGrouponDto.getTaskId()));
                //商品图片
                Map<String,String> imageMap = JSON.parseObject(userGrouponDto.getGoodsImage(),Map.class);
                userGrouponDto.setGoodsImage(imageMap.get("file0"));
                //结束时间毫秒数计算
                userGrouponDto.setEndTimeMilliSecond(handleEndTime(new Date(),userGrouponDto.getEndTime()));
                userGrouponDto.setNftAssetsLink("https://testnets.opensea.io/collection/cybercrowd-mvp-nft");
                //查询Task
                TaskInfo taskInfo = taskInfoMapper.selectTaskByTaskId(userGrouponDto.getTaskId());
                if("1".equals(taskInfo.getEnableNft())){
                    userGrouponDto.setNftAssetsLink("https://testnets.opensea.io/collection/cybercrowd-mvp-nft");
                }
            }
            userGrouponRes.setUserGrouponDtoList(userGrouponDtoList);
            baseResponse.setData(userGrouponRes);
        }
        logger.info("用户拼团列表查询业务,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @Override
    public BaseResponse userDistribution(UserDistributionReq userDistributionReq) {
        BaseResponse baseResponse = new BaseResponse();
        UserDistributionRes userDistributionRes = new UserDistributionRes();
        Long userId = userDistributionReq.getUserId();
        int pageNum = userDistributionReq.getPageNum();
        int pageSize = userDistributionReq.getPageSize();
        PageHelper.startPage(pageNum,pageSize);
        List<UserDistributionDto> userDistributionDtos = taskInfoMapper.selectUserDistributionList(userId);
        if(null != userDistributionDtos && userDistributionDtos.size() >0){
            PageInfo pageInfo = new PageInfo(userDistributionDtos);
            userDistributionRes.setTotalPage(pageInfo.getPages());
            userDistributionRes.setPageNum(pageNum);
            userDistributionRes.setPageSize(pageSize);
            for(UserDistributionDto userDistributionDto:userDistributionDtos){
                userDistributionDto.setDistributionLink("https://cybercrowd.store/mvp/goods/v1/detail/"+userDistributionDto.getTaskId());
                //商品图片
                Map<String,String> imageMap = JSON.parseObject(userDistributionDto.getGoodsImage(),Map.class);
                userDistributionDto.setGoodsImage(imageMap.get("file0"));
                //结束时间毫秒数计算
                userDistributionDto.setEndTimeMilliSecond(handleEndTime(new Date(),userDistributionDto.getEndTime()));
                //查询规则
                List<TaskRules> taskRulesList = taskRulesMapper.selectTaskRulesByTaskId(userDistributionDto.getTaskId());
                for(TaskRules taskRules:taskRulesList) {
                    if(TaskRulesTypeEnum.DISTRIBUTOR.getCode().equals(taskRules.getRulesType())) {
                        DistributionRulesDto distributionRulesDto = JSON.parseObject(taskRules.getTaskRulesJson(), DistributionRulesDto.class);
                        userDistributionDto.setDistributionPrice(distributionRulesDto.getDistributionPrice());
                        userDistributionDto.setDistributionMin(distributionRulesDto.getDistributionMin());
                    }else {
                        GrouponRulesDto grouponRulesDto = JSON.parseObject(taskRules.getTaskRulesJson(),GrouponRulesDto.class);
                        userDistributionDto.setGrouponPrice(grouponRulesDto.getPrice());
                        userDistributionDto.setPeople(grouponRulesDto.getParticipantsMin());
                        userDistributionDto.setPeopleLimit(grouponRulesDto.getParticipantsMax());
                    }
                }

                //查询Task
                TaskInfo taskInfo = taskInfoMapper.selectTaskByTaskId(userDistributionDto.getTaskId());
                if("1".equals(taskInfo.getEnableNft())){
                    userDistributionDto.setNftAssetsLink("https://testnets.opensea.io/collection/cybercrowd-mvp-nft");
                }
            }
            userDistributionRes.setUserDistributionDtoList(userDistributionDtos);
            baseResponse.setData(userDistributionRes);
        }
        logger.info("用户分销列表查询业务,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    private GoodsStoreDto setGoodsStoreRes(TaskInfo taskInfo,GoodsInfo goodsInfo, List<TaskRules> taskRulesList) {
        GoodsStoreDto goodsStoreDto = new GoodsStoreDto();
        goodsStoreDto.setGoodsTitle(goodsInfo.getGoodsTitle());
        goodsStoreDto.setGrouponQuantity(taskInfo.getGrouponQuantity());
        goodsStoreDto.setDistributionQuantity(taskInfo.getDistributorQuantity());
        goodsStoreDto.setDealQuantity(taskInfo.getDealQuantity());
        goodsStoreDto.setStoreLink("https://cybercrowd.store/mvp/goods/v1/details/"+taskInfo.getTaskId());
        goodsStoreDto.setPublishTime(goodsInfo.getPublishTime());
        Map<String,String> imageMap = JSON.parseObject(goodsInfo.getGoodsImageJson(),Map.class);
        goodsStoreDto.setGoodsImage(imageMap.get("file0"));
        goodsStoreDto.setPublishTime(goodsInfo.getPublishTime());

        for(TaskRules taskRules:taskRulesList){
            if(TaskTypeEnum.GROUPON.getCode().equals(taskRules.getRulesType())){
                GrouponRulesDto grouponRulesDto = JSON.parseObject(taskRules.getTaskRulesJson(),GrouponRulesDto.class);
                goodsStoreDto.setGrouponPrice(grouponRulesDto.getPrice());
                goodsStoreDto.setGrouponPeople(grouponRulesDto.getParticipantsMin());
                goodsStoreDto.setGrouponPeopleLimit(grouponRulesDto.getParticipantsMax());
            }else {
                DistributionRulesDto distributionRulesDto = JSON.parseObject(taskRules.getTaskRulesJson(), DistributionRulesDto.class);
                goodsStoreDto.setDistributionMin(distributionRulesDto.getDistributionMin());
                goodsStoreDto.setDistributionPrice(distributionRulesDto.getDistributionPrice());
            }
        }
        goodsStoreDto.setTaskStatus(taskInfo.getTaskStatus());
        return goodsStoreDto;
    }

    private void setGoodsDetails(List<TaskRules> taskRulesList, TaskInfo taskInfo, GoodsInfo goodsInfo,
                                 List<ProgressGroupDto> progressGroupList,Long userId, GoodsDetailsRes goodsDetailsRes,
                                 BaseDaoDto baseDaoDto) {
        goodsDetailsRes.setOwnerAddress(userInfoMapper.queryUserByUserId(taskInfo.getTaskOwnerId()).getUserWalletAddr());
        goodsDetailsRes.setGoodsNftAddress("0xa2510dbfcbfd2f8ee79a18fe154da4d76ba5f46d8c3a159eb35cc3749409164c");
        goodsDetailsRes.setTaskId(taskInfo.getTaskId());
        goodsDetailsRes.setGoodsDetailsIntro(goodsInfo.getGoodsDetailsIntro());
        goodsDetailsRes.setGoodsIntro(goodsInfo.getGoodsIntro());
        goodsDetailsRes.setGoodsImageJson(goodsInfo.getGoodsImageJson());
        goodsDetailsRes.setGoodsTitle(goodsInfo.getGoodsTitle());
        goodsDetailsRes.setGoodsSalePrice(goodsInfo.getGoodsSalePrice());
        goodsDetailsRes.setDaoNo(baseDaoDto.getDaoNo());

        if(null != userId){
            goodsDetailsRes.setOwner(taskInfo.getTaskOwnerId().intValue() == userId.intValue() ? true:false);
        }
        //规则
        if(null != taskRulesList && taskRulesList.size() > 0){
            for(TaskRules taskRules:taskRulesList){
                if(TaskRulesTypeEnum.DISTRIBUTOR.getCode().equals(taskRules.getRulesType())){
                    goodsDetailsRes.setDistributionRulesDto(
                            JSON.parseObject(taskRules.getTaskRulesJson(),DistributionRulesDto.class));
                }else {
                    goodsDetailsRes.setGrouponRulesDto(JSON.parseObject(taskRules.getTaskRulesJson(),GrouponRulesDto.class));
                }
            }
        }

        //进行中的团
        if(null != progressGroupList && progressGroupList.size() >0){
            Date now = new Date();
            for(ProgressGroupDto progressGroupDto:progressGroupList){
                Date taskEndTime = progressGroupDto.getTaskEndTime();
                //计算倒计时时间
                progressGroupDto.setTaskEndTimeMillisecond(handleEndTime(now,taskEndTime));
            }
        }
        goodsDetailsRes.setProgressGroupList(progressGroupList);
    }

    private TaskInfo createGoodsTask(GoodsInfo goodsInfo,TaskTypeEnum taskTypeEnum) {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskId(IDUtils.getTaskId());
        taskInfo.setGoodsId(goodsInfo.getId());
        taskInfo.setTaskType(taskTypeEnum.getCode());
        taskInfo.setTaskOwnerId(goodsInfo.getPublishUserId());
        taskInfo.setDealQuantity(0l);
        taskInfo.setDistributorQuantity(0l);
        taskInfo.setGrouponQuantity(0l);
        taskInfo.setEnableDistributor("0");
        taskInfo.setEnableNft("0");
        taskInfo.setTaskEndTime(null);
        taskInfo.setTaskStatus(TaskStatusEnum.NOT_STARTED.getCode());
        taskInfo.setCreateTime(new Date());
        return taskInfo;
    }

    private GoodsInfo createGoodsInfo(GoodsCreateReq goodsCreateReq, Map<String,String> filePathMap) {
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setCreateTime(new Date());
        goodsInfo.setLastUpdateTime(new Date());
        goodsInfo.setPublishUserId(goodsCreateReq.getUserId());
        goodsInfo.setGoodsDetailsIntro(goodsCreateReq.getGoodsDetailsIntro());
        goodsInfo.setGoodsIntro(goodsCreateReq.getGoodsIntro());
        goodsInfo.setGoodsPrice(goodsCreateReq.getGoodsPrice());
        goodsInfo.setGoodsSalePrice(goodsCreateReq.getGoodsSalePrice());
        goodsInfo.setGoodsTitle(goodsCreateReq.getGoodsTitle());
        goodsInfo.setGoodsImageJson(JSON.toJSONString(filePathMap));
        goodsInfo.setGoodsStock(90000000l);
        return goodsInfo;
    }

    private Long handleEndTime(Date now,Date sellEndTime) {
        if(null != sellEndTime) {
            if(now.compareTo(sellEndTime) < 0){
                return sellEndTime.getTime() - now.getTime();
            }
        }
        return 0l;
    }


}
