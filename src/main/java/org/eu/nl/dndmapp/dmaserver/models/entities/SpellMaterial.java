package org.eu.nl.dndmapp.dmaserver.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.eu.nl.dndmapp.dmaserver.models.primaryKeys.SpellMaterialId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "`spell_material`")
public class SpellMaterial {
    @JsonIgnore
    @EmbeddedId
    private SpellMaterialId id;

    @Column(
        name = "`cost`",
        columnDefinition = "DOUBLE NOT NULL DEFAULT 0.00"
    )
    private Double cost;

    @Column(
        name = "`consumed`",
        columnDefinition = "TINYINT NOT NULL DEFAULT 0"
    )
    private Boolean consumed;

    @Column(
        name = "`order`",
        columnDefinition = "TINYINT NOT NULL DEFAULT 0"
    )
    private Integer order;

    /* CONSTRUCTORS */

    public SpellMaterial() {}

    public SpellMaterial(SpellMaterialId id, Double cost, Boolean consumed, Integer order) {
        this.id = id;
        this.cost = cost;
        this.consumed = consumed;
        this.order = order;
    }

    /* GETTERS & SETTERS */

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Boolean getConsumed() {
        return consumed;
    }

    public void setConsumed(Boolean consumed) {
        this.consumed = consumed;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SpellMaterial)) return false;
        if (this == object) return true;

        SpellMaterial other = (SpellMaterial) object;

        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format(
            "SpellMaterial{ id: %s, cost: '%8.2f', consumed: '%s', order: '%d'",
            this.id.toString(),
            this.cost,
            this.consumed,
            this.order
        );
    }
}
