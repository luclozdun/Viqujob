package com.viqujob.jobagapi.security.domain.service;

import com.viqujob.jobagapi.security.domain.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> getAllUsers(Pageable pageable);

    User getUserById(Long userId);

    User updateUser(Long userId, User userRequest);

    Boolean getByEmail(String email);
}
