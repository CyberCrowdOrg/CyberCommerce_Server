package org.cybercrowd.mvp.mapper;

import org.cybercrowd.mvp.model.NftMaterial;
import org.springframework.stereotype.Repository;

/**
 * NftMaterialMapper继承基类
 */
@Repository
public interface NftMaterialMapper extends MyBatisBaseDao<NftMaterial, Long> {
}