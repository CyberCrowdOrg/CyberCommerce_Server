package org.cybercrowd.mvp.enums;

/**
 * 电子消费卷类型
 */
public enum VoucherTypeEnum {

    TYPE_4001("4001","油品券"),
    TYPE_4002("4002","汽油券"),
    TYPE_4003("4003","柴油券"),
    TYPE_4004("4004","非油券"),
    TYPE_4005("4005","商城券"),
    TYPE_4006("4006","异业券"),

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

    VoucherTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static VoucherTypeEnum toEnum(String code){
        for(VoucherTypeEnum voucherTypeEnum:VoucherTypeEnum.values()){
            if(voucherTypeEnum.getCode().equals(code)){
                return voucherTypeEnum;
            }
        }
        return null;
    }
}
