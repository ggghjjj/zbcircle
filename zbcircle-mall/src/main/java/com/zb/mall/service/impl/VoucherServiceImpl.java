package com.zb.mall.service.impl;

import com.zb.auth.common.constants.RedisConstants;
import com.zb.mall.dao.VoucherMapper;
import com.zb.mall.pojo.Voucher;
import com.zb.mall.service.VoucherOrderService;
import com.zb.mall.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherMapper voucherMapper;

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

    @Override
    public void addSeckillVoucher(Voucher voucher) {
        voucherMapper.insert(voucher);
        // 保存秒杀信息
        stringRedisTemplate.opsForValue().set(RedisConstants.SECKILL_STOCK_KEY + voucher.getId(),voucher.getStock().toString());
    }
}
