package com.zb.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.mall.pojo.VoucherOrder;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


public interface VoucherOrderMapper extends BaseMapper<VoucherOrder> {


    int deleteByPrimaryKey(Long id);

    int insert(VoucherOrder record);

    int insertSelective(VoucherOrder record);


    VoucherOrder selectByPrimaryKey(Long id);


    int updateByPrimaryKeySelective(VoucherOrder record);

    int updateByPrimaryKey(VoucherOrder record);
}