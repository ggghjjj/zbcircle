package com.zb.auth.service;

import com.zb.auth.dto.LoginDTO;

import java.util.Map;

public interface AuthService {

    String login(LoginDTO loginDto);
}
