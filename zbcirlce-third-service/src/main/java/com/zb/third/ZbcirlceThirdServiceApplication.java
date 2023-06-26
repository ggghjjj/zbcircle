package com.zb.third;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ZbcirlceThirdServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZbcirlceThirdServiceApplication.class, args);
    }

}
