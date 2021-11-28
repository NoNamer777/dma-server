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

    @Column(name = "`title`", columnDefinition = "VARCHAR(24) DEFAULT NULL")
    private String title;

    @Column(name = "`order`", columnDefinition = "TINYINT NOT NULL DEFAULT 0")
    private Integer order;

    @Column(name = "`text`", columnDefinition = "MEDIUMTEXT NOT NULL")
    private String text;

    public Description(String id) {
        super(id);
    }
}
