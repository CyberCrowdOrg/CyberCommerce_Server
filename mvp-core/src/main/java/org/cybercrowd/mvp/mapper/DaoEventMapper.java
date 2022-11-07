package org.cybercrowd.mvp.mapper;

import org.cybercrowd.mvp.model.DaoEvent;
import org.springframework.stereotype.Repository;

/**
 * DaoEventMapper继承基类
 */
@Repository
public interface DaoEventMapper extends MyBatisBaseDao<DaoEvent, Long> {
}