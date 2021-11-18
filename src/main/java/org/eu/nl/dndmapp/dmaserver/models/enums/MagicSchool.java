package org.eu.nl.dndmapp.dmaserver.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public static MagicSchool parse(String value) {
        if (value == null) return null;

        return Arrays.stream(MagicSchool.values())
            .filter(magicSchool -> magicSchool.getName().equals(value))
            .findFirst()
            .orElse(null);
    }
}
