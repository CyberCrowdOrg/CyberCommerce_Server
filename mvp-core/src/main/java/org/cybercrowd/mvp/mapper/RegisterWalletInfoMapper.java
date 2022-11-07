package org.cybercrowd.mvp.mapper;

import org.cybercrowd.mvp.model.RegisterWalletInfo;
import org.springframework.stereotype.Repository;

/**
 * RegisterWalletInfoMapper继承基类
 */
@Repository
public interface RegisterWalletInfoMapper extends MyBatisBaseDao<RegisterWalletInfo, Long> {
}