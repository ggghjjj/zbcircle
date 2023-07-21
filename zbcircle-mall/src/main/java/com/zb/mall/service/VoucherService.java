package com.zb.mall.service;

import com.zb.mall.pojo.Voucher;

public interface VoucherService {
    void save(Voucher voucher);

    void deleteVoucher(Long id);

    void addSeckillVoucher();
}
