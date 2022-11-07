package org.cybercrowd.mvp.mapper;

import org.cybercrowd.mvp.model.PayChannel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PayChannelMapper继承基类
 */
@Repository
public interface PayChannelMapper extends MyBatisBaseDao<PayChannel, Long> {

   PayChannel findPayChannel(String channelId);

   List<PayChannel> findAvailableChannelList();

}