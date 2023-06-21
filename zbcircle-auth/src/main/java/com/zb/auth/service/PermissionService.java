package com.zb.auth.service;

import java.util.List;

public interface PermissionService {
    List<String> getPermsById(Long id);
}
