package org.eu.nl.dndmapp.dmaserver.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.eu.nl.dndmapp.dmaserver.models.DmaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "`material`")
public class Material extends DmaEntity {
    @Column(
        name = "`description`",
        columnDefinition = "TEXT(64) NOT NULL"
    )
    private String description;

    @JsonBackReference
    @OneToMany(mappedBy = "id.material")
    private final Set<SpellMaterial> spells = new HashSet<>();

    /* CONSTRUCTORS */

    public Material() {}

    public Material(String id, String description) {
        super(id);

        this.description = description;
    }

    /* GETTERS & SETTERS */

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<SpellMaterial> getSpells() {
        return spells;
    }

    public void addSpellMaterial(SpellMaterial spellMaterial) {
        spells.add(spellMaterial);
    }

    public void removeSpellMaterial(SpellMaterial spellMaterial) {
        spells.remove(spellMaterial);
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object) || !(object instanceof Material)) return false;
        if (this == object) return true;

        Material other = (Material) object;

        return Objects.equals(this.description, other.description);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Material{ id: '%s', description: '%s' }", this.getId(), this.description);
    }
}
