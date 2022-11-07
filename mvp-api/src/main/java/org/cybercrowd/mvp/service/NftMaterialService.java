package org.cybercrowd.mvp.service;

import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.UploadNftFileReq;

public interface NftMaterialService {

    BaseResponse uploadNftFile(UploadNftFileReq uploadNftFileReq);
}
