package com.zb.post;

import com.zb.post.pojo.Follow;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class ZbcirclePostApplicationTests {

	@Test
	void contextLoads() {
		Follow follow = new Follow().builder()
				.userId(5L).followUserId(15L).createTime(LocalDateTime.now()).build();
		System.out.println(follow);
	}

}
