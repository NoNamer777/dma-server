package org.eu.nl.dndmapp.dmaserver.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntityMismatchException extends RuntimeException {

    public EntityMismatchException(String message) {
        super(message);
    }
}
