package com.petadopt.service;

import com.petadopt.dto.ProfileUpdateRequest;
import com.petadopt.entity.User;

import java.util.List;

public interface UserService {

    User getById(Long id);

    List<User> listAllUsers();

    void updateProfile(Long userId, ProfileUpdateRequest request);

    void updateStatus(Long id, String status);
}
