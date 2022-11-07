package org.cybercrowd.mvp.enums;

public enum UserOriginEnum {

    GOOGLE("google","谷歌"),
    TWITTER("twitter","推特"),
    WECHAT("wechat","微信"),
    METAMASK("metamask","小狐狸插件"),
    FACEBOOK("facebook","脸书"),
    WALLETCONNECT("walletconnect","walletconnect"),
    COINBASE("coinbase","coinbase"),
    ;

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    UserOriginEnum(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public static UserOriginEnum toEnum(String code){
        for(UserOriginEnum userOriginEnum:UserOriginEnum.values()){
            if(userOriginEnum.getCode().equals(code)){
                return userOriginEnum;
            }
        }
        return null;
    }
}
