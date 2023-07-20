package com.zb.mall.controller;

import com.zb.auth.common.model.RestResponse;
import com.zb.mall.pojo.Voucher;
import com.zb.mall.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 定时发布优惠券 增删改查
 */
@RestController
@RequestMapping("/mall/voucher")
public class VoucherController {


    @Resource
    private VoucherService voucherService;

    /**
     * 手动新增优惠券 不参与秒杀
     *
     * @param voucher 优惠券信息
     * @return 优惠券id
     */
    @PostMapping("/add/voucher")
    public RestResponse addVoucher(@RequestBody Voucher voucher) {
        voucherService.save(voucher);
        return RestResponse.success("添加成功");

    }

    @DeleteMapping("/delete/{id}")
    public RestResponse deleteVoucher(@PathVariable Long id) {
        voucherService.deleteVoucher(id);
        return RestResponse.success("删除成功");

    }
    /**
     * 新增秒杀券
     *
     * @param voucher 优惠券信息，包含秒杀信息
     * @return 优惠券id
     */
    @PostMapping("/add/seckill")
    public RestResponse addSeckillVoucher(@RequestBody Voucher voucher) {
        voucherService.addSeckillVoucher(voucher);
        return RestResponse.success("添加成功");
    }

}
