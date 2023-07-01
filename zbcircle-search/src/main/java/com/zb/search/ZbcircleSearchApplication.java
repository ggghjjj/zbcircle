package com.zb.search;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSwagger2Doc
@EnableDiscoveryClient
public class ZbcircleSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZbcircleSearchApplication.class, args);
    }

}
