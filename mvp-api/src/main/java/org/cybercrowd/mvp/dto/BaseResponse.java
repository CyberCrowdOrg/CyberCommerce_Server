package org.cybercrowd.mvp.dto;

import org.cybercrowd.mvp.enums.ReturnCodeEnum;

import java.io.Serializable;

/**
 * 通用返回结果
 *
 * @author llj
 */
public class BaseResponse implements Serializable {

    /**
     * 0000,请求成功 9999,请求失败
     */
    public String returnCode = "0000";
    public String returnMsg = "请求成功";

    public Object data;

    public boolean successFlag() {

        if (ReturnCodeEnum.SUCCESS.getCode().equals(returnCode)) {
            return true;
        }

        return false;
    }

    public boolean failFlag() {

        if (ReturnCodeEnum.SUCCESS.getCode().equals(returnCode)) {
            return false;
        }

        return true;
    }

    public BaseResponse() {
        super();
    }

    public static BaseResponse ok() {
        return new BaseResponse();
    }

    public static BaseResponse ok(Object data) {
        BaseResponse r = new BaseResponse();
        r.setData(data);
        return r;
    }

    public static BaseResponse error(ReturnCodeEnum returnCodeEnum) {
        BaseResponse r = new BaseResponse();
        r.setReturnCode(returnCodeEnum.getCode());
        r.setReturnMsg(returnCodeEnum.getMsg());
        return r;
    }

    public void setReturnCodeEnum(ReturnCodeEnum returnCodeEnum) {
        setReturnCode(returnCodeEnum.getCode());
        setReturnMsg(returnCodeEnum.getMsg());
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
