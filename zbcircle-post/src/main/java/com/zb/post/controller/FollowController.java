package com.zb.post.controller;


import com.zb.auth.common.annotation.WebLog;
import com.zb.auth.common.model.RestResponse;
import com.zb.post.pojo.User;
import com.zb.post.service.FollowService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/post/follow")
public class FollowController {

    @Resource
    private FollowService followService;

    @PutMapping("/{id}/{isFollow}")
    @WebLog(description = "请求了关注和取消关注")
    public RestResponse follow(@PathVariable("id") Long followUserId, @PathVariable("isFollow") Boolean isFollow) {
        String message = followService.follow(followUserId,isFollow);
        return RestResponse.success(message);
    }

    @GetMapping("/or/not/{id}")
    @WebLog(description = "是否关注")
    public RestResponse isfollow(@PathVariable("id") Long followUserId) {

        Boolean bool = followService.isfollow(followUserId);
        return RestResponse.success(bool);
    }

    @GetMapping("/common/{id}")
    @WebLog(description = "共同关注")
    public RestResponse followCommons(@PathVariable("id") Long id) {
        List<User> list= followService.followCommons(id);
        return RestResponse.success(list);
    }
}
