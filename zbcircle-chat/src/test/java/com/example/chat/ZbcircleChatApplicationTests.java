package com.example.chat;

import com.zb.chat.ZbcircleChatApplication;
import com.zb.chat.feignclient.UserServiceClient;
import com.zb.chat.pojo.User;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = {ZbcircleChatApplication.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ZbcircleChatApplicationTests {

    @Autowired
    private UserServiceClient userServiceClient;
    @Test
    void contextLoads() {
        //User user = userServiceClient.getUser(12306L);
        User user1= userServiceClient.getUserByName("o5PB1sytMXJGGv2x5OBP2uMzCpS8");

      //  System.out.println(user);
        System.out.println(user1);
    }

    @Test
    void test(){
        System.out.println("hah");
    }

}
