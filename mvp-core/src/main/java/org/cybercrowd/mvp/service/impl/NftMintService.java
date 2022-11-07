package org.cybercrowd.mvp.service.impl;

import com.alibaba.fastjson.JSON;
import org.aspectj.util.FileUtil;
import org.cybercrowd.mvp.constant.AwsS3UploadDirectoryConstants;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.enums.NftFileTypeEnum;
import org.cybercrowd.mvp.enums.ReturnCodeEnum;
import org.cybercrowd.mvp.mapper.DaoNftAssetsMapper;
import org.cybercrowd.mvp.model.DaoNftAssets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.*;

@Service("nftMintService")
public class NftMintService{

    private Logger logger = LoggerFactory.getLogger(NftMintService.class);

    @Value("${contract.owner_address}")
    private String contractOwnerAddress;
    @Value("${contract.erc721}")
    private String nftContractAddress;

    @Autowired
    DaoNftAssetsMapper daoNftAssetsMapper;
    @Autowired
    CommonService commonService;
    @Autowired
    AwsS3StorageService awsS3StorageService;

    public BaseResponse daoNftMint(Map<String, MultipartFile> fileMap, String daoNo, String taskId, String receiveAddr){
        BaseResponse baseResponse = new BaseResponse();
        logger.info("DAO专属NFT铸造业务,请求入参: daoNo:{} taksId:{} receiveAddr:{} NFT数量:{} 合约Owner地址:{}",
                daoNo,taskId,receiveAddr,fileMap.size(),contractOwnerAddress);
        try {
            long startTime = System.currentTimeMillis();
            //NFT数据上传
            List<String> fileNames = new ArrayList<>();
            List<String> fileSuffixList = new ArrayList<>();
            List<String> nftFilePathList = new ArrayList<>();
            List<String> nftJsonFilePathList = nftFileMultUpload(fileMap, fileNames, fileSuffixList,nftFilePathList, daoNo);
            logger.info("DAO专属NFT铸造业务,上传NFT素材后的的文件:{}" ,JSON.toJSONString(nftJsonFilePathList));
            long endTime = System.currentTimeMillis();
            logger.info("NFT素材文件上传耗时:{}",(endTime - startTime));
            //部署NFT合约
//            baseResponse = commonService.deployNftContract(daoNo + "-DAO-NFT", "DAONFT");
//            String nftContract = (String) baseResponse.getData();
            //批量铸造NFT
            startTime = System.currentTimeMillis();
            baseResponse = commonService.nftMultiMint(nftContractAddress,nftJsonFilePathList,receiveAddr);
            logger.info("DAO专属NFT铸造业务,批量铸造响应结果:{}",JSON.toJSONString(baseResponse));
            endTime = System.currentTimeMillis();
            logger.info("NFT铸造耗时:{}",(endTime - startTime));
            //保存DAO NFT资产数据
            if(ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())) {
                List<String> tokenIds = (List<String>) baseResponse.getData();
                batchSaveDaoNftAssets(tokenIds, nftFilePathList, fileNames, fileSuffixList,
                        nftContractAddress, daoNo, taskId, receiveAddr, contractOwnerAddress);
                baseResponse.setData(null);
            }
        }catch (Exception e){
            logger.error("DAO专属NFT铸造业务,执行异常:{}",e.getMessage(),e);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
        }
        return baseResponse;
    }

    private List<String> nftFileMultUpload(Map<String, MultipartFile> fileMap, List<String> fileNames,
                                           List<String> fileSuffixList,List<String> nftFilePathList, String daoNo) throws Exception {
        List<String> nftJsonFilePathList = new ArrayList<>();
        int i = 1;
        for(String fileKey:fileMap.keySet()){
            File file = null;
            try{
                MultipartFile multipartFile = fileMap.get(fileKey);
                String nftName = "DAO NFT #00"+i;
                String originalFilename = multipartFile.getOriginalFilename();
                String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                String fileName = UUID.randomUUID().toString().replace("-","");
                String fileDirectory = MessageFormat.format("{0}{1}{2}",AwsS3UploadDirectoryConstants.NFT_FILE_DIR,
                        UUID.randomUUID().toString().replace("-",""),fileSuffix);
                String nftFilePath = awsS3StorageService.upload(multipartFile.getInputStream(),multipartFile.getSize(),fileDirectory);

                file = creatNftJsonFile(fileName,daoNo,nftName,nftFilePath);
                fileDirectory = MessageFormat.format("{0}{1}",AwsS3UploadDirectoryConstants.NFT_FILE_DIR,
                        file.getName());

                String jsonPath = awsS3StorageService.upload(new FileInputStream(file),file.length(),fileDirectory);

                fileNames.add(nftName);
                fileSuffixList.add(fileSuffix);
                nftJsonFilePathList.add(jsonPath);
                nftFilePathList.add(nftFilePath);
                i++;
            }catch (Exception e){
                throw e;
            }finally {
                if(null != file || file.exists()){
                    file.delete();
                }
            }
        }
        return nftJsonFilePathList;
    }

    private void batchSaveDaoNftAssets(List<String> tokenIds,List<String> nftFilePathList,
                                       List<String> fileNames,List<String> fileSuffixList,
                                       String nftContract,String daoNo, String taskId,
                                       String receiveAddr, String contractOwnerAddress) {
        for(int i = 0 ;i<tokenIds.size();i++){
            DaoNftAssets daoNftAssets = new DaoNftAssets();
            daoNftAssets.setDaoNo(daoNo);
            daoNftAssets.setTaskId(taskId);
            daoNftAssets.setNftTokenId(tokenIds.get(i));
            daoNftAssets.setNftContract(nftContract);
            daoNftAssets.setNftName(fileNames.get(i));
            daoNftAssets.setNftDescription(daoNo+" DAO NFT");
            daoNftAssets.setNftFileType(getNftFileType(fileSuffixList.get(i)));
            daoNftAssets.setNftContract(nftContract);
            daoNftAssets.setNftFilePath(nftFilePathList.get(i));
            daoNftAssets.setNftHolderAddress(receiveAddr);
            daoNftAssets.setNftNumber(1l);
            daoNftAssets.setNftMintAddress(contractOwnerAddress);
            daoNftAssets.setNftMarketLink("https://testnets.opensea.io/assets/goerli/"+ nftContract+"/"+tokenIds.get(i));
            daoNftAssets.setCreateTime(new Date());
            daoNftAssets.setUpdateTime(new Date());
            daoNftAssetsMapper.insertSelective(daoNftAssets);
        }
    }

    private File creatNftJsonFile(String fileName,String daoNo,String nftName, String nftFilePath) throws Exception {
        String tmpPath = System.getProperty("java.io.tmpdir");
        File file = new File(tmpPath +"/" + "metadata-"+fileName + ".json");
        file.createNewFile();
        //将JSON数据写入文件中
        Map<String,Object> jsonMap = new HashMap<>();
        jsonMap.put("image",nftFilePath);
        jsonMap.put("name",nftName);
        jsonMap.put("description",daoNo+" DAO专属NFT");
        String json = JSON.toJSONString(jsonMap);
        FileUtil.writeAsString(file,json);
        return file;
    }

    private String getNftFileType(String fileSuffix) {

        if(".jpeg".equalsIgnoreCase(fileSuffix) || ".jpg".equalsIgnoreCase(fileSuffix)
                || ".png".equalsIgnoreCase(fileSuffix) || ".svg".equalsIgnoreCase(fileSuffix)
                || ".gif".equalsIgnoreCase(fileSuffix) || ".tif".equalsIgnoreCase(fileSuffix)
                || ".tag".equalsIgnoreCase(fileSuffix) || ".bmp".equalsIgnoreCase(fileSuffix)
                || ".dds".equalsIgnoreCase(fileSuffix) || ".eps".equalsIgnoreCase(fileSuffix)){
            return NftFileTypeEnum.IMAGE.getCode();
        }else if(".dsd".equalsIgnoreCase(fileSuffix)
                || ".wav".equalsIgnoreCase(fileSuffix) || ".ape".equalsIgnoreCase(fileSuffix)
                || ".flac".equalsIgnoreCase(fileSuffix) || ".tif".equalsIgnoreCase(fileSuffix)
                || ".tag".equalsIgnoreCase(fileSuffix) || ".bmp".equalsIgnoreCase(fileSuffix)
                || ".dds".equalsIgnoreCase(fileSuffix) || ".mp3".equalsIgnoreCase(fileSuffix)
                || ".acc".equalsIgnoreCase(fileSuffix) || ".amr".equalsIgnoreCase(fileSuffix)
                || ".dds".equalsIgnoreCase(fileSuffix) || ".opus".equalsIgnoreCase(fileSuffix)
                || ".ogg".equalsIgnoreCase(fileSuffix) || ".wma".equalsIgnoreCase(fileSuffix)
                || ".ac3".equalsIgnoreCase(fileSuffix)){
            return NftFileTypeEnum.AUDIO.getCode();
        }else if("MP4".equalsIgnoreCase(fileSuffix)
                || ".MOV".equalsIgnoreCase(fileSuffix) || ".WMV".equalsIgnoreCase(fileSuffix)
                || ".FLV".equalsIgnoreCase(fileSuffix) || ".AVI".equalsIgnoreCase(fileSuffix)
                || ".AVCHD".equalsIgnoreCase(fileSuffix) || ".WebM".equalsIgnoreCase(fileSuffix)
                || ".MKV".equalsIgnoreCase(fileSuffix)){
            return NftFileTypeEnum.VIDEO.getCode();
        }
        return NftFileTypeEnum.FILE.getCode();
    }
}
