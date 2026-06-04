package org.vedruna.filmapi.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a title cannot be found, either locally or in the Watchmode API.
 * Results in a 404 NOT FOUND HTTP response.
 */
public class TitleNotFoundException extends FilmapiException {

    /**
     * Constructs a TitleNotFoundException for a given Watchmode ID.
     *
     * @param watchmodeId the Watchmode ID of the title that was not found
     */
    public TitleNotFoundException(Integer watchmodeId) {
        super("Title not found with Watchmode ID: " + watchmodeId, HttpStatus.NOT_FOUND);
    }

    /**
     * Constructs a TitleNotFoundException with a custom message.
     *
     * @param message the error detail message
     */
    public TitleNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
