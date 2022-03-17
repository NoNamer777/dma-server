package org.eu.nl.dndmapp.dmaserver.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.eu.nl.dndmapp.dmaserver.models.exceptions.DataMismatchException;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UserRole {
    DUNGEON_MASTER("Dungeon Master"),
    ADMIN("Admin");

    @JsonValue
    private final String value;

    public static UserRole parse(String value) throws DataMismatchException {
        if (value == null) throw new DataMismatchException("Cannot find UserRole with no value provided");

        return Arrays.stream(UserRole.values())
            .filter(role -> role.getValue().equals(value))
            .findFirst()
            .orElseThrow(() -> new DataMismatchException(String.format("Cannot parse UserRole with value '%s'", value)));
    }
}
