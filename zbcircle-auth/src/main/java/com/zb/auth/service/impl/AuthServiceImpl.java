package com.zb.auth.service.impl;

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
        redisTemplate.opsForValue().set(RedisConstants.LOGIN_USER_KEY+user.getId(),jwt,RedisConstants.LOGIN_USER_TTL);
        System.out.println(jwt);
        return jwt;
    }

    @Override
    public String logout(Long id) {
        redisTemplate.delete(RedisConstants.LOGIN_USER_KEY+id);
        if (!redisTemplate.delete(RedisConstants.LOGIN_USER_KEY+id)) {
            throw new ZbException("改用户未登陆");
        }

        return null;
    }
}
