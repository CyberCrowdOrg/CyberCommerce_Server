package org.cybercrowd.mvp.enums;

public enum OrderTypeEnum {

    RECHARGE("recharge","充值"),
    WITHDRAW("withdraw","提现"),
    PAYMENT("payment","付款"),
    REFUND("refund","退款"),
    REWARD("reward","奖励"),

    TRADE("trade","交易"),
    GROUPON_BUY("groupon_buy","拼团"),
    HAGGLE("haggle","砍价"),
    PLEDGE("pledge","质押"),

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

    OrderTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static OrderTypeEnum toEnum(String code) {
        for(OrderTypeEnum orderTypeEnum:OrderTypeEnum.values()){
            if(orderTypeEnum.getCode().equals(code)){
                return orderTypeEnum;
            }
        }
        return null;
    }
}
