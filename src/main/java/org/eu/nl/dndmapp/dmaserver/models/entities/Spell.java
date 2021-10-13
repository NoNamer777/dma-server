package org.eu.nl.dndmapp.dmaserver.models.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eu.nl.dndmapp.dmaserver.models.NamedEntity;
import org.eu.nl.dndmapp.dmaserver.models.converters.MagicSchoolConverter;
import org.eu.nl.dndmapp.dmaserver.models.converters.SpellComponentConverter;
import org.eu.nl.dndmapp.dmaserver.models.enums.MagicSchool;
import org.eu.nl.dndmapp.dmaserver.models.enums.SpellComponent;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "`spell`")
public class Spell extends NamedEntity {

    @Column(name = "`level`")
    private Integer level = 0;

    @Column(name = "`magic_school`")
    @Convert(converter = MagicSchoolConverter.class)
    private MagicSchool magicSchool;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @Convert(converter = SpellComponentConverter.class)
    @Column(name = "`component`")
    @JoinTable(
        name = "`spell_component`",
        joinColumns = @JoinColumn(name = "`spell_id`")
    )
    private final Set<SpellComponent> components = new HashSet<>();


    public Spell(String id) {
        super(id);
    }

    public void addComponent(SpellComponent component) {
        components.add(component);
    }

    public void removeComponent(SpellComponent component) {
        components.remove(component);
    }



    }

    }
}
