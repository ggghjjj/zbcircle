package com.zb.chat.service.impl;

import com.zb.chat.feignclient.UserServiceClient;
import com.zb.chat.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserServiceClient userServiceClient;
    @Override
    public User getUserBynName(String username) {
        User user = userServiceClient.getUserByName(username);

        return user;
    }
}
