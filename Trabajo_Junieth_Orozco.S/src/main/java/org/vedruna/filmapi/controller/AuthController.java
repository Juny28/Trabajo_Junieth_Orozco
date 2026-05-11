package org.vedruna.filmapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vedruna.filmapi.dto.AuthResponseDto;
import org.vedruna.filmapi.dto.LoginDto;
import org.vedruna.filmapi.dto.UserRegistrationDto;
import org.vedruna.filmapi.service.AuthService;

/**
 * Controller for authentication endpoints.
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody UserRegistrationDto registrationDto) {
        authService.register(registrationDto);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }
}
