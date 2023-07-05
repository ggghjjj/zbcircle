package com.zb.search.feignclient;

import com.zb.auth.common.model.RestResponse;
import com.zb.search.pojo.Blog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "zbcircle-post")
@RequestMapping("/post/blog")
public interface BlogServiceClient {


    @GetMapping("/{id}")
    public RestResponse<Blog> getBlogById(@PathVariable("id") Long id);

}
