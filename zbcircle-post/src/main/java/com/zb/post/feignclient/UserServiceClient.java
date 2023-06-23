package com.zb.post.feignclient;


import com.zb.post.utils.SecurityUtil;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "zbcircle-auth")
@RequestMapping("/auth/user")
public interface UserServiceClient {

    @GetMapping("/userid/{id}")
    SecurityUtil.User getUser(@PathVariable("id") Long userId);

    @GetMapping("/username/{username}")
    SecurityUtil.User getUserByName(@PathVariable("username") String username);

}
