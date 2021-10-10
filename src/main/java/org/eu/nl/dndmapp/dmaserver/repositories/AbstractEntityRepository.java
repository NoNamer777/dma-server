package org.eu.nl.dndmapp.dmaserver.repositories;

import org.eu.nl.dndmapp.dmaserver.models.ClassData;
import org.eu.nl.dndmapp.dmaserver.models.DmaEntity;
import org.eu.nl.dndmapp.dmaserver.models.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Transactional
public class AbstractEntityRepository<E extends DmaEntity> implements DmaEntityRepository<E> {

    @Autowired
    private EntityManager entityManager;

    private final ClassData<E> classData;

    public AbstractEntityRepository(ClassData<E> classData) {
        this.classData = classData;
    }

    @Override
    public List<E> findAll() {
        return findAllByQuery(String.format("find_all_%s", classData.getMultiple()));
    }

    @Override
    public List<E> findAllByQuery(String queryName, Object... params) {
        TypedQuery<E> query = entityManager.createNamedQuery(queryName, classData.getEntityClass());

        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            query.setParameter(i + 1, param);
        }

        return query.getResultList();
    }

    @Override
    public E findById(UUID id) {
        E entityFoundById = entityManager.find(classData.getEntityClass(), id);

        if (entityFoundById == null) {
            throw new EntityNotFoundException(String.format("%s with id: '%s' is not found.", classData.getSingular(), id));
        }
        return entityFoundById;
    }

    @Override
    public E save(E entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        }

        entityManager.merge(entity);
        return entity;
    }

    @Override
    public Boolean delete(UUID id) {
        try {
            E toDelete = findById(id);
            entityManager.remove(toDelete);

            return true;

        } catch (EntityNotFoundException exception) {
            throw new EntityNotFoundException(String.format("Could not delete %s with id: '%s' because it does not exist.", classData.getSingular(), id));
        }
    }
}
