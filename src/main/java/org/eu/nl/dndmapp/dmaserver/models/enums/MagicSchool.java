package org.eu.nl.dndmapp.dmaserver.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum MagicSchool {
    ABJURATION("Abjuration"),
    CONJURATION("Conjuration"),
    DIVINATION("Divination"),
    ENCHANTMENT("Enchantment"),
    EVOCATION("Evocation"),
    ILLUSION("Illusion"),
    NECROMANCY("Necromancy"),
    TRANSMUTATION("Transmutation");

    @JsonValue
    private final String name;

    /* CONSTRUCTORS */

    MagicSchool(String name) {
        this.name = name;
    }

    /* GETTERS & SETTERS */

    public String getName() {
        return this.name;
    }

    public static MagicSchool parse(String value) {
        if (value == null) return null;

        return Arrays.stream(MagicSchool.values())
            .filter(magicSchool -> magicSchool.name.equals(value))
            .findFirst()
            .orElse(null);
    }
}
