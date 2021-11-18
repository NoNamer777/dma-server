package org.eu.nl.dndmapp.dmaserver.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public class Description extends DmaEntity {

    @Column(name = "`title`")
    private String title;

    @Column(name = "`order`")
    private Integer order;

    @Column(name = "`text`")
    private String text;

    public Description(String id) {
        super(id);
    }
}
