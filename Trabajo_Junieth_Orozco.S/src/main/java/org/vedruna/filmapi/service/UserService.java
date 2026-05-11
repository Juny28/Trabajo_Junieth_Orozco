package org.vedruna.filmapi.service;

import org.vedruna.filmapi.dto.UserDto;
import org.vedruna.filmapi.dto.UsernamePatchDto;

public interface UserService {
    UserDto getProfile(String username);
    void updateUsername(String currentUsername, UsernamePatchDto patchDto);
}
