package com.zb.post.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.zb.auth.common.annotation.WebLog;
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


/**
 * @author ghj
 * @since 2023-06-28
 */
@RestController
@RequestMapping("/post/blog")
@Slf4j
public class BlogController {

    @Resource
    private BlogService blogService;

    @PostMapping("/save")
    @WebLog(description = "发布帖子")
    public RestResponse saveBlog(@RequestBody Blog blog) {
        blogService.saveBlog(blog);

        return RestResponse.success("保存成功");
    }

    /**
     * 按照时间倒叙查询
     * @param params
     * @return
     */
    @PostMapping("/list")
    @WebLog(description = "获取所有发布的帖子")
    public RestResponse getList(@RequestBody PageParams params) {
       PageResult<Blog> pageResult =  blogService.getList(params);
       return RestResponse.success(pageResult);
    }

    @DeleteMapping("/delete/{id}")
    @WebLog(description = "删除帖子")
    public RestResponse deleteBlog(@PathVariable(value = "id") Long id) {
        blogService.deleteBlog(id);
        return RestResponse.success("删除成功");
    }


    /**
     * 给帖子点赞和取消点赞
     * @param id
     * @return
     */
    @PutMapping("/like/{id}")
    @ApiParam(value = "帖子ID", example = "123")
    @WebLog(description = "点赞帖子")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", required = true),
    })
    public RestResponse likeBlog(@PathVariable("id") Long id) {
        blogService.likeBlog(id);
        return RestResponse.success("成功");
    }

    /**
     * 查询自己发布的帖子
     * @param params
     * @return
     */
    @PostMapping("/of/me")
    @WebLog(description = "查询我发布的帖子")
    public RestResponse queryMyBlog(@RequestBody PageParams params) {
        // 获取登录用户
        log.info("获取用户");

        PageResult<Blog> blogs = blogService.queryMyBlog(params);
        return RestResponse.success(blogs);
    }

    /**
     * 查询点赞最多的帖子
     * @param params
     * @return
     */
    @PostMapping("/hot")
    @WebLog(description = "查询热点帖子")
    public RestResponse queryHotBlog(@RequestBody PageParams params) {
        PageResult<Blog> blogs = blogService.queryHotBlog(params);
        return RestResponse.success(blogs);
    }

    @GetMapping("/{id}")
    @WebLog(description = "查询一个帖子详情")
    public RestResponse<Blog> queryBlog(@PathVariable("id") Long id) {
        log.info("查询id为{}",id);
        Blog blog =  blogService.queryBlog(id);

        return RestResponse.success(blog);
    }


    @GetMapping("/of/user")
    @WebLog(description = "查询该用户的发布的帖子")
    public RestResponse queryBlogByUserId(
            @RequestBody PageParams params,
            @RequestParam("id") Long id) {
        // 根据用户查询
        PageResult<Blog>  blogs = blogService.queryBlogByUserId(params, id);
        return RestResponse.success(blogs);
    }

}
