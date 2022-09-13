package com.viqujob.jobagapi.security.service;

import com.viqujob.jobagapi.exception.ResourceNotFoundException;
import com.viqujob.jobagapi.security.domain.model.User;
import com.viqujob.jobagapi.security.domain.repository.UserRepository;
import com.viqujob.jobagapi.security.domain.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
    }

    @Override
    public User updateUser(Long userId, User userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return userRepository.save(
                user.setFirstname(userRequest.getFirstname())
                        .setLastname(userRequest.getLastname())
                        .setEmail(userRequest.getEmail())
                        .setNumber(userRequest.getNumber())
                        .setPassword(userRequest.getPassword())
                        .setDocument(userRequest.getDocument()));
    }

    @Override
    public Boolean getByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
