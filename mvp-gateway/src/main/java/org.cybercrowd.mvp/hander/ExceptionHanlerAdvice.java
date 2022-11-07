package org.cybercrowd.mvp.hander;

import org.cybercrowd.mvp.common.exception.NotLoginException;
import org.cybercrowd.mvp.common.exception.RequestException;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.enums.ReturnCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class ExceptionHanlerAdvice {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHanlerAdvice.class);

    /**
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse exception(Exception exception, WebRequest request) {

        log.error("拦截到异常 exception", exception);
        //log.error("拦截到异常", exception.getMessage());
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setReturnCode(ReturnCodeEnum.NETWORK_BUSY.getCode());
        baseResponse.setReturnMsg(ReturnCodeEnum.NETWORK_BUSY.getMsg());

        return baseResponse;
    }


    /**
     * 请求业务异常
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(value = RequestException.class)
    @ResponseBody
    public BaseResponse RequestException(RequestException exception, WebRequest request) {

        log.error("拦截到请求业务异常:{}", exception.getMessage());
        BaseResponse baseResponse = exception.getBaseResponse();
        if (null == baseResponse) {
            baseResponse = new BaseResponse();
            baseResponse.setReturnCode(exception.getCode());
            baseResponse.setReturnMsg(exception.getMessage());
        }
        return baseResponse;
    }


    /**
     * 未登录异常
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(value = {NotLoginException.class})
    @ResponseBody
    public BaseResponse NotLoginException(NotLoginException exception, WebRequest request) {

        log.warn("拦截到未登录：{}", request.getContextPath());
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setReturnCode(ReturnCodeEnum.PLEASE_LOGIN.getCode());
        baseResponse.setReturnMsg(ReturnCodeEnum.PLEASE_LOGIN.getMsg());
        return baseResponse;

    }


    /**
     * 参数校验异常
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    public BaseResponse MethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {

        log.warn("拦截到参数校验异常:{}", exception.getMessage());
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setReturnCode(ReturnCodeEnum.PLEASE_ENTER_THE_INFO_IN_CORRECT_FORMAT.getCode());
        baseResponse.setReturnMsg(ReturnCodeEnum.PLEASE_ENTER_THE_INFO_IN_CORRECT_FORMAT.getMsg());
        return baseResponse;

    }

    /**
     * 参数校验异常
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(value = {ConstraintViolationException.class, BindException.class})
    @ResponseBody
    public BaseResponse ConstraintViolationException(Exception exception, WebRequest request) {

        log.warn("拦截到参数校验异常:{}", exception.getMessage());
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setReturnCode(ReturnCodeEnum.PLEASE_ENTER_THE_INFO_IN_CORRECT_FORMAT.getCode());
        baseResponse.setReturnMsg(ReturnCodeEnum.PLEASE_ENTER_THE_INFO_IN_CORRECT_FORMAT.getMsg());
        return baseResponse;

    }

    /**
     * 请求参数异常
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseBody
    public BaseResponse HttpMessageNotReadableException(HttpMessageNotReadableException exception, WebRequest request) {

        log.warn("拦截到请求参数异常:{}", exception.getMessage());
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setReturnCode(ReturnCodeEnum.PLEASE_ENTER_THE_INFO_IN_CORRECT_FORMAT.getCode());
        baseResponse.setReturnMsg(ReturnCodeEnum.PLEASE_ENTER_THE_INFO_IN_CORRECT_FORMAT.getMsg());
        return baseResponse;

    }

//    /**
//     * 版本检查异常
//     *
//     * @param exception
//     * @param request
//     * @return
//     */
//    @ExceptionHandler(value = {CheckVersionException.class})
//    @ResponseBody
//    public BaseResponse CheckVersionException(CheckVersionException exception, WebRequest request) {
//
//        log.warn("拦截到版本检查异常");
//        String data = exception.getMessage();
//        BaseResponse baseResponse = new BaseResponse();
//        baseResponse.setReturnCode(ReturnCodeEnum.SUCCESS.getCode());
//
//        CheckVersionResDTO checkVersionResDTO = JSON.parseObject(data, CheckVersionResDTO.class);
//        baseResponse.setData(checkVersionResDTO);
//
//        baseResponse.setReturnMsg(MessageUtils.get(baseResponse.getReturnCode()));
//        return baseResponse;
//
//    }


}
