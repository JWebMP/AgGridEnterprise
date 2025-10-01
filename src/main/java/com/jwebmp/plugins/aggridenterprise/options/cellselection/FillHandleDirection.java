package com.jwebmp.plugins.aggridenterprise.options.cellselection;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Direction constraint for the Fill Handle drag.
 */
public enum FillHandleDirection {
    X("x"),
    Y("y"),
    XY("xy");

    private final String json;

    FillHandleDirection(String json) {
        this.json = json;
    }

    @JsonValue
    public String getJson() {
        return json;
    }
}
