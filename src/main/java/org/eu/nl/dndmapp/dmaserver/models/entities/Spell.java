package org.eu.nl.dndmapp.dmaserver.models.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eu.nl.dndmapp.dmaserver.models.converters.MagicSchoolConverter;
import org.eu.nl.dndmapp.dmaserver.models.converters.SpellComponentConverter;
import org.eu.nl.dndmapp.dmaserver.models.enums.MagicSchool;
import org.eu.nl.dndmapp.dmaserver.models.enums.SpellComponent;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "`spell`")
public class Spell {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "`id`")
    private UUID id;

    @Column(name = "`name`")
    private String name;

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
        if (id == null) return;

        this.id = UUID.fromString(id);
    }

    public void addComponent(SpellComponent component) {
        components.add(component);
    }

    public void removeComponent(SpellComponent component) {
        components.remove(component);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Spell spell = (Spell) o;

        return id.equals(spell.id) && name.equals(spell.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
