package org.cybercrowd.mvp.mapper;

import org.apache.ibatis.annotations.Param;
import org.cybercrowd.mvp.dto.BaseDaoDto;
import org.cybercrowd.mvp.model.DaoOrganize;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DaoOrganizeMapper继承基类
 */
@Repository
public interface DaoOrganizeMapper extends MyBatisBaseDao<DaoOrganize, Long> {

    DaoOrganize findDaoOrganizeByTaskId(@Param("taskId") String taskId);

    DaoOrganize selectDaoOrganize(@Param("daoNo") String daoNo);

    List<BaseDaoDto> findUserDaoList(@Param("userId") Long userId);

    BaseDaoDto findDaoDetails(@Param("daoNo") String daoNo);

    BaseDaoDto findDaoDetailsByTaskId(@Param("taskId") String taskId);

    List<BaseDaoDto> findDaoDetailsListByTaskId(List<String> taskIdList);

    List<BaseDaoDto> selectAll(@Param("daoNo") String daoNo);
}