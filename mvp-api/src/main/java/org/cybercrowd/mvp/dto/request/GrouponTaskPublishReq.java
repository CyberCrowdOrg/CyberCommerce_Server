package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;

public class GrouponTaskPublishReq implements Serializable {

    private String taskParentId;
    private boolean enableNft;
    private Long userId;

    private Object nftFileMap;

    public String getTaskParentId() {
        return taskParentId;
    }

    public void setTaskParentId(String taskParentId) {
        this.taskParentId = taskParentId;
    }

    public boolean isEnableNft() {
        return enableNft;
    }

    public void setEnableNft(boolean enableNft) {
        this.enableNft = enableNft;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Object getNftFileMap() {
        return nftFileMap;
    }

    public void setNftFileMap(Object nftFileMap) {
        this.nftFileMap = nftFileMap;
    }
}
