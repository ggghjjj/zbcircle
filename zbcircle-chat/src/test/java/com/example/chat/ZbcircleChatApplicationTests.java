package com.example.chat;

import com.zb.chat.ZbcircleChatApplication;
import com.zb.chat.feignclient.UserServiceClient;
import com.zb.chat.pojo.Record;
import com.zb.chat.pojo.User;
import com.zb.chat.service.RecordService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;




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
    @Autowired
    private RestTemplate restTemplate;

    @Test
    void test(){

        String getUserByUserName = "http://127.0.0.1:8090/auth/user/username/";

        User user = restTemplate.getForObject(getUserByUserName + "o5PB1sytMXJGGv2x5OBP2uMzCpS8", User.class);
        System.out.println(user);
    }
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    RecordService recordService;

    @Test
    void test1(){
        Record record = new Record().builder().fromName("o5PB1sytMXJGGv2x5OBP2uMzCpS8")
                .toName("123456")
                .fromAvatar("https://thirdwx.qlogo.cn/mmopen/vi_32/P8h3wxWDqCqfI0BmHxAYHEzppsaBp4xt7nscyiaQ2ZptEhzyKcEia7loT6pO6zExaM9FR7BnSguxqKYuIe5B3aEA/132")
                .time(dateFormat.format(new Date()))
                .content("aaa").build();
//        System.out.println(record);

        Boolean insert = recordService.insert(record);
        System.out.println(insert);

    }

    @Test
    void test2(){
        System.out.println("ha");
    }
}
