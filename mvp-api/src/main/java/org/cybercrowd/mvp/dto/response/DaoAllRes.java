package org.cybercrowd.mvp.dto.response;

import org.cybercrowd.mvp.dto.BaseDaoDto;

import java.io.Serializable;
import java.util.List;

public class DaoAllRes implements Serializable {

    private int pageNum;
    private int pageSize;
    private int totalPage;
    private List<BaseDaoDto> baseDaoDtoList;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<BaseDaoDto> getBaseDaoDtoList() {
        return baseDaoDtoList;
    }

    public void setBaseDaoDtoList(List<BaseDaoDto> baseDaoDtoList) {
        this.baseDaoDtoList = baseDaoDtoList;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
