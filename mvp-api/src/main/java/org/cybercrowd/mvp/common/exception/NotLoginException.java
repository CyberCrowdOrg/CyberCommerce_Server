package org.cybercrowd.mvp.common.exception;


/**
 * 未登录异常处理
 */
public class NotLoginException extends RuntimeException {

    private String code;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NotLoginException() {
        super();
    }
    
    public NotLoginException(String code, String message) {
        super(message);
        this.setCode(code);
    }

    public NotLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

    }

    public NotLoginException(String message, Throwable cause) {
        super(message, cause);

    }

    public NotLoginException(String message) {
        super(message);

    }

    public NotLoginException(Throwable cause) {
        super(cause);

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
