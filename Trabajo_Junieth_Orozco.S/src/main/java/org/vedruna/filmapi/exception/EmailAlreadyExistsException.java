package org.vedruna.filmapi.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a registration is attempted with an email that is already registered.
 * Results in a 409 CONFLICT HTTP response.
 */
public class EmailAlreadyExistsException extends FilmapiException {

    /**
     * Constructs an EmailAlreadyExistsException for a given email.
     *
     * @param email the email address that is already registered
     */
    public EmailAlreadyExistsException(String email) {
        super("Email already registered: " + email, HttpStatus.CONFLICT);
    }
}
