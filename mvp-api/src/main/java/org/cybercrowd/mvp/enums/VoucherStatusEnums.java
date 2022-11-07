package org.cybercrowd.mvp.enums;

public enum VoucherStatusEnums {

    UNUSED("0","未使用"),
    USED("1","已使用"),
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

    VoucherStatusEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
