package com.zb.auth.service;

import com.zb.auth.dto.LoginDTO;


public interface AuthService {

    String login(LoginDTO loginDto);
}
