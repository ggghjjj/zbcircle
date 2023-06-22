package com.zb.post.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.zb.auth.common.model.PageParams;
import com.zb.auth.common.model.RestResponse;
import com.zb.post.pojo.Blog;
import com.zb.post.service.BlogService;
import com.zb.post.utils.SecurityUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/post/blog")
public class BlogController {

    @Resource
    private BlogService blogService;

    @PostMapping("/save")
    public RestResponse saveBlog(@RequestBody Blog blog) {
        blogService.saveBlog(blog);

        return RestResponse.success("保存成功");
    }

    @PutMapping("/like/{id}")
    public RestResponse likeBlog(@PathVariable("id") Long id) {
        blogService.likeBlog(id);
        return RestResponse.success("成功");
    }

    @PostMapping("/of/me")
    public RestResponse queryMyBlog(@RequestBody PageParams params) {
        // 获取登录用户

        List<Blog> blogs = blogService.queryMyBlog(params);
        return RestResponse.success(blogs);
    }

    @PostMapping("/hot")
    public RestResponse queryHotBlog(@RequestBody PageParams params) {
        List<Blog> blogs = blogService.queryMyBlog(params);
        return RestResponse.success(blogs);
    }

    @GetMapping("/{id}")
    public RestResponse queryBlog(@PathVariable("id") Long id) {
        Blog blog =  blogService.queryBlog(id);
        return RestResponse.success(blog);
    }


    @GetMapping("/of/user")
    public RestResponse queryBlogByUserId(
            @RequestBody PageParams params,
            @RequestParam("id") Long id) {
        // 根据用户查询
        Blog blog = blogService.queryBlogByUserId(params, id);
        return RestResponse.success(blog);
    }

}
