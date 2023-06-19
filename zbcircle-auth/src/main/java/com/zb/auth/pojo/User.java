package com.zb.auth.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zb.auth.dto.RegisterDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("zb_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "学号")
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
    @ApiModelProperty(value = "性别别")
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


    public static User of(RegisterDTO registerDTO) {
        User user = new User();
        BeanUtils.copyProperties(registerDTO,user);
        return user;
    }


}
