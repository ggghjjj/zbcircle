package com.zb.auth.controller;

import com.zb.auth.dto.LoginDTO;
import com.zb.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.zb.auth.common.model.RestResponse;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public RestResponse login() {

        return RestResponse.success("aaa");
    }
    @PostMapping("/login")
    public RestResponse login(@RequestBody LoginDTO loginDto) {
        System.out.println(loginDto);
        String token = authService.login(loginDto);
        return RestResponse.success(token);
    }


    @RequestMapping("/r/r1")
    @PreAuthorize("hasAuthority(xc_teachmanager)")
    public String r1() {
        return "访问r1权限";
    }

    @RequestMapping("/r/r2")
    public String r2() {
        return "访问r2权限";
    }


}
