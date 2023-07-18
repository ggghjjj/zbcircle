package com.zb.mall.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "zbcircle-auth")
@RequestMapping("/auth/perm")
public interface PermsServiceClient {

    @GetMapping("/{id}")
    public List<String> getPermsById(@PathVariable("id") Long id);
}
