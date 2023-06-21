package com.zb.auth.controller;

import com.zb.auth.service.PermissionService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth/perm")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/{id}")
    public List<String> getPermsById(@PathVariable("id") Long id) {
        return permissionService.getPermsById(id);
    }

}
