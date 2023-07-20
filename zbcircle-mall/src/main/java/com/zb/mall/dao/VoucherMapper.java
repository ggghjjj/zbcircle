package com.zb.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.mall.pojo.Voucher;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VoucherMapper extends BaseMapper<Voucher> {


    int deleteByPrimaryKey(Long id);

    int insert(Voucher record);

    int insertSelective(Voucher record);


    Voucher selectByPrimaryKey(Long id);


    int updateByPrimaryKeySelective(Voucher record);

    int updateByPrimaryKey(Voucher record);
}