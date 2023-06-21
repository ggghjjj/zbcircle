package com.zb.auth.service.impl;

import com.zb.auth.dao.ZbMenuMapper;
import com.zb.auth.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private ZbMenuMapper menuMapper;
    @Override
    public List<String> getPermsById(Long id) {
        List<String> list = menuMapper.selectPermsByUserId(id);
        return list;
    }
}
