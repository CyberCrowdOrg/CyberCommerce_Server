package org.cybercrowd.mvp.enums;

public enum NftFileTypeEnum {

    IMAGE("image","图片"),
    AUDIO("audio","音频"),
    VIDEO("video","视频"),
    FILE("FILE","文件"),
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

    NftFileTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
