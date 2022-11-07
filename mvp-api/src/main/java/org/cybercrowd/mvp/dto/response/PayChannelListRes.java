package org.cybercrowd.mvp.dto.response;

import org.cybercrowd.mvp.dto.BasePayChannelDto;

import java.io.Serializable;
import java.util.List;

public class PayChannelListRes implements Serializable {

    private int pageNum;
    private int pageSize;
    private int totalPage;

    private List<BasePayChannelDto> payChannelDtoList;

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

    public List<BasePayChannelDto> getPayChannelDtoList() {
        return payChannelDtoList;
    }

    public void setPayChannelDtoList(List<BasePayChannelDto> payChannelDtoList) {
        this.payChannelDtoList = payChannelDtoList;
    }
}
