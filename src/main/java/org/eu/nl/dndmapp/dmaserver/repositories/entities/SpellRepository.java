package org.eu.nl.dndmapp.dmaserver.repositories.entities;

import org.eu.nl.dndmapp.dmaserver.models.ClassData;
import org.eu.nl.dndmapp.dmaserver.models.entities.Spell;
import org.eu.nl.dndmapp.dmaserver.repositories.AbstractEntityRepository;
import org.springframework.stereotype.Repository;


@Repository("SPELLS.JPA")
public class SpellRepository extends AbstractEntityRepository<Spell> {

    public SpellRepository() {
        super(new ClassData<>(Spell.class, "spell", "spells"));
    }
}
