package org.eu.nl.dndmapp.dmaserver.models;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public class NamedEntity extends DmaEntity {
    @Column(
        name = "`name`",
        columnDefinition = "VARCHAR(64) NOT NULL"
    )
    private String name;

    /* CONSTRUCTORS */

    public NamedEntity() {
        super();
    }

    public NamedEntity(String id, String name) {
        super(id);

        this.name = name;
    }

    /* GETTERS & SETTERS */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NamedEntity) || !super.equals(object)) return false;
        if (this == object) return true;

        NamedEntity that = (NamedEntity) object;

        return this.getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName());
    }

    @Override
    public String toString() {
        return String.format("NamedEntity{ id: '%s', name: '%s' }", this.getId(), this.getName());
    }
}
