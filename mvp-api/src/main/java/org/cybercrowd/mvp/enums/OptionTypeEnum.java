package org.cybercrowd.mvp.enums;

public enum OptionTypeEnum {

    SINGLE_CHOICE("0","单选"),
    MULTIPLE_CHOICE("1","多选"),
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

    OptionTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static OptionTypeEnum toEnum(String code){
        for(OptionTypeEnum optionTypeEnum:OptionTypeEnum.values()){
            if(optionTypeEnum.getCode().equals(code)){
                return optionTypeEnum;
            }
        }
        return null;
    }
}
