package com.petadopt.service.impl;

import com.petadopt.common.BusinessException;
import com.petadopt.common.ResultCode;
import com.petadopt.dto.ProfileUpdateRequest;
import com.petadopt.entity.User;
import com.petadopt.mapper.UserMapper;
import com.petadopt.service.UserService;
import com.petadopt.util.PasswordUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getById(Long id) {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "user not found");
        }
        user.setPassword(null);
        return user;
    }

    @Override
    public List<User> listAllUsers() {
        List<User> users = userMapper.findAll();
        users.forEach(item -> item.setPassword(null));
        return users;
    }

    @Override
    public void updateProfile(Long userId, ProfileUpdateRequest request) {
        User user = new User();
        user.setId(userId);
        user.setUsername(request.getUsername());
        user.setPhone(request.getPhone());
        if (StringUtils.hasText(request.getPassword())) {
            user.setPassword(PasswordUtil.md5(request.getPassword()));
        }
        userMapper.updateProfile(user);
    }

    @Override
    public void updateStatus(Long id, String status) {
        userMapper.updateStatus(id, status);
    }
}
