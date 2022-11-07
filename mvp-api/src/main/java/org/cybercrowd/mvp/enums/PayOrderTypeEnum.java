package org.cybercrowd.mvp.enums;

public enum PayOrderTypeEnum {


    PAY("pay","付款"),
    REFUND("refund","退款"),
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

    PayOrderTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static PayOrderTypeEnum toEnum(String code) {
        for(PayOrderTypeEnum payOrderTypeEnum:PayOrderTypeEnum.values()){
            if(payOrderTypeEnum.getCode().equals(code)){
                return payOrderTypeEnum;
            }
        }
        return null;
    }
}
