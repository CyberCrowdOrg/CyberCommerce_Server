package org.cybercrowd.mvp.service;

import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.UserLoginBindReq;
import org.cybercrowd.mvp.dto.request.UserLoginReq;

public interface UserService {

    /**
     * 用户登录
     * @param userLoginReq
     * @return
     */
    BaseResponse userLogin(UserLoginReq userLoginReq);

    /**
     * 查询用户个人信息
     * @param userId
     * @return
     */
    BaseResponse userPersonal(Long userId);

    /**
     * 用户登录方式绑定
     * @param userLoginBindReq
     * @return
     */
    BaseResponse userLoginBind(UserLoginBindReq userLoginBindReq);
}
