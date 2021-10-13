package org.eu.nl.dndmapp.dmaserver.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.eu.nl.dndmapp.dmaserver.models.RequestBodyExtractor;
import org.eu.nl.dndmapp.dmaserver.models.entities.Spell;
import org.eu.nl.dndmapp.dmaserver.models.enums.MagicSchool;
import org.eu.nl.dndmapp.dmaserver.models.enums.SpellComponent;
import org.eu.nl.dndmapp.dmaserver.models.exceptions.EntityMismatchException;
import org.eu.nl.dndmapp.dmaserver.models.exceptions.EntityNotFoundException;
import org.eu.nl.dndmapp.dmaserver.models.exceptions.UniqueEntityException;
import org.eu.nl.dndmapp.dmaserver.services.SpellsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
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
    public Page<Spell> getSpells(
        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
        @RequestParam(value = "query", required = false) String query
    ) {
        if (query != null) {
            return spellsService.querySpellsByNameLike("%" + query + "%", page);
        }

        return spellsService.getSpells(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Spell> getSpell(@PathVariable("id") String id) {
        UUID spellId = UUID.fromString(id);
        Spell spellFoundById = spellsService.getSpell(spellId);

        return ResponseEntity.ok(spellFoundById);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSpell(@PathVariable("id") String id) {
        UUID spellId = UUID.fromString(id);
        spellsService.deleteSpell(spellId);

        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Spell> saveSpell(@RequestBody ObjectNode data) {
        Spell requestSpell = null;
        try {
             requestSpell = extractSpell(data);

            if (requestSpell.getId() != null) throw new UniqueEntityException(String.format(
                "Cannot save Spell with ID: '%s' because IDs are generated.",
                requestSpell.getId()
            ));
            spellsService.getSpellByName(requestSpell.getName());

            throw new UniqueEntityException(String.format(
                "A Spell already exists with the name: '%s'.",
                requestSpell.getName()
            ));
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println(illegalArgumentException.getMessage());
            if (illegalArgumentException.getMessage() != null && illegalArgumentException.getMessage().contains("Invalid UUID string")) {
                throw new UniqueEntityException(String.format(
                    "Cannot save Spell with ID: '%s' because IDs are generated.",
                    RequestBodyExtractor.getText(data, "id")
                ));
            }

            throw illegalArgumentException;
        } catch (EntityNotFoundException exception) {/* Absorb exception */}

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

            if (!requestSpell.getId().equals(spellFoundById.getId())) throw new EntityMismatchException(String.format(
                "Cannot update Spell with ID: '%s' with data from Spell with ID: '%s'",
                requestSpell.getId(),
                id
            ));
            mergeSpells(spellFoundById, requestSpell);
            spellFoundById = spellsService.saveSpell(spellFoundById);

            return ResponseEntity.ok(spellFoundById);
        } catch (EntityNotFoundException exception) {
            throw new EntityNotFoundException(String.format(
                "Cannot update Spell with ID: '%s' because it does not exist.",
                id
            ));
        }
    }

    private void mergeSpells(Spell original, Spell newData) {
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

            for (SpellComponent component: originalComponents) {
                if (!newComponents.contains(component)) {
                    original.removeComponent(component);
                }
            }
            for (SpellComponent component: newComponents) {
                if (!originalComponents.contains(component)) {
                    original.addComponent(component);
                }
            }
        }
    }

    private Spell extractSpell(ObjectNode spellData) {
        String id = RequestBodyExtractor.getText(spellData, "id");
        Spell spell = new Spell(id);

        String name = RequestBodyExtractor.getText(spellData, "name");
        spell.setName(name);

        Integer level = RequestBodyExtractor.getInteger(spellData, "level");
        spell.setLevel(level);

        String magicSchoolData = RequestBodyExtractor.getText(spellData, "magicSchool");
        MagicSchool magicSchool = MagicSchool.parse(magicSchoolData);
        spell.setMagicSchool(magicSchool);

        Boolean ritual = RequestBodyExtractor.getBoolean(spellData, "ritual");
        spell.setRitual(ritual);

        String castingTime = RequestBodyExtractor.getText(spellData, "castingTime");
        spell.setCastingTime(castingTime);

        String range = RequestBodyExtractor.getText(spellData, "range");
        spell.setRange(range);

        Boolean concentration = RequestBodyExtractor.getBoolean(spellData, "concentration");
        spell.setConcentration(concentration);

        String duration = RequestBodyExtractor.getText(spellData, "duration");
        spell.setDuration(duration);

        ArrayNode spellComponentsData = RequestBodyExtractor.getList(spellData, "components");
        addSpellComponents(spell, spellComponentsData);

        return spell;
    }

    private void addSpellComponents(Spell spell, ArrayNode data) {
        if (data == null) return;

        for (JsonNode node: data) {
            String componentData = node.isTextual() ? node.asText() : null;
            SpellComponent component = SpellComponent.parse(componentData);

            if (component == null) continue;

            spell.addComponent(component);
        }
    }
}
