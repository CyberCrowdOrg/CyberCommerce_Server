package org.cybercrowd.mvp.mapper;

import org.cybercrowd.mvp.model.PayOrder;
import org.springframework.stereotype.Repository;

/**
 * PayOrderMapper继承基类
 */
@Repository
public interface PayOrderMapper extends MyBatisBaseDao<PayOrder, Long> {


}