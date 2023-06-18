package com.zb.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zb.auth.dao")
public class AbcircleAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbcircleAuthApplication.class, args);
    }

}
