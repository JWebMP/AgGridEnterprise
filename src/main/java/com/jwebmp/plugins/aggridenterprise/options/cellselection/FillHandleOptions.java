package com.jwebmp.plugins.aggridenterprise.options.cellselection;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

/**
 * Configuration for the Fill Handle behaviour in Cell Selection.
 * Serializes to: { "mode":"fill", ... }
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class FillHandleOptions implements ISelectionHandleOptions<FillHandleOptions> {

    /** Fixed mode for Fill Handle per AG Grid API. */
    @JsonProperty("mode")
    private final String mode = "fill";

    /** Prevent clearing values when the selection is reduced. */
    @JsonProperty("suppressClearOnFillReduction")
    private Boolean suppressClearOnFillReduction;

    /** Direction restriction for dragging: 'x' | 'y' | 'xy' (default 'xy'). */
    @JsonProperty("direction")
    private FillHandleDirection direction;

    /**
     * Raw JavaScript callback: (params) => any | false
     * Return a value to set, false to let grid perform default, or params.currentCellValue to skip cell.
     */
    @JsonProperty("setFillValue")
    @JsonRawValue
    private String setFillValue;

    public Boolean getSuppressClearOnFillReduction() {
        return suppressClearOnFillReduction;
    }

    public FillHandleOptions setSuppressClearOnFillReduction(Boolean suppressClearOnFillReduction) {
        this.suppressClearOnFillReduction = suppressClearOnFillReduction;
        return this;
    }

    public FillHandleDirection getDirection() {
        return direction;
    }

    public FillHandleOptions setDirection(FillHandleDirection direction) {
        this.direction = direction;
        return this;
    }

    public String getSetFillValue() {
        return setFillValue;
    }

    public FillHandleOptions setSetFillValue(String setFillValueRawJs) {
        this.setFillValue = setFillValueRawJs;
        return this;
    }
}
