package org.cybercrowd.mvp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.cybercrowd.mvp.dto.BasePayChannelDto;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.PayChannelListReq;
import org.cybercrowd.mvp.dto.response.PayChannelListRes;
import org.cybercrowd.mvp.mapper.PayChannelMapper;
import org.cybercrowd.mvp.model.PayChannel;
import org.cybercrowd.mvp.service.PayChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("payChannelService")
public class PayChannelServiceImpl implements PayChannelService {

    private Logger logger = LoggerFactory.getLogger(PayChannelServiceImpl.class);

    @Autowired
    PayChannelMapper payChannelMapper;

    @Override
    public BaseResponse payChannelList(PayChannelListReq payChannelListReq) {
        BaseResponse baseResponse = new BaseResponse();
        PayChannelListRes payChannelListRes = new PayChannelListRes();
        int pageNum = payChannelListReq.getPageNum();
        int pageSize = payChannelListReq.getPageSize();

        PageHelper.startPage(pageNum,pageSize);
        List<PayChannel> availableChannelList = payChannelMapper.findAvailableChannelList();

        if(null != availableChannelList && availableChannelList.size() >0){
            PageInfo pageInfo = new PageInfo(availableChannelList);
            payChannelListRes.setTotalPage(pageInfo.getPages());
            payChannelListRes.setPageNum(pageNum);
            payChannelListRes.setPageSize(pageSize);
            List<BasePayChannelDto> payChannelDtoList = new ArrayList<>();
            for(PayChannel payChannel:availableChannelList){
                BasePayChannelDto basePayChannelDto = new BasePayChannelDto();
                basePayChannelDto.setChannelId(payChannel.getChannelId());
                basePayChannelDto.setChannelName(payChannel.getChannelName());
                basePayChannelDto.setChannelLogo(payChannel.getChannelLogo());
                basePayChannelDto.setSupportCoinType(payChannel.getSupportCoinType());
                basePayChannelDto.setCoinIcon(payChannel.getChannelCoinIcon());
                basePayChannelDto.setCoinName(payChannel.getChannelCoinName());
                payChannelDtoList.add(basePayChannelDto);
            }
            payChannelListRes.setPayChannelDtoList(payChannelDtoList);
            baseResponse.setData(payChannelListRes);
        }
        return baseResponse;
    }
}
