package org.vedruna.filmapi.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a role cannot be found in the database.
 * Results in a 500 INTERNAL SERVER ERROR because the system cannot function without roles.
 */
public class RoleNotFoundException extends FilmapiException {
    public RoleNotFoundException(String roleName) {
        super("Role not found: " + roleName, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
