package org.eu.nl.dndmapp.dmaserver.models.filters;

import static org.springframework.data.domain.ExampleMatcher.*;

public class Filter {
    private final String property;

    private final String builderMethodName;
    private final boolean requiresBuilderParam;

    private final String requestParam;

    private final GenericPropertyMatcher propertyMatcher;

    /* CONSTRUCTORS */

    public Filter(
        String property,
        String builderMethodName,
        boolean requiresBuilderParam,
        String requestParam,
        GenericPropertyMatcher propertyMatcher
    ) {
        this.property = property;
        this.builderMethodName = builderMethodName;
        this.requiresBuilderParam = requiresBuilderParam;
        this.requestParam = requestParam;
        this.propertyMatcher = propertyMatcher;
    }

    public Filter(String property, String builderMethodName, boolean requiresBuilderParam, String requestParam) {
        this(property, builderMethodName, requiresBuilderParam, requestParam, GenericPropertyMatchers.exact());
    }

    /* GETTERS & SETTERS */

    public String getProperty() {
        return property;
    }

    public String getBuilderMethodName() {
        return builderMethodName;
    }

    public boolean isBuilderParamRequired() {
        return requiresBuilderParam;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public GenericPropertyMatcher getPropertyMatcher() {
        return this.propertyMatcher;
    }
}
