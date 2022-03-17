package org.eu.nl.dndmapp.dmaserver.models.converters;

import org.eu.nl.dndmapp.dmaserver.models.enums.UserRole;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UserRoleConverter implements AttributeConverter<UserRole, String> {
    @Override
    public UserRole convertToEntityAttribute(String databaseValue) {
        return UserRole.parse(databaseValue);
    }

    @Override
    public String convertToDatabaseColumn(UserRole role) {
        return role.getValue();
    }
}
