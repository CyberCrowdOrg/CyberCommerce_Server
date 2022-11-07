package org.cybercrowd.mvp.service;

import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.PayOrderCreateReq;
import org.cybercrowd.mvp.dto.request.PayOrderUpdateReq;

public interface PayOrderService {

    BaseResponse createPayOrder(PayOrderCreateReq payOrderCreateReq);

    BaseResponse updatePayOrder(PayOrderUpdateReq payOrderUpdateReq);

}
