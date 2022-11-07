package org.cybercrowd.mvp.mapper;

import org.apache.ibatis.annotations.Param;
import org.cybercrowd.mvp.dto.BaseDaoNftAssetsDto;
import org.cybercrowd.mvp.model.DaoNftAssets;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DaoNftAssetsMapper继承基类
 */
@Repository
public interface DaoNftAssetsMapper extends MyBatisBaseDao<DaoNftAssets, Long> {

    List<BaseDaoNftAssetsDto> findBaseDaoNftAssets(@Param("daoNo") String daoNo,@Param("taskId")  String taskId);
}