package org.vedruna.filmapi.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a requested user cannot be found in the database.
 * Results in a 404 Not Found HTTP response.
 */
public class UserNotFoundException extends FilmapiException {

    /**
     * Constructs the exception for a given username.
     *
     * @param username the username that was not found
     */
    public UserNotFoundException(String username) {
        super("User not found: " + username, HttpStatus.NOT_FOUND);
    }
}
