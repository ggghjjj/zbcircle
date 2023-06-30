package com.zb.gateway;

import com.zb.gateway.feignclient.PermsServiceClient;
import com.zb.gateway.feignclient.UserServiceClient;
import com.zb.gateway.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ZbcircleGatewayApplicationTests {

    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private PermsServiceClient permsServiceClient;
    @Test
    void contextLoads() {
        User user = userServiceClient.getUserByName("o5PB1sytMXJGGv2x5OBP2uMzCpS8");
        System.out.println(user);
        List<String> permsById = permsServiceClient.getPermsById(12306L);
        System.out.println(permsById);

    }

    @Test
    void test() {
        System.out.println("hah");
    }

}
