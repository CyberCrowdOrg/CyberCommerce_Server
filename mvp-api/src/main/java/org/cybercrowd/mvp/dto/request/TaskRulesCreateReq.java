package org.cybercrowd.mvp.dto.request;


import java.io.Serializable;

public class TaskRulesCreateReq implements Serializable {

    private Long userId;

    private String taskId;

    private GrouponRulesDto grouponRulesDto;

    private DistributionRulesDto distributionRulesDto;

    private String taskRulesType;

    public GrouponRulesDto getGrouponRulesDto() {
        return grouponRulesDto;
    }

    public void setGrouponRulesDto(GrouponRulesDto grouponRulesDto) {
        this.grouponRulesDto = grouponRulesDto;
    }

    public DistributionRulesDto getDistributionRulesDto() {
        return distributionRulesDto;
    }

    public void setDistributionRulesDto(DistributionRulesDto distributionRulesDto) {
        this.distributionRulesDto = distributionRulesDto;
    }

    public String getTaskRulesType() {
        return taskRulesType;
    }

    public void setTaskRulesType(String taskRulesType) {
        this.taskRulesType = taskRulesType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
