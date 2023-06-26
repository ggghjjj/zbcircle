package com.zb.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zb.auth.common.constants.RedisConstants;
import com.zb.post.dao.FollowMapper;
import com.zb.post.feignclient.UserServiceClient;
import com.zb.post.pojo.Follow;
import com.zb.post.service.FollowService;
import com.zb.post.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
     private FollowMapper followMapper;

    @Autowired
    private UserServiceClient userServiceClient;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    //关注和取消关注
    @Override
    public String follow(Long followUserId, Boolean isFollow) {
        SecurityUtil.User user = SecurityUtil.getUser();
        Long id = user.getId();

        if(isFollow) {
            QueryWrapper<Follow> queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_id",user.getId());
            queryWrapper.eq("follow_user_id",followUserId);
            int count = followMapper.delete(queryWrapper);

            if(count > 0) {
                stringRedisTemplate.opsForSet().remove(RedisConstants.FOLLOW_KEY+id,followUserId.toString());
            }
        }else {
            Follow follow = new Follow().builder()
                    .userId(id).followUserId(followUserId).createTime(LocalDateTime.now()).build();
            followMapper.insert(follow);
            stringRedisTemplate.opsForSet().add(RedisConstants.FOLLOW_KEY+id,followUserId.toString());
        }
        return null;
    }

    // 是否关注
    @Override
    public Boolean isfollow(Long followUserId) {
        SecurityUtil.User user = SecurityUtil.getUser();
        QueryWrapper<Follow> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",user.getId());
        queryWrapper.eq("follow_user_id",followUserId);

        Integer count = followMapper.selectCount(queryWrapper);

        return count > 0 ? false : true;

    }

    // 共同关注的人
    @Override
    public List<SecurityUtil.User> followCommons(Long id) {
        SecurityUtil.User user = SecurityUtil.getUser();
        Long userId = user.getId();

        Set<String> intersect = stringRedisTemplate.opsForSet().intersect(RedisConstants.FOLLOW_KEY + id, RedisConstants.FOLLOW_KEY + userId);

        if(intersect == null || intersect.isEmpty()) {
            return Collections.emptyList();
        }
        List<SecurityUtil.User> list = new ArrayList<>();
        intersect.forEach(ids->{
            SecurityUtil.User user1 = userServiceClient.getUser(Long.valueOf(ids));
            list.add(user1);
        });

        return list;
    }
}
