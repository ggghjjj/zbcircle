package com.zb.auth.controller;

import com.zb.auth.common.model.RestResponse;
import com.zb.auth.dto.RegisterDTO;
import com.zb.auth.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "用户信息接口",tags = "用户信息接口")
@RestController
@RequestMapping("/auth")
public class UserController {


    @Autowired
    UserService userService;

    @ApiOperation("注册")
    @PostMapping("/register")
    public RestResponse register(@RequestBody RegisterDTO registerDTO) {
        userService.register(registerDTO);
        return RestResponse.success("注册成功");
    }
}
