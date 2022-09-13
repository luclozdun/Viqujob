package com.viqujob.jobagapi.security.controller;

import javax.validation.Valid;

import com.viqujob.jobagapi.security.domain.model.User;
import com.viqujob.jobagapi.security.domain.service.UserService;
import com.viqujob.jobagapi.security.resource.SaveUserResource;
import com.viqujob.jobagapi.security.resource.UserResource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Get Users", description = "Get All Users")
    @GetMapping
    public Page<UserResource> getAllUsers(Pageable pageable) {
        Page<User> userPage = userService.getAllUsers(pageable);
        List<UserResource> resources = userPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Update Users", description = "Update Users")
    @PutMapping("/{userId}")
    public UserResource updateUser(@PathVariable Long userId, @Valid @RequestBody SaveUserResource resource) {
        User user = convertToEntity(resource);
        return convertToResource(userService.updateUser(userId, user));
    }

    @Operation(summary = "Get UsersById", description = "Get UsersById")
    @GetMapping("/{userId}")
    public UserResource getUserById(@PathVariable Long userId) {
        return convertToResource(userService.getUserById(userId));
    }

    private User convertToEntity(SaveUserResource resource) {
        return mapper.map(resource, User.class);
    }

    private UserResource convertToResource(User entity) {
        return mapper.map(entity, UserResource.class);
    }

}
