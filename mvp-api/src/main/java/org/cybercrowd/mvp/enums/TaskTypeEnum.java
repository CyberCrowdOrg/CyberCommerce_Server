package org.cybercrowd.mvp.enums;

public enum TaskTypeEnum {

//TODO 中英文切换
//    GROUPON("groupon","发起拼团"),
//    DISTRIBUTOR("distributor","发起分销"),
//    SALES("sales","商品发布销售"),

    GROUPON("groupon","Collective buying"),
    DISTRIBUTOR("distributor","Distribution"),
    SALES("sales","Product launch"),
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

    TaskTypeEnum(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public static TaskTypeEnum toEnum(String code){
        for(TaskTypeEnum taskTypeEnum:TaskTypeEnum.values()){
            if(taskTypeEnum.getCode().equals(code)){
                return taskTypeEnum;
            }
        }
        return null;
    }
}
