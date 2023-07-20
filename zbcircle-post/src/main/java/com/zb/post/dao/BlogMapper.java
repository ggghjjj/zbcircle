package com.zb.post.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.post.pojo.Blog;


public interface BlogMapper extends BaseMapper<Blog> {
    public void increaseLike(Blog blog);
}
