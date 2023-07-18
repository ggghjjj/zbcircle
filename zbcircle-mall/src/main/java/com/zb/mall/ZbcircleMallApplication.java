package com.zb.mall;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.zb.mall.dao")
@EnableSwagger2Doc
@EnableDiscoveryClient
@EnableFeignClients
public class ZbcircleMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZbcircleMallApplication.class, args);
	}

}
