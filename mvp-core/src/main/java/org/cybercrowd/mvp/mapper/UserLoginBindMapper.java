package org.cybercrowd.mvp.mapper;

import org.cybercrowd.mvp.dto.LoginBindDto;
import org.cybercrowd.mvp.model.UserLoginBind;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserLoginBindMapper继承基类
 */
@Repository
public interface UserLoginBindMapper extends MyBatisBaseDao<UserLoginBind, Long> {

    UserLoginBind selectUserLoginBind(UserLoginBind userLoginBind);

    List<LoginBindDto> userLoginBindList(Long userId);
}