package com.zb.mall.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.zb.auth.common.model.RestResponse;
import com.zb.mall.service.VoucherOrderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 抢到优惠券的表
 */
@RestController
@RequestMapping("/mall/voucherorder")
public class VoucherOrderController {

    @Resource
    private VoucherOrderService voucherOrderService;

    @PostMapping("/seckill/{id}")
    public RestResponse seckillVoucher(@PathVariable("id") Long voucherId) {

        Boolean flag =  voucherOrderService.seckillVoucher(voucherId);
        String message = flag ? "成功": "失败";
        return RestResponse.success(message);
    }
}
