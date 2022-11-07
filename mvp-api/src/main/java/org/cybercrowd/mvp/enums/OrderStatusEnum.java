package org.cybercrowd.mvp.enums;

public enum OrderStatusEnum {

    UNPAID("0","未付款"),
    PAYING("5","付款中"),
    SUCCESS("6","付款成功"),
    FAIL("7","付款失败"),
    REFUND("8","退款"),
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

    OrderStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
