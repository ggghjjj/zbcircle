package com.zb.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class ZbcircleGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZbcircleGatewayApplication.class, args);
    }

}
