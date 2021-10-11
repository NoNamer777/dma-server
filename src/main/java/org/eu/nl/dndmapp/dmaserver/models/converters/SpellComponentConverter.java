package org.eu.nl.dndmapp.dmaserver.models.converters;

import org.eu.nl.dndmapp.dmaserver.models.enums.SpellComponent;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SpellComponentConverter implements AttributeConverter<SpellComponent, String> {

    @Override
    public SpellComponent convertToEntityAttribute(String databaseValue) {
        return SpellComponent.parse(databaseValue);
    }

    @Override
    public String convertToDatabaseColumn(SpellComponent spellComponent) {
        return spellComponent.getName();
    }
}
