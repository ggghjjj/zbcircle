package com.zb.chat.service.impl;

import com.zb.auth.common.exception.ZbException;
import com.zb.chat.feignclient.PermsServiceClient;
import com.zb.chat.feignclient.UserServiceClient;
import com.zb.chat.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private PermsServiceClient permsServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userServiceClient.getUserByName(username);
        if (user == null) {
            throw new ZbException("用户不存在");
        }
        return getUserPrincipal(user);
    }

    public UserDetails getUserPrincipal(User user) {
        List<String> permissions = permsServiceClient.getPermsById(user.getId());
        System.out.println(permissions);
        return new UserDetailsImpl(user,permissions);
    }
}
