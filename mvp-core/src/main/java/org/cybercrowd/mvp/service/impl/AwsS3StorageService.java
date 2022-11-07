package org.cybercrowd.mvp.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.InputStream;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 亚马逊S3对象存储服务
 */
@Service
public class AwsS3StorageService {

    private Logger logger = LoggerFactory.getLogger(AwsS3StorageService.class);

    @Value("${aws.s3.bucket_name}")
    private String bucketName;
    @Value("${aws.s3.base_url}")
    private String baseUrl;

    @Autowired
    private S3Client s3Client;

    /**
     * 单笔上传
     * @param multipartFile
     * @param directory 目录
     * @return
     * @throws Exception
     */
    public String upload(MultipartFile multipartFile,String directory) throws Exception{
        String originalFilename = multipartFile.getOriginalFilename();
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileDirectory = MessageFormat.format("{0}{1}{2}",directory,
                UUID.randomUUID().toString().replace("-",""),fileSuffix);
        logger.info("待上传文件原名:{} 上传后文件目录及名称:{}",originalFilename,fileDirectory);
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileDirectory)
                .build();
        PutObjectResponse putObjectResponse = s3Client.putObject(objectRequest,
                RequestBody.fromInputStream(multipartFile.getInputStream(),multipartFile.getSize()));
        if(putObjectResponse.sdkHttpResponse().isSuccessful()){
            return baseUrl +"/" + bucketName +"/"+ fileDirectory;
        }else{
            logger.info("上传文件失败....");
        }
        return null;
    }


    /**
     * 单笔上传
     * @param inputStream
     * @param directory 目录
     * @return
     * @throws Exception
     */
    public String upload(InputStream inputStream,long fileSize, String directory) throws Exception{
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(directory)
                .build();
        PutObjectResponse putObjectResponse = s3Client.putObject(objectRequest,
                RequestBody.fromInputStream(inputStream,fileSize));
        if(putObjectResponse.sdkHttpResponse().isSuccessful()){
            return baseUrl +"/" + bucketName +"/"+ directory;
        }else{
            logger.info("上传文件失败....");
        }
        return null;
    }


    public void batchUpload(Map<String, MultipartFile> fileMap, String directory){

    }


    private String createRandomFileName(String fileSuffix) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String fileName = localDateTime.format(fmt);
        Random random = new Random();
        int i = random.nextInt();
        fileName = fileName + i + fileSuffix;
        return fileName;
    }



}
