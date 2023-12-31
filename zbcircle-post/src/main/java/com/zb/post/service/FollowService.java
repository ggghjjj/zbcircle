package com.zb.post.service;

import com.zb.post.pojo.User;
import com.zb.post.utils.SecurityUtil;

import java.util.List;

public interface FollowService {
    String follow(Long followUserId, Boolean isFollow);

    Boolean isfollow(Long followUserId);

    List<User> followCommons(Long id);
}
