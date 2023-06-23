package com.zb.post.utils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
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
        private Long id;
        private String sno;
        @ApiModelProperty(value = "用户名")
        private String username;
        @ApiModelProperty(value = "密码")
        private String password;
        @ApiModelProperty(value = "姓名")
        private String name;
        @ApiModelProperty(value = "昵称")
        private String nickname;
        @ApiModelProperty(value = "微信号")
        private String wxUnionid;
        @ApiModelProperty(value = "课程id")
        private String classId;
        /**
         * 头像
         */
        @ApiModelProperty(value = "投降")
        private String userpic;

        @ApiModelProperty(value = "生日")
        private LocalDateTime birthday;
        @ApiModelProperty(value = "性别")
        private String sex;
        @ApiModelProperty(value = "邮箱")
        private String email;
        @ApiModelProperty(value = "手机号")
        private String phone;
        @ApiModelProperty(value = "qq号")
        private String qq;

        /**
         * 用户状态
         */
        @ApiModelProperty(value = "用户状态")
        private String status;
        @ApiModelProperty(value = "创建时间")
        private LocalDateTime createTime;
        @ApiModelProperty(value = "更新时间")
        private LocalDateTime updateTime;

    }

}
