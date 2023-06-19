package com.zb.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zb.auth.common.exception.ZbException;
import com.zb.auth.common.utils.RegexpUtils;
import com.zb.auth.dao.UserMapper;
import com.zb.auth.dto.RegisterDTO;
import com.zb.auth.pojo.User;
import com.zb.auth.service.UserService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void register(RegisterDTO registerDTO) {

        if(!RegexpUtils.isPhone(registerDTO.getPhone())) {
            throw new ZbException("手机号格式有误");
        }
        if(!RegexpUtils.isEmail(registerDTO.getEmail())) {
            throw new ZbException("邮箱格式有误");
        }
        String username = registerDTO.getUsername();
        String password = registerDTO.getPassword();
        String confirmedPassword = registerDTO.getConfirmPassword();

        if (username == null) {
            throw new ZbException("用户名不能为空");
        }
        if (password == null || confirmedPassword == null) {
            throw new ZbException("密码不能为空");
        }

        username = username.trim();
        if (username.length() == 0) {
            throw new ZbException("用户名不能为空");
        }

        if (password.length() == 0 || confirmedPassword.length() == 0) {
            throw new ZbException("密码不能为空");
        }

        if (username.length() > 100) {
            throw new ZbException("用户名长度不能大于100");
        }

        if (password.length() > 100 || confirmedPassword.length() > 100) {
            throw new ZbException("密码长度不能大于100");
        }

        if (!password.equals(confirmedPassword)) {
            throw new ZbException("两次输入的密码不一致");
        }

        User user = User.of(registerDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setId(Long.valueOf(RandomUtil.randomNumbers(8)));

        userMapper.insert(user);
    }
}
