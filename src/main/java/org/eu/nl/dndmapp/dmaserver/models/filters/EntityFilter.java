package org.eu.nl.dndmapp.dmaserver.models.filters;

import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;

import java.util.List;

public class EntityFilter {
    public final static String FILTER_KEY_PAGE_NUMBER = "pageNumber";
    public final static String FILTER_KEY_PAGE_SIZE = "pageSize";
    public final static String FILTER_KEY_SORT_DIRECTION = "sortDirection";

    public final static String FILTER_KEY_NAME = "name";
    public final static String FILTER_KEY_LEVEL = "level";
    public final static String FILTER_KEY_MAGIC_SCHOOL = "magicSchool";
    public final static String FILTER_KEY_RITUAL = "ritual";
    public final static String FILTER_KEY_CASTING_TIME = "castingTime";
    public final static String FILTER_KEY_RANGE = "range";
    public final static String FILTER_KEY_CONCENTRATION = "concentration";
    public final static String FILTER_KEY_DURATION = "duration";

    public static final List<Filter> SPELL_FILTERS = List.of(
        new Filter("name", "withName", true, FILTER_KEY_NAME, GenericPropertyMatchers.contains()),
        new Filter("level", "withLevel", true, FILTER_KEY_LEVEL),
        new Filter("magicSchool", "withMagicSchool", true, FILTER_KEY_MAGIC_SCHOOL),
        new Filter("ritual", "isRitual", false, FILTER_KEY_RITUAL),
        new Filter("castingTime", "withCastingTime", true, FILTER_KEY_CASTING_TIME),
        new Filter("range", "withRange", true, FILTER_KEY_RANGE),
        new Filter("concentration", "requiresConcentration", false, FILTER_KEY_CONCENTRATION),
        new Filter("duration", "withDuration", true, FILTER_KEY_DURATION)
    );
}
