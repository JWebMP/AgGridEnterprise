package com.jwebmp.plugins.aggridenterprise.options.setfilter;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Excel mode options for Set Filter behaviour.
 */
public enum ExcelMode {
    MAC("mac"),
    WINDOWS("windows");

    private final String json;

    ExcelMode(String json) {
        this.json = json;
    }

    @JsonValue
    public String getJson() {
        return json;
    }
}
