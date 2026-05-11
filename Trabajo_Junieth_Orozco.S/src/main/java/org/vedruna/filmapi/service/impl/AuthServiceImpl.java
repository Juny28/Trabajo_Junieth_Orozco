package org.vedruna.filmapi.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vedruna.filmapi.dto.AuthResponseDto;
import org.vedruna.filmapi.dto.LoginDto;
import org.vedruna.filmapi.dto.UserRegistrationDto;
import org.vedruna.filmapi.persistence.model.Rol;
import org.vedruna.filmapi.persistence.model.User;
import org.vedruna.filmapi.persistence.repository.RolRepository;
import org.vedruna.filmapi.persistence.repository.UserRepository;
import org.vedruna.filmapi.security.JwtUtils;
import org.vedruna.filmapi.security.UserDetailsServiceImpl;
import org.vedruna.filmapi.service.AuthService;

import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for authentication and registration.
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(UserRepository userRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Registers a new user.
     * @param registrationDto the registration data
     */
    @Override
    public void register(UserRegistrationDto registrationDto) {
        log.info("Attempting to register user: {}", registrationDto.getUsername());
        if (userRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            log.warn("Username {} already exists", registrationDto.getUsername());
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            log.warn("Email {} already exists", registrationDto.getEmail());
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        
        Rol userRole = rolRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        user.setRole(userRole);

        userRepository.save(user);
        log.info("User {} registered successfully", registrationDto.getUsername());
    }

    /**
     * Authenticates a user and returns a JWT token.
     * @param loginDto the login data
     * @return the AuthResponseDto containing the token
     */
    @Override
    public AuthResponseDto login(LoginDto loginDto) {
        log.info("Attempting login for user: {}", loginDto.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getUsername());
        final String jwt = jwtUtils.generateToken(userDetails);
        log.info("User {} authenticated successfully", loginDto.getUsername());
        return new AuthResponseDto(jwt);
    }
}
