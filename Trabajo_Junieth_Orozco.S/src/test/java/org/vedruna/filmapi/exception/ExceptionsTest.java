package org.vedruna.filmapi.exception;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ExceptionsTest {

    @Test
    void testTitleNotFoundException() {
        TitleNotFoundException ex = new TitleNotFoundException("123");
        assertEquals("Title '123' not found", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void testReviewNotFoundException() {
        ReviewNotFoundException ex = new ReviewNotFoundException(1L);
        assertEquals("Review '1' not found", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void testRoleNotFoundException() {
        RoleNotFoundException ex = new RoleNotFoundException("ADMIN");
        assertEquals("Role 'ADMIN' not found", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void testEmailAlreadyExistsException() {
        EmailAlreadyExistsException ex = new EmailAlreadyExistsException("test@test.com");
        assertEquals("Email 'test@test.com' already exists", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void testUsernameAlreadyExistsException() {
        UsernameAlreadyExistsException ex = new UsernameAlreadyExistsException("user");
        assertEquals("Username 'user' already exists", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }
}
