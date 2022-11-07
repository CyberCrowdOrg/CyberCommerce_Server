package org.cybercrowd.mvp.mapper;

import org.cybercrowd.mvp.model.UserAddress;
import org.springframework.stereotype.Repository;

/**
 * UserAddressMapper继承基类
 */
@Repository
public interface UserAddressMapper extends MyBatisBaseDao<UserAddress, Long> {
}