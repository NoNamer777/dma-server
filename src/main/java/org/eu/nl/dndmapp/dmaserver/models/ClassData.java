package org.eu.nl.dndmapp.dmaserver.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassData<E extends DmaEntity> {

    private Class<E> entityClass;

    private String singular;

    private String multiple;
}
