package com.zb.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.auth.common.constants.RedisConstants;
import com.zb.auth.common.exception.ZbException;
import com.zb.auth.dto.LoginDTO;
import com.zb.auth.pojo.User;
import com.zb.auth.service.AuthService;
import com.zb.auth.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
     AuthenticationManager authenticationManager;

    @Autowired
    StringRedisTemplate redisTemplate;
    @Override
    public String login(LoginDTO loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);  // 登录失败，会自动处理

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
        User user = loginUser.getUser();
        String jwt = JwtUtil.createJWT(user.getId().toString());
        redisTemplate.opsForValue().set(RedisConstants.LOGIN_TOKEN_KEY+user.getId(),jwt,RedisConstants.LOGIN_TOKEN_TTL, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(RedisConstants.LOGIN_USER_KEY+user.getId(), JSON.toJSONString(loginUser));
        return jwt;
    }

    @Override
    public void logout(Long id) {
        redisTemplate.delete(RedisConstants.LOGIN_TOKEN_KEY+id);
        redisTemplate.delete(RedisConstants.LOGIN_USER_KEY+id);
    }
}
