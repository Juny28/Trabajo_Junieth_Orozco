package org.vedruna.filmapi.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a user attempts to modify or delete a resource that does not belong to them.
 * Results in a 403 FORBIDDEN HTTP response.
 */
public class UnauthorizedActionException extends FilmapiException {

    /**
     * Constructs an UnauthorizedActionException with a descriptive message.
     *
     * @param action   the action that was attempted (e.g. "edit", "delete")
     * @param resource the resource type involved (e.g. "review")
     */
    public UnauthorizedActionException(String action, String resource) {
        super("You are not authorized to " + action + " this " + resource, HttpStatus.FORBIDDEN);
    }
}
