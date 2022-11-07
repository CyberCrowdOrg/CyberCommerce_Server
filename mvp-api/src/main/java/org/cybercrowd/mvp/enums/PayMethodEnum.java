package org.cybercrowd.mvp.enums;

public enum PayMethodEnum {

    balance("balance","余额"),
    online("online","在线支付"),
    chain("chain","链上付款"),

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

    PayMethodEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static PayMethodEnum toEnum(String code) {
        for(PayMethodEnum payMethodEnum:PayMethodEnum.values()){
            if(payMethodEnum.getCode().equals(code)){
                return payMethodEnum;
            }
        }
        return null;
    }
}
