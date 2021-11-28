package org.eu.nl.dndmapp.dmaserver.controllers;

import org.eu.nl.dndmapp.dmaserver.models.entities.Spell;
import org.eu.nl.dndmapp.dmaserver.services.SpellsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Page<Spell> getSpells(
        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
        @RequestParam(value = "name", required = false) String name
    ) {
        if (name != null) {
            return spellsService.querySpellsByNameLike("%" + name + "%", page);
        }

        return spellsService.getSpells(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Spell> getSpell(@PathVariable("id") String id) {
        UUID spellId = UUID.fromString(id);
        Spell spellFoundById = spellsService.getSpell(spellId);

        return ResponseEntity.ok(spellFoundById);
    }
}
