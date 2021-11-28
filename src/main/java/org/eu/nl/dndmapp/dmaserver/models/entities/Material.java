package org.eu.nl.dndmapp.dmaserver.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eu.nl.dndmapp.dmaserver.models.DmaEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "`material`")
public class Material extends DmaEntity {

    @Column(name = "`description`", columnDefinition = "TEXT(64) NOT NULL")
    private String description;

    @JsonBackReference
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "id.material")
    private Set<SpellMaterial> spells;

    public Material(String id) {
        super(id);
    }

    public void addSpellMaterial(SpellMaterial spellMaterial) {
        if (spells == null) {
            spells = new HashSet<>();
        }
        spells.add(spellMaterial);
    }

    public void removeSpellMaterial(SpellMaterial spellMaterial) {
        if (spells == null) return;

        spells.remove(spellMaterial);
    }
}
