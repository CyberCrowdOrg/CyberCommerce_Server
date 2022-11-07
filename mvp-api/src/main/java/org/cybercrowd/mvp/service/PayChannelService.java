package org.cybercrowd.mvp.service;

import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.PayChannelListReq;

public interface PayChannelService {

    BaseResponse payChannelList(PayChannelListReq payChannelListReq);
}
