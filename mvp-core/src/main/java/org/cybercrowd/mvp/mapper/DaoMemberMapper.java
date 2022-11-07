package org.cybercrowd.mvp.mapper;

import org.apache.ibatis.annotations.Param;
import org.cybercrowd.mvp.model.DaoMember;
import org.springframework.stereotype.Repository;

/**
 * DaoMemberMapper继承基类
 */
@Repository
public interface DaoMemberMapper extends MyBatisBaseDao<DaoMember, Long> {

    DaoMember findMember(@Param("userId") Long userId,@Param("daoNo") String daoNo);

    Integer countDaoMember(@Param("daoNo") String daoNo);
}