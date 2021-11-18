package org.eu.nl.dndmapp.dmaserver.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eu.nl.dndmapp.dmaserver.models.Description;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "`spell_description`")
public class SpellDescription extends Description implements Serializable {

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "`spell_id`")
    private Spell spell;

    public SpellDescription(String id) {
        super(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpellDescription other = (SpellDescription) o;

        return this.getOrder().equals(other.getOrder()) &&
            this.getText().equals(other.getText()) &&
            Objects.equals(this.getTitle(), other.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), spell);
    }

    @Override
    public String toString() {
        return String.format(
            "SpellDescription{\n\torder: '%d',\n\ttitle: '%s',\n\ttext: '%s'\n}\n",
            this.getOrder(),
            this.getTitle() == null ? "" : this.getTitle(),
            this.getText()
        );
    }
}
