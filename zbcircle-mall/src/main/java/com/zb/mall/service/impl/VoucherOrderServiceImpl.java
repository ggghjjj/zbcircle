package com.zb.mall.service.impl;

import com.zb.mall.service.VoucherOrderService;
import org.springframework.stereotype.Service;

@Service
public class VoucherOrderServiceImpl implements VoucherOrderService {
    @Override
    public Boolean seckillVoucher(Long voucherId) {
        // 已经提前把优惠券加入到 redis 中了 我们这里直接生成订单

        return null;
    }
}
