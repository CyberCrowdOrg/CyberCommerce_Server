package org.cybercrowd.mvp.enums;

public enum MerchantStatusEnum {
    INIT("0","初始状态"),
    NORMAL("1","正常"),
    FREEZE("2","冻结"),
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

    MerchantStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
