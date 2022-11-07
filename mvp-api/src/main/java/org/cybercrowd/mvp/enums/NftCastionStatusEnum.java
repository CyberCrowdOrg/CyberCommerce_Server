package org.cybercrowd.mvp.enums;

public enum NftCastionStatusEnum {

    UNCAST("0","未铸造"),
    CAST("1","已铸造"),
    INTCASTING("2","铸造中"),
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

    NftCastionStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
