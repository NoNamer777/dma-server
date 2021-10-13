package org.eu.nl.dndmapp.dmaserver.services;

import org.eu.nl.dndmapp.dmaserver.models.entities.MaterialComponent;
import org.eu.nl.dndmapp.dmaserver.models.entities.Spell;
import org.eu.nl.dndmapp.dmaserver.models.entities.SpellDescription;
import org.eu.nl.dndmapp.dmaserver.models.enums.SpellComponent;
import org.eu.nl.dndmapp.dmaserver.models.exceptions.EntityNotFoundException;
import org.eu.nl.dndmapp.dmaserver.repositories.MaterialComponentRepository;
import org.eu.nl.dndmapp.dmaserver.repositories.SpellDescriptionRepository;
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

    private final MaterialComponentRepository materialComponentsRepo;

    private final SpellDescriptionRepository spellDescriptionsRepo;

    @Autowired
    public SpellsService(
        SpellRepository spellsRepo,
        MaterialComponentRepository materialComponentsRepo,
        SpellDescriptionRepository spellDescriptionRepository
    ) {
        this.spellsRepo = spellsRepo;
        this.materialComponentsRepo = materialComponentsRepo;
        this.spellDescriptionsRepo = spellDescriptionRepository;
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
            Spell spell = getSpell(id);
            List<MaterialComponent> materials = new ArrayList<>(spell.getMaterials());

            for (MaterialComponent material: materials) {
                spell.removeMaterial(material);
                materialComponentsRepo.save(material);
            }
            spellsRepo.deleteById(id);

        } catch (EntityNotFoundException exception) {
            throw new EntityNotFoundException(String.format("Could no delete Spell with ID: '%s' because it does not exist", id));
        }
    }

    public Spell saveSpell(Spell spell) {
        List<MaterialComponent> materials = new ArrayList<>(spell.getMaterials());
        List<SpellDescription> descriptions = new ArrayList<>(spell.getDescriptions());

        optionallyProvideMaterialsOfId(spell, materials);

        if (spell.getId() == null) {
            spell.removeAllDescriptions();

            spell = spellsRepo.save(spell);
            spell.addAllDescriptions(descriptions);
        }
        optionallyProvideDescriptionsOfId(spell, descriptions);

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
        if (newData.getMaterials() != null) {
            List<MaterialComponent> originalMaterials = new ArrayList<>(original.getMaterials());
            List<MaterialComponent> newMaterials = new ArrayList<>(newData.getMaterials());

            originalMaterials
                .stream()
                .filter(material -> !newMaterials.contains(material))
                .forEach(material -> {
                    original.removeMaterial(material);

                    materialComponentsRepo.save(material);
                });

            newMaterials
                .stream()
                .filter(material -> !originalMaterials.contains(material))
                .forEach(original::addMaterial);
        }
        if (newData.getDescriptions() != null) {
            List<SpellDescription> originalDescriptions = new ArrayList<>(original.getDescriptions());
            List<SpellDescription> newDescriptions = new ArrayList<>(newData.getDescriptions());

            originalDescriptions
                .stream()
                .filter(description -> !newDescriptions.contains(description))
                .forEach(spellDescription -> {
                    original.removeDescription(spellDescription);

                    spellDescriptionsRepo.delete(spellDescription);
                });

            newDescriptions
                .stream()
                .filter(description -> !originalDescriptions.contains(description))
                .forEach(original::addDescription);
        }
    }

    private void optionallyProvideMaterialsOfId(Spell spell, List<MaterialComponent> materials) {
        materials
            .stream()
            .filter(material -> material.getId() == null)
            .forEach(material -> {
                try {
                    MaterialComponent materialFoundByName = materialComponentsRepo
                        .findByName(material.getName())
                        .orElseThrow(() -> new EntityNotFoundException(
                            String.format("No Spell Material was found by name '%s'", material.getName())
                    ));

                    spell.removeMaterial(material);
                    spell.addMaterial(materialFoundByName);

                } catch (EntityNotFoundException exception) {

                    materialComponentsRepo.save(material);
                }
        });
    }

    private void optionallyProvideDescriptionsOfId(Spell spell, List<SpellDescription> descriptions) {
        descriptions
            .stream()
            .filter(description -> description.getId() == null)
            .forEach(description -> {
                try {
                    List<SpellDescription> descriptionsFoundBySpell = spellDescriptionsRepo
                        .findBySpellIdOrderByOrderAsc(description.getSpell().getId());

                    SpellDescription matchingDescription = descriptionsFoundBySpell
                        .stream()
                        .filter(spellDescription -> spellDescription.equals(description))
                        .findFirst()
                        .orElse(null);

                    if (descriptionsFoundBySpell.isEmpty() || matchingDescription == null) {
                        throw new EntityNotFoundException(String.format(
                            "Spell with ID: '%s' does not have the following description: %s",
                            description.getSpell().getId(),
                            description
                        ));
                    }
                    spell.removeDescription(description);
                    spell.addDescription(matchingDescription);

                } catch (EntityNotFoundException exception) {

                    spellDescriptionsRepo.save(description);
                }
        });
    }
}
