package com.zb.post;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.zb.post.dao")
@EnableSwagger2Doc
@EnableDiscoveryClient
public class ZbcirclePostApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZbcirclePostApplication.class, args);
	}

}
