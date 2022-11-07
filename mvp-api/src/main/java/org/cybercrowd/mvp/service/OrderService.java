package org.cybercrowd.mvp.service;

import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.*;

public interface OrderService {
    /**
     * 拼团订单
     * @param grouponOrderReq
     * @return
     */
    BaseResponse grouponOrder(GrouponOrderReq grouponOrderReq);

    /**
     * 单独订单
     * @param separateOrderReq
     * @return
     */
    BaseResponse separateOrder(SeparateOrderReq separateOrderReq);

    /**
     * 充值订单
     * @param rechargeOrderReq
     * @return
     */
    BaseResponse rechargeOrder(RechargeOrderReq rechargeOrderReq);

    /**
     * 砍价奖励订单
     * @param haggleRewardOrderReq
     * @return
     */
    BaseResponse haggleRewardOrder(HaggleRewardOrderReq haggleRewardOrderReq);

    /**
     * 订单列表查询
     * @param orderListReq
     * @return
     */
    BaseResponse orderList(OrderListReq orderListReq);

    /**
     * 创建订单，拼团购买、砍一刀、质押
     * @param orderCreateReq
     * @return
     */
    BaseResponse createOrder(OrderCreateReq orderCreateReq);
}
