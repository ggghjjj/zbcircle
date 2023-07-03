package com.zb.search.controller;

import com.zb.auth.common.model.PageResult;
import com.zb.auth.common.model.RestResponse;
import com.zb.search.pojo.BlogDoc;
import com.zb.search.pojo.RequestBlogParams;
import com.zb.search.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping("/list")
    public RestResponse getList(@RequestBody RequestBlogParams requestParams) {
        PageResult pageResult = blogService.getList(requestParams);
        return RestResponse.success(pageResult);
    }

    @GetMapping("/suggestion")
    public RestResponse getSuggestions(@RequestParam("key") String pre) {
        System.out.println(pre);
        List<String> list = blogService.getSuggestions(pre);
        return RestResponse.success(list);
    }

}
