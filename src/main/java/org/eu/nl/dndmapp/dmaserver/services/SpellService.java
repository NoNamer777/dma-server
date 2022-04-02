package org.eu.nl.dndmapp.dmaserver.services;

import org.eu.nl.dndmapp.dmaserver.models.entities.Spell;
import org.eu.nl.dndmapp.dmaserver.models.entities.Spell.SpellBuilder;
import org.eu.nl.dndmapp.dmaserver.models.exceptions.EntityNotFoundException;
import org.eu.nl.dndmapp.dmaserver.models.filters.Filter;
import org.eu.nl.dndmapp.dmaserver.repositories.SpellRepository;
import org.eu.nl.dndmapp.dmaserver.utils.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.eu.nl.dndmapp.dmaserver.models.filters.EntityFilter.SPELL_FILTERS;


@Service
public class SpellService {
    private final SpellRepository spellsRepo;

    @Autowired
    public SpellService(SpellRepository spellsRepo) {
        this.spellsRepo = spellsRepo;
    }

    public Page<Spell> getSpells(Integer pageNumber, Integer pageSize, String sortDirection) {
        return spellsRepo.findAll(
            PageRequest.of(pageNumber, pageSize, Sort.Direction.fromString(sortDirection), "name")
        );
    }

    public Page<Spell> getSpellsFiltered(
        Map<String, Object> filters,
        Integer pageNumber,
        Integer pageSize,
        String sortDirection
    ) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        SpellBuilder spellBuilder = new SpellBuilder();
        ExampleMatcher matcher = ExampleMatcher.matching();

        List<String> ignoredPaths = new ArrayList<>(List.of("id", "components", "materials", "descriptions"));
        ignoredPaths.addAll(SPELL_FILTERS.stream().map(Filter::getRequestParam).collect(Collectors.toList()));

        for (Filter filter : SPELL_FILTERS) {
            if (!filters.containsKey(filter.getRequestParam())) continue;

            if (filter.isBuilderParamRequired()) {
                Executors.methodExecutor(spellBuilder, filter.getBuilderMethodName(), filters.get(filter.getRequestParam()));
            }
            else {
                Executors.methodExecutor(spellBuilder, filter.getBuilderMethodName());
            }
            matcher = matcher.withMatcher(filter.getProperty(), filter.getPropertyMatcher());
            ignoredPaths.remove(filter.getProperty());
        }
        Example<Spell> exampleSpell = Example.of(
            spellBuilder.build(),
            matcher
                .withIgnorePaths(ignoredPaths.toArray(String[]::new))
                .withIgnoreCase()
        );

        return spellsRepo.findAll(exampleSpell, PageRequest.of(pageNumber, pageSize, Sort.Direction.fromString(sortDirection), "name"));
    }

    public Spell getSpell(UUID id) {
        return spellsRepo
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format("No Spell was found by ID '%s'", id)));
    }
}
