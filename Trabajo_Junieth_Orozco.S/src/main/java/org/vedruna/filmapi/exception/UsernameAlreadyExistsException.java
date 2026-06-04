package org.vedruna.filmapi.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a registration is attempted with a username that already exists.
 * Results in a 409 CONFLICT HTTP response.
 */
public class UsernameAlreadyExistsException extends FilmapiException {

    /**
     * Constructs a UsernameAlreadyExistsException for a given username.
     *
     * @param username the username that is already taken
     */
    public UsernameAlreadyExistsException(String username) {
        super("Username already exists: " + username, HttpStatus.CONFLICT);
    }
}
