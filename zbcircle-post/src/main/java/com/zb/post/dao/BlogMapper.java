package com.zb.post.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.post.pojo.Blog;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface BlogMapper extends BaseMapper<Blog> {
    public void increaseLike(Blog blog);
}
