package org.cybercrowd.mvp.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.cybercrowd.mvp.constant.AwsS3UploadDirectoryConstants;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.BaseUserTaskDto;
import org.cybercrowd.mvp.dto.request.*;
import org.cybercrowd.mvp.dto.response.UserTaskListRes;
import org.cybercrowd.mvp.enums.*;
import org.cybercrowd.mvp.mapper.*;
import org.cybercrowd.mvp.model.*;
import org.cybercrowd.mvp.service.DaoService;
import org.cybercrowd.mvp.service.TaskRulesService;
import org.cybercrowd.mvp.service.TaskService;
import org.cybercrowd.mvp.util.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

    private Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    AwsS3StorageService awsS3StorageService;
    @Autowired
    CommonService commonService;
    @Autowired
    DaoService daoService;
    @Autowired
    TaskInfoMapper taskInfoMapper;
    @Autowired
    TaskEventMapper taskEventMapper;
    @Autowired
    TaskRulesMapper taskRulesMapper;
    @Autowired
    GoodsInfoMapper goodsInfoMapper;
    @Autowired
    NftMaterialMapper nftMaterialMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    TaskRulesService taskRulesService;
    @Autowired
    NftMintService nftMintService;

    @Override
    public BaseResponse publishTask(TaskPublishReq taskPublishReq) {
        BaseResponse baseResponse = new BaseResponse();
        logger.info("任务发布业务,请求入参:{}",JSON.toJSONString(taskPublishReq));
        //验证待发布任务是否存在
        String taskId = taskPublishReq.getTaskId();
        TaskInfo taskInfo = taskInfoMapper.selectTaskByTaskIdAndStatus(
                taskId, TaskStatusEnum.NOT_STARTED.getCode());
        if(null == taskInfo){
            logger.info("任务发布业务,taskId:{} 任务未找到....",taskId);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            return baseResponse;
        }

        TaskEventEnum taskEventEnum = null;
        if(TaskTypeEnum.DISTRIBUTOR.getCode().equals(taskInfo.getTaskType())){
            taskEventEnum = TaskEventEnum.DISTRIBUTOR;
            TaskRules taskRules = taskRulesMapper.selectTaskRulesByType(taskInfo.getTaskParentId(), TaskRulesTypeEnum.DISTRIBUTOR.getCode());
            GrouponRulesDto grouponRulesDto = JSON.parseObject(taskRules.getTaskRulesJson(),GrouponRulesDto.class);
            //计算任务结束时间
            //计算时间
            String timezone = "GMT+8";
            ZoneId zoneId = ZoneId.of(timezone);
            LocalDateTime localDateTime = LocalDateTime.now();
            taskInfo.setTaskStartTime(new Date());
            localDateTime = localDateTime.plusDays(Long.valueOf(grouponRulesDto.getExpirationDay()));
            taskInfo.setTaskEndTime(Date.from(localDateTime.atZone(zoneId).toInstant()));
        }else if(TaskTypeEnum.SALES.getCode().equals(taskInfo.getTaskType())){
            taskEventEnum = TaskEventEnum.SALES;
        }else if(TaskTypeEnum.GROUPON.getCode().equals(taskInfo.getTaskType())){
            taskEventEnum = TaskEventEnum.GROUPON;
        }

        //如果是分销和拼团这里还需要加入父级任务的Dao组织
        if(TaskTypeEnum.GROUPON.getCode().equals(taskInfo.getTaskType())
                        || TaskTypeEnum.DISTRIBUTOR.getCode().equals(taskInfo.getTaskType())){
           baseResponse = daoService.joinDaoOrganize(taskInfo.getTaskOwnerId(), taskInfo.getTaskParentId());
            //更新指定任务分销次数
            taskInfoMapper.updateTaskDistributionQuantity(taskInfo.getTaskParentId());
        }

        if(ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())) {
            //创建任务事件
            TaskEvent taskEvent = commonService.createTaskEvent(taskInfo, taskEventEnum);
            //保存事件更新任务状态
            taskEventMapper.insertSelective(taskEvent);
            logger.info("任务发布业务,保存任务事件信息:{}", JSON.toJSONString(taskEvent));
            taskInfo.setTaskStatus(TaskStatusEnum.STARTED.getCode());
            taskInfoMapper.updateByPrimaryKeySelective(taskInfo);
            logger.info("任务发布业务,更新任务状态:{}", TaskStatusEnum.STARTED.getMsg());
            goodsInfoMapper.updateGoodsPublishTime(taskInfo.getGoodsId(), new Date());
        }

        //更新商品发布时间
        logger.info("任务发布业务,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @Override
    public BaseResponse createGroupon(GrouponCreateReq grouponCreateReq) {
        BaseResponse baseResponse = new BaseResponse();
        logger.info("创建拼团业务,请求入参:{}",JSON.toJSONString(grouponCreateReq));
//        String taskParentId = grouponCreateReq.getTaskParentId();
//        Long userId = grouponCreateReq.getUserId();
//        Long goodsId = grouponCreateReq.getGoodsId();
//        //创建拼团任务
//        grouponCreateReq.
////        TaskInfo grouponTask = createGrouponTask(userId,taskParentId,goodsId,,grouponCreateReq.getGrouponRulesDto());
////        taskInfoMapper.insertSelective(grouponTask);
//        //创建任务事件
//        TaskEvent taskEvent = commonService.createTaskEvent(grouponTask, TaskEventEnum.GROUPON);
//        taskEventMapper.insertSelective(taskEvent);
//        //加入父级任务Dao组织
//        daoService.joinDaoOrganize(userId,taskParentId);
//        //创建Dao
//        daoService.createDaoOrganize(userId,grouponTask.getTaskId(),DaoEventEnum.GROUPON);
//        logger.info("创建拼团业务,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @Override
    public BaseResponse userTaskList(UserTaskListReq userTaskListReq) {
        BaseResponse baseResponse = new BaseResponse();
        int pageNum = userTaskListReq.getPageNum();
        int pageSize = userTaskListReq.getPageSize();
        Long userId = userTaskListReq.getUserId();
        PageHelper.startPage(pageNum,pageSize);

        List<BaseUserTaskDto> baseUserTaskList = taskInfoMapper.findBaseUserTaskList(userId);
        if(null != baseUserTaskList && baseUserTaskList.size() >0){
            UserTaskListRes userTaskListRes = new UserTaskListRes();
            userTaskListRes.setPageNum(pageNum);
            userTaskListRes.setPageSize(pageSize);
            userTaskListRes.setTotalPage(new PageInfo(baseUserTaskList).getPages());

            for(BaseUserTaskDto baseUserTaskDto:baseUserTaskList){
                Long daoOwnerId = baseUserTaskDto.getDaoOwnerId();
                String userNickName = userInfoMapper.findUserNickName(daoOwnerId);
                //TODO 中英文切换
                baseUserTaskDto.setTaskName(TaskEventEnum.toEnum(baseUserTaskDto.getEventType()).getMsg() + " Task");
//                baseUserTaskDto.setTaskName(TaskEventEnum.toEnum(baseUserTaskDto.getEventType()).getMsg() + "任务");
                baseUserTaskDto.setDaoName(userNickName + TaskTypeEnum.toEnum(baseUserTaskDto.getTaskType()).getMsg());
                //砍一刀和购买直接成功
                if(TaskEventEnum.HAGGLE.getCode().equals(baseUserTaskDto.getEventType())
                        || TaskEventEnum.GROUPON_BUY.getCode().equals(baseUserTaskDto.getEventType())){
                    baseUserTaskDto.setTaskStatus(TaskStatusEnum.COMPLETED.getCode());
                }
            }
            userTaskListRes.setBaseUserTaskList(baseUserTaskList);
            baseResponse.setData(userTaskListRes);
        }
        logger.info("用户任务列表查询业务,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @Override
    public BaseResponse publishSaleTask(SaleTaskPublishReq saleTaskPublishReq) {
        BaseResponse baseResponse = new BaseResponse();
        //查询上级任务信息,确认是否符合条件
        Long userId = saleTaskPublishReq.getUserId();
        DistributionRulesDto distributionRulesDto = saleTaskPublishReq.getDistributionRulesDto();
        GrouponRulesDto grouponRulesDto = saleTaskPublishReq.getGrouponRulesDto();
        BigDecimal goodsSalePrice = saleTaskPublishReq.getGoodsSalePrice();
        baseResponse = checkTaskRule(grouponRulesDto,distributionRulesDto,goodsSalePrice);
        if(!ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
            logger.info("商品销售任务发布业务,规则错误,发布失败:{}",JSON.toJSONString(baseResponse));
            return baseResponse;
        }
        //处理商品数据
        baseResponse = commonService.uploadGoodsImages((Map<String, MultipartFile>) saleTaskPublishReq.getFileMap());
        if(!ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
            logger.info("商品销售任务发布业务,上传商品图片文件失败:{}",JSON.toJSONString(baseResponse));
            return baseResponse;
        }
        GoodsInfo goodsInfo = createGoods(saleTaskPublishReq,(Map<String, String>)baseResponse.getData());
        //创建拼团任务
        boolean enableNft = saleTaskPublishReq.isEnableNft();
        TaskInfo taskInfo = createtTask(goodsInfo,TaskTypeEnum.SALES,enableNft,null == distributionRulesDto ? false:true);
//        //上传NFT图片
//        if(enableNft) {
//            Object nftFileMap = saleTaskPublishReq.getNftFileMap();
//            baseResponse = commonService.uploadNftFile(nftFileMap,userId,taskInfo.getTaskId());
//            if(!ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
//                return baseResponse;
//            }
//        }
        //保存任务规则
        String taskId = taskInfo.getTaskId();
        saveTaskRules(grouponRulesDto,distributionRulesDto,taskId,userId);
        //保存商品信息
        goodsInfoMapper.insertSelective(goodsInfo);
        //创建自己的拼团DAO
        baseResponse = daoService.createDaoOrganize(userId,taskId,DaoEventEnum.SALES);
        //保存拼团任务
        taskInfo.setGoodsId(goodsInfo.getId());
        taskInfo.setEnableDistributor("1");
        taskInfo.setTaskStatus(TaskStatusEnum.STARTED.getCode());
        taskInfoMapper.insertSelective(taskInfo);
        //创建事件
        TaskEvent taskEvent = commonService.createTaskEvent(taskInfo, TaskEventEnum.SALES);
        taskEventMapper.insertSelective(taskEvent);

        //NFT铸造
        if(enableNft) {
            Object nftFileMap = saleTaskPublishReq.getNftFileMap();
            baseResponse = nftMintService.daoNftMint((Map<String, MultipartFile>) nftFileMap,
                    (String)baseResponse.getData(),taskInfo.getTaskId(),"0x34BB184bFb7B7795cc1Ded050deA1344c392A7C1");
            if(!ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
                return baseResponse;
            }
        }
        return baseResponse;
    }

    private TaskInfo createtTask(GoodsInfo goodsInfo,TaskTypeEnum taskTypeEnum,
                                 boolean enableNft,boolean distributor) {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskId(IDUtils.getTaskId());
        taskInfo.setGoodsId(goodsInfo.getId());
        taskInfo.setTaskType(taskTypeEnum.getCode());
        taskInfo.setTaskOwnerId(goodsInfo.getPublishUserId());
        taskInfo.setDealQuantity(0l);
        taskInfo.setDistributorQuantity(0l);
        taskInfo.setGrouponQuantity(0l);
        taskInfo.setEnableDistributor(distributor ? "1":"0");
        taskInfo.setEnableNft(enableNft ? "1":"0");
        taskInfo.setDistributorQuantity(0l);
        taskInfo.setTaskEndTime(null);
        taskInfo.setTaskStatus(TaskStatusEnum.NOT_STARTED.getCode());
        taskInfo.setCreateTime(new Date());
        return taskInfo;
    }

    private GoodsInfo createGoods(SaleTaskPublishReq saleTaskPublishReq,Map<String,String> filePathMap) {
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setCreateTime(new Date());
        goodsInfo.setLastUpdateTime(new Date());
        goodsInfo.setPublishUserId(saleTaskPublishReq.getUserId());
        goodsInfo.setGoodsDetailsIntro(saleTaskPublishReq.getGoodsDetailsIntro());
        goodsInfo.setGoodsIntro(saleTaskPublishReq.getGoodsIntro());
        goodsInfo.setGoodsPrice(saleTaskPublishReq.getGoodsPrice());
        goodsInfo.setGoodsSalePrice(saleTaskPublishReq.getGoodsSalePrice());
        goodsInfo.setGoodsTitle(saleTaskPublishReq.getGoodsTitle());
        goodsInfo.setGoodsStock(90000000l);
        goodsInfo.setPublishTime(new Date());
        goodsInfo.setGoodsImageJson(JSON.toJSONString(filePathMap));
        return goodsInfo;
    }

    private GoodsInfo createGoods(DistributorTaskPublishReq distributorTaskPublishReq,GoodsInfo parentGoodsInfo,Map<String,String> filePathMap) {
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setCreateTime(new Date());
        goodsInfo.setLastUpdateTime(new Date());
        goodsInfo.setPublishUserId(distributorTaskPublishReq.getUserId());
        goodsInfo.setGoodsDetailsIntro(parentGoodsInfo.getGoodsDetailsIntro());
        goodsInfo.setGoodsIntro(parentGoodsInfo.getGoodsIntro());
        goodsInfo.setGoodsPrice(distributorTaskPublishReq.getGoodsPrice());
        goodsInfo.setGoodsSalePrice(distributorTaskPublishReq.getGoodsSalePrice());
        goodsInfo.setGoodsTitle(parentGoodsInfo.getGoodsTitle());
        goodsInfo.setGoodsStock(90000000l);
        goodsInfo.setPublishTime(new Date());
        goodsInfo.setGoodsImageJson(JSON.toJSONString(filePathMap));
        return goodsInfo;
    }

    @Override
    public BaseResponse publishGrouponTask(GrouponTaskPublishReq grouponTaskPublishReq) {
        BaseResponse baseResponse = new BaseResponse();
        //查询上级任务信息,确认是否符合条件
        String taskParentId = grouponTaskPublishReq.getTaskParentId();
        Long userId = grouponTaskPublishReq.getUserId();
        TaskInfo parentTaskInfo = taskInfoMapper.selectTaskByTaskId(taskParentId);
        //检查是否满足发布条件
        baseResponse = checkTaskPublishCondition(parentTaskInfo,TaskTypeEnum.GROUPON,userId);
        if(!ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
            logger.info("拼团任务发布业务,不满足发布条件,发布失败:{}",JSON.toJSONString(baseResponse));
            return baseResponse;
        }
        //查询拼团规则数据
        TaskRules taskRules = taskRulesMapper.selectTaskRulesByType(taskParentId, TaskRulesTypeEnum.GROUPON.getCode());
        GrouponRulesDto grouponRulesDto = JSON.parseObject(taskRules.getTaskRulesJson(), GrouponRulesDto.class);
        //处理商品数据
        GoodsInfo newGoodsInfo = newGoodsHandle(parentTaskInfo,grouponRulesDto.getPrice(),userId);
        //创建拼团任务
        boolean enableNft = grouponTaskPublishReq.isEnableNft();
        TaskInfo grouponTask = createGrouponTask(userId, parentTaskInfo.getTaskId(),
                newGoodsInfo.getId(), enableNft, grouponRulesDto);
//        //上传NFT图片
//        if(enableNft) {
//            Object nftFileMap = grouponTaskPublishReq.getNftFileMap();
//            baseResponse = commonService.uploadNftFile(nftFileMap,userId,grouponTask.getTaskId());
//            if(!ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
//                return baseResponse;
//            }
//        }
        //保存商品信息
        goodsInfoMapper.insertSelective(newGoodsInfo);
        //加入上级DAO组织
        daoService.joinDaoOrganize(userId,parentTaskInfo.getTaskId());
        //创建自己的拼团DAO
        baseResponse = daoService.createDaoOrganize(userId,grouponTask.getTaskId(),DaoEventEnum.GROUPON);
        //保存拼团任务
        grouponTask.setGoodsId(newGoodsInfo.getId());
        taskInfoMapper.insertSelective(grouponTask);
        //上级DAO拼团数+1
        parentTaskInfo.setGrouponQuantity(parentTaskInfo.getGrouponQuantity()+1);
        taskInfoMapper.updateByPrimaryKeySelective(parentTaskInfo);
        //创建事件
        TaskEvent taskEvent = commonService.createTaskEvent(grouponTask, TaskEventEnum.GROUPON);
        taskEventMapper.insertSelective(taskEvent);

        //NFT铸造
        if(enableNft) {
            Object nftFileMap = grouponTaskPublishReq.getNftFileMap();
            baseResponse = nftMintService.daoNftMint((Map<String, MultipartFile>) nftFileMap,
                    (String) baseResponse.getData(),grouponTask.getTaskId(),"0x34BB184bFb7B7795cc1Ded050deA1344c392A7C1");
            if(!ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
                return baseResponse;
            }
        }
        return baseResponse;
    }

    @Override
    public BaseResponse publishDistributorTask(DistributorTaskPublishReq distributorTaskPublishReq) {
        BaseResponse baseResponse = new BaseResponse();
        //查询上级任务信息,确认是否符合条件
        String taskParentId = distributorTaskPublishReq.getTaskParentId();
        Long userId = distributorTaskPublishReq.getUserId();
        TaskInfo parentTaskInfo = taskInfoMapper.selectTaskByTaskId(taskParentId);
        //检查是否满足发布条件
        baseResponse = checkTaskPublishCondition(parentTaskInfo,TaskTypeEnum.DISTRIBUTOR,userId);
        if(!ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
            logger.info("分销任务发布业务,不满足发布条件,发布失败:{}",JSON.toJSONString(baseResponse));
            return baseResponse;
        }
        //验证规则参数
        BigDecimal goodsSalePrice = distributorTaskPublishReq.getGoodsSalePrice();
        BigDecimal goodsPrice = distributorTaskPublishReq.getGoodsPrice();
        DistributionRulesDto distributionRulesDto = distributorTaskPublishReq.getDistributionRulesDto();
        GrouponRulesDto grouponRulesDto = distributorTaskPublishReq.getGrouponRulesDto();
        baseResponse = checkTaskRule(grouponRulesDto,distributionRulesDto,goodsSalePrice);
        if(!ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
            logger.info("分销任务发布业务,规则错误,发布失败:{}",JSON.toJSONString(baseResponse));
            return baseResponse;
        }
//        //处理商品数据
//        GoodsInfo newGoodsInfo = newGoodsHandle(parentTaskInfo,goodsSalePrice,userId);
//        newGoodsInfo.setGoodsPrice(goodsPrice);

        //处理商品图片
        baseResponse = commonService.uploadGoodsImages((Map<String, MultipartFile>) distributorTaskPublishReq.getFileMap());
        if(!ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
            logger.info("分销任务发布业务,上传商品图片文件失败:{}",JSON.toJSONString(baseResponse));
            return baseResponse;
        }
        //创建商品数据
        //获取父级商品信息
        GoodsInfo parnetGoodsInfo = goodsInfoMapper.selectByPrimaryKey(parentTaskInfo.getGoodsId());
        GoodsInfo goodsInfo = createGoods(distributorTaskPublishReq,parnetGoodsInfo,(Map<String, String>)baseResponse.getData());

        //创建分销任务
        boolean enableNft = distributorTaskPublishReq.isEnableNft();
        //拿到上级任务规定的分销规则
        TaskRules taskRules = taskRulesMapper.selectTaskRulesByType(taskParentId, TaskRulesTypeEnum.DISTRIBUTOR.getCode());
        TaskInfo distributorTask = createDistributorTask(userId, taskParentId, enableNft,
                JSON.parseObject(taskRules.getTaskRulesJson(),DistributionRulesDto.class));
        String taskId = distributorTask.getTaskId();

        //保存任务规则
        saveTaskRules(grouponRulesDto,distributionRulesDto,taskId,userId);
        //保存商品信息
        goodsInfoMapper.insertSelective(goodsInfo);
        //加入上级DAO组织
        daoService.joinDaoOrganize(userId,taskParentId);
        //创建自己的拼团DAO
        baseResponse = daoService.createDaoOrganize(userId,taskId,DaoEventEnum.DISTRIBUTOR);
        //保存拼团任务
        distributorTask.setGoodsId(goodsInfo.getId());
        distributorTask.setEnableDistributor("1");
        taskInfoMapper.insertSelective(distributorTask);
        //上级DAO拼团数+1
        parentTaskInfo.setDistributorQuantity(parentTaskInfo.getGrouponQuantity()+1);
        taskInfoMapper.updateByPrimaryKeySelective(parentTaskInfo);
        //创建事件
        TaskEvent taskEvent = commonService.createTaskEvent(distributorTask, TaskEventEnum.DISTRIBUTOR);
        taskEventMapper.insertSelective(taskEvent);
        //铸造NFT
        if(enableNft) {
            Object nftFileMap = distributorTaskPublishReq.getNftFileMap();
            baseResponse = nftMintService.daoNftMint((Map<String, MultipartFile>) nftFileMap,
                    (String) baseResponse.getData(),distributorTask.getTaskId(),"0x34BB184bFb7B7795cc1Ded050deA1344c392A7C1");
            if(!ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
                return baseResponse;
            }
        }
        return baseResponse;
    }

    private void saveTaskRules(GrouponRulesDto grouponRulesDto, DistributionRulesDto distributionRulesDto, String taskId, Long userId) {
        TaskRulesCreateReq taskRulesCreateReq = new TaskRulesCreateReq();
        taskRulesCreateReq.setTaskId(taskId);
        taskRulesCreateReq.setTaskRulesType(TaskRulesTypeEnum.GROUPON.getCode());
        taskRulesCreateReq.setUserId(userId);
        taskRulesCreateReq.setGrouponRulesDto(grouponRulesDto);
        taskRulesService.createTaskRules(taskRulesCreateReq);

        taskRulesCreateReq.setTaskRulesType(TaskRulesTypeEnum.DISTRIBUTOR.getCode());
        taskRulesCreateReq.setDistributionRulesDto(distributionRulesDto);
        taskRulesService.createTaskRules(taskRulesCreateReq);
    }

    private BaseResponse checkTaskRule(GrouponRulesDto grouponRulesDto,
                                       DistributionRulesDto distributionRulesDto, BigDecimal goodsSalePrice) {
        BaseResponse baseResponse = new BaseResponse();
        //拼团规则
        BigDecimal grouponPrice = grouponRulesDto.getPrice();
        if(grouponPrice.compareTo(goodsSalePrice) >= 0){
            logger.info("任务发布,拼团规则校验,拼团价格:{} 大于或者等于商品单独销售价格:{}",grouponPrice,goodsSalePrice);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.TASK_PUBLISH_ERROR);
            return baseResponse;
        }
        int expirationDay = grouponRulesDto.getExpirationDay();
        if(expirationDay <=0){
            logger.info("任务发布,拼团规则校验,任务截止天数不能小于等于0");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.TASK_PUBLISH_ERROR);
            return baseResponse;
        }
        //分销规则
        return baseResponse;
    }


    private TaskInfo createDistributorTask(Long userId,String taskParentId,
                                       boolean enableNft,DistributionRulesDto distributionRulesDto) {
        TaskInfo grouponTask = new TaskInfo();
        grouponTask.setEnableNft(enableNft ? "1":"0");
        grouponTask.setEnableDistributor("1");
        grouponTask.setTaskId(IDUtils.getTaskId());
        grouponTask.setTaskParentId(taskParentId);
        grouponTask.setTaskType(TaskTypeEnum.DISTRIBUTOR.getCode());
        grouponTask.setTaskOwnerId(userId);
        grouponTask.setDealQuantity(0l);
        grouponTask.setDistributorQuantity(0l);
        grouponTask.setGrouponQuantity(0l);
        grouponTask.setEnableDistributor("0");
        grouponTask.setTaskStartTime(new Date());
        grouponTask.setTaskStatus(TaskStatusEnum.STARTED.getCode());
        grouponTask.setCreateTime(new Date());
        grouponTask.setUpdateTime(new Date());
        int expirationDay = distributionRulesDto.getExpirationDay();
        //计算时间
        String timezone = "GMT+8";
        ZoneId zoneId = ZoneId.of(timezone);
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.plusDays(Long.valueOf(expirationDay));
        grouponTask.setTaskEndTime(Date.from(localDateTime.atZone(zoneId).toInstant()));
        return grouponTask;
    }

    private TaskInfo createGrouponTask(Long userId,String taskParentId,Long goodsId,
                                       boolean enableNft,GrouponRulesDto grouponRulesDto) {
        TaskInfo grouponTask = new TaskInfo();
        grouponTask.setEnableNft(enableNft ? "1":"0");
        grouponTask.setEnableDistributor("0");
        grouponTask.setTaskId(IDUtils.getTaskId());
        grouponTask.setTaskParentId(taskParentId);
        grouponTask.setGoodsId(goodsId);
        grouponTask.setTaskType(TaskTypeEnum.GROUPON.getCode());
        grouponTask.setTaskOwnerId(userId);
        grouponTask.setDealQuantity(0l);
        grouponTask.setDistributorQuantity(0l);
        grouponTask.setGrouponQuantity(0l);
        grouponTask.setEnableDistributor("0");
        grouponTask.setTaskStartTime(new Date());
        grouponTask.setTaskStatus(TaskStatusEnum.STARTED.getCode());
        grouponTask.setCreateTime(new Date());
        grouponTask.setUpdateTime(new Date());
        grouponTask.setGrouponPeople(Long.valueOf(grouponRulesDto.getParticipantsMin()));
        grouponTask.setGrouponPeopleLimit(Long.valueOf(grouponRulesDto.getParticipantsMax()));
        int expirationDay = grouponRulesDto.getExpirationDay();
        //计算时间
        String timezone = "GMT+8";
        ZoneId zoneId = ZoneId.of(timezone);
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.plusDays(Long.valueOf(expirationDay));
        grouponTask.setTaskEndTime(Date.from(localDateTime.atZone(zoneId).toInstant()));
        return grouponTask;
    }

    private BaseResponse checkTaskPublishCondition(TaskInfo parentTaskInfo,TaskTypeEnum taskTypeEnum,Long userId) {
        BaseResponse baseResponse = new BaseResponse();

        if(null == parentTaskInfo){
            logger.info("任务发布条件校验,任务未找到");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.TASK_NOFT_FOUND_ERROR);
            return baseResponse;
        }

        Long taskOwnerId = parentTaskInfo.getTaskOwnerId();
        if(taskOwnerId.intValue() == userId.intValue()){
            logger.info("任务发布条件校验,自己不能参与自己发布的任务");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.TASK_PUBLISH_ERROR);
            return baseResponse;
        }
        String taskType = parentTaskInfo.getTaskType();
        if((TaskTypeEnum.SALES.getCode().equals(taskType)
                && (TaskTypeEnum.GROUPON == taskTypeEnum || TaskTypeEnum.DISTRIBUTOR == taskTypeEnum))
                || (TaskTypeEnum.DISTRIBUTOR.getCode().equals(taskType))
                && (TaskTypeEnum.GROUPON == taskTypeEnum || TaskTypeEnum.DISTRIBUTOR == taskTypeEnum)){
        }else {
            logger.info("任务发布条件校验,发布的任务类型和父任务类型关系不匹配");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.TASK_PUBLISH_ERROR);
            return baseResponse;
        }
        String status = parentTaskInfo.getTaskStatus();
        if(!TaskStatusEnum.STARTED.getCode().equals(status)){
            logger.info("任务发布条件校验,父任务已结束子任务无法发布");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.TASK_PUBLISH_ERROR);
            return baseResponse;
        }
        return baseResponse;
    }

    private GoodsInfo newGoodsHandle(TaskInfo parentTaskInfo, BigDecimal goodsSalePrice, Long userId) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(parentTaskInfo.getGoodsId());
        //拼团商品继续使用任务商品基本信息，删除 goods id 修改价格、时间信息
        goodsInfo.setId(null);
        goodsInfo.setPublishUserId(userId);
        goodsInfo.setGoodsSalePrice(goodsSalePrice);
        goodsInfo.setLastUpdateTime(new Date());
        goodsInfo.setCreateTime(new Date());
        goodsInfo.setPublishTime(new Date());
        return goodsInfo;
    }
}
