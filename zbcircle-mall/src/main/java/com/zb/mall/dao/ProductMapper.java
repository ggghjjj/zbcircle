package com.zb.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.mall.pojo.Product;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductMapper extends BaseMapper<Product> {


    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);


    Product selectByPrimaryKey(Long id);


    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}