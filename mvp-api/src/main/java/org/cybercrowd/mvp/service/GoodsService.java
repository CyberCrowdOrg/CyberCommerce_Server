package org.cybercrowd.mvp.service;

import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.*;


public interface GoodsService {

    BaseResponse createGoods(GoodsCreateReq goodsCreateReq);

    BaseResponse publishGoods(PublishGoodsReqDto publishGoodsReqDto);

    BaseResponse goodsList(GoodsListReq goodsListReq);

    BaseResponse goodsDetails(GoodsDetailsReq goodsDetailsReq);

    BaseResponse createDistributionGoods(DistributionGoodsCreateReq distributionGoodsCreateReq);

    BaseResponse createGrouponGoods(GrouponGoodsCreateReq grouponGoodsCreateReq);

    /**
     * 查询用户商品店铺
     * @param userGoodsStoreReq
     * @return
     */
    BaseResponse userGoodsStore(UserGoodsStoreReq userGoodsStoreReq);


    /**
     * 查询用户拼团数据
     * 我发起或者参与的拼团
     * @param userGrouponReq
     * @return
     */
    BaseResponse userGroupon(UserGrouponReq userGrouponReq);

    /**
     * 查询用户分销数据
     * @param userDistributionReq
     * @return
     */
    BaseResponse userDistribution(UserDistributionReq userDistributionReq);
}
