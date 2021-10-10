package org.eu.nl.dndmapp.dmaserver.services;

import org.eu.nl.dndmapp.dmaserver.models.entities.Spell;
import org.eu.nl.dndmapp.dmaserver.repositories.DmaEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SpellsService {

    private final DmaEntityRepository<Spell> spellsRepo;

    @Autowired
    public SpellsService(DmaEntityRepository<Spell> spellsRepo) {
        this.spellsRepo = spellsRepo;
    }

    public List<Spell> getSpells() {
        return spellsRepo.findAll();
    }

    public Spell getSpellByName(String name) {
        List<Spell> foundSpells = spellsRepo.findAllByQuery("find_spell_by_name", name);

        return foundSpells.isEmpty() ? null : foundSpells.get(0);
    }

    public Spell getSpell(UUID id) {
        return spellsRepo.findById(id);
    }

    public boolean deleteSpell(UUID id) {
        return spellsRepo.delete(id);
    }

    public Spell saveSpell(Spell spell) {
        return spellsRepo.save(spell);
    }
}
