package com.zb.third;

import com.aliyun.oss.*;
import com.aliyun.oss.model.GetObjectRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest
class ZbcirlceThirdServiceApplicationTests {
    @Resource
    OSSClient ossClient;
    @Test
    void contextLoads() throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("/home/gaohuijie/Pictures/picture/7035dc3f80ff17ecc1f2e362e90fb73f.png");
        ossClient.putObject("zbcircle","abc.jpg",inputStream);
        ossClient.shutdown();
        System.out.println("上传完成");

    }

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endpoint ;
    @Value("${spring.cloud.alicloud.oss.bucket}")
    private String bucketName;

    @Value("${spring.cloud.alicloud.access-key}")
    private String accessKeyId;
    @Value("${spring.cloud.alicloud.secret-key}")
    private String accessKeySecret ;
    @Test
    public void contextLoads1() throws FileNotFoundException {

        FileInputStream inputStream = new FileInputStream("/home/gaohuijie/Pictures/picture/7035dc3f80ff17ecc1f2e362e90fb73f.png");
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName,"a.jpg",inputStream);
        ossClient.shutdown();
    }

}
