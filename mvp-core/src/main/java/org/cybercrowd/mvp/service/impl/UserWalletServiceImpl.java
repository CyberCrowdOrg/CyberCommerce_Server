package org.cybercrowd.mvp.service.impl;

import com.alibaba.fastjson2.JSON;
import org.cybercrowd.mvp.constant.RedisKeyConstant;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.UserWalletService;
import org.cybercrowd.mvp.enums.OrderTypeEnum;
import org.cybercrowd.mvp.enums.ReturnCodeEnum;
import org.cybercrowd.mvp.mapper.UserWalletInfoMapper;
import org.cybercrowd.mvp.model.UserWalletInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service("userWalletService")
public class UserWalletServiceImpl implements UserWalletService {

    private Logger logger = LoggerFactory.getLogger(UserWalletServiceImpl.class);

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    UserWalletInfoMapper userWalletInfoMapper;


    @Override
    public BaseResponse userWalletBalanceUpdate(Long userId, Long coinId, String orderNo,
                                                String orderType, BigDecimal amount) {
        BaseResponse baseResponse = new BaseResponse();
        logger.info("用户钱包余额更新业务,用户ID:{} 币种ID:{} 订单号:{} 订单类型:{} 金额:{}",userId,coinId,orderNo,orderType,amount);
        //查询钱包账户
        UserWalletInfo userWalletInfo = userWalletInfoMapper.selectUserWallet(userId,coinId);
        if(null != userWalletInfo) {
            try {
                BigDecimal availableAmt = userWalletInfo.getAvailableAmt();
                BigDecimal totalOutAmt = userWalletInfo.getTotalOutAmt();
                BigDecimal totalInAmt = userWalletInfo.getTotalInAmt();
                BigDecimal totalRechargeAmt = userWalletInfo.getTotalRechargeAmt();

                if (OrderTypeEnum.PAYMENT.getCode().equals(orderType)
                        || OrderTypeEnum.WITHDRAW.getCode().equals(orderType)) {
                    if (availableAmt.compareTo(amount) < 0) {
                        logger.info("用户钱包余额更新业务,用户ID:{} 币种ID:{} 用户钱包余额不足",userId,coinId);
                        baseResponse.setReturnCodeEnum(ReturnCodeEnum.USER_WALLET_INSUFFICIENT_BALANCE_ERROR);
                        return baseResponse;
                    }
                    logger.info("用户钱包余额更新业务,用户ID:{} 币种ID:{} 业务金额:{} 操作前可用余额:{} 累计支出金额:{}", userId, coinId, amount, availableAmt, totalOutAmt);
                    BigDecimal newAvailableAmt = availableAmt.subtract(amount);
                    BigDecimal newTotalOutAmt = totalOutAmt.add(amount);
                    userWalletInfo.setAvailableAmt(newAvailableAmt);
                    userWalletInfo.setTotalOutAmt(newTotalOutAmt);
                    userWalletInfo.setUpdateTime(new Date());
                    userWalletInfoMapper.updateByPrimaryKeySelective(userWalletInfo);
                    logger.info("用户钱包余额更新业务,用户ID:{} 币种ID:{} 业务金额:{} " +
                            "操作后可用余额:{} 累计支出金额:{}", userId, coinId, amount, newAvailableAmt, newTotalOutAmt);
                } else if (OrderTypeEnum.RECHARGE.getCode().equals(orderType)
                        || OrderTypeEnum.REWARD.getCode().equals(orderType)
                        || OrderTypeEnum.REFUND.getCode().equals(orderType)) {
                    logger.info("用户钱包余额更新业务,用户ID:{} 币种ID:{} 业务金额:{}" +
                                    " 操作前可用余额:{} 累计支出金额:{} 累计收入金额:{} 累计充值金额:{}",
                            userId, coinId, amount, availableAmt, totalOutAmt, totalInAmt, totalRechargeAmt);
                    BigDecimal newAvailableAmt = availableAmt.add(amount);
                    BigDecimal newTotalInAmt = totalInAmt;
                    BigDecimal newTotalRechargeAmt = totalRechargeAmt;
                    BigDecimal newTotalOutAmt = totalOutAmt;
                    if (OrderTypeEnum.REFUND.getCode().equals(orderType)) {
                        newTotalOutAmt = totalOutAmt.subtract(amount);
                        userWalletInfo.setAvailableAmt(newAvailableAmt);
                        userWalletInfo.setTotalOutAmt(newTotalOutAmt);
                    } else {
                        newTotalInAmt = totalInAmt.add(amount);
                        newTotalRechargeAmt = totalRechargeAmt.add(amount);
                        userWalletInfo.setAvailableAmt(newAvailableAmt);
                        userWalletInfo.setTotalInAmt(newTotalInAmt);
                        userWalletInfo.setTotalRechargeAmt(newTotalRechargeAmt);
                    }
                    userWalletInfo.setUpdateTime(new Date());
                    userWalletInfoMapper.updateByPrimaryKeySelective(userWalletInfo);
                    logger.info("用户钱包余额更新业务,用户ID:{} 币种ID:{} 业务金额:{}" +
                                    " 操作后可用余额:{} 累计支出金额:{} 累计收入金额:{} 累计充值金额:{}",
                            userId, coinId, amount, newAvailableAmt, newTotalOutAmt, newTotalInAmt, newTotalRechargeAmt);
                }
            }catch (Exception e){
                logger.error("用户钱包余额更新业务,执行异常:{}",e.getMessage(),e);
                baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
            }
        }
        return baseResponse;
    }
}
