package com.zb.mall.service.impl;

import com.xxl.job.core.handler.annotation.XxlJob;
import com.zb.auth.common.annotation.WebLog;
import com.zb.auth.common.constants.RedisConstants;
import com.zb.mall.dao.VoucherMapper;
import com.zb.mall.pojo.Voucher;
import com.zb.mall.service.VoucherService;
import com.zb.mall.utils.RedisIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherMapper voucherMapper;

    @Autowired
    private RedisIdWorker redisIdWorker;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void save(Voucher voucher) {
        voucherMapper.insert(voucher);
    }

    @Override
    public void deleteVoucher(Long id) {
        voucherMapper.deleteByPrimaryKey(id);
    }

    @XxlJob("addSeckill")
    @Override
    public void addSeckillVoucher() {
        LocalDateTime startTime = LocalDateTime.now(); // 可以根据实际情况获取抢购开始时间

        LocalDateTime endTime = startTime.plusDays(3);
        Voucher voucher = new Voucher().builder().stock(100).beginTime(startTime).endTime(endTime)
                .rules("任意商品,满100减10").title("每周限定秒杀优惠券").createTime(LocalDateTime.now()).updateTime(LocalDateTime.now())
                .payValue(1000L).actualValue(100L).id(redisIdWorker.nextId("seckill")).build();

        voucherMapper.insert(voucher);
        log.info("秒杀啊优惠券信息{}",voucher);
        System.out.println("保存成功");
        System.out.println(voucher.getStock());
        // 保存秒杀信息
        stringRedisTemplate.opsForValue().set(RedisConstants.SECKILL_STOCK_KEY + voucher.getId(),voucher.getStock().toString());
    }
}
