package org.eu.nl.dndmapp.dmaserver.repositories;

import org.eu.nl.dndmapp.dmaserver.models.entities.User;
import org.eu.nl.dndmapp.dmaserver.models.enums.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    Iterable<User> findAllByRolesContaining(UserRole role);
}
