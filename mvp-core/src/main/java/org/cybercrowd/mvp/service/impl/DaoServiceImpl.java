package org.cybercrowd.mvp.service.impl;

import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.cybercrowd.mvp.dto.*;
import org.cybercrowd.mvp.dto.request.*;
import org.cybercrowd.mvp.dto.response.DaoAllRes;
import org.cybercrowd.mvp.dto.response.UserDaoDetailsRes;
import org.cybercrowd.mvp.dto.response.UserDaoListRes;
import org.cybercrowd.mvp.enums.*;
import org.cybercrowd.mvp.mapper.*;
import org.cybercrowd.mvp.model.*;
import org.cybercrowd.mvp.service.DaoService;
import org.cybercrowd.mvp.util.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.*;

@Service("daoService")
public class DaoServiceImpl implements DaoService {

    private Logger logger = LoggerFactory.getLogger(DaoServiceImpl.class);

    @Autowired
    CommonService commonService;
    @Autowired
    NftMintService nftMintService;
    @Autowired
    DaoOrganizeMapper daoOrganizeMapper;
    @Autowired
    DaoMemberMapper daoMemberMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    DaoEventMapper daoEventMapper;
    @Autowired
    TaskInfoMapper taskInfoMapper;
    @Autowired
    TaskRulesMapper taskRulesMapper;
    @Autowired
    TaskEventMapper taskEventMapper;
    @Autowired
    ProposalMapper proposalMapper;
    @Autowired
    ProposalOptionsMapper proposalOptionsMapper;
    @Autowired
    DaoNftAssetsMapper daoNftAssetsMapper;


    @Override
    public BaseResponse createDaoOrganize(Long userId, String taskId, DaoEventEnum daoEventEnum) {
        BaseResponse baseResponse = new BaseResponse();
        logger.info("创建DAO组织业务,请求入参: userId:{} taskId:{}",userId,taskId);
        //查询任务ID关联Dao组织是否存在
        DaoOrganize daoOrganize = daoOrganizeMapper.findDaoOrganizeByTaskId(taskId);
        if(null != daoOrganize){
            logger.info("创建DAO组织业务,任务所属DAO组织已存在");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            return baseResponse;
        }
        daoOrganize = new DaoOrganize();
        daoOrganize.setDaoNo(IDUtils.getDaoNo());
        daoOrganize.setDaoName(taskId);
        daoOrganize.setDaoIntro("还没有DAO相关介绍");
        daoOrganize.setDaoOwnerId(userId);
        daoOrganize.setTaskId(taskId);
        daoOrganize.setCreateTime(new Date());
        daoOrganize.setUpdateTime(new Date());
        //新增Dao成员
        DaoMember daoMember = createDaoMember(userId, daoOrganize.getDaoNo(), "1");
        daoOrganizeMapper.insertSelective(daoOrganize);
        daoMemberMapper.insertSelective(daoMember);
        //创建Dao事件
        DaoEvent daoEvent = commonService.createDaoEvent(userId, daoOrganize, "", daoEventEnum);
        daoEventMapper.insertSelective(daoEvent);
        baseResponse.setData(daoOrganize.getDaoNo());
        return baseResponse;
    }

    @Override
    public BaseResponse joinDaoOrganize(Long userId, String taskId) {
        BaseResponse baseResponse = new BaseResponse();
        //查询任务ID关联Dao组织是否存在
        DaoOrganize daoOrganize = daoOrganizeMapper.findDaoOrganizeByTaskId(taskId);
        if(null == daoOrganize){
            logger.info("Dao组织成员加入业务,DAO组织不存在");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            return baseResponse;
        }
        //检查待加入的成员是否已存在,不存在则创建成员数据
        DaoMember member = daoMemberMapper.findMember(userId, daoOrganize.getDaoNo());
        if(null == member){
            logger.info("Dao组织成员加入业务,当前待加入用户不再组织中,现在开始创建成员数据");
            DaoMember daoMember = createDaoMember(userId, daoOrganize.getDaoNo(), "0");
            logger.info("Dao组织成员加入业务,当前待加入用户不再组织中,现在开始创建成员数据:{}", JSON.toJSONString(daoMember));
            daoMemberMapper.insertSelective(daoMember);
        }
        logger.info("Dao组织成员加入业务,处理完成...");
        return baseResponse;
    }

    @Override
    public BaseResponse updateDaoOrganize(DaoOrganizeUpdateReq daoOrganizeUpdateReq) {
        return null;
    }

    @Override
    public BaseResponse createDaoEvent(Long userId, String taskId,String proposalNo, DaoEventEnum daoEventEnum) {
        BaseResponse baseResponse = new BaseResponse();
        DaoOrganize daoOrganize = daoOrganizeMapper.findDaoOrganizeByTaskId(taskId);
        DaoEvent daoEvent = commonService.createDaoEvent(userId,daoOrganize,proposalNo,daoEventEnum);
        daoEventMapper.insertSelective(daoEvent);
        return baseResponse;
    }

