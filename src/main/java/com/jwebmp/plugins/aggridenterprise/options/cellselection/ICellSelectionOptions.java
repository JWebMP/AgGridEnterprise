package com.jwebmp.plugins.aggridenterprise.options.cellselection;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CRTP contract for Enterprise Cell Selection configuration.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ICellSelectionOptions<J extends ICellSelectionOptions<J>> {

    /**
     * If true, only a single range can be selected.
     */
    @JsonProperty("suppressMultiRanges")
    Boolean getSuppressMultiRanges();
    J setSuppressMultiRanges(Boolean suppressMultiRanges);

    /**
     * If true, headers for columns included in the selection will be highlighted.
     */
    @JsonProperty("enableHeaderHighlight")
    Boolean getEnableHeaderHighlight();
    J setEnableHeaderHighlight(Boolean enableHeaderHighlight);

    /**
     * Handle behaviour configuration. Can be RangeHandleOptions or FillHandleOptions.
     */
    @JsonProperty("handle")
    ISelectionHandleOptions<?> getHandle();
    J setHandle(ISelectionHandleOptions<?> handle);
}
