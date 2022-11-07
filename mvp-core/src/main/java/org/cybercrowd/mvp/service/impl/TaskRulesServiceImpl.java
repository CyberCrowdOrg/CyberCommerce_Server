package org.cybercrowd.mvp.service.impl;

import com.alibaba.fastjson2.JSON;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.DistributionRulesCreateReq;
import org.cybercrowd.mvp.dto.request.DistributionRulesDto;
import org.cybercrowd.mvp.dto.request.GrouponRulesDto;
import org.cybercrowd.mvp.dto.request.TaskRulesCreateReq;
import org.cybercrowd.mvp.enums.ReturnCodeEnum;
import org.cybercrowd.mvp.enums.TaskRulesTypeEnum;
import org.cybercrowd.mvp.enums.TaskStatusEnum;
import org.cybercrowd.mvp.mapper.TaskInfoMapper;
import org.cybercrowd.mvp.mapper.TaskRulesMapper;
import org.cybercrowd.mvp.model.TaskInfo;
import org.cybercrowd.mvp.model.TaskRules;
import org.cybercrowd.mvp.service.TaskRulesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;

@Service("taskRulesService")
public class TaskRulesServiceImpl implements TaskRulesService {

    private Logger logger = LoggerFactory.getLogger(TaskRulesServiceImpl.class);

    @Autowired
    TaskRulesMapper taskRulesMapper;
    @Autowired
    TaskInfoMapper taskInfoMapper;

    @Override
    public BaseResponse createTaskRules(TaskRulesCreateReq taskRulesCreateReq) {
        BaseResponse baseResponse = new BaseResponse();
        logger.info("创建任务规则业务,请求入参:{}",JSON.toJSONString(taskRulesCreateReq));
        GrouponRulesDto grouponRulesDto = taskRulesCreateReq.getGrouponRulesDto();
        DistributionRulesDto distributionRulesDto = taskRulesCreateReq.getDistributionRulesDto();

        String taskRulesType = taskRulesCreateReq.getTaskRulesType();
        String taskId = taskRulesCreateReq.getTaskId();
//
//        TaskInfo taskInfo = taskInfoMapper.selectTaskByTaskIdAndStatus(taskId, TaskStatusEnum.NOT_STARTED.getCode());
//        if(null == taskInfo){
//            logger.info("创建任务规则业务, task id:{} 待开始的任务未找到,创建规则：{}失败",taskId,taskRulesType);
//            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
//            return baseResponse;
//        }

        TaskRules taskRules = taskRulesMapper.selectTaskRulesByType(taskId,taskRulesType);
        if(null != taskRules){
            logger.info("创建任务规则业务, task id:{} task rules type:{} 规则已存在",taskId,taskRulesType);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            return baseResponse;
        }

        taskRules = new TaskRules();
        taskRules.setTaskId(taskId);
        taskRules.setCreateTime(new Date());
        taskRules.setUpdateTime(new Date());
        if(TaskRulesTypeEnum.GROUPON.getCode().equals(taskRulesType)){
            taskRules.setTaskRulesJson(JSON.toJSONString(grouponRulesDto));
            taskRules.setTaskRulesContent(generateGrouponTaskRulesContent(grouponRulesDto));
        }else if(TaskRulesTypeEnum.DISTRIBUTOR.getCode().equals(taskRulesType)) {
            taskRules.setTaskRulesJson(JSON.toJSONString(distributionRulesDto));
            taskRules.setTaskRulesContent(generateDistributionTaskRulesContent(distributionRulesDto));
        }
        taskRules.setRulesType(taskRulesType);
        taskRulesMapper.insertSelective(taskRules);
//        taskInfo.setEnableDistributor("1");
//        taskInfo.setUpdateTime(new Date());
//        taskInfoMapper.updateByPrimaryKeySelective(taskInfo);
        baseResponse.setData(taskRules.getTaskRulesContent());
        logger.info("创建任务规则业务,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    private String generateDistributionTaskRulesContent(DistributionRulesDto distributionRulesDto) {
        String content = "1.成为经销商享受专属分销拿货价格{0}\n " +
                "2.最低分销数量{1}\n " +
                "3.经销商需要质押{2}保证金,完成分销任务即可赎回\n " +
                "4.用户完成分销任务奖励分销金额{3}%奖励金\n " +
                "5.分销任务有效期在领取任务开始{4}天后结束\n " +
                "6.任务到期未完成分销任务将扣除质押保证金{5}% 作为惩罚";

//        String content = "1. Become a distributor and enjoy the exclusive distribution price {0}\n" +
//                "2. Minimum Distribution Quantity {1}\n" +
//                "3. Dealers need to stake {2} security deposit, which can be redeemed after completing the distribution task.\n" +
//                "4. User completes distribution task reward distribution amount {3}% bonus.\n" +
//                "5. The distribution task valid period ends {4} days after the start of the task.\n" +
//                "6. If the distribution task is not completed at the expiration of the task, the stake deposit {5}% will be deducted as a punishment.";

        return MessageFormat.format(content,distributionRulesDto.getDistributionPrice().toString(),
                distributionRulesDto.getDistributionMin(),distributionRulesDto.getPledgeAmount().toString(),
                distributionRulesDto.getDistributionRewardRatio().toString(),distributionRulesDto.getExpirationDay(),
                distributionRulesDto.getPenaltyRatio());
    }

    private String generateGrouponTaskRulesContent(GrouponRulesDto grouponRulesDto) {
        String content = "1.参与团购享受拼团优惠购买价格:{0}\n " +
                "2.参与团购砍一刀用户可获得砍价金额等额奖励（砍价金额范围{1}~{2})\n " +
                "3.邀请用户参与拼团活动可享随机盲盒抽奖资格,总奖励金额{3} ,数量有限先到先得\n " +
                "4.团购活动需满足参与人数: 最低{4}人最高{5}人\n " +
                "5.团长购买商品最终享受优惠价格 = (拼团优惠价格-(砍一刀参与人数*累计砍价金额）\n " +
                "6.团长在活动结束时满足活动最低参与人数要求即可获得该团购销售金额{6}%的奖励\n " +
                "7.活动有效期在拼团活动开始{7}天后结束";

//        String content = "1. Group buying and enjoy the group discount purchase price: {0}\n" +
//                "2. Users participating in group buying can get the same amount of reward for bargain(bargain amount range {1}~{2})\n" +
//                "3. Invite customer engagement group activities to enjoy random blind box lucky draw qualifications, total reward amount {3}, limited quantity first come first served.\n" +
//                "4. Group buying activity needs to the number of participants: minimum {4} people maximum {5} people.\n" +
//                "5. The head of the group buys the goods and finally enjoys the preferential price = (group preferential price - (the number of participants * the cumulative bargain amount)\n" +
//                "6. The team leader who  has the minimum number of participants at the end of the campaign can get a reward of {6}% of the group buying sales amount.\n" +
//                "7. The valid period of the campaign ends {7} days after the start of the group campaign.";
        return MessageFormat.format(content,grouponRulesDto.getPrice().toString(),
                grouponRulesDto.getHaggleMinAmount().toString(),grouponRulesDto.getHaggleMaxAmount().toString(),
                grouponRulesDto.getBlindBoxTotalRewardAmount().toString(),grouponRulesDto.getParticipantsMin(),
                grouponRulesDto.getParticipantsMax(),grouponRulesDto.getOwnerRewardRatio().toString(),grouponRulesDto.getExpirationDay());
    }


}
