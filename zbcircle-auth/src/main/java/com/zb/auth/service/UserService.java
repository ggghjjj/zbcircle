package com.zb.auth.service;

import com.zb.auth.dto.RegisterDTO;
import com.zb.auth.pojo.User;

public interface UserService {
    void register(RegisterDTO registerDTO);

    User getUserById(Long userId);

    User getUserByName(String username);
}
