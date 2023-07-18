package com.zb.post.controller;


import com.zb.auth.common.annotation.WebLog;
import com.zb.auth.common.model.PageParams;
import com.zb.auth.common.model.PageResult;
import com.zb.auth.common.model.RestResponse;
import com.zb.post.pojo.BlogComments;
import com.zb.post.service.BlogCommentsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author ghj
 * @since 2023-07-06
 * 需求: 1. 保存评论
 * 2.删除评论
 * 3.分页获取评论
 * 4.点赞评论
 * 5.打开该评论的子级评论
 */
@RestController
@RequestMapping("/post/comments")
@Slf4j
public class BlogCommentsController {

    @Resource
    private BlogCommentsService blogCommentsService;

    @PostMapping("/save")
    @WebLog(description = "发布评论")
    public RestResponse save(@RequestBody BlogComments blogComments) {
        blogCommentsService.save(blogComments);
        return RestResponse.success("保存成功");
    }

    /**
     * 先按照点赞排序，再时间倒叙查询
     * @param params
     * @return
     */
    @PostMapping("/list")
    @WebLog(description = "获取评论")
    public RestResponse getList(@RequestBody PageParams params) {
        PageResult<BlogComments> pageResult =  blogCommentsService.getList(params);
        return RestResponse.success(pageResult);
    }

    @DeleteMapping("/delete/{id}")
    @WebLog(description = "删除该评论")
    public RestResponse delete(@PathVariable(value = "id") Long id) {
        blogCommentsService.deleteBlog(id);
        return RestResponse.success("删除成功");
    }


    /**
     * 给帖子点赞和取消点赞
     * @param id
     * @return
     */
    @PutMapping("/like/{id}")
    @ApiParam(value = "博客ID", example = "123")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", required = true),
    })
    @WebLog(description = "给评论点赞")
    public RestResponse like(@PathVariable("id") Long id) {
        blogCommentsService.like(id);
        return RestResponse.success("成功");
    }

    @GetMapping("/{id}")
    @WebLog(description = "分页查询二级评论")
    public RestResponse queryBlogComments(@RequestBody PageParams params,@PathVariable("id") Long id) {
        log.info("查询id为{}",id);
        PageResult<BlogComments> BlogComments =  blogCommentsService.queryBlogComments(params,id);

        return RestResponse.success(BlogComments);
    }



}
