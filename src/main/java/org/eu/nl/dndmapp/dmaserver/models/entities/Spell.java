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

    @Column(name = "`level`")
    private Integer level = 0;

    @Column(name = "`magic_school`")
    @Convert(converter = MagicSchoolConverter.class)
    private MagicSchool magicSchool;

    @Column(name = "`ritual`")
    private Boolean ritual = false;

    @Column(name = "`casting_time`")
    private String castingTime;

    @Column(name = "`range`")
    private String range;

    @Column(name = "`concentration`")
    private Boolean concentration = false;

    @Column(name = "`duration`")
    private String duration;

    @ElementCollection(fetch = FetchType.EAGER)
    @Convert(converter = SpellComponentConverter.class)
    @Column(name = "`component`")
    @JoinTable(
        name = "`spell_component`",
        joinColumns = @JoinColumn(name = "`spell_id`")
    )
    private Set<SpellComponent> components;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "`spell_material_component`",
        joinColumns = @JoinColumn(name = "`spell_id`"),
        inverseJoinColumns = @JoinColumn(name = "`material_id`")
    )
    private Set<MaterialComponent> materials;

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

    public void addMaterial(MaterialComponent material) {
        if (materials == null) {
            this.materials = new HashSet<>();
        }
        materials.add(material);

        material.addSpell(this);
    }

    public void removeMaterial(MaterialComponent material) {
        materials.remove(material);

        material.removeSpell(this);
    }

    public void removeAllMaterials() {
        List<MaterialComponent> materialComponents = new ArrayList<>(materials);

        materialComponents.forEach(this::removeMaterial);
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
