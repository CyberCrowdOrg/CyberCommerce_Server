package org.cybercrowd.mvp.mapper;

import org.cybercrowd.mvp.dto.response.UserNftAssetsRes;
import org.cybercrowd.mvp.model.UserNftAssets;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserNftAssetsMapper继承基类
 */
@Repository
public interface UserNftAssetsMapper extends MyBatisBaseDao<UserNftAssets, Long> {

    List<UserNftAssetsRes> userNftAssetsList(Long userId);
}