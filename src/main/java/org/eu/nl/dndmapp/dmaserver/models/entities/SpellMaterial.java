package org.eu.nl.dndmapp.dmaserver.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eu.nl.dndmapp.dmaserver.models.primaryKeys.SpellMaterialId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "`spell_material`")
public class SpellMaterial {

    @JsonIgnore
    @EmbeddedId
    private SpellMaterialId id;

    @Column(name = "`cost`", columnDefinition = "DOUBLE NOT NULL DEFAULT 0.00")
    private Double cost = 0.0;

    @Column(name = "`consumed`", columnDefinition = "TINYINT NOT NULL DEFAULT 0")
    private Boolean consumed = false;

    @Column(name = "`order`", columnDefinition = "TINYINT NOT NULL DEFAULT 0")
    private Integer order = 0;

    public SpellMaterial(Spell spell, Material material) {
        this.id = new SpellMaterialId(spell, material);
    }

    public SpellMaterial(SpellMaterialId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) return false;
        if (this == other) return true;

        SpellMaterial that = (SpellMaterial) other;

        return this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @JsonBackReference
    public Spell getSpell() {
        return this.id.getSpell();
    }

    public void setSpell(Spell spell) {
        this.id.setSpell(spell);
    }

    @JsonManagedReference
    public Material getMaterial() {
        return this.id.getMaterial();
    }

    public void setMaterial(Material material) {
        this.id.setMaterial(material);
    }
}
