package com.zb.post.feignclient;


import com.zb.post.pojo.User;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "zbcircle-auth")
@RequestMapping("/auth/user")
public interface UserServiceClient {

    @GetMapping("/userid/{id}")
    User getUser(@PathVariable("id") Long userId);

    @GetMapping("/username/{username}")
    User getUserByName(@PathVariable("username") String username);

}
