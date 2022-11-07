package org.cybercrowd.mvp.mapper;

import org.apache.ibatis.annotations.Param;
import org.cybercrowd.mvp.model.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * UserInfoMapper继承基类
 */
@Repository
public interface UserInfoMapper extends MyBatisBaseDao<UserInfo, Long> {

    UserInfo queryUserByLoginAccount(String loginAccount);

    UserInfo queryUserByUserId(Long userId);

    UserInfo queryUserByWalletAddr(String walletAddr);

    String findUserNickName(@Param("userId") Long userId);
}