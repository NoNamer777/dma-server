package org.eu.nl.dndmapp.dmaserver.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.UUID;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DmaEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "`id`")
    private UUID id;

    public DmaEntity(String id) {
        if (id == null) return;

        this.id = UUID.fromString(id);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        DmaEntity dmaEntity = (DmaEntity) object;

        return id.equals(dmaEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
