package com.zb.auth.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author itcast
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("zb_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String roleName;

    private String roleCode;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
