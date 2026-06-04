package org.vedruna.filmapi.exception;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ExceptionsTest {

    @Test
    void testTitleNotFoundException() {
        TitleNotFoundException ex = new TitleNotFoundException("123");
        assertEquals("123", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void testReviewNotFoundException() {
        ReviewNotFoundException ex = new ReviewNotFoundException(1L);
        assertEquals("Review not found with ID: 1", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void testRoleNotFoundException() {
        RoleNotFoundException ex = new RoleNotFoundException("ADMIN");
        assertEquals("Role not found: ADMIN", ex.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
    }

    @Test
    void testEmailAlreadyExistsException() {
        EmailAlreadyExistsException ex = new EmailAlreadyExistsException("test@test.com");
        assertEquals("Email already registered: test@test.com", ex.getMessage());
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
    }

    @Test
    void testUsernameAlreadyExistsException() {
        UsernameAlreadyExistsException ex = new UsernameAlreadyExistsException("user");
        assertEquals("Username already exists: user", ex.getMessage());
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
    }
}
