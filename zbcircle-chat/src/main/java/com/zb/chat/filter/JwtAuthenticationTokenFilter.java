package com.zb.chat.filter;


import com.alibaba.fastjson.JSON;
import com.zb.chat.feignclient.UserServiceClient;
import com.zb.chat.service.impl.UserDetailsImpl;
import com.zb.chat.utils.JwtUtil;
import com.zb.chat.pojo.User;
import com.zb.auth.common.constants.RedisConstants;
import com.zb.auth.common.exception.ZbException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserServiceClient userServiceClient;

    /**
     * 刷新token  获取用户存入上下文 获取用户权限
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = token.substring(7);
        System.out.println(token);
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            throw new ZbException("用户名未登录");
        }
        User user = userServiceClient.getUser(Long.valueOf(userid));

        if (user == null) {
            throw new ZbException("用户名未登录");
        }
        String json = stringRedisTemplate.opsForValue().get(RedisConstants.LOGIN_USER_KEY+userid);
        System.out.println(json);
        UserDetailsImpl loginUser = JSON.parseObject(json, UserDetailsImpl.class);


        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String key = RedisConstants.LOGIN_TOKEN_KEY+user.getId();
        stringRedisTemplate.expire(key,RedisConstants.LOGIN_TOKEN_TTL, TimeUnit.MINUTES);

        filterChain.doFilter(request, response);
    }
}