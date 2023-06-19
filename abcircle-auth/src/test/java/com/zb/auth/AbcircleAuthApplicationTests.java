package com.zb.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zb.auth.dao.UserMapper;
import com.zb.auth.dao.ZbMenuMapper;
import com.zb.auth.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AbcircleAuthApplicationTests {

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
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username","o5PB1sytMXJGGv2x5OBP2uMzCpS8");
        //System.out.println(username);
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);

    }
}
