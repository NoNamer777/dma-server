package org.eu.nl.dndmapp.dmaserver.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SpellComponent {
    VOCAL("Vocal"),
    SOMATIC("Somatic"),
    MATERIAL("Material");

    @JsonValue
    private final String name;

    public static SpellComponent parse(String value) {
        if (value == null) return null;

        return Arrays.stream(SpellComponent.values())
            .filter(spellComponent -> spellComponent.getName().equals(value))
            .findFirst()
            .orElse(null);
    }
}
