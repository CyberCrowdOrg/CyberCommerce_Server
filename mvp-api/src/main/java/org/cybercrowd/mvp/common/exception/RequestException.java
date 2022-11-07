package org.cybercrowd.mvp.common.exception;

import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.enums.ReturnCodeEnum;

public class RequestException extends RuntimeException {

    private String code;
    private BaseResponse baseResponse;

    public RequestException(String code, String message) {
        super(message);
        this.code = code;
    }

    public RequestException(ReturnCodeEnum returnCodeEnum) {
        super(returnCodeEnum.getMsg());
        this.code = returnCodeEnum.getCode();
    }

    public RequestException(BaseResponse baseResponse) {
        super(baseResponse.getReturnMsg());
        this.code = baseResponse.getReturnCode();
        this.baseResponse = baseResponse;
    }

    private static final long serialVersionUID = 1L;

    public RequestException() {
        super();
    }

    public RequestException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

    public RequestException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public RequestException(String arg0) {
        super(arg0);
    }

    public RequestException(Throwable arg0) {
        super(arg0);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }
}
