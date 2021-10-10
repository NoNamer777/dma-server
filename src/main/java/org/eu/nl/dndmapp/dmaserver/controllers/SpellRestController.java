package org.eu.nl.dndmapp.dmaserver.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.eu.nl.dndmapp.dmaserver.models.RequestBodyExtractor;
import org.eu.nl.dndmapp.dmaserver.models.entities.Spell;
import org.eu.nl.dndmapp.dmaserver.models.exceptions.EntityMismatchException;
import org.eu.nl.dndmapp.dmaserver.models.exceptions.EntityNotFoundException;
import org.eu.nl.dndmapp.dmaserver.models.exceptions.UniqueEntityException;
import org.eu.nl.dndmapp.dmaserver.services.SpellsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/spell")
public class SpellRestController {

    private final SpellsService spellsService;

    @Autowired
    public SpellRestController(SpellsService spellsService) {
        this.spellsService = spellsService;
    }

    @GetMapping
    public List<Spell> getSpells() {
        return spellsService.getSpells();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Spell> getSpell(@PathVariable("id") String id) {
        UUID spellId = UUID.fromString(id);
        Spell spellFoundById = spellsService.getSpell(spellId);

        return ResponseEntity.ok(spellFoundById);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteSpell(@PathVariable("id") String id) {
        UUID spellId = UUID.fromString(id);

        boolean isSpellDeleted = spellsService.deleteSpell(spellId);

        return ResponseEntity.ok(isSpellDeleted);
    }

    @PostMapping
    public ResponseEntity<Spell> saveSpell(@RequestBody ObjectNode data) {
        Spell requestSpell = extractSpell(data);
        Spell spellFoundByName = spellsService.getSpellByName(requestSpell.getName());

        if (spellFoundByName != null) {
            throw new UniqueEntityException(String.format("A Spell already exists with the name: '%s'", requestSpell.getName()));
        }
        requestSpell = spellsService.saveSpell(requestSpell);

        URI spellLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(requestSpell.getId())
                .toUri();

        return ResponseEntity.created(spellLocation).body(requestSpell);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Spell> updateSpell(@PathVariable("id") String id, @RequestBody ObjectNode data) {
        try {
            Spell requestSpell = extractSpell(data);
            Spell spellFoundById = spellsService.getSpell(UUID.fromString(id));

            if (!requestSpell.getId().equals(spellFoundById.getId())) {
                throw new EntityMismatchException(String.format("Cannot update Spell with ID: '%s' with data from Spell with ID: '%s'", requestSpell.getId(), id));
            }
            mergeSpells(spellFoundById, requestSpell);
            spellFoundById = spellsService.saveSpell(spellFoundById);

            return ResponseEntity.ok(spellFoundById);
        } catch (EntityNotFoundException exception) {

            throw new EntityNotFoundException(String.format("Cannot update Spell with ID: '%s' because it does not exist.", id));
        }
    }

    private void mergeSpells(Spell original, Spell newData) {
        if (newData.getName() != null && !original.getName().equals(newData.getName())) {
            original.setName(newData.getName());
        }
        if (newData.getLevel() != null && !original.getLevel().equals(newData.getLevel())) {
            original.setLevel(newData.getLevel());
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
        if (newData.getDuration() != null && !original.getDuration().equals(newData.getDuration())) {
            original.setDuration(newData.getDuration());
        }
    }

    private Spell extractSpell(ObjectNode spellData) {
        String id = RequestBodyExtractor.getText(spellData, "id");
        String name = RequestBodyExtractor.getText(spellData, "name");
        Integer level = RequestBodyExtractor.getInteger(spellData, "level");
        Boolean ritual = RequestBodyExtractor.getBoolean(spellData, "ritual");
        String castingTime = RequestBodyExtractor.getText(spellData, "castingTime");
        String range = RequestBodyExtractor.getText(spellData, "range");
        Boolean concentration = RequestBodyExtractor.getBoolean(spellData, "concentration");
        String duration = RequestBodyExtractor.getText(spellData, "duration");

        Spell spell = new Spell(id);

        spell.setName(name);
        spell.setLevel(level);
        spell.setRitual(ritual);
        spell.setCastingTime(castingTime);
        spell.setRange(range);
        spell.setConcentration(concentration);
        spell.setDuration(duration);

        return spell;
    }
}
