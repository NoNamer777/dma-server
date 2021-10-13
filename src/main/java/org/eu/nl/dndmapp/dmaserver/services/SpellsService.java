package org.eu.nl.dndmapp.dmaserver.services;

import org.eu.nl.dndmapp.dmaserver.models.entities.Spell;
import org.eu.nl.dndmapp.dmaserver.models.enums.SpellComponent;
import org.eu.nl.dndmapp.dmaserver.models.exceptions.EntityNotFoundException;
import org.eu.nl.dndmapp.dmaserver.repositories.SpellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SpellsService {

    private final SpellRepository spellsRepo;

    @Autowired
    public SpellsService(SpellRepository spellsRepo) {
        this.spellsRepo = spellsRepo;
    }

    public Page<Spell> getSpells(int page) {
        return spellsRepo.findAll(PageRequest.of(page, 20, Sort.Direction.ASC, "name"));
    }

    public Page<Spell> querySpellsByNameLike(String name, int page) {
        return spellsRepo.findByNameLikeIgnoreCase(name, PageRequest.of(page, 20, Sort.Direction.ASC, "name"));
    }

    public Spell getSpellByName(String name) {
        return spellsRepo
            .findByName(name)
            .orElseThrow(() -> new EntityNotFoundException(String.format("No Spell was found by name '%s'", name)));
    }

    public Spell getSpell(UUID id) {
        return spellsRepo
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format("No Spell was found by ID '%s'", id)));
    }

    public void deleteSpell(UUID id) {
        try {
            getSpell(id);
            spellsRepo.deleteById(id);

        } catch (EntityNotFoundException exception) {
            throw new EntityNotFoundException(String.format("Could no delete Spell with ID: '%s' because it does not exist", id));
        }
    }

    public Spell saveSpell(Spell spell) {
        return spellsRepo.save(spell);
    }

    public void mergeSpells(Spell original, Spell newData) {
        if (newData.getName() != null && !original.getName().equals(newData.getName())) {
            original.setName(newData.getName());
        }
        if (newData.getLevel() != null && !original.getLevel().equals(newData.getLevel())) {
            original.setLevel(newData.getLevel());
        }
        if (newData.getMagicSchool() != null && !original.getMagicSchool().equals(newData.getMagicSchool())) {
            original.setMagicSchool(newData.getMagicSchool());
        }
        if (newData.getRitual() != null && !original.getRitual().equals(newData.getRitual())) {
            original.setRitual(newData.getRitual());
        }
        if (newData.getCastingTime() != null && !original.getCastingTime().equals(newData.getCastingTime())) {
            original.setCastingTime(newData.getCastingTime());
        }
        if (newData.getRange() != null && !original.getRange().equals(newData.getRange())) {
            original.setRange(newData.getRange());
        }
        if (newData.getConcentration() != null && !original.getConcentration().equals(newData.getConcentration())) {
            original.setConcentration(newData.getConcentration());
        }
        if (newData.getDuration() != null && !original.getDuration().equals(newData.getDuration())) {
            original.setDuration(newData.getDuration());
        }
        if (newData.getComponents() != null) {
            List<SpellComponent> originalComponents = new ArrayList<>(original.getComponents());
            List<SpellComponent> newComponents = new ArrayList<>(newData.getComponents());

            originalComponents
                    .stream()
                    .filter(component -> !newComponents.contains(component))
                    .forEach(original::removeComponent);

            newComponents
                    .stream()
                    .filter(component -> !originalComponents.contains(component))
                    .forEach(original::addComponent);
        }
    }
}
