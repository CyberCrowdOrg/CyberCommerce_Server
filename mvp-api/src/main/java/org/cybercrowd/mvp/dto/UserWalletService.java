package org.cybercrowd.mvp.dto;

import java.math.BigDecimal;

public interface UserWalletService {

    /**
     * 用户钱包余额更新
     * @param userId
     * @param coinId
     * @param orderNo
     * @param orderType
     * @param amount
     * @return
     */
    BaseResponse userWalletBalanceUpdate(Long userId, Long coinId,
                                         String orderNo,String orderType, BigDecimal amount);
}
