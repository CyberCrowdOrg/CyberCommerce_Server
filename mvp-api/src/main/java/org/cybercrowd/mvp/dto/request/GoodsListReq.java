package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;

public class GoodsListReq implements Serializable {

    private Long userId;

    private int pageNum = 1;

    private int pageSize = 4;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
