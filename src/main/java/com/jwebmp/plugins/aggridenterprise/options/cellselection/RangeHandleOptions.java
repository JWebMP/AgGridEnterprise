package com.jwebmp.plugins.aggridenterprise.options.cellselection;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Configuration for the Range Handle behaviour in Cell Selection.
 * Serializes to: { "mode": "range" }
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class RangeHandleOptions implements ISelectionHandleOptions<RangeHandleOptions> {

    /**
     * Fixed mode for Range Handle per AG Grid API.
     */
    @JsonProperty("mode")
    private final String mode = "range";
}
