package org.cybercrowd.mvp.mapper;

import org.apache.ibatis.annotations.Param;
import org.cybercrowd.mvp.dto.BaseOrderDto;
import org.cybercrowd.mvp.model.OrderInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * OrderInfoMapper继承基类
 */
@Repository
public interface OrderInfoMapper extends MyBatisBaseDao<OrderInfo, Long> {

    List<BaseOrderDto> selectOrderList(@Param("userId") Long userId, @Param("orderNo") String orderNo,
                                       @Param("orderType") String orderType, @Param("coinId") Long coinId);

    OrderInfo selectOrderByOrderNo(@Param("orderNo") String orderNo);

    OrderInfo selectOrder(OrderInfo orderInfo);
}