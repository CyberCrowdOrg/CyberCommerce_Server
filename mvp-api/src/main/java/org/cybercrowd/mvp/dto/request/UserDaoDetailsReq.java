package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;

public class UserDaoDetailsReq implements Serializable {

    private Long userId;

    private String daoNo;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDaoNo() {
        return daoNo;
    }

    public void setDaoNo(String daoNo) {
        this.daoNo = daoNo;
    }
}
