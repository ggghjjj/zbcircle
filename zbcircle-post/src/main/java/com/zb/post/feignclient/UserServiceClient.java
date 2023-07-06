package com.zb.post.feignclient;



import com.zb.post.pojo.User;

import com.zb.post.pojo.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "zbcircle-auth")
@RequestMapping("/auth/user")
public interface UserServiceClient {

    @GetMapping("/userid/{id}")
    User getUser(@PathVariable("id") Long userId);

    @GetMapping("/username/{username}")
    User getUserByName(@PathVariable("username") String username);

    @GetMapping("/get/userinfo/{id}")
    UserInfo getUserInfo(@PathVariable("id") Long id);

    @PostMapping("/update/userInfo")
    Boolean updateUserInfo(@RequestBody UserInfo userInfo);

}
