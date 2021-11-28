package org.eu.nl.dndmapp.dmaserver.repositories;

import org.eu.nl.dndmapp.dmaserver.models.entities.Material;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface MaterialComponentRepository extends CrudRepository<Material, UUID> {

    Optional<Material> findByDescription(String description);
}
