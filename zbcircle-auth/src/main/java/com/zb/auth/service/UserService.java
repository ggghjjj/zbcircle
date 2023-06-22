package com.zb.auth.service;

import com.zb.auth.dto.RegisterDTO;
import com.zb.auth.pojo.User;
import com.zb.auth.pojo.UserInfo;

public interface UserService {
    void register(RegisterDTO registerDTO);

    User getUserById(Long userId);

    User getUserByName(String username);

    Boolean UpdateUserInfo(UserInfo userInfo);

    Boolean UpdateUser(User user);

    Boolean DeleteUser(Long id);

    User selectUser(Long id);

    UserInfo selectUserInfo(Long id);
}
