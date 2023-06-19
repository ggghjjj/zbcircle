package com.zb.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.auth.pojo.ZbMenu;

import java.util.List;

public interface ZbMenuMapper extends BaseMapper<ZbMenu> {
    List<String> selectPermsByUserId(Long userid);
}
