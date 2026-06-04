package org.vedruna.filmapi.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vedruna.filmapi.converter.UserConverter;
import org.vedruna.filmapi.dto.UserDto;
import org.vedruna.filmapi.dto.UsernamePatchDto;
import org.vedruna.filmapi.persistence.model.User;
import org.vedruna.filmapi.persistence.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getProfile_ShouldReturnUserDto_WhenUserExists() {
        User user = new User();
        user.setUsername("testuser");
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userConverter.toDto(user)).thenReturn(userDto);

        UserDto result = userService.getProfile("testuser");

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void getProfile_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getProfile("nonexistent"));
    }

    @Test
    void updateUsername_ShouldUpdateUsername_WhenNewUsernameIsAvailable() {
        User user = new User();
        user.setUsername("oldname");
        UsernamePatchDto patchDto = new UsernamePatchDto();
        patchDto.setUsername("newname");

        when(userRepository.findByUsername("oldname")).thenReturn(Optional.of(user));
        when(userRepository.findByUsername("newname")).thenReturn(Optional.empty());

        userService.updateUsername("oldname", patchDto);

        assertEquals("newname", user.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUsername_ShouldThrowException_WhenNewUsernameIsTaken() {
        User user = new User();
        user.setUsername("oldname");
        UsernamePatchDto patchDto = new UsernamePatchDto();
        patchDto.setUsername("takenname");

        when(userRepository.findByUsername("oldname")).thenReturn(Optional.of(user));
        when(userRepository.findByUsername("takenname")).thenReturn(Optional.of(new User()));

        assertThrows(RuntimeException.class, () -> userService.updateUsername("oldname", patchDto));
    }
}
