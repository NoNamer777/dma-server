package org.eu.nl.dndmapp.dmaserver.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eu.nl.dndmapp.dmaserver.models.NamedEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "`material_component`")
public class MaterialComponent extends NamedEntity {

    @Column(name = "`cost`")
    private Double cost = 0.0;

    @Column(name = "`consumed`")
    private Boolean consumedBySpell = false;

    @JsonBackReference
    @ManyToMany(mappedBy = "materials", fetch = FetchType.EAGER)
    private final Set<Spell> spells = new HashSet<>();

    public MaterialComponent(String id) {
        super(id);
    }

    public void addSpell(Spell spell) {
        spells.add(spell);
    }

    public void removeSpell(Spell spell) {
        spells.remove(spell);
    }
}
