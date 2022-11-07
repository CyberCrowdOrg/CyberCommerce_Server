package org.cybercrowd.mvp.dto.response;

import org.cybercrowd.mvp.dto.BaseDaoDto;

import java.io.Serializable;
import java.util.List;

public class UserDaoListRes implements Serializable {

    private int pageNum = 1;

    private int pageSize = 10;

    private int totalPage;

    private List<BaseDaoDto> baseDaoDtoList;

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

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<BaseDaoDto> getUserDaoDtoList() {
        return baseDaoDtoList;
    }

    public void setUserDaoDtoList(List<BaseDaoDto> baseDaoDtoList) {
        this.baseDaoDtoList = baseDaoDtoList;
    }
}
