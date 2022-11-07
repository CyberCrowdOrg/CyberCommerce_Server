package org.cybercrowd.mvp.mapper;

import org.cybercrowd.mvp.model.BaseCoin;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BaseCoinMapper继承基类
 */
@Repository
public interface BaseCoinMapper extends MyBatisBaseDao<BaseCoin, Long> {

    List<BaseCoin> selectDefaultWalletCoinList();

    List<BaseCoin> selectDefaultRechargeCoinList();

    BaseCoin selectBaseCoinByCoinId(Long coinId);
}