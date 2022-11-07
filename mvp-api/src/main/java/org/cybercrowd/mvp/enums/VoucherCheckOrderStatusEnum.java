package org.cybercrowd.mvp.enums;

public enum VoucherCheckOrderStatusEnum {

    NOT_WRITTEN_OFF("0","待核销"),
    WRITTEN_OFF("1","已核销"),
    FAILED_WRITTEN_OFF("2","核销失败"),

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

    VoucherCheckOrderStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
