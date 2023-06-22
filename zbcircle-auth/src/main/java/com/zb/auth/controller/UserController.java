package com.zb.auth.controller;

import com.zb.auth.common.model.RestResponse;
import com.zb.auth.dto.RegisterDTO;
import com.zb.auth.pojo.User;
import com.zb.auth.pojo.UserInfo;
import com.zb.auth.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.management.LockInfo;

@Api(value = "用户信息接口",tags = "用户信息接口")
@RestController
@RequestMapping("/auth/user")
public class UserController {


    @Autowired
    UserService userService;

    @ApiOperation("注册")
    @PostMapping("/register")
    public RestResponse register(@RequestBody RegisterDTO registerDTO) {
        userService.register(registerDTO);
        return RestResponse.success("注册成功");
    }


    @GetMapping("/userid/{id}")
    public User getUserById(@PathVariable("id") Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/username/{username}")
    public User getUserByName(@PathVariable("username") String username) {
        return userService.getUserByName(username);
    }

    @PostMapping("/update/user")
    public Boolean updateUser(@RequestBody User user) {
        return userService.UpdateUser(user);
    }
    @PostMapping("/update/userInfo")
    public Boolean updateUserInfo(@RequestBody UserInfo userInfo) {
        return userService.UpdateUserInfo(userInfo);
    }

    @DeleteMapping("/delete/{id}")
    public Boolean Delete(@PathVariable("id") Long id) {
        return userService.DeleteUser(id);
    }

    @GetMapping("/get/user/{id}")
    public User SelectUser(@PathVariable("id") Long id) {
        return userService.selectUser(id);
    }

    @GetMapping("/get/userinfo/{id}")
    public UserInfo SelectUserInfo(@PathVariable("id") Long id) {
        return userService.selectUserInfo(id);
    }
}
