package com.zb.post.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.zb.auth.common.model.PageParams;
import com.zb.auth.common.model.PageResult;
import com.zb.auth.common.model.RestResponse;
import com.zb.post.pojo.Blog;
import com.zb.post.service.BlogService;
import com.zb.post.utils.SecurityUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;


@RestController
@RequestMapping("/post/blog")
@Slf4j
public class BlogController {

    @Resource
    private BlogService blogService;

    @PostMapping("/save")
    public RestResponse saveBlog(@RequestBody Blog blog) {
        blogService.saveBlog(blog);

        return RestResponse.success("保存成功");
    }

    @DeleteMapping("/delete/{id}")
    public RestResponse deleteBlog(@PathVariable(value = "id") Long id) {
        blogService.deleteBlog(id);
        return RestResponse.success("删除成功");
    }


    @PutMapping("/like/{id}")
    @ApiParam(value = "博客ID", example = "123")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", required = true),
    })
    public RestResponse likeBlog(@PathVariable("id") Long id) {
        blogService.likeBlog(id);
        return RestResponse.success("成功");
    }

    @PostMapping("/of/me")
    public RestResponse queryMyBlog(@RequestBody PageParams params) {
        // 获取登录用户
        log.info("获取用户");

        PageResult<Blog> blogs = blogService.queryMyBlog(params);
        return RestResponse.success(blogs);
    }

    @PostMapping("/hot")
    public RestResponse queryHotBlog(@RequestBody PageParams params) {
        PageResult<Blog> blogs = blogService.queryHotBlog(params);
        return RestResponse.success(blogs);
    }

    @GetMapping("/{id}")
    public RestResponse<Blog> queryBlog(@PathVariable("id") Long id) {
        log.info("查询id为{}",id);
        Blog blog =  blogService.queryBlog(id);

        return RestResponse.success(blog);
    }


    @GetMapping("/of/user")
    public RestResponse queryBlogByUserId(
            @RequestBody PageParams params,
            @RequestParam("id") Long id) {
        // 根据用户查询
        PageResult<Blog>  blogs = blogService.queryBlogByUserId(params, id);
        return RestResponse.success(blogs);
    }

}
