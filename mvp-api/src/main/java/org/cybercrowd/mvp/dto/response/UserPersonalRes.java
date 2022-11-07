package org.cybercrowd.mvp.dto.response;

import org.cybercrowd.mvp.dto.LoginBindDto;

import java.io.Serializable;
import java.util.List;

public class UserPersonalRes implements Serializable {

    private String userId;
    private String nickName;
    private String userAvatar;
    private String userOrigin;
    private Long creditScore;
    private Long creditAmount;

    private List<UserWalletBalanceRes> walletBalanceResList;

    private List<UserNftAssetsRes> nftAssetsResList;

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

    public List<UserWalletBalanceRes> getWalletBalanceResList() {
        return walletBalanceResList;
    }

    public void setWalletBalanceResList(List<UserWalletBalanceRes> walletBalanceResList) {
        this.walletBalanceResList = walletBalanceResList;
    }

    public List<UserNftAssetsRes> getNftAssetsResList() {
        return nftAssetsResList;
    }

    public void setNftAssetsResList(List<UserNftAssetsRes> nftAssetsResList) {
        this.nftAssetsResList = nftAssetsResList;
    }

    public List<LoginBindDto> getLoginBindList() {
        return loginBindList;
    }

    public void setLoginBindList(List<LoginBindDto> loginBindList) {
        this.loginBindList = loginBindList;
    }
}
