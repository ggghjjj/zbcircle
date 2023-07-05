package com.zb.post;

import com.zb.auth.common.constants.RedisConstants;
import com.zb.post.dao.FollowMapper;
import com.zb.post.pojo.Follow;
import com.zb.post.service.FollowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class ZbcirclePostApplicationTests {

	@Test
	void contextLoads() {
		Follow follow = new Follow().builder()
				.userId(5L).followUserId(15L).createTime(LocalDateTime.now()).build();
		System.out.println(follow);
	}

	@Autowired
	private FollowMapper followMapper;

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@Test
	void test() {
		List<Follow> follows = followMapper.selectList(null);
		System.out.println(follows);

		follows.stream().forEach(follow -> {
				String key  = RedisConstants.FOLLOW_KEY + follow.getUserId();

			stringRedisTemplate.opsForSet().add(key,follow.getFollowUserId().toString());


		});

	}
}
