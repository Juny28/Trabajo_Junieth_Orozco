package org.vedruna.filmapi.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a review cannot be found in the database.
 * Results in a 404 NOT FOUND HTTP response.
 */
public class ReviewNotFoundException extends FilmapiException {

    /**
     * Constructs a ReviewNotFoundException for a given review ID.
     *
     * @param id the ID of the review that was not found
     */
    public ReviewNotFoundException(Long id) {
        super("Review not found with ID: " + id, HttpStatus.NOT_FOUND);
    }
}
