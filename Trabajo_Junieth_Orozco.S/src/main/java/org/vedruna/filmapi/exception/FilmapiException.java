package org.vedruna.filmapi.exception;

import org.springframework.http.HttpStatus;

/**
 * Base exception for all application-specific errors in FilmAPI.
 * All custom exceptions must extend this class so they are captured
 * by the {@link GlobalExceptionHandler} and returned as ProblemDetail.
 */
public abstract class FilmapiException extends RuntimeException {

    /** The HTTP status that should be returned to the client. */
    private final HttpStatus status;

    /**
     * Constructs a new FilmapiException.
     *
     * @param message human-readable error description
     * @param status  HTTP status code to return
     */
    protected FilmapiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    /**
     * Returns the HTTP status associated with this exception.
     *
     * @return the {@link HttpStatus}
     */
    public HttpStatus getStatus() {
        return status;
    }
}
