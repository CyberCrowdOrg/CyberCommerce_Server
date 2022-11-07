package org.cybercrowd.mvp.enums;

public enum ProposalStatusEnum {

    NOT_START("0","未开始"),
    STARTED("1","进行中"),
    OVER("2","已结束"),

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

    ProposalStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
