package org.cybercrowd.mvp.dto.response;

import org.cybercrowd.mvp.dto.BaseOrderDto;

import java.io.Serializable;
import java.util.List;

public class OrderListRes implements Serializable {

    List<BaseOrderDto> baseOrderDtoList;

    private int pageNum;

    private int pageSize;

    private int totalPage;

    public List<BaseOrderDto> getBaseOrderDtoList() {
        return baseOrderDtoList;
    }

    public void setBaseOrderDtoList(List<BaseOrderDto> baseOrderDtoList) {
        this.baseOrderDtoList = baseOrderDtoList;
    }

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
}
