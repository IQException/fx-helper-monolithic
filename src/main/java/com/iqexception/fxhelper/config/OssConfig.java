package com.iqexception.fxhelper.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import jakarta.annotation.PreDestroy;

@Configuration
@PropertySource("classpath:ali-config.properties")
public class OssConfig {


    public static final String endpoint = "oss-cn-shanghai.aliyuncs.com";

    @Value("${ALIBABA_CLOUD_ACCESS_KEY_ID}")
    private String accessKeyId;

    @Value("${ALIBABA_CLOUD_ACCESS_KEY_SECRET}")
    private String accessKeySecret;

    private OSS ossClient;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    @Bean
    public OSS getOssClient() {
        ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        return ossClient;
    }

    @PreDestroy
    public void onDestroy() {
        ossClient.shutdown();
    }
}
