package com.zb.chat;

import com.spring4all.swagger.EnableSwagger2Doc;
import com.zb.chat.feignclient.UserServiceClient;
import com.zb.chat.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableSwagger2Doc
@EnableDiscoveryClient
@EnableFeignClients
public class ZbcircleChatApplication {


    public static void main(String[] args) {


        SpringApplication.run(ZbcircleChatApplication.class, args);
    }

}
