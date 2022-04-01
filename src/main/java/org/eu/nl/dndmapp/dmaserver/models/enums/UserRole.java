package org.eu.nl.dndmapp.dmaserver.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import org.eu.nl.dndmapp.dmaserver.models.exceptions.DataMismatchException;

import java.util.Arrays;

public enum UserRole {
    DUNGEON_MASTER("Dungeon Master"),
    ADMIN("Admin");

    @JsonValue
    private final String name;

    /* CONSTRUCTORS */

    UserRole(String name) {
        this.name = name;
    }

    /* GETTERS & SETTERS */

    public String getName() {
        return name;
    }

    public static UserRole parse(String value) throws DataMismatchException {
        if (value == null) throw new DataMismatchException("Cannot find UserRole with no value provided");

        return Arrays.stream(UserRole.values())
            .filter(role -> role.name.equals(value))
            .findFirst()
            .orElseThrow(() -> new DataMismatchException(String.format("Cannot parse UserRole with value '%s'", value)));
    }
}
