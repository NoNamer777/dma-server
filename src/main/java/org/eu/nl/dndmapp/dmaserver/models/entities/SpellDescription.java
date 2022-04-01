package org.eu.nl.dndmapp.dmaserver.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.eu.nl.dndmapp.dmaserver.models.Description;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "`spell_description`")
public class SpellDescription extends Description implements Serializable {
    @JsonBackReference
    @ManyToOne
    @JoinColumn(
        name = "`spell_id`",
        columnDefinition = "VARCHAR(64) NOT NULL"
    )
    private Spell spell;

    /* CONSTRUCTORS */

    public SpellDescription() {}

    public SpellDescription(Spell spell) {
        this.spell = spell;
    }

    /* GETTERS & SETTERS */

    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
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
        return super.toString();
    }
}
