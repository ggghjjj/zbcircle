package com.zb.post.service;

import com.zb.auth.common.model.PageParams;
import com.zb.auth.common.model.RestResponse;
import com.zb.post.pojo.Blog;

import java.util.List;

public interface BlogService {

    Boolean saveBlog(Blog blog);

    void likeBlog(Long id);

    List<Blog> queryMyBlog(PageParams params);

    Blog queryBlogByUserId(PageParams params, Long id);

    Blog queryBlog(Long id);
}
