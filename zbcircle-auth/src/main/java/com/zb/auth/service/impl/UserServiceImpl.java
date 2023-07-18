package com.zb.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zb.auth.common.exception.ZbException;
import com.zb.auth.common.utils.RegexpUtils;
import com.zb.auth.dao.UserInfoMapper;
import com.zb.auth.dao.UserMapper;
import com.zb.auth.dto.RegisterDTO;
import com.zb.auth.pojo.User;
import com.zb.auth.pojo.UserInfo;
import com.zb.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;
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

        UserInfo userInfo = new UserInfo().builder().userId(user.getId()).build();
        userInfoMapper.insert(userInfo);
        userMapper.insert(user);
    }

    @Override
    public User getUserById(Long userId) {
        User user = userMapper.selectById(userId);
        return user;
    }

    @Override
    public User getUserByName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        System.out.println(username);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public Boolean UpdateUserInfo(UserInfo userInfo) {
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id",userInfo.getUserId());
        updateWrapper.set("city",userInfo.getCity());
        updateWrapper.set("introduce",userInfo.getIntroduce());
        updateWrapper.set("fans",userInfo.getFans());
        updateWrapper.set("follow",userInfo.getFollow());
        log.info("userinfo={}",userInfo);
        userInfoMapper.update(userInfo,updateWrapper);
        return true;
    }

    @Override
    public Boolean UpdateUser(User user) {
        int count = userMapper.updateById(user);
        return count > 0 ? true : false;
    }

    @Override
    public Boolean DeleteUser(Long id) {
        User user = userMapper.selectById(id);
        if(user==null) {
            return false;
        }
        int count1 = userMapper.deleteById(id);
        int count2 = userInfoMapper.deleteById(id);

        return true;
    }

    @Override
    public User selectUser(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public UserInfo selectUserInfo(Long id) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",id);
        return userInfoMapper.selectOne(queryWrapper);
    }
}
