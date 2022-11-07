package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;

public class UploadNftFileReq implements Serializable {

    private Long userId;

    private String taskId;

    private Object fileMap;

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

    public Object getFileMap() {
        return fileMap;
    }

    public void setFileMap(Object fileMap) {
        this.fileMap = fileMap;
    }
}
