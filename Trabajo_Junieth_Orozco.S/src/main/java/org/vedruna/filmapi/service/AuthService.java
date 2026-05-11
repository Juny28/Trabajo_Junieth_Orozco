package org.vedruna.filmapi.service;

import org.vedruna.filmapi.dto.AuthResponseDto;
import org.vedruna.filmapi.dto.LoginDto;
import org.vedruna.filmapi.dto.UserRegistrationDto;

public interface AuthService {
    void register(UserRegistrationDto registrationDto);
    AuthResponseDto login(LoginDto loginDto);
}
