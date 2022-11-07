package org.cybercrowd.mvp.enums;

/**
 * 商户审核状态
 */
public enum MerchantCheckStatusEnum {

    INIT("-1","初始状态"),
    PENDING_CHECK("0","待审核"),
    CHECK_SUCCESS("1","审核通过"),
    CHECK_FAIL("2","审核失败")
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

    MerchantCheckStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static MerchantCheckStatusEnum toEnum(String code){
        for(MerchantCheckStatusEnum checkStatusEnum:MerchantCheckStatusEnum.values()){
            if(checkStatusEnum.getCode().equals(code)){
                return checkStatusEnum;
            }
        }
        return null;
    }
}
