package com.zb.post.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("zb_sign")
public class Sign {
    private Long id;
    private Long userId;
    private Integer year;
    private Integer month;
    private LocalDateTime date;
    private boolean isBackup;
}
