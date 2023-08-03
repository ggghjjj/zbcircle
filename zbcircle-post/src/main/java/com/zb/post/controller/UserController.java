package com.zb.post.controller;

import com.zb.auth.common.model.PageResult;
import com.zb.auth.common.model.RestResponse;
import com.zb.post.service.UserService;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post/follow")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign")
    public RestResponse sign() {
        userService.sign();
        return RestResponse.success("签到成功");

    }
}
