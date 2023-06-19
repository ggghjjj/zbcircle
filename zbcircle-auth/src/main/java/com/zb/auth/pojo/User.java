package com.zb.auth.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("zb_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    private String name;
    private String nickname;
    private String wxUnionid;
    private String classId;
    /**
     * 头像
     */
    private String userpic;


    private LocalDateTime birthday;

    private String sex;

    private String email;

    private String phone;

    private String qq;

    /**
     * 用户状态
     */
    private String status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;



}
