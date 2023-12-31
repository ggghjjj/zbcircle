package com.zb.gateway;

import com.zb.gateway.feignclient.UserServiceClient;
import com.zb.gateway.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ZbcircleGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZbcircleGatewayApplication.class, args);

    }

}
