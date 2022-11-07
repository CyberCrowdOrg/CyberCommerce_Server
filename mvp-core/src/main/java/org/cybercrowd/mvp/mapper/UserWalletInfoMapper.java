package org.cybercrowd.mvp.mapper;

import org.apache.ibatis.annotations.Param;
import org.cybercrowd.mvp.dto.response.UserWalletBalanceRes;
import org.cybercrowd.mvp.model.UserWalletInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserWalletInfoMapper继承基类
 */
@Repository
public interface UserWalletInfoMapper extends MyBatisBaseDao<UserWalletInfo, Long> {

    List<UserWalletBalanceRes> userWalletBalanceList(Long userId);

    UserWalletInfo selectUserWallet(@Param("userId") Long userId,@Param("coinId")  Long coinId);
}