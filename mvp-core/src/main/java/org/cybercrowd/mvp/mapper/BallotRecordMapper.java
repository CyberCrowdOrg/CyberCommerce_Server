package org.cybercrowd.mvp.mapper;

import org.cybercrowd.mvp.model.BallotRecord;
import org.springframework.stereotype.Repository;

/**
 * BallotRecordMapper继承基类
 */
@Repository
public interface BallotRecordMapper extends MyBatisBaseDao<BallotRecord, Long> {
}