package org.cybercrowd.mvp.enums;

public enum TaskRulesTypeEnum {

    GROUPON("groupon","拼团"),
    DISTRIBUTOR("distributor","分销"),
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

    TaskRulesTypeEnum(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public static TaskRulesTypeEnum toEnum(String code){
        for(TaskRulesTypeEnum taskRulesTypeEnum:TaskRulesTypeEnum.values()){
            if(taskRulesTypeEnum.getCode().equals(code)){
                return taskRulesTypeEnum;
            }
        }
        return null;
    }
}
