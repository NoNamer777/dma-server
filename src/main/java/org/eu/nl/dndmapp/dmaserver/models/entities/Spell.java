package org.eu.nl.dndmapp.dmaserver.models.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eu.nl.dndmapp.dmaserver.models.NamedEntity;

import javax.persistence.*;

@Entity
@Table(name = "`spell`")
@NamedQueries({
    @NamedQuery(name = "find_all_spells", query = "SELECT S FROM Spell S order by S.name"),
    @NamedQuery(name = "find_spell_by_id", query = "SELECT S FROM Spell S WHERE S.id = ?1"),
    @NamedQuery(name = "find_spell_by_name", query = "SELECT S FROM Spell S WHERE S.name = ?1"),
})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Spell extends NamedEntity {

    @Column(name = "`level`")
    private Integer level = 0;

    @Column(name = "`ritual`")
    private Boolean ritual = false;

    @Column(name = "`casting_time`")
    private String castingTime;

    @Column(name = "`range`")
    private String range;

    @Column(name = "`concentration`")
    private Boolean concentration = false;

    @Column(name = "`duration`")
    private String duration;

    public Spell(String id) {
        super(id);
    }
}
