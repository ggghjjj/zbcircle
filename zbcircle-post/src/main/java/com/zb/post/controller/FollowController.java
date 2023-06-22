package com.zb.post.controller;


import com.zb.auth.common.model.RestResponse;
import com.zb.post.service.FollowService;
import com.zb.post.utils.SecurityUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/post/follow")
public class FollowController {

    @Resource
    private FollowService followService;

    @PutMapping("/{id}/{isFollow}")
    public RestResponse follow(@PathVariable("id") Long followUserId, @PathVariable("isFollow") Boolean isFollow) {
        String message = followService.follow(followUserId,isFollow);
        return RestResponse.success(message);
    }

    @GetMapping("/or/not/{id}")
    public RestResponse isfollow(@PathVariable("id") Long followUserId) {

        Boolean bool = followService.isfollow(followUserId);
        return RestResponse.success(bool);
    }

    @GetMapping("/common/{id}")
    public RestResponse followCommons(@PathVariable("id") Long id) {
        List<SecurityUtil.User> list= followService.followCommons(id);
        return RestResponse.success(list);
    }
}
