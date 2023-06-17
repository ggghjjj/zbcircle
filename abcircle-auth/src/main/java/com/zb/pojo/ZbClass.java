package com.zb.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("zb_class")
public class ZbClass implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String cname;
    private String intro;

    private Long collegeId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
