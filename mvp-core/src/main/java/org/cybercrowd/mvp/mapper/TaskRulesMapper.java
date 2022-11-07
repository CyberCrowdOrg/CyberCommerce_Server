package org.cybercrowd.mvp.mapper;

import org.apache.ibatis.annotations.Param;
import org.cybercrowd.mvp.model.TaskRules;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TaskRulesMapper继承基类
 */
@Repository
public interface TaskRulesMapper extends MyBatisBaseDao<TaskRules, Long> {

    List<TaskRules> selectTaskRulesByTaskId(@Param("taskId") String taskId);

    TaskRules selectTaskRulesByType(@Param("taskId") String taskId,@Param("rulesType") String rulesType);
}