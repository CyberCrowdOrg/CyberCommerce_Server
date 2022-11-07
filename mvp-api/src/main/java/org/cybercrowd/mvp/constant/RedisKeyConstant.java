package org.cybercrowd.mvp.constant;

public class RedisKeyConstant {

    /**
     * APP名称
     */
    public static final String APP_NAME = "MVP";

    /**
     * 缓存前缀
     */
    public static final String REDIS_CACHE_PREFIX = APP_NAME + ":REDIS:CACHE";

    /**
     * 验证码前缀
     */
    public static final String CODE_KEY_PREFIX = APP_NAME + ":CODE:";

    /**
     * SESSION ID
     */
    public static final String USER_SESSION_ID = APP_NAME + ":USER_SESSION_ID:";

    /**
     * 系统参数配置
     */
    public static final String SYSTEM_CONFIG = APP_NAME + ":SYSTEM_CONFIG:";

    /**
     * redis 锁 key
     */
    public static final String REDIS_LOCK_KEY_PREFIX = APP_NAME +":REDIS_LOCK_KEY:";

    /**
     * 用户钱包余额更新锁
     */
    public static final String USER_WALLET_BALANCE_UPDATE_LCOK_KEY = REDIS_LOCK_KEY_PREFIX + "USER_WALLET_BALANCE_UPDATE_LCOK:{0}:{1}";


    /**
     * 获取key
     *
     * @param prefix  前缀
     * @param postfix 后缀
     * @return
     */
    public static String getKey(String prefix, String postfix) {
        return prefix + postfix;
    }

}
