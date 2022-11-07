package org.cybercrowd.mvp.enums;

public enum UserLoginTypeEnum {

    WEB2("WEB2","web2"),
    WEB3("WEB3","web3"),
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

    UserLoginTypeEnum(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
