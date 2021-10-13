package org.eu.nl.dndmapp.dmaserver.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public class NamedEntity extends DmaEntity {

    @Column(name = "`name`")
    private String name;

    public NamedEntity(String id) {
        super(id);
    }
}
