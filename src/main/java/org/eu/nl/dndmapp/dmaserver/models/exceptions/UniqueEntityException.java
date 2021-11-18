package org.eu.nl.dndmapp.dmaserver.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UniqueEntityException extends RuntimeException {

    public UniqueEntityException(String message) {
        super(message);
    }
}
