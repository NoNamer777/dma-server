package org.eu.nl.dndmapp.dmaserver.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public class NamedEntity extends DmaEntity {

    @Column(name = "`name`", columnDefinition = "VARCHAR(64) NOT NULL")
    private String name;

    public NamedEntity(String id) {
        super(id);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || this.getClass() != object.getClass()) return false;

        NamedEntity that = (NamedEntity) object;

        return this.getName().equals(that.getName()) || super.equals(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName());
    }
}
