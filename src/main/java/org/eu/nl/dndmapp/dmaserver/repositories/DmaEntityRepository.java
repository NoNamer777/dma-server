package org.eu.nl.dndmapp.dmaserver.repositories;

import org.eu.nl.dndmapp.dmaserver.models.DmaEntity;

import java.util.List;
import java.util.UUID;

public interface DmaEntityRepository<E extends DmaEntity> {

    List<E> findAll();

    List<E> findAllByQuery(String queryName, Object... params);

    E findById(UUID id);

    E save(E entity);

    Boolean delete(UUID id);
}
