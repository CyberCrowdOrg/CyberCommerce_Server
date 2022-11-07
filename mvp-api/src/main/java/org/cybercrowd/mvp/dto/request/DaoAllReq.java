package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;

public class DaoAllReq implements Serializable {

    private int pageNum = 1;

    private int pageSize = 20;

    private String daoNo;

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

    public String getDaoNo() {
        return daoNo;
    }

    public void setDaoNo(String daoNo) {
        this.daoNo = daoNo;
    }
}
