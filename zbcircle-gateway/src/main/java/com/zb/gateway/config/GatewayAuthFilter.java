package com.zb.gateway.config;

import com.alibaba.fastjson.JSON;
import com.zb.auth.common.constants.RedisConstants;
import com.zb.auth.common.exception.ZbException;
import com.zb.gateway.feignclient.UserServiceClient;
import com.zb.gateway.pojo.User;
import com.zb.gateway.service.impl.UserDetailsImpl;
import com.zb.gateway.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.util.StringUtils;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class GatewayAuthFilter implements GlobalFilter, Ordered {
    //白名单

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserServiceClient userServiceClient;
    private static List<String> whitelist = null;

    static {
        //加载白名单
        try (
                InputStream resourceAsStream = GatewayAuthFilter.class.getResourceAsStream("/security-whitelist.properties")
        ) {
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            Set<String> strings = properties.stringPropertyNames();
            whitelist = new ArrayList<>(strings);
            System.out.println("ha");
        } catch (Exception e) {
            log.error("加载/security-whitelist.properties出错:{}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String requestUrl = exchange.getRequest().getPath().value();

        AntPathMatcher pathMatcher = new AntPathMatcher();
        System.out.println(getToken(exchange));

        //白名单放行
        for (String url : whitelist) {
            if (pathMatcher.match(url, requestUrl)) {
                System.out.println(url);
                System.out.println(requestUrl);
                return chain.filter(exchange);
            }
        }

        String token = getToken(exchange);

        if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            return buildReturnMono("未认证", exchange);
        }

        token = token.substring(7);
        System.out.println(token);
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            return buildReturnMono("认证错误", exchange);
        }
        User user = userServiceClient.getUser(Long.valueOf(userid));

        if (user == null) {
            return buildReturnMono("用户名未登陆", exchange);
        }
        String json = stringRedisTemplate.opsForValue().get(RedisConstants.LOGIN_USER_KEY+userid);
        System.out.println(json);
        UserDetailsImpl loginUser = JSON.parseObject(json, UserDetailsImpl.class);


        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());


        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String key = RedisConstants.LOGIN_TOKEN_KEY+user.getId();
        stringRedisTemplate.expire(key,RedisConstants.LOGIN_TOKEN_TTL, TimeUnit.MINUTES);
        //System.out.println(user);
        System.out.println("刷新成功");
        return chain.filter(exchange);
    }

    /**
     * 获取token
     */
    private String getToken(ServerWebExchange exchange) {
        String tokenStr = exchange.getRequest().getHeaders().getFirst("Authorization");

        return tokenStr;
    }

    private Mono<Void> buildReturnMono(String error, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        String jsonString = JSON.toJSONString(new RestErrorResponse(error));
        byte[] bits = jsonString.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
