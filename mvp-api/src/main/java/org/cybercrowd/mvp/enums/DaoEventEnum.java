package org.cybercrowd.mvp.enums;

public enum DaoEventEnum {

    GROUPON("groupon","发起拼团"),
    DISTRIBUTOR("distributor","分销"),
    SALES("sales","商品发布销售"),
    PROPOSAL("proposal","提案"),

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

    DaoEventEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
