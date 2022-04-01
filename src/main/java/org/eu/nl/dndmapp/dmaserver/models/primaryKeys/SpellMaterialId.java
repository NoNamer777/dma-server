package org.eu.nl.dndmapp.dmaserver.models.primaryKeys;

import org.eu.nl.dndmapp.dmaserver.models.entities.Material;
import org.eu.nl.dndmapp.dmaserver.models.entities.Spell;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SpellMaterialId implements Serializable {
    @ManyToOne
    @JoinColumn(
        name = "`spell_id`",
        columnDefinition = "VARCHAR(64)"
    )
    private Spell spell;

    @ManyToOne
    @JoinColumn(
        name = "`material_id`",
        columnDefinition = "VARCHAR(64)"
    )
    private Material material;

    /* CONSTRUCTORS */

    public SpellMaterialId() {}

    public SpellMaterialId(Spell spell, Material material) {
        this.spell = spell;
        this.material = material;
    }

    /* GETTERS & SETTERS */

    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SpellMaterialId)) return false;
        if (this == object) return true;

        SpellMaterialId other = (SpellMaterialId) object;

        return this.spell.equals(other.spell) && this.material.equals(other.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spell.hashCode(), material.hashCode());
    }

    @Override
    public String toString() {
        return String.format("SpellMaterialId{ %s, %s", this.spell.toString(), this.material.toString());
    }
}
