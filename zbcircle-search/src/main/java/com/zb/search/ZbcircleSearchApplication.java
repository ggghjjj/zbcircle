package com.zb.search;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableSwagger2Doc
@EnableDiscoveryClient
@EnableFeignClients
public class ZbcircleSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZbcircleSearchApplication.class, args);
    }

    @Bean
    RestHighLevelClient setUp() {
        return new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.43.155:9200")
        ));
    }
}
