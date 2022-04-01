package org.eu.nl.dndmapp.dmaserver.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DataMismatchException extends RuntimeException {
    public DataMismatchException(String message) {
        super(message);
    }

    public DataMismatchException(String messageFormat, Object... messageValues) {
        super(String.format(messageFormat, messageValues));
    }
}
