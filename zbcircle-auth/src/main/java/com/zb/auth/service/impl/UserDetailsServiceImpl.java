package com.zb.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zb.auth.common.exception.ZbException;
import com.zb.auth.dao.UserMapper;
import com.zb.auth.dao.ZbMenuMapper;
import com.zb.auth.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ZbMenuMapper menuMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        System.out.println(username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new ZbException("用户不存在");
        }

        return getUserPrincipal(user);
    }

    public UserDetails getUserPrincipal(User user) {
        List<String> permissions = menuMapper.selectPermsByUserId(user.getId());
        System.out.println(permissions);
        return new UserDetailsImpl(user,permissions);
    }
}
