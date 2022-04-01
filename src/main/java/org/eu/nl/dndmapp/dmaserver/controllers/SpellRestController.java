package org.eu.nl.dndmapp.dmaserver.controllers;

import org.eu.nl.dndmapp.dmaserver.models.entities.Spell;
import org.eu.nl.dndmapp.dmaserver.services.SpellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/spell")
public class SpellRestController {
    private final SpellService spellService;

    @Autowired
    public SpellRestController(SpellService spellService) {
        this.spellService = spellService;
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
        Spell spellFoundById = spellService.getSpell(spellId);

        return ResponseEntity.ok(spellFoundById);
    }
}
