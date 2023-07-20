package com.zb.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.mall.pojo.Order;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.ast.Or;

public interface OrderMapper extends BaseMapper<Order> {


    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);



    Order selectByPrimaryKey(Long id);



    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}