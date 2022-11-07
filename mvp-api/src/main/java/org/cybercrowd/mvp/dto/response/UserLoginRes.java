package org.cybercrowd.mvp.dto.response;

import org.cybercrowd.mvp.dto.LoginBindDto;

import java.io.Serializable;
import java.util.List;

public class UserLoginRes implements Serializable {

    private String userId;
    private String nickName;
    private String userAvatar;
    private String userOrigin;
    //用户登录类型:google wechat metamask
    private String loginType;
    private String userWalletAddr;
    private String loginToken;
    private Long creditScore;
    private Long creditAmount;

    private List<LoginBindDto> loginBindList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
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

    public String getUserWalletAddr() {
        return userWalletAddr;
    }

    public void setUserWalletAddr(String userWalletAddr) {
        this.userWalletAddr = userWalletAddr;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public Long getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Long creditScore) {
        this.creditScore = creditScore;
    }

    public Long getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Long creditAmount) {
        this.creditAmount = creditAmount;
    }

    public List<LoginBindDto> getLoginBindList() {
        return loginBindList;
    }

    public void setLoginBindList(List<LoginBindDto> loginBindList) {
        this.loginBindList = loginBindList;
    }
}
