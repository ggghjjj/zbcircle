package com.zb.post;

import com.spring4all.swagger.EnableSwagger2Doc;
import com.zb.post.feignclient.UserServiceClient;
import com.zb.post.pojo.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.zb.post.dao")
@EnableSwagger2Doc
@EnableDiscoveryClient
@EnableFeignClients
public class ZbcirclePostApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZbcirclePostApplication.class, args);
	}

}
