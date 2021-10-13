package org.eu.nl.dndmapp.dmaserver.repositories;

import org.eu.nl.dndmapp.dmaserver.models.entities.SpellDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpellDescriptionRepository extends JpaRepository<SpellDescription, UUID> {

    List<SpellDescription> findAllByOrderByOrderAsc();

    List<SpellDescription> findBySpellIdOrderByOrderAsc(UUID spellId);

    Optional<SpellDescription> findByIdAndSpellId(UUID descriptionId, UUID spellId);
}
