package org.eu.nl.dndmapp.dmaserver.services;

import org.eu.nl.dndmapp.dmaserver.models.entities.Spell;
import org.eu.nl.dndmapp.dmaserver.models.exceptions.EntityNotFoundException;
import org.eu.nl.dndmapp.dmaserver.repositories.SpellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class SpellService {
    private final SpellRepository spellsRepo;

    @Autowired
    public SpellService(SpellRepository spellsRepo) {
        this.spellsRepo = spellsRepo;
    }

    public Page<Spell> getSpells(int page) {
        return spellsRepo.findAll(PageRequest.of(page, 20, Sort.Direction.ASC, "name"));
    }

    public Page<Spell> querySpellsByNameLike(String name, int page) {
        return spellsRepo.findByNameLikeIgnoreCase(name, PageRequest.of(page, 20, Sort.Direction.ASC, "name"));
    }

    public Spell getSpell(UUID id) {
        return spellsRepo
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format("No Spell was found by ID '%s'", id)));
    }
}
