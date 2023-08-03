package com.zb.post.service.impl;

import com.zb.post.dao.SignMapper;
import com.zb.post.pojo.Sign;
import com.zb.post.service.UserService;
import com.zb.post.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.zb.auth.common.constants.RedisConstants.USER_SIGN_KEY;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SignMapper signMapper;
    @Override
    public void sign() {

        Long userId = SecurityUtil.getUser().getId();

        // 2. 获取日期
        LocalDateTime nowDateTime = LocalDateTime.now();
        String formatTime = nowDateTime.format(DateTimeFormatter.ofPattern(":yyyyMM"));

        // 3. 拼接 Key
        String key = USER_SIGN_KEY + userId + formatTime;

        // 4. 获取今天是本月的第几天（1～31，BitMap 则为 0～30）
        int dayOfMonth = nowDateTime.getDayOfMonth();

        Sign sign = Sign.builder().month(nowDateTime.getMonthValue()).year(nowDateTime.getYear()).date(nowDateTime).userId(userId).isBackup(true).build();
        signMapper.insert(sign);


        // 5. 写入 Redis  SETBIT key offset 1
        stringRedisTemplate.opsForValue().setBit(key, dayOfMonth - 1, true);

    }
}
