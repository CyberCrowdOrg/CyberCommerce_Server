package org.cybercrowd.mvp.enums;

public enum ChannelReturnCodeEnum {

    CHANNEL_3001_ERROR("3001","服务异常"),
    CHANNEL_3002_ERROR("3002","缺少接口参数"),
    CHANNEL_3003_ERROR("3003","缺少业务参数"),
    CHANNEL_3004_ERROR("3004","无效系统标识"),
    CHANNEL_3005_ERROR("3005","无效接口"),
    CHANNEL_3006_ERROR("3006","未授权接口"),
    CHANNEL_3007_ERROR("3007","授权未开始接口"),
    CHANNEL_3008_ERROR("3008","授权已过期接口"),
    CHANNEL_3009_ERROR("3009","无效签名"),
    CHANNEL_3010_ERROR("3010","重复请求"),
    CHANNEL_3011_ERROR("3011","请求超时"),
    CHANNEL_3012_ERROR("3012","已过期TOKEN"),
    CHANNEL_3013_ERROR("3013","调用次数超限"),
    CHANNEL_3014_ERROR("3014","参数解析错误"),
    CHANNEL_3015_ERROR("3015","获取电子券类型失败"),
    CHANNEL_3016_ERROR("3016","获取电子券失败"),
    CHANNEL_3017_ERROR("3017","获取核销码失败"),
    CHANNEL_3018_ERROR("3018","注销电子券失败"),
    CHANNEL_3019_ERROR("3019","获取电子券状态失败"),
    CHANNEL_3020_ERROR("3020","库存不存在"),
    CHANNEL_3021_ERROR("3021","库存不足"),
    CHANNEL_3022_ERROR("3022","券类型不存在"),
    CHANNEL_3023_ERROR("3023","券类型未授权"),
    CHANNEL_3024_ERROR("3024","网络有点拥挤，请稍等!"),
    CHANNEL_3025_ERROR("3025","电子券不存在"),
    CHANNEL_3026_ERROR("3026","电子券状态异常"),
    CHANNEL_3027_ERROR("3027","券已注销,不能重复注销"),
    CHANNEL_3028_ERROR("3028","券已使用,不能注销"),
    CHANNEL_3029_ERROR("3029","派发成功记录日志失败"),
    CHANNEL_3030_ERROR("3030","派发失败，每次只能派发一种券"),
    CHANNEL_3031_ERROR("3031","账单获取失败"),
    CHANNEL_3032_ERROR("3032","账单未生成"),
    CHANNEL_3033_ERROR("3033","账单已删除"),
    CHANNEL_3034_ERROR("3034","重复申请的账单日期"),
    CHANNEL_3035_ERROR("3035","用户手机号为空"),
    CHANNEL_3036_ERROR("3036","获取用户信息失败"),
    CHANNEL_3037_ERROR("3037","商户号不存在"),
    CHANNEL_3038_ERROR("3038","获取token失败"),
    CHANNEL_3039_ERROR("3039","BUSINESS_ID不能为空"),
    CHANNEL_3040_ERROR("3040","订单已存在"),
    CHANNEL_3041_ERROR("3041","核销码不存在或者已过期"),
    CHANNEL_3042_ERROR("3042","不支持的核销方式")
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

    ChannelReturnCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ChannelReturnCodeEnum toEnum(String code){
        for(ChannelReturnCodeEnum channelReturnCodeEnum:ChannelReturnCodeEnum.values()){
            if(channelReturnCodeEnum.getCode().equals(code)){
                return channelReturnCodeEnum;
            }
        }
        return null;
    }
}
