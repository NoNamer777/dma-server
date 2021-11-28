package org.eu.nl.dndmapp.dmaserver.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "`spell`")
public class Spell extends NamedEntity {

    @Column(name = "`level`", columnDefinition = "TINYINT NOT NULL DEFAULT 0")
    private Integer level = 0;

    @Column(name = "`magic_school`", columnDefinition = "VARCHAR(64) NOT NULL")
    @Convert(converter = MagicSchoolConverter.class)
    private MagicSchool magicSchool;

    @Column(name = "`ritual`", columnDefinition = "TINYINT NOT NULL DEFAULT 0")
    private Boolean ritual = false;

    @Column(name = "`casting_time`", columnDefinition = "VARCHAR(16) NOT NULL")
    private String castingTime;

    @Column(name = "`range`", columnDefinition = "VARCHAR(16) NOT NULL")
    private String range;

    @Column(name = "`concentration`", columnDefinition = "TINYINT NOT NULL DEFAULT 0")
    private Boolean concentration = false;

    @Column(name = "`duration`", columnDefinition = "VARCHAR(16) NOT NULL")
    private String duration;

    @ElementCollection(fetch = FetchType.EAGER)
    @Convert(converter = SpellComponentConverter.class)
    @Column(name = "`component`", columnDefinition = "VARCHAR(16)")
    @JoinTable(
        name = "`spell_component`",
        joinColumns = @JoinColumn(name = "`spell_id`", columnDefinition = "VARCHAR(64)")
    )
    private Set<SpellComponent> components;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id.spell")
    private Set<SpellMaterial> materials;

    @JsonManagedReference
    @OneToMany(mappedBy = "spell", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<SpellDescription> descriptions;

    public Spell(String id) {
        super(id);
    }

    public void addComponent(SpellComponent component) {
        if (components == null) {
            components = new HashSet<>();
        }
        components.add(component);
    }

    public void removeComponent(SpellComponent component) {
        components.remove(component);

        if (component == SpellComponent.MATERIAL) {
            removeAllMaterials();
        }
    }

    public void addMaterial(SpellMaterial material) {
        if (materials == null) {
            this.materials = new HashSet<>();
        }
        materials.add(material);

        material.setSpell(this);
    }

    public void removeMaterial(SpellMaterial material) {
        materials.remove(material);
    }

    public void removeAllMaterials() {
        List<SpellMaterial> materials = new ArrayList<>(this.materials);

        materials.forEach(this::removeMaterial);
    }

    public void addDescription(SpellDescription description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }
        descriptions.add(description);

        description.setSpell(this);
    }

    public void addAllDescriptions(List<SpellDescription> descriptions) {
        descriptions.forEach(this::addDescription);
    }

    public void removeDescription(SpellDescription description) {
        descriptions.remove(description);

        description.setSpell(null);
    }

    public void removeAllDescriptions() {
        List<SpellDescription> spellDescriptions = new ArrayList<>(descriptions);

        spellDescriptions.forEach(this::removeDescription);
    }
}
