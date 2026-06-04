package org.vedruna.filmapi.exception;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleFilmapiException_ShouldReturnProblemDetail() {
        UserNotFoundException ex = new UserNotFoundException("user"); // This is a concrete FilmapiException
        ProblemDetail result = handler.handleFilmapiException(ex);
        
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
        assertEquals("User 'user' not found", result.getDetail());
    }

    @Test
    void handleRuntimeException_ShouldReturnProblemDetail() {
        RuntimeException ex = new RuntimeException("Runtime error");
        ProblemDetail result = handler.handleRuntimeException(ex);
        
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
        assertEquals("Runtime error", result.getDetail());
    }

    @Test
    void handleGeneralException_ShouldReturnProblemDetail() {
        Exception ex = new Exception("General error");
        ProblemDetail result = handler.handleGeneralException(ex);
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatus());
    }
}
