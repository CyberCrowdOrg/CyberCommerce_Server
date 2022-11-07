package org.cybercrowd.mvp.dto.response;

import org.cybercrowd.mvp.dto.UserGrouponDto;

import java.io.Serializable;
import java.util.List;

public class UserGrouponRes implements Serializable {

    private List<UserGrouponDto> userGrouponDtoList;

    private int pageNum;

    private int pageSize;

    private int totalPage;

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

    public List<UserGrouponDto> getUserGrouponDtoList() {
        return userGrouponDtoList;
    }

    public void setUserGrouponDtoList(List<UserGrouponDto> userGrouponDtoList) {
        this.userGrouponDtoList = userGrouponDtoList;
    }
}
