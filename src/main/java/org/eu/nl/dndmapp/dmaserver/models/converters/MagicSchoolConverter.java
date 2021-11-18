package org.eu.nl.dndmapp.dmaserver.models.converters;

import org.eu.nl.dndmapp.dmaserver.models.enums.MagicSchool;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MagicSchoolConverter implements AttributeConverter<MagicSchool, String> {

    @Override
    public MagicSchool convertToEntityAttribute(String databaseValue) {
        return MagicSchool.parse(databaseValue);
    }

    @Override
    public String convertToDatabaseColumn(MagicSchool magicSchool) {
        return magicSchool.getName();
    }
}
