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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.InputStream;
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

//    @Autowired
//    private TokenStore tokenStore;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String requestUrl = exchange.getRequest().getPath().value();

        AntPathMatcher pathMatcher = new AntPathMatcher();
        //白名单放行
        for (String url : whitelist) {
            if (pathMatcher.match(url, requestUrl)) {
                return chain.filter(exchange);
            }
        }

        String token = getToken(exchange);

        if (!org.springframework.util.StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        token = token.substring(7);
        System.out.println(token);
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException(e);
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

        //检查token是否存在
  //      String token = getToken(exchange);
//        if (StringUtils.isBlank(token)) {
//            return buildReturnMono("没有认证", exchange);
//        }
        //判断是否是有效的token
//        OAuth2AccessToken oAuth2AccessToken;
//        try {
//            oAuth2AccessToken = tokenStore.readAccessToken(token);
//            boolean expired = oAuth2AccessToken.isExpired();
//            if (expired) {
//                return buildReturnMono("认证令牌已过期", exchange);
//            }
            return chain.filter(exchange);
//        } catch (InvalidTokenException e) {
//            log.info("认证令牌无效: {}", token);
//            return buildReturnMono("认证令牌无效", exchange);
//        }
    }

    /**
     * 获取token
     */
    private String getToken(ServerWebExchange exchange) {
        String tokenStr = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(tokenStr)) {
            return null;
        }
        String token = tokenStr.split(" ")[1];
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return token;
    }

//    private Mono<Void> buildReturnMono(String error, ServerWebExchange exchange) {
//        ServerHttpResponse response = exchange.getResponse();
//        String jsonString = JSON.toJSONString(new RestErrorResponse(error));
//        byte[] bits = jsonString.getBytes(StandardCharsets.UTF_8);
//        DataBuffer buffer = response.bufferFactory().wrap(bits);
//        response.setStatusCode(HttpStatus.UNAUTHORIZED);
//        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
//        return response.writeWith(Mono.just(buffer));
//    }

    @Override
    public int getOrder() {
        return 0;
    }
}
