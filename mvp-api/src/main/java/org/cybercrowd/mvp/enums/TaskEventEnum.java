package org.cybercrowd.mvp.enums;

public enum TaskEventEnum {

//TODO 中英文切换
//    GROUPON("groupon","发起拼团"),
//    DISTRIBUTOR("distributor","发起分销"),
//    SALES("sales","商品发布销售"),
//    GROUPON_BUY("groupon_buy","拼团购买"),
//    HAGGLE("haggle","帮忙砍一刀");

    GROUPON("groupon","Collective buying"),
    DISTRIBUTOR("distributor","Distribution"),
    SALES("sales","Product launch"),
    GROUPON_BUY("groupon_buy","Group buying"),
    HAGGLE("haggle","Bargain"),

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

    TaskEventEnum(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public static TaskEventEnum toEnum(String code){

        for(TaskEventEnum taskEventEnum:TaskEventEnum.values()){
            if(taskEventEnum.getCode().equals(code)){
                return taskEventEnum;
            }
        }
        return null;
    }
}
