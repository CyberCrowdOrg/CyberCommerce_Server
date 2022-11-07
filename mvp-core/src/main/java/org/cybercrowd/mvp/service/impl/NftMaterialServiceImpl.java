package org.cybercrowd.mvp.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.cybercrowd.mvp.constant.AwsS3UploadDirectoryConstants;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.UploadNftFileReq;
import org.cybercrowd.mvp.enums.NftCastionStatusEnum;
import org.cybercrowd.mvp.enums.NftFileTypeEnum;
import org.cybercrowd.mvp.enums.ReturnCodeEnum;
import org.cybercrowd.mvp.mapper.NftMaterialMapper;
import org.cybercrowd.mvp.mapper.TaskInfoMapper;
import org.cybercrowd.mvp.model.NftMaterial;
import org.cybercrowd.mvp.model.TaskInfo;
import org.cybercrowd.mvp.service.NftMaterialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("nftMaterialService")
public class NftMaterialServiceImpl implements NftMaterialService {

    private Logger logger = LoggerFactory.getLogger(NftMaterialServiceImpl.class);

    @Autowired
    AwsS3StorageService awsS3StorageService;
    @Autowired
    TaskInfoMapper taskInfoMapper;
    @Autowired
    NftMaterialMapper nftMaterialMapper;

    @Override
    public BaseResponse uploadNftFile(UploadNftFileReq uploadNftFileReq) {
        BaseResponse baseResponse = new BaseResponse();
        Map<String, MultipartFile> nftFileMap = (Map<String, MultipartFile>) uploadNftFileReq.getFileMap();
        logger.info("任务所属NFT素材文件上传业务,请求入参: userId:{} taskId:{} 上传NFT素材数量:{}",
                uploadNftFileReq.getUserId(),uploadNftFileReq.getTaskId(),nftFileMap.size());
        List<String> filePathMap = new ArrayList<>();
        //验证任务是否存在
        TaskInfo taskInfo = taskInfoMapper.selectTaskByTaskId(uploadNftFileReq.getTaskId());
        if(null == taskInfo){
            logger.info("任务所属NFT素材文件上传业务,任务未找到....");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        try {
            for (String fileKey : nftFileMap.keySet()) {
                String fileUrl = awsS3StorageService.
                        upload(nftFileMap.get(fileKey), AwsS3UploadDirectoryConstants.NFT_FILE_DIR);
                if (StringUtils.isEmpty(fileUrl)) {
                    //文件上传失败
                    logger.info("任务所属NFT素材文件上传业务,上传商品图片文件失败....");
                    baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
                    return baseResponse;
                }
                filePathMap.add(fileUrl);
            }
            //批量保存NFT素材数据
            List<NftMaterial> nftMaterialsList = batchSaveNftMaterial(
                    uploadNftFileReq.getUserId(),uploadNftFileReq.getTaskId(),filePathMap);
            taskInfo.setEnableNft("1");
            taskInfo.setUpdateTime(new Date());
            taskInfoMapper.updateByPrimaryKeySelective(taskInfo);
            baseResponse.setData(filePathMap);
        }catch (Exception e){
            logger.info("任务所属NFT素材文件上传业务,执行异常:{}",e.getMessage(),e);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
        }
        return baseResponse;
    }

    private List<NftMaterial> batchSaveNftMaterial(Long userId, String taskId, List<String> filePathMap) {
        List<NftMaterial> nftMaterialList = new ArrayList<>();
        for(String url:filePathMap){
            NftMaterial nftMaterial = new NftMaterial();
            nftMaterial.setTaskId(taskId);
            nftMaterial.setNftFileType(getNftFileType(url.substring(url.lastIndexOf(".")+1)));
            nftMaterial.setCreateTime(new Date());
            nftMaterial.setCastingStatus(NftCastionStatusEnum.UNCAST.getCode());
            nftMaterial.setFilePath(url);
            nftMaterial.setUpdateTime(new Date());
            nftMaterialMapper.insertSelective(nftMaterial);
            nftMaterialList.add(nftMaterial);
        }
        return nftMaterialList;
    }

    private String getNftFileType(String fileSuffix) {

        if("jpeg".equalsIgnoreCase(fileSuffix)
                || "png".equalsIgnoreCase(fileSuffix) || "svg".equalsIgnoreCase(fileSuffix)
                || "gif".equalsIgnoreCase(fileSuffix) || "tif".equalsIgnoreCase(fileSuffix)
                || "tag".equalsIgnoreCase(fileSuffix) || "bmp".equalsIgnoreCase(fileSuffix)
                || "dds".equalsIgnoreCase(fileSuffix) || "eps".equalsIgnoreCase(fileSuffix)){
            return NftFileTypeEnum.IMAGE.getCode();
        }else if("dsd".equalsIgnoreCase(fileSuffix)
                || "wav".equalsIgnoreCase(fileSuffix) || "ape".equalsIgnoreCase(fileSuffix)
                || "flac".equalsIgnoreCase(fileSuffix) || "tif".equalsIgnoreCase(fileSuffix)
                || "tag".equalsIgnoreCase(fileSuffix) || "bmp".equalsIgnoreCase(fileSuffix)
                || "dds".equalsIgnoreCase(fileSuffix) || "mp3".equalsIgnoreCase(fileSuffix)
                || "acc".equalsIgnoreCase(fileSuffix) || "amr".equalsIgnoreCase(fileSuffix)
                || "dds".equalsIgnoreCase(fileSuffix) || "opus".equalsIgnoreCase(fileSuffix)
                || "ogg".equalsIgnoreCase(fileSuffix) || "wma".equalsIgnoreCase(fileSuffix)
                || "ac3".equalsIgnoreCase(fileSuffix)){
            return NftFileTypeEnum.AUDIO.getCode();
        }else if("MP4".equalsIgnoreCase(fileSuffix)
                || "MOV".equalsIgnoreCase(fileSuffix) || "WMV".equalsIgnoreCase(fileSuffix)
                || "FLV".equalsIgnoreCase(fileSuffix) || "AVI".equalsIgnoreCase(fileSuffix)
                || "AVCHD".equalsIgnoreCase(fileSuffix) || "WebM".equalsIgnoreCase(fileSuffix)
                || "MKV".equalsIgnoreCase(fileSuffix)){
            return NftFileTypeEnum.VIDEO.getCode();
        }
        return NftFileTypeEnum.FILE.getCode();
    }
}
