package org.vedruna.filmapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.vedruna.filmapi.dto.UserDto;
import org.vedruna.filmapi.dto.UsernamePatchDto;
import org.vedruna.filmapi.service.UserService;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Endpoints for user profile management")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public UserDto getProfile(@PathVariable String username) {
        return userService.getProfile(username);
    }

    @PatchMapping("/me/username")
    public void updateUsername(@Valid @RequestBody UsernamePatchDto patchDto, @AuthenticationPrincipal UserDetails userDetails) {
        userService.updateUsername(userDetails.getUsername(), patchDto);
    }
}
