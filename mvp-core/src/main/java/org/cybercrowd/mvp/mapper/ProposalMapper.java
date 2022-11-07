package org.cybercrowd.mvp.mapper;

import org.apache.ibatis.annotations.Param;
import org.cybercrowd.mvp.dto.DaoProposalDto;
import org.cybercrowd.mvp.model.Proposal;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ProposalMapper继承基类
 */
@Repository
public interface ProposalMapper extends MyBatisBaseDao<Proposal, Long> {

    List<DaoProposalDto> selectDaoProposalList(@Param("daoNo") String daoNo);
}