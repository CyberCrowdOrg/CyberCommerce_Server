package org.cybercrowd.mvp.mapper;

import org.apache.ibatis.annotations.Param;
import org.cybercrowd.mvp.dto.BaseUserTaskDto;
import org.cybercrowd.mvp.dto.ProgressGroupDto;
import org.cybercrowd.mvp.dto.UserDistributionDto;
import org.cybercrowd.mvp.dto.UserGrouponDto;
import org.cybercrowd.mvp.model.TaskInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TaskInfoMapper继承基类
 */
@Repository
public interface TaskInfoMapper extends MyBatisBaseDao<TaskInfo, Long> {

    TaskInfo selectTaskByTaskId(String taskId);

    TaskInfo selectTaskByTaskIdAndStatus(@Param("taskId") String taskId, @Param("taskStatus") String taskStatus);

    /**
     * 查询进行中的团
     * @return
     */
    List<ProgressGroupDto> selectProgressGroupList(@Param("taskParentId") String taskParentId);

    /**
     * 根据任务状态 查询用户任务
     * @param userId
     * @param taskStatus
     * @return
     */
    List<TaskInfo> selectUserTaskByStatus(@Param("userId") Long userId,@Param("taskStatus") String taskStatus);

    List<TaskInfo> selectList(TaskInfo taskInfo);

    List<TaskInfo> selectListByUserId(@Param("userId") Long userId);

    List<UserGrouponDto> selectUserGroupList(@Param("userId") Long userId);

    List<UserDistributionDto> selectUserDistributionList(@Param("userId") Long userId);

    /**
     * 更新任务分销次数
     * @param taskId
     * @return
     */
    Integer updateTaskDistributionQuantity(@Param("taskId") String taskId);

    /**
     * 查询子任务IDList
     * @param taskParentId
     * @return
     */
    List<String> findSubTaskIdList(@Param("taskParentId") String taskParentId);


    List<BaseUserTaskDto> findBaseUserTaskList(@Param("userId") Long userId);
}