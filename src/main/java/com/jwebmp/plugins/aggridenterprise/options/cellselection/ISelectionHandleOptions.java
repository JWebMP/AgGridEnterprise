package com.jwebmp.plugins.aggridenterprise.options.cellselection;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Marker CRTP interface for Cell Selection handle configuration.
 * Implementations: RangeHandleOptions, FillHandleOptions.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ISelectionHandleOptions<J extends ISelectionHandleOptions<J>> {
}
