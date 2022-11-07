package org.cybercrowd.mvp.dto.response;

import org.cybercrowd.mvp.dto.BaseUserTaskDto;

import java.io.Serializable;
import java.util.List;

public class UserTaskListRes implements Serializable {

    private int pageNum = 1;

    private int pageSize = 10;

    private int totalPage;

    private List<BaseUserTaskDto> baseUserTaskList;

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

    public List<BaseUserTaskDto> getBaseUserTaskList() {
        return baseUserTaskList;
    }

    public void setBaseUserTaskList(List<BaseUserTaskDto> baseUserTaskList) {
        this.baseUserTaskList = baseUserTaskList;
    }
}
