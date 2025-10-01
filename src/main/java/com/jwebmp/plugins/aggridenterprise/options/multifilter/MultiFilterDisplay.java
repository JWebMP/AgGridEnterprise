package com.jwebmp.plugins.aggridenterprise.options.multifilter;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Display styles for Multi Filter child filters.
 */
public enum MultiFilterDisplay {
    INLINE("inline"),
    ACCORDION("accordion"),
    SUB_MENU("subMenu");

    private final String json;

    MultiFilterDisplay(String json) {
        this.json = json;
    }

    @JsonValue
    public String getJson() {
        return json;
    }
}
