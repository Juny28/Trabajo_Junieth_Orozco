package org.vedruna.filmapi.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vedruna.filmapi.converter.UserConverter;
import org.vedruna.filmapi.dto.UserDto;
import org.vedruna.filmapi.dto.UsernamePatchDto;
import org.vedruna.filmapi.persistence.model.User;
import org.vedruna.filmapi.persistence.repository.UserRepository;
import org.vedruna.filmapi.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for user profile management.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    /**
     * Gets the profile of a user.
     * @param username the username
     * @return the UserDto
     */
    @Override
    public UserDto getProfile(String username) {
        log.info("Fetching profile for user: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User {} not found", username);
                    return new RuntimeException("User not found");
                });
        return userConverter.toDto(user);
    }

    /**
     * Updates the username of the current user.
     * @param currentUsername the current username
     * @param patchDto the new username data
     */
    @Override
    @Transactional
    public void updateUsername(String currentUsername, UsernamePatchDto patchDto) {
        log.info("User {} is changing username to {}", currentUsername, patchDto.getUsername());
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (userRepository.findByUsername(patchDto.getUsername()).isPresent()) {
            log.warn("Username {} is already taken", patchDto.getUsername());
            throw new RuntimeException("Username already taken");
        }

        user.setUsername(patchDto.getUsername());
        userRepository.save(user);
        log.info("Username updated successfully to {}", patchDto.getUsername());
    }
}
