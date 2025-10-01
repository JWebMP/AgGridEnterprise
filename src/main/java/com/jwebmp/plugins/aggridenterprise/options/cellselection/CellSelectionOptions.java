package com.jwebmp.plugins.aggridenterprise.options.cellselection;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Concrete implementation for Enterprise Cell Selection configuration.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class CellSelectionOptions implements ICellSelectionOptions<CellSelectionOptions> {

    @JsonProperty("suppressMultiRanges")
    private Boolean suppressMultiRanges;

    @JsonProperty("enableHeaderHighlight")
    private Boolean enableHeaderHighlight;

    @JsonProperty("handle")
    private ISelectionHandleOptions<?> handle;

    @Override
    public Boolean getSuppressMultiRanges() {
        return suppressMultiRanges;
    }

    @Override
    public CellSelectionOptions setSuppressMultiRanges(Boolean suppressMultiRanges) {
        this.suppressMultiRanges = suppressMultiRanges;
        return this;
    }

    @Override
    public Boolean getEnableHeaderHighlight() {
        return enableHeaderHighlight;
    }

    @Override
    public CellSelectionOptions setEnableHeaderHighlight(Boolean enableHeaderHighlight) {
        this.enableHeaderHighlight = enableHeaderHighlight;
        return this;
    }

    @Override
    public ISelectionHandleOptions<?> getHandle() {
        return handle;
    }

    @Override
    public CellSelectionOptions setHandle(ISelectionHandleOptions<?> handle) {
        this.handle = handle;
        return this;
    }

    /**
     * Convenience: enable a Range Handle. Equivalent to handle: { mode: 'range' }.
     */
    public CellSelectionOptions enableRangeHandle() {
        this.handle = new RangeHandleOptions();
        return this;
    }

    /**
     * Convenience: enable a Fill Handle. Equivalent to handle: { mode: 'fill' }.
     */
    public CellSelectionOptions enableFillHandle() {
        this.handle = new FillHandleOptions();
        return this;
    }
}
