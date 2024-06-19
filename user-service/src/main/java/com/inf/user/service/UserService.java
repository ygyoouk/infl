package com.inf.user.service;

import com.inf.user.dto.UserDto;
import com.inf.user.jpa.UserEntity;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserById(String userId);
    Iterable<UserEntity> getUserByAll();
}
