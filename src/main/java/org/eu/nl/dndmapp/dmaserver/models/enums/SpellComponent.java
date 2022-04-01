package org.eu.nl.dndmapp.dmaserver.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum SpellComponent {
    VOCAL("Vocal"),
    SOMATIC("Somatic"),
    MATERIAL("Material");

    @JsonValue
    private final String name;

    /* CONSTRUCTORS */

    SpellComponent(String name) {
        this.name = name;
    }

    /* GETTERS & SETTERS */

    public String getName() {
        return this.name;
    }

    public static SpellComponent parse(String value) {
        if (value == null) return null;

        return Arrays.stream(SpellComponent.values())
            .filter(spellComponent -> spellComponent.name.equals(value))
            .findFirst()
            .orElse(null);
    }
}
