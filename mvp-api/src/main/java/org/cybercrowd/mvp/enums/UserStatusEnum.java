package org.cybercrowd.mvp.enums;

public enum UserStatusEnum {

    NORMAL("normal","正常"),
    DISABLE_LOGIN("disable","禁止登录"),
    LOG_OFF("logoff","注销账户"),
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

    UserStatusEnum(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
