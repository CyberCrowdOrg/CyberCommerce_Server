package org.cybercrowd.mvp.exception;

/**
 * 
 * @Title: API请求异常处理
 * @Description:
 * @author llj
 * @date 2019年9月2日
 */
public class APIException extends RuntimeException {

	private String code;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public APIException() {
		super();
		
	}

	public APIException(String code, String message) {
		super(message);
		this.setCode(code);
	}

	public APIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public APIException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public APIException(String message) {
		super(message);
		
	}

	public APIException(Throwable cause) {
		super(cause);
		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
