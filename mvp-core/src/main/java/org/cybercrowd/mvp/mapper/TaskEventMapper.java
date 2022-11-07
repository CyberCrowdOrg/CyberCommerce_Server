package org.cybercrowd.mvp.mapper;

import org.apache.ibatis.annotations.Param;
import org.cybercrowd.mvp.dto.UserTaskEventDetailsDto;
import org.cybercrowd.mvp.model.TaskEvent;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TaskEventMapper继承基类
 */
@Repository
public interface TaskEventMapper extends MyBatisBaseDao<TaskEvent, Long> {

    TaskEvent selectTaskEvent(@Param("taskId") String taskId,@Param("taskEventType") String taskEventType);

    List<TaskEvent> selectList(@Param("taskId") String taskId);

    Integer countGrouponPeopleNumber(String taskId);

    TaskEvent findUserTaskEvent(@Param("userId") Long userId,@Param("taskId") String taskId,@Param("taskEventType") String taskEventType);

    List<UserTaskEventDetailsDto> findUserTaskEventDetails(@Param("taskId") String taskId,@Param("taskEventType") String taskEventType);
}