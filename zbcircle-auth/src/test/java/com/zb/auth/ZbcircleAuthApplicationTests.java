package com.zb.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zb.auth.dao.UserMapper;
import com.zb.auth.dao.ZbMenuMapper;
import com.zb.auth.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class ZbcircleAuthApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ZbMenuMapper zbMenuMapper;
    @Test
    void contextLoads() {
        User user = userMapper.selectById(12306);
        System.out.println(user);
    }

    @Test
    public void testSelectPermsByUserId() {
        List<String> list = zbMenuMapper.selectPermsByUserId(12306L);
        System.out.println(list);
    }
    @Test
    public void testUser() {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username","o5PB1sytMXJGGv2x5OBP2uMzCpS8");
//        //System.out.println(username);
//        User user = userMapper.selectOne(queryWrapper);
//        System.out.println(user);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String a = bCryptPasswordEncoder.encode("o5PB1sytMXJGGv2x5OBP2uMzCpS8");
        System.out.println(a);
        boolean bool = bCryptPasswordEncoder.matches("o5PB1sytMXJGGv2x5OBP2uMzCpS8", "$2a$10$5KFuoRrkqbJ542.4nq4ajeActFcBmlsg9pcyms2ycYxmpnlVszIpm");
        System.out.println(bool);
    }

    @Test
    public void testDateTime() {
        System.out.println(LocalDateTime.now());
    }

}
