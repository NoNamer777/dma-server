package org.eu.nl.dndmapp.dmaserver.models.primaryKeys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eu.nl.dndmapp.dmaserver.models.entities.Material;
import org.eu.nl.dndmapp.dmaserver.models.entities.Spell;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SpellMaterialId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "`spell_id`", columnDefinition = "VARCHAR(64)")
    private Spell spell;

    @ManyToOne
    @JoinColumn(name = "`material_id`", columnDefinition = "VARCHAR(64)")
    private Material material;

    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) return false;
        if (this == other) return true;

        SpellMaterialId that = (SpellMaterialId) other;

        return this.spell.equals(that.spell) && this.material.equals(that.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spell, material);
    }
}
