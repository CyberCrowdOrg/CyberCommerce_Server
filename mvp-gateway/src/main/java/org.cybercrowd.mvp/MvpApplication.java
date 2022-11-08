package org.cybercrowd.mvp;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;

@SpringBootApplication
public class MvpApplication {

    private static final Logger logger = LoggerFactory.getLogger(MvpApplication.class);

//    private static S3Client s3;

    public static void main(String[] args) {
        SpringApplication.run(MvpApplication.class,args);
        logger.info("...........MvpApplication Run...........");
    }

    @Value("${aws.s3.access_key}")
    private String accessKey;
    @Value("${aws.s3.secret_key}")
    private String secretKey;

    /**
     * 初始化 亚马逊S3云存储客户端
     * @return
     */
    @Bean
    public S3Client initS3Client(){
        AwsCredentialsProvider awsCredentialsProvider = AwsCredentialsProviderChain.builder().
                addCredentialsProvider(new AwsCredentialsProvider() {
                    @Override
                    public AwsCredentials resolveCredentials() {
                        return AwsBasicCredentials
                                .create(accessKey,secretKey);
                    }
                }).build();

        return S3Client.builder().credentialsProvider(awsCredentialsProvider)
                .region(Region.AP_SOUTHEAST_1)
                .build();
    }

}