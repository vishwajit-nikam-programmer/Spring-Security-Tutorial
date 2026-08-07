package com.security.code.service;

import com.security.code.entities.UserEntity;

public interface UserService {
    UserEntity getUserFromUsername(String username);
}
