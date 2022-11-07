package org.cybercrowd.mvp.enums;


public enum ReturnCodeEnum {

    SUCCESS("0000", "请求成功"),

    PLEASE_ENTER_THE_INFO_IN_CORRECT_FORMAT("1001", "请输入正确的格式"),
    THIS_ACCOUNT_DOES_NOT_EXIST("1003", "账号不存在"),
    MERCHANT_LOGIN_PWD_ERROR("1004","登录密码错误"),
    MERCHANT_SMS_CODE_ERROR("1005","验证码错误"),
    INPUT_MERCHNAT_PHONE_ERROR("1006","请输入正确的手机号"),
    INPUT_MERCHANT_PWD_ERROR("1007","请输入登录密码"),
    INPUT_MERCHNAT_SMS_CODE_ERROR("1008","请输入短信验证码"),
    OLD_LOGIN_PWD_ERROR("1009","原登录密码错误"),
    SAME_LOGIN_PWD_ERROR("1010","新密码不能与原登录密码相同"),
    INPUT_NEW_LOGIN_PWD_ERROR("1011","请输入新的登录密码"),
    INPUT_OLD_LOGIN_PWD_ERROR("1012","请输入原登录密码"),
    MERCHANT_CHECK_STATUS_EXCEPTION("1013","商户审核状态异常"),
    MERCHANT_CHECK_STATUS_NOT_CERTIFIED("1014","商户未实名认证"),
    MERCHANT_CHECK_STATUS_REJECTED("1015","商户审核未通过,请重新上传资料"),
    MERCHANT_CHECK_STATUS_NOT_PENDING("1016","商户实名认证待审核"),
    MERCHNAT_PHONE_FORMAT_ERROR("1017","手机号格式错误"),
    MERCHNAT_PHONE_EXISTED_ERROR("1018","入网手机号已存在"),
    USER_LOGIN_BIND_ERROR("1019","绑定失败,该账号已被其他用户绑定"),
    USER_LGOIN_BIND_ERROR2("1020","绑定失败,当前登录方式已存在绑定账号"),

    //短信
    RESP_VERIFICATION_CODE_TIME_LIMIT_ERROR("1051", "10分钟内已获取4次验证码，10分钟后再试"),
    RESP_VERIFICATION_CODE_FAIL_ERROR("1052", "验证码不正确，请重新输入"),
    RESP_VERIFICATION_CODE_INVALID_ERROR("1053", "验证码已失效，请重新获取"),
    GET_VERIFICATION_CODE_ERROR("1054", "获取验证码失败，请重新获取"),
    CERTIFY_FAIL("1055", "验证未通过"),

    //参数错误
    REQUEST_PARAMETER_NOT_NULL_ERROR("8200", "参数错误"),
    REQUEST_PARAMETER_PARSING_ERROR("8201", "参数解析错误"),

    //用户钱包
    USER_WALLET_INSUFFICIENT_BALANCE_ERROR("3001","钱包余额不足"),
    UNSUPPORTED_RECHARGE_COIN_ERROR("3002","当前币种不支持充值"),
    //订单类
    RECHARGE_COIN_NOT_FOUND_ERROR("4001","未找到充值币种"),
    REPEAT_HAGGLING_ERROR("4001","您已参与过砍价活动"),
    ORDER_CREATE_ERROR("4002","创建订单失败"),
    OWNER_ORDER_CRATE_ERROR("4003","订单创建失败,你不能参与自己的商品任何交易"),
    GET_ORDER_AMOUNT_ERROR("4005","未获取到正确的订单金额"),
    ORDER_NOT_FOUND_ERROR("4006","订单未找到"),
    ORDER_STATUS_PAYING_ERROR("4007","订单付款中,不能再支付"),
    ORDER_STATUS_SUCCESS_ERROR("4008","订单已付款,不能再支付"),
    ORDER_STATUS_REFUND_ERROR("4009","订单已退款,不能再支付"),
    //通道类
    PAYCHANNEL_ERROR("6000","支付通道异常"),
    PAYCHANNEL_NOT_FOUND_ERROR("6001","支付通道未找到或者不可用"),
    //任务类
    TASK_PUBLISH_ERROR("7000","发布失败"),
    TASK_NOFT_FOUND_ERROR("7001","未找到父任务"),

    //DAO类

    //提案类
    PROPOSAL_CREATE_PERMISSION_DENIED_ERROR("5001","没有创建提案权限"),
    PROPOSAL_OPTION_REPEAT_ERROR("5002","重复的提案选项"),

    PLEASE_LOGIN("9001", "请登录"),
    NETWORK_BUSY("9998", "网络繁忙,请稍后再试"),
    REQUEST_FAILED("9999", "请求失败"),


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

    private ReturnCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
