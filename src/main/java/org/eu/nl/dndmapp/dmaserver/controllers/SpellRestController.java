package org.eu.nl.dndmapp.dmaserver.controllers;

import org.eu.nl.dndmapp.dmaserver.models.EntityPageResponse;
import org.eu.nl.dndmapp.dmaserver.models.entities.Spell;
import org.eu.nl.dndmapp.dmaserver.services.SpellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.eu.nl.dndmapp.dmaserver.models.filters.EntityFilter.*;

@RestController
@RequestMapping("/api/spell")
public class SpellRestController {
    private final SpellService spellService;

    @Autowired
    public SpellRestController(SpellService spellService) {
        this.spellService = spellService;
    }

    @GetMapping
    public EntityPageResponse<Spell> getSpells(
        @RequestParam(name = FILTER_KEY_PAGE_NUMBER, required = false, defaultValue = "0") Integer pageNumber,
        @RequestParam(name = FILTER_KEY_PAGE_SIZE, required = false, defaultValue = "20") Integer pageSize,
        @RequestParam(name = FILTER_KEY_SORT_DIRECTION, required = false, defaultValue = "ASC") String sortDirection,
        @RequestParam(name = FILTER_KEY_SORT_PROPERTY, required = false, defaultValue = "name") String sortOnProperty,
        HttpServletRequest request
    ) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map<String, Object> filters = this.buildFiltersMap(request.getParameterMap());
        Page<Spell> results = filters.size() > 0
            ? spellService.getSpellsFiltered(filters, pageNumber, pageSize, sortDirection, sortOnProperty)
            : spellService.getSpells(pageNumber, pageSize, sortDirection, sortOnProperty);

        return new EntityPageResponse<>(
            (int) results.getTotalElements(),
            pageSize,
            results.getTotalPages(),
            results.getNumber() + 1,
            sortDirection,
            results.getContent()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Spell> getSpell(@PathVariable("id") String id) {
        UUID spellId = UUID.fromString(id);
        Spell spellFoundById = spellService.getSpell(spellId);

        return ResponseEntity.ok(spellFoundById);
    }

    private Map<String, Object> buildFiltersMap(Map<String, String[]> requestParameters) {
        Map<String, Object> filters = new HashMap<>();

        requestParameters.forEach((key, value) -> {
            switch (key) {
                case FILTER_KEY_NAME:
                case FILTER_KEY_CASTING_TIME:
                case FILTER_KEY_RANGE:
                case FILTER_KEY_DURATION:
                case FILTER_KEY_MAGIC_SCHOOL:
                    filters.put(key, value[0]);
                    break;
                case FILTER_KEY_RITUAL:
                case FILTER_KEY_CONCENTRATION:
                    filters.put(key, Boolean.valueOf(value[0]));
                    break;
                case FILTER_KEY_LEVEL:
                    filters.put(key, Integer.valueOf(value[0]));
                    break;
            }
        });

        return filters;
    }
}
