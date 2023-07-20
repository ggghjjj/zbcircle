package com.zb.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.mall.pojo.Category;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategoryMapper extends BaseMapper<CategoryMapper> {

    int deleteByPrimaryKey(String id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}