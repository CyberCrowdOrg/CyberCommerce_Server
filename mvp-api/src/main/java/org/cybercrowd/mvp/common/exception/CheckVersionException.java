package org.cybercrowd.mvp.common.exception;


/**
 * 版本检查异常处理
 */
public class CheckVersionException extends RuntimeException {

    private String code;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public CheckVersionException() {
        super();

    }

    public CheckVersionException(String code, String message) {
        super(message);
        this.setCode(code);
    }

    public CheckVersionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

    }

    public CheckVersionException(String message, Throwable cause) {
        super(message, cause);

    }

    public CheckVersionException(String message) {
        super(message);

    }

    public CheckVersionException(Throwable cause) {
        super(cause);

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
