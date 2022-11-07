package org.cybercrowd.mvp.mapper;

import org.apache.ibatis.annotations.Param;
import org.cybercrowd.mvp.dto.BaseOptionDto;
import org.cybercrowd.mvp.model.ProposalOptions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ProposalOptionsMapper继承基类
 */
@Repository
public interface ProposalOptionsMapper extends MyBatisBaseDao<ProposalOptions, Long> {

    List<BaseOptionDto> selectOptionList(@Param("proposalNo") String proposalNo);

    Integer batchInsertOptions(List<ProposalOptions> list);

}