package com.zb.post.utils;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Mr.M
 * @version 1.0
 * @description 用户身份信息获取工具类
 * @date 2022/10/20 11:41
 */
@Slf4j
public class SecurityUtil {

    public static User getUser() {
        //拿jwt中的用户身份
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof String){
            String jsonString = (String) principal;
            User user = null;
            try {
                user = JSON.parseObject(jsonString, User.class);
            } catch (Exception e) {
                log.debug("解析jwt中的用户身份无法转成XcUser对象:{}",jsonString);
            }
            return user;

        }
        return null;
    }

    @Data
    public static class User implements Serializable {

        private static final long serialVersionUID = 1L;

        private String id;

        private String username;

        private String password;

        private String salt;

        private String name;
        private String nickname;
        private String wxUnionid;
        private String companyId;
        /**
         * 头像
         */
        private String userpic;

        private String utype;

        private LocalDateTime birthday;

        private String sex;

        private String email;

        private String cellphone;

        private String qq;

        /**
         * 用户状态
         */
        private String status;

        private LocalDateTime createTime;

        private LocalDateTime updateTime;
    }

}
