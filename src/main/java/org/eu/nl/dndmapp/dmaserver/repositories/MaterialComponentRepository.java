package org.eu.nl.dndmapp.dmaserver.repositories;

import org.eu.nl.dndmapp.dmaserver.models.entities.MaterialComponent;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface MaterialComponentRepository extends CrudRepository<MaterialComponent, UUID> {

    Optional<MaterialComponent> findByName(String name);
}
