package com.zb.post.service.impl;

import com.zb.auth.common.model.PageParams;
import com.zb.auth.common.model.RestResponse;
import com.zb.post.pojo.Blog;
import com.zb.post.service.BlogService;

import java.util.List;

public class BlogServiceImpl implements BlogService {
    @Override
    public Boolean saveBlog(Blog blog) {
        return null;
    }

    @Override
    public void likeBlog(Long id) {

    }

    @Override
    public List<Blog> queryMyBlog(PageParams params) {
        return null;
    }

    @Override
    public Blog queryBlogByUserId(PageParams params, Long id) {
        return null;
    }

    @Override
    public Blog queryBlog(Long id) {
        return null;
    }
}
