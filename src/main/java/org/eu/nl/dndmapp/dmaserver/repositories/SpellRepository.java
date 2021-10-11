package org.eu.nl.dndmapp.dmaserver.repositories;

import org.eu.nl.dndmapp.dmaserver.models.entities.Spell;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpellRepository extends CrudRepository<Spell, UUID> {

    Page<Spell> findAll(Pageable pageable);

    Page<Spell> findByNameLikeIgnoreCase(String name, Pageable pageable);

    Optional<Spell> findByName(String name);
}