    @Override
    public BaseResponse userDaoList(UserDaoListReq userDaoListReq) {
        BaseResponse baseResponse = new BaseResponse();
        UserDaoListRes userDaoListRes = new UserDaoListRes();
        logger.info("用户DAO列表查询业务,请求入参:{}",JSON.toJSONString(userDaoListReq));
        Long userId = userDaoListReq.getUserId();
        int pageNum = userDaoListReq.getPageNum();
        int pageSize = userDaoListReq.getPageSize();
        //分页查询
        PageHelper.startPage(pageNum,pageSize);
        List<BaseDaoDto> userDaoList = daoOrganizeMapper.findUserDaoList(userId);
        if(null != userDaoList && userDaoList.size() >0){
            for(BaseDaoDto baseDaoDto :userDaoList){
                baseDaoDto.setDaoName(baseDaoDto.getUserNickName()
                        + " " + TaskTypeEnum.toEnum(baseDaoDto.getTaskType()).getMsg());
                baseDaoDto.setMemberNumber(daoMemberMapper.countDaoMember(baseDaoDto.getDaoNo()));
                if(baseDaoDto.getDaoOwnerId().intValue() == userId){
                    baseDaoDto.setOwner(true);
                }
            }
            userDaoListRes.setUserDaoDtoList(userDaoList);
            PageInfo pageInfo = new PageInfo(userDaoList);
            userDaoListRes.setTotalPage(pageInfo.getPages());
            userDaoListRes.setPageNum(pageNum);
            userDaoListRes.setPageSize(pageSize);
            baseResponse.setData(userDaoListRes);
        }
        logger.info("用户DAO列表查询业务,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @Override
    public BaseResponse userDaoDetails(UserDaoDetailsReq userDaoDetailsReq) {
        BaseResponse baseResponse = new BaseResponse();
        UserDaoDetailsRes userDaoDetailsRes = new UserDaoDetailsRes();
        Long userId = userDaoDetailsReq.getUserId();
        String daoNo = userDaoDetailsReq.getDaoNo();
        //查询Dao介绍信息
        BaseDaoDto baseDaoDto = daoOrganizeMapper.findDaoDetails(daoNo);
        if(null == baseDaoDto){
            logger.info("用户DAO详情查询业务,daoNo:{} 未找到DAO信息",daoNo);
            return baseResponse;
        }
        //处理UserDao信息
        baseDaoDto.setDaoName(userNickNameHandle(baseDaoDto.getUserNickName()) + "\n" + TaskTypeEnum.toEnum(baseDaoDto.getTaskType()).getMsg());
        baseDaoDto.setMemberNumber(daoMemberMapper.countDaoMember(baseDaoDto.getDaoNo()));
        if(baseDaoDto.getDaoOwnerId().intValue() == userId){
            baseDaoDto.setOwner(true);
        }
        userDaoDetailsRes.setUserDaoDto(baseDaoDto);
        //Dao规则
        setDaoRulesInfo(userDaoDetailsRes, baseDaoDto);
        //DAO组织图数据
        setDaoViweInfo(userDaoDetailsRes,userId);
        //DAO提案列表
        userDaoDetailsRes.setDaoProposalList(setDaoProposalInfo(baseDaoDto.getDaoNo()));
        //DAO NFT集合
        TaskInfo taskInfo = taskInfoMapper.selectTaskByTaskId(baseDaoDto.getTaskId());
        if("1".equals(taskInfo.getEnableNft())){
            userDaoDetailsRes.setNftAssetsLink("https://testnets.opensea.io/collection/cybercrowd-mvp-nft-v3");
        }
        //DAO NFT资产
        List<BaseDaoNftAssetsDto> baseDaoNftAssets = daoNftAssetsMapper.findBaseDaoNftAssets(baseDaoDto.getDaoNo(), baseDaoDto.getTaskId());
        userDaoDetailsRes.setDaoNftAssetsDtoList(baseDaoNftAssets);
        baseResponse.setData(userDaoDetailsRes);
        logger.info("用户DAO详情查询业务,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @Override
    public BaseResponse daoAll(DaoAllReq daoAllReq) {
        BaseResponse baseResponse = new BaseResponse();
        int pageNum = daoAllReq.getPageNum();
        int pageSize = daoAllReq.getPageSize();

        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("daoo.create_time asc");
        String daoNo = daoAllReq.getDaoNo();
        List<BaseDaoDto> baseDaoDtos = daoOrganizeMapper.selectAll(daoNo);
        if(null != baseDaoDtos && baseDaoDtos.size() >0){
            DaoAllRes daoAllRes = new DaoAllRes();
            PageInfo pageInfo = new PageInfo(baseDaoDtos);
            daoAllRes.setTotalPage(pageInfo.getPages());
            daoAllRes.setPageNum(pageNum);
            daoAllRes.setPageSize(pageSize);
            for(BaseDaoDto baseDaoDto:baseDaoDtos){
                baseDaoDto.setDaoName(userNickNameHandle(baseDaoDto.getUserNickName()) + "\n" + TaskTypeEnum.toEnum(baseDaoDto.getTaskType()).getMsg());
                baseDaoDto.setMemberNumber(daoMemberMapper.countDaoMember(baseDaoDto.getDaoNo()));
            }
            daoAllRes.setBaseDaoDtoList(baseDaoDtos);
            baseResponse.setData(daoAllRes);
        }
        return baseResponse;
    }

    private List<DaoProposalDto> setDaoProposalInfo(String daoNo) {
        List<DaoProposalDto> daoProposalList = proposalMapper.selectDaoProposalList(daoNo);
        if(null != daoProposalList && daoProposalList.size() > 0){
            for(DaoProposalDto daoProposalDto:daoProposalList){
                String proposalNo = daoProposalDto.getProposalNo();
                daoProposalDto.setEndTimeMillisecond(
                        handleEndTime(new Date(),daoProposalDto.getProposalEndTime()));
                //查询选项数据
                List<BaseOptionDto> baseOptionDtos = proposalOptionsMapper.selectOptionList(proposalNo);
                daoProposalDto.setOptionList(baseOptionDtos);
            }
            return daoProposalList;
        }
        return null;
    }

    @Override
    public BaseResponse userDaoDetailsV2(UserDaoDetailsReq userDaoDetailsReq) {
        BaseResponse baseResponse = new BaseResponse();
        UserDaoDetailsRes userDaoDetailsRes = new UserDaoDetailsRes();
        Long userId = userDaoDetailsReq.getUserId();
        String daoNo = userDaoDetailsReq.getDaoNo();
        //查询Dao介绍信息
        BaseDaoDto baseDaoDto = daoOrganizeMapper.findDaoDetails(daoNo);
        if(null == baseDaoDto){
            logger.info("用户DAO详情查询业务V2,daoNo:{} 未找到DAO信息",daoNo);
            return baseResponse;
        }
        //处理UserDao信息
        baseDaoDto.setDaoName(userNickNameHandle(baseDaoDto.getUserNickName()) + "\n" + TaskTypeEnum.toEnum(baseDaoDto.getTaskType()).getMsg());
        baseDaoDto.setMemberNumber(daoMemberMapper.countDaoMember(baseDaoDto.getDaoNo()));
        if(baseDaoDto.getDaoOwnerId().intValue() == userId){
            baseDaoDto.setOwner(true);
        }
        userDaoDetailsRes.setUserDaoDto(baseDaoDto);
        //Dao规则
        setDaoRulesInfo(userDaoDetailsRes, baseDaoDto);
        //DAO组织图数据
        setDaoViweInfo2(userDaoDetailsRes,userId);
        //DAO提案列表
        userDaoDetailsRes.setDaoProposalList(setDaoProposalInfo(baseDaoDto.getDaoNo()));
        //DAO NFT集合
        TaskInfo taskInfo = taskInfoMapper.selectTaskByTaskId(baseDaoDto.getTaskId());
        if("1".equals(taskInfo.getEnableNft())){
            userDaoDetailsRes.setNftAssetsLink("https://testnets.opensea.io/collection/cybercrowd-mvp-nft-v3");
        }
        //DAO NFT资产
        List<BaseDaoNftAssetsDto> baseDaoNftAssets = daoNftAssetsMapper.findBaseDaoNftAssets(baseDaoDto.getDaoNo(), baseDaoDto.getTaskId());
        userDaoDetailsRes.setDaoNftAssetsDtoList(baseDaoNftAssets);
        baseResponse.setData(userDaoDetailsRes);
        logger.info("用户DAO详情查询业务V2,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @Override
    public BaseResponse daoNftMint(DaoNftMintReq daoNftMintReq) {
        BaseResponse baseResponse = new BaseResponse();
        Object nftFileMap = daoNftMintReq.getNftFileMap();
        String daoNo = daoNftMintReq.getDaoNo();

        DaoOrganize daoOrganize = daoOrganizeMapper.selectDaoOrganize(daoNo);
        if(null == daoOrganize){
            logger.info("DAO NFT铸造业务,DAO:{} 未找到",daoNo);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            return baseResponse;
        }

        baseResponse = nftMintService.daoNftMint((Map<String, MultipartFile>) nftFileMap,
                daoNo,daoOrganize.getTaskId(),"0x34BB184bFb7B7795cc1Ded050deA1344c392A7C1");
        if(!ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
            return baseResponse;
        }
        logger.info("DAO NFT铸造业务,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    private void setDaoViweInfo(UserDaoDetailsRes userDaoDetailsRes,Long loginUserId){
        DaoViewRootDto daoViewRootDto = new DaoViewRootDto();
        List<DaoViewNodeDto> daoViewNodeDtoList = new ArrayList<>();
        BaseDaoDto baseDaoDto = userDaoDetailsRes.getUserDaoDto();
        String currentDaoNo = baseDaoDto.getDaoNo();
        //所有数据
        Map<String,DaoViewChildDto> daoOrganizeAllMap = new HashMap<>();
        //数据关系
        Map<String,Object> daoRelationMap = new HashMap<>();
        //当前任务ID
        String currentDaoTaskId = baseDaoDto.getTaskId();

        int viewLayers = 1;
        String  rootNodeId = "";

        //获得DAO的层级关系和全部相关DAO数据
        while (true){
            //获取到当前Dao数据
            BaseDaoDto subBaseDaoDto = daoOrganizeMapper.findDaoDetailsByTaskId(currentDaoTaskId);
            if(baseDaoDto.getTaskId().equals(currentDaoTaskId)){
                daoOrganizeAllMap.put(subBaseDaoDto.getTaskId(),setDaoViewChildDto2(subBaseDaoDto,loginUserId,true));
            }else{
                daoOrganizeAllMap.put(subBaseDaoDto.getTaskId(),setDaoViewChildDto2(subBaseDaoDto,loginUserId,false));
            }
            //查询当前DAO数据,看看有没有上级的
            TaskInfo subTaskInfo = taskInfoMapper.selectTaskByTaskId(currentDaoTaskId);
            String taskParentId = subTaskInfo.getTaskParentId();

            //可能存在平级DAO
            if(!StringUtils.isEmpty(taskParentId)){
                //平级DAO数据查询
                setSameLevelDaoData(taskParentId,loginUserId,currentDaoNo,daoOrganizeAllMap,daoRelationMap);
            }else {
                BaseDaoDto parentBaseDaoDto = daoOrganizeMapper.findDaoDetailsByTaskId(currentDaoTaskId);
                //根节点数据找到了
                daoViewNodeDtoList = setDateViewRootInfo(parentBaseDaoDto,loginUserId, parentBaseDaoDto.getDaoNo().equals(currentDaoNo) ? true:false);
                daoViewNodeDtoList.get(0).setId(parentBaseDaoDto.getTaskId());
                rootNodeId = parentBaseDaoDto.getDaoNo();
                break;
            }
            currentDaoTaskId = taskParentId;
        }
        //处理数据
        if(null != daoRelationMap && daoRelationMap.size() >0){
            for(String key:daoRelationMap.keySet()) {
                List<DaoViewChildDto> daoViewChildList = new ArrayList<>();
                Object taskObject = daoRelationMap.get(key);

                if (null != taskObject) {
                    if (taskObject instanceof List<?>) {
                        List<String> taskIds = (List<String>) taskObject;
                        for (String taskId : taskIds) {
                            DaoViewChildDto daoViewChildDto2 = daoOrganizeAllMap.get(taskId);
                            //如果是当前查看的DAO,把当前DAO的下级数据保存进去
                            if (daoViewChildDto2.getId().equals(baseDaoDto.getTaskId())) {
                                viewLayers++;
                                List<DaoViewChildDto> list = setCurrentDaoViweInfo(userDaoDetailsRes, loginUserId);
                                daoViewChildDto2.setChildren(list);
                            }
                            daoViewChildList.add(daoViewChildDto2);
                        }
                        viewLayers++;
                    } else {
                        DaoViewChildDto daoViewChildDto2 = daoOrganizeAllMap.get((String) taskObject);
                        if(null != daoViewChildDto2) {
                            //如果是当前查看的DAO,把当前DAO的下级数据保存进去
                            if (daoViewChildDto2.getId().equals(baseDaoDto.getTaskId())) {
                                viewLayers++;
                                List<DaoViewChildDto> list = setCurrentDaoViweInfo(userDaoDetailsRes, loginUserId);
                                daoViewChildDto2.setChildren(list);
                            }
                            daoViewChildList.add(daoViewChildDto2);
                            viewLayers++;
                        }
                    }
                }

                //如果key是根节点,将数据直接存到根节点数据中
                if (key.equals(daoViewNodeDtoList.get(0).getId())) {
                    daoViewNodeDtoList.get(0).setChildren(daoViewChildList);
                } else {
                    //如果不是根节点那么就是子节点,拿到父节点数据把子节点数据寸进去
                    DaoViewChildDto daoViewChildDto = daoOrganizeAllMap.get(key);
                    daoViewChildDto.setChildren(daoViewChildList);
                    daoOrganizeAllMap.put(key, daoViewChildDto);
                }
            }
        }
        userDaoDetailsRes.setViewLayers(viewLayers);
        daoViewNodeDtoList.get(0).setId(rootNodeId);
        daoViewRootDto.setRootId(rootNodeId);
        daoViewRootDto.setNodes(daoViewNodeDtoList);
        userDaoDetailsRes.setDaoViewRootDto(daoViewRootDto);
    }

    private void setSameLevelDaoData(String taskParentId,Long loginUserId,String currentDaoNo,
                                     Map<String, DaoViewChildDto> daoOrganizeAllMap, Map<String, Object> daoRelationMap) {
        //查询与父级DAO任务ID平级的TaskID
        List<String> taskIdList = taskInfoMapper.findSubTaskIdList(taskParentId);
        //查询相关DAO数据
        List<BaseDaoDto> baseDaoDtoRes = daoOrganizeMapper.findDaoDetailsListByTaskId(taskIdList);
        //同一父级DAO任务下的所有子DAO任务滚西
        daoRelationMap.put(taskParentId,taskIdList);
        //保存数据
        for(BaseDaoDto userDao: baseDaoDtoRes){
            daoOrganizeAllMap.put(userDao.getTaskId(),setDaoViewChildDto2(userDao,loginUserId,userDao.getDaoNo().equals(currentDaoNo) ? true:false));
        }
    }

    private DaoViewChildDto setDaoViewChildDto2(BaseDaoDto userDao, Long loginUserId, boolean currentDaoTag) {
        DaoViewChildDto daoViewChildDto = new DaoViewChildDto();
        daoViewChildDto.setId(userDao.getDaoNo());
        daoViewChildDto.setText(userNickNameHandle(userDao.getUserNickName()) + "\n" + TaskTypeEnum.toEnum(userDao.getTaskType()).getMsg());
        //底色控制
        if(currentDaoTag){
            daoViewChildDto.setColor("#827BFA");
        }
        //高亮
        int daoOnwerId = userDao.getDaoOwnerId().intValue();
        if(daoOnwerId == loginUserId.intValue()){

            daoViewChildDto.setStyleClass("highlight-node");
        }
        return daoViewChildDto;
    }

    private List<DaoViewNodeDto> setDateViewRootInfo(BaseDaoDto rootBaseDaoDto, Long loginUserId, boolean currentDaoTag) {
        List<DaoViewNodeDto> daoViewNodeDtoList = new ArrayList<>();
        //节点
        DaoViewNodeDto daoViewNodeDto = new DaoViewNodeDto();
        daoViewNodeDto.setText(rootBaseDaoDto.getDaoName());
        daoViewNodeDto.setId(rootBaseDaoDto.getTaskId());
        daoViewNodeDto.setText(userNickNameHandle(rootBaseDaoDto.getUserNickName()) + "\n" + TaskTypeEnum.toEnum(rootBaseDaoDto.getTaskType()).getMsg());
        //底色控制
        if(currentDaoTag){
            daoViewNodeDto.setColor("#827BFA");
        }
        //高亮
        int daoOnwerId = rootBaseDaoDto.getDaoOwnerId().intValue();
        if(daoOnwerId == loginUserId.intValue()){
            daoViewNodeDto.setStyleClass("highlight-node");
        }
        daoViewNodeDtoList.add(daoViewNodeDto);
        return daoViewNodeDtoList;
    }

    private List<DaoViewChildDto> setCurrentDaoViweInfo(UserDaoDetailsRes userDaoDetailsRes,Long loginUserId) {
        BaseDaoDto baseDaoDto = userDaoDetailsRes.getUserDaoDto();
        List<DaoViewChildDto> daoViewChildDtoList = new ArrayList<>();
        if(TaskTypeEnum.GROUPON.getCode().equals(baseDaoDto.getTaskType())) {
            //查询拼团任务里面发生的事件记录
            List<UserTaskEventDetailsDto> userTaskEventDetailsList = taskEventMapper.findUserTaskEventDetails(
                    baseDaoDto.getTaskId(), TaskEventEnum.GROUPON.getCode());
            if (null != userTaskEventDetailsList && userTaskEventDetailsList.size() > 0) {
                setDaoViewChildDto(userTaskEventDetailsList, daoViewChildDtoList,loginUserId);
            }
        }else if(TaskTypeEnum.DISTRIBUTOR.getCode().equals(baseDaoDto.getTaskType())){
            //查询分销任务里面发生的事件记录
            List<UserTaskEventDetailsDto> userTaskEventDetailsList = taskEventMapper.findUserTaskEventDetails(
                    baseDaoDto.getTaskId(), TaskEventEnum.DISTRIBUTOR.getCode());
            if (null != userTaskEventDetailsList && userTaskEventDetailsList.size() > 0) {
                setDaoViewChildDto(userTaskEventDetailsList, daoViewChildDtoList,loginUserId);
            }
        }else if(TaskTypeEnum.SALES.getCode().equals(baseDaoDto.getTaskType())) {
            //查询商品销售服务任务里面发生的事件记录
            List<UserTaskEventDetailsDto> userTaskEventDetailsList = taskEventMapper.findUserTaskEventDetails(
                    baseDaoDto.getTaskId(), TaskEventEnum.SALES.getCode());
            if (null != userTaskEventDetailsList && userTaskEventDetailsList.size() > 0) {
                setDaoViewChildDto(userTaskEventDetailsList, daoViewChildDtoList,loginUserId);
            }
        }
        return daoViewChildDtoList;
    }

    private List<DaoViewChildDto> setCurrentDaoViweInfo(BaseDaoDto baseDaoDto,Long loginUserId) {
        List<DaoViewChildDto> daoViewChildDtoList = new ArrayList<>();
        if(TaskTypeEnum.GROUPON.getCode().equals(baseDaoDto.getTaskType())) {
            //查询拼团任务里面发生的事件记录
            List<UserTaskEventDetailsDto> userTaskEventDetailsList = taskEventMapper.findUserTaskEventDetails(
                    baseDaoDto.getTaskId(), TaskEventEnum.GROUPON.getCode());
            if (null != userTaskEventDetailsList && userTaskEventDetailsList.size() > 0) {
                setDaoViewChildDto(userTaskEventDetailsList, daoViewChildDtoList,loginUserId);
            }
        }else if(TaskTypeEnum.DISTRIBUTOR.getCode().equals(baseDaoDto.getTaskType())){
            //查询分销任务里面发生的事件记录
            List<UserTaskEventDetailsDto> userTaskEventDetailsList = taskEventMapper.findUserTaskEventDetails(
                    baseDaoDto.getTaskId(), TaskEventEnum.DISTRIBUTOR.getCode());
            if (null != userTaskEventDetailsList && userTaskEventDetailsList.size() > 0) {
                setDaoViewChildDto(userTaskEventDetailsList, daoViewChildDtoList,loginUserId);
            }
        }else if(TaskTypeEnum.SALES.getCode().equals(baseDaoDto.getTaskType())) {
            //查询商品销售服务任务里面发生的事件记录
            List<UserTaskEventDetailsDto> userTaskEventDetailsList = taskEventMapper.findUserTaskEventDetails(
                    baseDaoDto.getTaskId(), TaskEventEnum.SALES.getCode());
            if (null != userTaskEventDetailsList && userTaskEventDetailsList.size() > 0) {
                setDaoViewChildDto(userTaskEventDetailsList, daoViewChildDtoList,loginUserId);
            }
        }
        return daoViewChildDtoList;
    }

    private void setDaoViewChildDto(List<UserTaskEventDetailsDto> userTaskEventDetailsList, List<DaoViewChildDto> daoViewChildDtoList,Long loginUserId) {
        for(UserTaskEventDetailsDto userTaskEventDetailsDto:userTaskEventDetailsList){
            DaoViewChildDto daoViewChildDto = new DaoViewChildDto();
            if(TaskEventEnum.GROUPON.getCode().equals(userTaskEventDetailsDto.getEventType())
                    || TaskEventEnum.DISTRIBUTOR.getCode().equals(userTaskEventDetailsDto.getEventType())){
                daoViewChildDto.setId(userTaskEventDetailsDto.getDaoNo());
            }
            daoViewChildDto.setId(String.valueOf(userTaskEventDetailsDto.getTaskEventId()));
            if(TaskEventEnum.HAGGLE.getCode().equals(userTaskEventDetailsDto.getEventType())){
                daoViewChildDto.setText(userNickNameHandle(userTaskEventDetailsDto.getUserNickName()) + "\n" + TaskEventEnum.toEnum(userTaskEventDetailsDto.getEventType()).getMsg() +",砍下"+userTaskEventDetailsDto.getEventAmount().toString() );
            }else{
                daoViewChildDto.setText(userNickNameHandle(userTaskEventDetailsDto.getUserNickName()) + "\n" + TaskEventEnum.toEnum(userTaskEventDetailsDto.getEventType()).getMsg());
            }
            Long userId = userTaskEventDetailsDto.getUserId();
            if(userId.intValue() == loginUserId.intValue()){
                daoViewChildDto.setStyleClass("highlight-node");
            }
            daoViewChildDtoList.add(daoViewChildDto);
        }
    }

    private void setDaoRulesInfo(UserDaoDetailsRes userDaoDetailsRes, BaseDaoDto baseDaoDto) {
        String taskId = baseDaoDto.getTaskId();
        if(TaskTypeEnum.GROUPON.getCode().equals(baseDaoDto.getTaskType())){
            TaskInfo taskInfo = taskInfoMapper.selectTaskByTaskId(taskId);
            TaskRules taskRules = taskRulesMapper.selectTaskRulesByType(taskInfo.getTaskParentId(), TaskRulesTypeEnum.GROUPON.getCode());

            GrouponRulesDto grouponRulesDto =
                    JSON.parseObject(taskRules.getTaskRulesJson(),GrouponRulesDto.class);
            userDaoDetailsRes.setGrouponRules(generateGrouponTaskRulesContent(grouponRulesDto));

        }else if(TaskTypeEnum.DISTRIBUTOR.getCode().equals(baseDaoDto.getTaskType())
                || TaskTypeEnum.SALES.getCode().equals(baseDaoDto.getTaskType())){
            List<TaskRules> taskRulesList = taskRulesMapper.selectTaskRulesByTaskId(taskId);

            for(TaskRules taskRules:taskRulesList){
                if(TaskRulesTypeEnum.DISTRIBUTOR.getCode().equals(taskRules.getRulesType())){
                    DistributionRulesDto distributionRulesDto =
                            JSON.parseObject(taskRules.getTaskRulesJson(),DistributionRulesDto.class);
                    userDaoDetailsRes.setDistributionRules(generateDistributionTaskRulesContent(distributionRulesDto));
                }else {
                    GrouponRulesDto grouponRulesDto =
                            JSON.parseObject(taskRules.getTaskRulesJson(),GrouponRulesDto.class);
                    userDaoDetailsRes.setGrouponRules(generateGrouponTaskRulesContent(grouponRulesDto));
                }
            }
        }
    }


    private DaoMember createDaoMember(Long userId,String daoNo,String ownerTag){
        DaoMember daoMember = new DaoMember();
        daoMember.setDaoNo(daoNo);
        daoMember.setUserId(userId);
        daoMember.setCreateTime(new Date());
        daoMember.setUpdateTime(new Date());
        daoMember.setDeleteStatus("0");
        daoMember.setOwnerTag(ownerTag);
       return daoMember;
    }

    private Long handleEndTime(Date now,Date sellEndTime) {
        if(null != sellEndTime) {
            if(now.compareTo(sellEndTime) < 0){
                return sellEndTime.getTime() - now.getTime();
            }
        }
        return 0l;
    }

    private String userNickNameHandle(String nickName){
        int length = nickName.length();
        if(nickName.length() <= 8){
            return nickName;
        }else {
            return nickName.substring(0,4) + "..."
                    + nickName.substring(nickName.length()-4,length);
        }
    }

//    private DaoViewRootDto getDaoTreeView(BaseDaoDto baseDaoDto,Long loginUserId){
//        DaoViewRootDto daoViewRootDto = new DaoViewRootDto();
//        String currentDaoNo = baseDaoDto.getDaoNo();
//        String currentDaoTaskId = baseDaoDto.getTaskId();
//        String currentParentDaoTaskId = baseDaoDto.getTaskId();
//        //所有数据
//        Map<String,DaoViewChildDto> daoOrganizeAllMap = new HashMap<>();
//        //数据关系
//        Map<String,Object> daoRelationMap = new HashMap<>();
//        //DAO树父DAO任务Id集合
//        List<String> parentDaoTasks = new ArrayList<>();
//
//        //假定当前DAO只是整个DAO树的其中一个子节点
//        daoOrganizeAllMap.put(baseDaoDto.getTaskId(),setDaoViewChildDto2(baseDaoDto,loginUserId,true));
//
//        //以当前DAO为起点向上寻找上级DAO信息和平级DAO信息
//        while (true){
//            TaskInfo subTaskInfo = taskInfoMapper.selectTaskByTaskId(currentDaoTaskId);
//            String taskParentId = subTaskInfo.getTaskParentId();
//
//            if (!StringUtils.isEmpty(taskParentId)) {
//                //查询平级DAO信息,并将查询到的平级DAO Task当成父DAO任务保存到List中,方便后续查找它的子DAO
//                setSameLevelDao(taskParentId, loginUserId, currentDaoNo, daoOrganizeAllMap, daoRelationMap,parentDaoTasks);
//                currentDaoTaskId = taskParentId;
//            } else {
//                //没有上级，说明他是整个DAO树的顶级节点
//                BaseDaoDto parentBaseDaoDto = daoOrganizeMapper.findDaoDetailsByTaskId(currentDaoTaskId);
//                //根节点数据找到了
//                daoViewRootDto.setNodes(setDateViewRootInfo(parentBaseDaoDto, loginUserId, parentBaseDaoDto.getDaoNo().equals(currentDaoNo) ? true : false));
//                daoViewRootDto.getNodes().get(0).setId(parentBaseDaoDto.getTaskId());
//                daoViewRootDto.setRootId(parentBaseDaoDto.getDaoNo());
//                //用户查看的DAO是最顶级的节点,将当前节点DAO task 保存,用于查询子DAO数据
//                if(subTaskInfo.getTaskId().equals(currentParentDaoTaskId)){
//                    parentDaoTasks.add(subTaskInfo.getTaskId());
//                }
//                break;
//            }
//        }
//    }

    private void setDaoViweInfo2(UserDaoDetailsRes userDaoDetailsRes,Long loginUserId){
        DaoViewRootDto daoViewRootDto = new DaoViewRootDto();
        List<DaoViewNodeDto> daoViewNodeDtoList = new ArrayList<>();
        BaseDaoDto baseDaoDto = userDaoDetailsRes.getUserDaoDto();
        String currentDaoNo = baseDaoDto.getDaoNo();
        //所有数据
        Map<String,DaoViewChildDto> daoOrganizeAllMap = new HashMap<>();
        //数据关系
        Map<String,Object> daoRelationMap = new HashMap<>();
        //当前任务ID
        String currentDaoTaskId = baseDaoDto.getTaskId();

        List<String> taskParentIds = new ArrayList<>();
        taskParentIds.add(baseDaoDto.getTaskId());

        int viewLayers = 1;
        String  rootNodeId = "";

        //获得DAO的层级关系和全部相关DAO数据
        while (true){
            //获取到当前Dao数据
            BaseDaoDto subBaseDaoDto = daoOrganizeMapper.findDaoDetailsByTaskId(currentDaoTaskId);
            if(baseDaoDto.getTaskId().equals(currentDaoTaskId)){
                daoOrganizeAllMap.put(subBaseDaoDto.getTaskId(),setDaoViewChildDto2(subBaseDaoDto,loginUserId,true));
            }else{
                daoOrganizeAllMap.put(subBaseDaoDto.getTaskId(),setDaoViewChildDto2(subBaseDaoDto,loginUserId,false));
            }
            //查询当前DAO数据,看看有没有上级的
            TaskInfo subTaskInfo = taskInfoMapper.selectTaskByTaskId(currentDaoTaskId);
            String taskParentId = subTaskInfo.getTaskParentId();

            //可能存在平级DAO
            if(!StringUtils.isEmpty(taskParentId)){
                //平级DAO数据查询
                setSameLevelDaoData(taskParentId,loginUserId,currentDaoNo,daoOrganizeAllMap,daoRelationMap);
            }else {
                BaseDaoDto parentBaseDaoDto = daoOrganizeMapper.findDaoDetailsByTaskId(currentDaoTaskId);
                //根节点数据找到了
                daoViewNodeDtoList = setDateViewRootInfo(parentBaseDaoDto,loginUserId, parentBaseDaoDto.getDaoNo().equals(currentDaoNo) ? true:false);
                daoViewNodeDtoList.get(0).setId(parentBaseDaoDto.getTaskId());
                rootNodeId = parentBaseDaoDto.getDaoNo();
                break;
            }
            currentDaoTaskId = taskParentId;
        }

        //处理当前用户所在DAO的所有子DAO数据
        while (true){
            List<String> taskParentIds2 = new ArrayList<>();
            if(taskParentIds.size() > 0){
                for(String taksParentId:taskParentIds){
                    setSameLevelDao(taksParentId,loginUserId,currentDaoNo,daoOrganizeAllMap,daoRelationMap,taskParentIds2);
                }
                taskParentIds = new ArrayList<>();
                taskParentIds.addAll(taskParentIds2);
            }else{
                break;
            }
        }

        //处理数据
        if(null != daoRelationMap && daoRelationMap.size() >0){
            for(String key:daoRelationMap.keySet()) {
                List<DaoViewChildDto> daoViewChildList = new ArrayList<>();
                Object taskObject = daoRelationMap.get(key);

                if (null != taskObject) {
                    if (taskObject instanceof List<?>) {
                        List<String> taskIds = (List<String>) taskObject;
                        for (String taskId : taskIds) {
                            DaoViewChildDto daoViewChildDto2 = daoOrganizeAllMap.get(taskId);
                            //如果是当前查看的DAO,把当前DAO的下级数据保存进去
                            if (daoViewChildDto2.getId().equals(baseDaoDto.getTaskId())) {
                                viewLayers++;
                                List<DaoViewChildDto> list = setCurrentDaoViweInfo(userDaoDetailsRes, loginUserId);
                                daoViewChildDto2.setChildren(list);
                            }
                            daoViewChildList.add(daoViewChildDto2);
                        }
                        viewLayers++;
                    } else {
                        DaoViewChildDto daoViewChildDto2 = daoOrganizeAllMap.get((String) taskObject);
                        if(null != daoViewChildDto2) {
                            //如果是当前查看的DAO,把当前DAO的下级数据保存进去
                            if (daoViewChildDto2.getId().equals(baseDaoDto.getTaskId())) {
                                viewLayers++;
                                List<DaoViewChildDto> list = setCurrentDaoViweInfo(userDaoDetailsRes, loginUserId);
                                daoViewChildDto2.setChildren(list);
                            }
                            daoViewChildList.add(daoViewChildDto2);
                            viewLayers++;
                        }
                    }
                }

                //如果key是根节点,将数据直接存到根节点数据中
                if (key.equals(daoViewNodeDtoList.get(0).getId())) {
                    daoViewNodeDtoList.get(0).setChildren(daoViewChildList);
                } else {
                    //如果不是根节点那么就是子节点,拿到父节点数据把子节点数据寸进去
                    DaoViewChildDto daoViewChildDto = daoOrganizeAllMap.get(key);
                    daoViewChildDto.setChildren(daoViewChildList);
                    daoOrganizeAllMap.put(key, daoViewChildDto);
                }
            }
        }
        userDaoDetailsRes.setViewLayers(viewLayers);
        daoViewNodeDtoList.get(0).setId(rootNodeId);
        daoViewRootDto.setRootId(rootNodeId);
        daoViewRootDto.setNodes(daoViewNodeDtoList);
        userDaoDetailsRes.setDaoViewRootDto(daoViewRootDto);
    }

    private void setSameLevelDao(String taskParentId, Long loginUserId,
                                 String currentDaoNo, Map<String, DaoViewChildDto> daoOrganizeAllMap,
                                 Map<String, Object> daoRelationMap, List<String> parentDaoTasks) {

        //查询与父级DAO任务ID平级的TaskID
        List<String> taskIdList = taskInfoMapper.findSubTaskIdList(taskParentId);
        if(null != taskIdList && taskIdList.size() > 0){
            //查询相关DAO数据
            List<BaseDaoDto> baseDaoDtoRes = daoOrganizeMapper.findDaoDetailsListByTaskId(taskIdList);
            //同一父级DAO任务下的所有子DAO任务滚西
            daoRelationMap.put(taskParentId,taskIdList);
            //保存数据
            for(BaseDaoDto userDao: baseDaoDtoRes){
                daoOrganizeAllMap.put(userDao.getTaskId(),setDaoViewChildDto2(userDao,loginUserId,userDao.getDaoNo().equals(currentDaoNo) ? true:false));
                parentDaoTasks.add(userDao.getTaskId());
            }
        }else {
            BaseDaoDto baseDaoDto = daoOrganizeMapper.findDaoDetailsByTaskId(taskParentId);
            daoOrganizeAllMap.put(baseDaoDto.getTaskId(),setDaoViewChildDto2(baseDaoDto,loginUserId,baseDaoDto.getDaoNo().equals(currentDaoNo) ? true:false));
        }

    }

    private String generateDistributionTaskRulesContent(DistributionRulesDto distributionRulesDto) {
        //TODO 中英文切换
//        String content = "1.成为经销商享受专属分销拿货价格{0}\n " +
//                "2.最低分销数量{1}\n " +
//                "3.经销商需要质押{2}保证金,完成分销任务即可赎回\n " +
//                "4.用户完成分销任务奖励分销金额{3}%奖励金\n " +
//                "5.分销任务有效期在领取任务开始{4}天后结束\n " +
//                "6.任务到期未完成分销任务将扣除质押保证金{5}% 作为惩罚";

        String content = "1. Become a distributor and enjoy the exclusive distribution price {0}\n" +
                "2. Minimum Distribution Quantity {1}\n" +
                "3. Dealers need to stake {2} security deposit, which can be redeemed after completing the distribution task.\n" +
                "4. User completes distribution task reward distribution amount {3}% bonus.\n" +
                "5. The distribution task valid period ends {4} days after the start of the task.\n" +
                "6. If the distribution task is not completed at the expiration of the task, the stake deposit {5}% will be deducted as a punishment.";

        return MessageFormat.format(content,distributionRulesDto.getDistributionPrice().toString(),
                distributionRulesDto.getDistributionMin(),distributionRulesDto.getPledgeAmount().toString(),
                distributionRulesDto.getDistributionRewardRatio().toString(),distributionRulesDto.getExpirationDay(),
                distributionRulesDto.getPenaltyRatio());
    }

    private String generateGrouponTaskRulesContent(GrouponRulesDto grouponRulesDto) {
        //TODO 中英文切换
//        String content = "1.参与团购享受拼团优惠购买价格:{0}\n " +
//                "2.参与团购砍一刀用户可获得砍价金额等额奖励（砍价金额范围{1}~{2})\n " +
//                "3.邀请用户参与拼团活动可享随机盲盒抽奖资格,总奖励金额{3} ,数量有限先到先得\n " +
//                "4.团购活动需满足参与人数: 最低{4}人最高{5}人\n " +
//                "5.团长购买商品最终享受优惠价格 = (拼团优惠价格-(砍一刀参与人数*累计砍价金额）\n " +
//                "6.团长在活动结束时满足活动最低参与人数要求即可获得该团购销售金额{6}%的奖励\n " +
//                "7.活动有效期在拼团活动开始{7}天后结束";

        String content = "1. Group buying and enjoy the group discount purchase price: {0}\n" +
                "2. Users participating in group buying can get the same amount of reward for bargain(bargain amount range {1}~{2})\n" +
                "3. Invite customer engagement group activities to enjoy random blind box lucky draw qualifications, total reward amount {3}, limited quantity first come first served.\n" +
                "4. Group buying activity needs to the number of participants: minimum {4} people maximum {5} people.\n" +
                "5. The head of the group buys the goods and finally enjoys the preferential price = (group preferential price - (the number of participants * the cumulative bargain amount)\n" +
                "6. The team leader who  has the minimum number of participants at the end of the campaign can get a reward of {6}% of the group buying sales amount.\n" +
                "7. The valid period of the campaign ends {7} days after the start of the group campaign.";
        return MessageFormat.format(content,grouponRulesDto.getPrice().toString(),
                grouponRulesDto.getHaggleMinAmount().toString(),grouponRulesDto.getHaggleMaxAmount().toString(),
                grouponRulesDto.getBlindBoxTotalRewardAmount().toString(),grouponRulesDto.getParticipantsMin(),
                grouponRulesDto.getParticipantsMax(),grouponRulesDto.getOwnerRewardRatio().toString(),grouponRulesDto.getExpirationDay());
    }
}
