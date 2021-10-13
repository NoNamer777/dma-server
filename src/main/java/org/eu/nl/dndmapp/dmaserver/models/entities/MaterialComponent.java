package org.eu.nl.dndmapp.dmaserver.models.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eu.nl.dndmapp.dmaserver.models.NamedEntity;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "`material_component`")
public class MaterialComponent extends NamedEntity {

    @Column(name = "`cost`")
    private Double cost = 0.0;

    @Column(name = "`consumed`")
    private Boolean consumedBySpell = false;

    public MaterialComponent(String id) {
        super(id);
    }
}
