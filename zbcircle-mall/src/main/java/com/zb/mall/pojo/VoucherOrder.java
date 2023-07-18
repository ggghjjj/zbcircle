package com.zb.mall.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("zb_voucher_order")
public class VoucherOrder {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long voucherId;
    private Byte status;
    private Date createTime;
    private Date useTime;
    private Date updateTime;

}
