package org.cybercrowd.mvp.enums;

public enum SmsCodeTypeEnum {

    LOGIN_CODE("LOGIN","登录验证码"),
    UDPATE_PWD_CODE("UPDATE_PWD","修改密码验证码")

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

    SmsCodeTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
