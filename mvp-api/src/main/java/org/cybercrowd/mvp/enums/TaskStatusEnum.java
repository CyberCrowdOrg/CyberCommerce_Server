package org.cybercrowd.mvp.enums;

public enum TaskStatusEnum {

    NOT_STARTED("0","任务未开始"),
    STARTED("1","任务已开始"),
    ACCEPTIONG("5","任务验收中"),
    COMPLETED("6","任务已完成"),
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

    TaskStatusEnum(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

}
