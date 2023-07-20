package com.zb.mall.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("zb_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long uid;
    private String orderSn;
    private Long vid;
    private String uname;
    private BigDecimal totalAmount;
    private BigDecimal payAmount;
    private BigDecimal couponAmount;
    private Byte payType;
    private Byte status;
    private String realName;
    private String realPhone;
    private String realAddress;
    private Byte confirmStatus;
    private Date payTime;
    private Date createTime;

}