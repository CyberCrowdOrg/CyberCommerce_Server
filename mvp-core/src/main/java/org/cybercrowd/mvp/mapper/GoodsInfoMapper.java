package org.cybercrowd.mvp.mapper;

import org.apache.ibatis.annotations.Param;
import org.cybercrowd.mvp.dto.BaseGoodsDto;
import org.cybercrowd.mvp.model.GoodsInfo;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * GoodsInfoMapper继承基类
 */
@Repository
public interface GoodsInfoMapper extends MyBatisBaseDao<GoodsInfo, Long> {

    List<BaseGoodsDto> selectGoodsList(@Param("taskStatus") String taskStatus);

    void updateGoodsPublishTime(@Param("goodsId") Long goodsId,@Param("publishTime") Date publishTime);
}