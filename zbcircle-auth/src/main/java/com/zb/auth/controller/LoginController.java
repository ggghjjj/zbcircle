package com.zb.auth.controller;

import com.zb.auth.common.model.RestResponse;
import com.zb.auth.dto.LoginDTO;
import com.zb.auth.service.AuthService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
        String token = authService.login(loginDto);
        Map<String,String> result = new HashMap<>();
        result.put("token",token);
        result.put("username", loginDto.getUsername());
        return RestResponse.success(result);
    }


    @PostMapping("/logout/{id}")
    public RestResponse logout(@PathVariable Long id) {
        authService.logout(id);
        return RestResponse.success("退出成功");
    }


    @GetMapping("/r/r1")
    @PreAuthorize("hasAuthority('xc_teachmanager_course')")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", required = true),
    })
    public String r1() {
        return "访问r1权限";
    }

    @GetMapping("/r/r2")
    @PreAuthorize("hasAuthority('xc_teach')")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", required = true),
    })
    public String r2() {
        return "访问r2权限";
    }


}
