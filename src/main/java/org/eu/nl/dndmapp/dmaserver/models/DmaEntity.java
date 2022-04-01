package org.eu.nl.dndmapp.dmaserver.models;

import org.eu.nl.dndmapp.dmaserver.models.exceptions.DataMismatchException;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public class DmaEntity {
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
        name = "uuid",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(
        name = "`id`",
        columnDefinition = "VARCHAR(64)"
    )
    private UUID id;

    /* CONSTRUCTORS */

    public DmaEntity() {}

    public DmaEntity(String id) {
        if (id == null) return;

        try {
            this.id = UUID.fromString(id);
        }
        catch (IllegalArgumentException exception) {
            throw new DataMismatchException("The ID '%s' is not a valid UUID format. Please, provide a valid ID.", id);
        }
    }

    /* GETTERS & SETTERS */

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DmaEntity)) return false;
        if (this == object) return true;

        DmaEntity other = (DmaEntity) object;

        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return String.format("DmaEntity{ id: '%s' }", this.getId());
    }
}
