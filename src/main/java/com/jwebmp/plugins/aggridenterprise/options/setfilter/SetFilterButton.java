package com.jwebmp.plugins.aggridenterprise.options.setfilter;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Action buttons available for Set Filter.
 */
public enum SetFilterButton {
    APPLY("apply"),
    CLEAR("clear"),
    RESET("reset"),
    CANCEL("cancel");

    private final String json;

    SetFilterButton(String json) {
        this.json = json;
    }

    @JsonValue
    public String getJson() {
        return json;
    }
}
