package org.cybercrowd.mvp.service;

import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.*;

public interface TaskService {

    BaseResponse publishTask(TaskPublishReq taskPublishReq);
    //创建拼团
    BaseResponse createGroupon(GrouponCreateReq grouponCreateReq);

    BaseResponse userTaskList(UserTaskListReq userTaskListReq);

    /**
     * 发布商品销售任务
     * @param saleTaskPublishReq
     * @return
     */
    BaseResponse publishSaleTask(SaleTaskPublishReq saleTaskPublishReq);
    /**
     * 发布拼团任务
     * @param grouponTaskPublishReq
     * @return
     */
    BaseResponse publishGrouponTask(GrouponTaskPublishReq grouponTaskPublishReq);

    /**
     * 发布分销任务
     * @param distributorTaskPublishReq
     * @return
     */
    BaseResponse publishDistributorTask(DistributorTaskPublishReq distributorTaskPublishReq);



}
