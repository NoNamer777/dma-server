package org.eu.nl.dndmapp.dmaserver.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.eu.nl.dndmapp.dmaserver.models.NamedEntity;
import org.eu.nl.dndmapp.dmaserver.models.converters.MagicSchoolConverter;
import org.eu.nl.dndmapp.dmaserver.models.converters.SpellComponentConverter;
import org.eu.nl.dndmapp.dmaserver.models.enums.MagicSchool;
import org.eu.nl.dndmapp.dmaserver.models.enums.SpellComponent;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "`spell`")
public class Spell extends NamedEntity {
    @Column(name = "`level`", columnDefinition = "TINYINT NOT NULL DEFAULT 0")
    private Integer level;

    @Column(name = "`magic_school`", columnDefinition = "VARCHAR(64) NOT NULL")
    @Convert(converter = MagicSchoolConverter.class)
    private MagicSchool magicSchool;

    @Column(name = "`ritual`", columnDefinition = "TINYINT NOT NULL DEFAULT 0")
    private Boolean ritual;

    @Column(name = "`casting_time`", columnDefinition = "VARCHAR(16) NOT NULL")
    private String castingTime;

    @Column(name = "`range`", columnDefinition = "VARCHAR(16) NOT NULL")
    private String range;

    @Column(name = "`concentration`", columnDefinition = "TINYINT NOT NULL DEFAULT 0")
    private Boolean concentration;

    @Column(name = "`duration`", columnDefinition = "VARCHAR(16) NOT NULL")
    private String duration;

    @ElementCollection(fetch = FetchType.EAGER)
    @Convert(converter = SpellComponentConverter.class)
    @Column(name = "`component`", columnDefinition = "VARCHAR(16)")
    @JoinTable(
        name = "`spell_component`",
        joinColumns = @JoinColumn(name = "`spell_id`", columnDefinition = "VARCHAR(64)")
    )
    private final Set<SpellComponent> components = new HashSet<>();;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id.spell")
    private final Set<SpellMaterial> materials = new HashSet<>();;

    @JsonManagedReference
    @OneToMany(mappedBy = "spell", fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<SpellDescription> descriptions = new HashSet<>();

    /* CONSTRUCTORS */

    public Spell() {
        super();
    }

    public Spell(String id, String name) {
        super(id, name);
    }

    public Spell(
        String id,
        String name,
        Integer level,
        MagicSchool magicSchool,
        Boolean ritual,
        String castingTime,
        String range,
        Boolean concentration,
        String duration,
        List<SpellComponent> components,
        List<SpellMaterial> materials,
        List<SpellDescription> descriptions
    ) {
        super(id, name);

        this.level = level;
        this.magicSchool = magicSchool;
        this.ritual = ritual;
        this.castingTime = castingTime;
        this.range = range;
        this.concentration = concentration;
        this.duration = duration;

        addAllComponents(components);
        addAllMaterials(materials);
        addAllDescriptions(descriptions);
    }

    /* GETTERS & SETTERS */

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public MagicSchool getMagicSchool() {
        return magicSchool;
    }

    public void setMagicSchool(MagicSchool magicSchool) {
        this.magicSchool = magicSchool;
    }

    public Boolean getRitual() {
        return ritual;
    }

    public void setRitual(Boolean ritual) {
        this.ritual = ritual;
    }

    public String getCastingTime() {
        return castingTime;
    }

    public void setCastingTime(String castingTime) {
        this.castingTime = castingTime;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public Boolean getConcentration() {
        return concentration;
    }

    public void setConcentration(Boolean concentration) {
        this.concentration = concentration;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void addAllComponents(List<SpellComponent> components) {
        components.forEach(this::addComponent);
    }

    public void addComponent(SpellComponent component) {
        components.add(component);
    }

    public void removeAllComponents() {
        Set<SpellComponent> oldComponents = new HashSet<>(this.components);

        oldComponents.forEach(this::removeComponent);
    }

    public void removeComponent(SpellComponent component) {
        components.remove(component);

        if (component == SpellComponent.MATERIAL) {
            removeAllMaterials();
        }
    }

    public void addAllMaterials(List<SpellMaterial> materials) {
        materials.forEach(this::addMaterial);
    }

    public void addMaterial(SpellMaterial material) {
        materials.add(material);

        material.setSpell(this);
    }

    public void removeAllMaterials() {
        List<SpellMaterial> materials = new ArrayList<>(this.materials);

        materials.forEach(this::removeMaterial);
    }

    public void removeMaterial(SpellMaterial material) {
        materials.remove(material);
    }

    public void addAllDescriptions(List<SpellDescription> descriptions) {
        descriptions.forEach(this::addDescription);
    }

    public void addDescription(SpellDescription description) {
        descriptions.add(description);

        description.setSpell(this);
    }

    public void removeAllDescriptions() {
        List<SpellDescription> spellDescriptions = new ArrayList<>(descriptions);

        spellDescriptions.forEach(this::removeDescription);
    }

    public void removeDescription(SpellDescription description) {
        descriptions.remove(description);

        description.setSpell(null);
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return String.format(
            "Spell{ id: '%s', name: '%s', level: '%d', magicSchool: '%s' }",
            this.getId(),
            this.getName(),
            this.level,
            this.magicSchool == null ? null : this.magicSchool.getName()
        );
    }
    }
}
