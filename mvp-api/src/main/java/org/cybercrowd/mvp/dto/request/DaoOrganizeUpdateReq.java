package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;

public class DaoOrganizeUpdateReq implements Serializable {

    private String daoNo;

    private String daoName;

    private String daoLogoImage;

    private String daoIntro;

    private Long userId;

    public String getDaoNo() {
        return daoNo;
    }

    public void setDaoNo(String daoNo) {
        this.daoNo = daoNo;
    }

    public String getDaoName() {
        return daoName;
    }

    public void setDaoName(String daoName) {
        this.daoName = daoName;
    }

    public String getDaoLogoImage() {
        return daoLogoImage;
    }

    public void setDaoLogoImage(String daoLogoImage) {
        this.daoLogoImage = daoLogoImage;
    }

    public String getDaoIntro() {
        return daoIntro;
    }

    public void setDaoIntro(String daoIntro) {
        this.daoIntro = daoIntro;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
