package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;

public class DaoNftMintReq implements Serializable {

    private String daoNo;
    private Long userId;
    private Object nftFileMap;

    public String getDaoNo() {
        return daoNo;
    }

    public void setDaoNo(String daoNo) {
        this.daoNo = daoNo;
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
