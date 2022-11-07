package org.cybercrowd.mvp.dto.request;

import java.io.Serializable;

public class UserLoginBindReq implements Serializable {
    /**
     * 用户来源,WEB3 WEB2
     */
    private String userOrigin;

    /**
     * 用户登录类型:google wechat metamask
     */
    private String loginType;

    private String account;

    private String loginKey;

    //第三方登录需要,跳转地址
    private String redirectUri;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserOrigin() {
        return userOrigin;
    }

    public void setUserOrigin(String userOrigin) {
        this.userOrigin = userOrigin;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLoginKey() {
        return loginKey;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
