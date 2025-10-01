package com.jwebmp.plugins.aggridenterprise.options.multifilter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jwebmp.plugins.aggrid.options.filters.IFilterParams;
import com.jwebmp.plugins.aggridenterprise.options.setfilter.SetFilterButton;

import java.util.List;

/**
 * CRTP interface describing Multi Filter parameters.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface IMultiFilterParams<J extends IMultiFilterParams<J>> extends IFilterParams<J> {

    /**
     * Array of child filter definitions.
     */
    @JsonProperty("filters")
    List<IMultiFilterDef<?>> getFilters();
    J setFilters(List<IMultiFilterDef<?>> filters);

    /**
     * If true, disables UI inputs in this Multi Filter. Child filters must be configured individually for readOnly if needed.
     */
    @JsonProperty("readOnly")
    Boolean getReadOnly();
    J setReadOnly(Boolean readOnly);

    /**
     * Optional buttons controlling all child filters together.
     */
    @JsonProperty("buttons")
    List<SetFilterButton> getButtons();
    J setButtons(List<SetFilterButton> buttons);

    /**
     * When true, closes popup on apply/reset/cancel as per AG Grid rules.
     */
    @JsonProperty("closeOnApply")
    Boolean getCloseOnApply();
    J setCloseOnApply(Boolean closeOnApply);
}
