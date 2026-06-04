package org.vedruna.filmapi.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

public class JwtUtilsTest {

    private JwtUtils jwtUtils;
    private String jwtSecret = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        ReflectionTestUtils.setField(jwtUtils, "secretKey", jwtSecret);
        jwtUtils.init(); // Initialize the key
    }

    @Test
    void generateToken_ShouldReturnValidToken() {
        UserDetails userDetails = new User("testuser", "password", Collections.emptyList());
        
        String token = jwtUtils.generateToken(userDetails);

        assertNotNull(token);
        assertEquals("testuser", jwtUtils.extractUsername(token));
        assertTrue(jwtUtils.validateToken(token, userDetails));
    }

    @Test
    void validateToken_ShouldReturnFalse_WhenUsernameDiffers() {
        UserDetails userDetails = new User("testuser", "password", Collections.emptyList());
        String token = jwtUtils.generateToken(userDetails);
        
        UserDetails otherUser = new User("other", "password", Collections.emptyList());
        assertFalse(jwtUtils.validateToken(token, otherUser));
    }
}
