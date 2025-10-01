package com.jwebmp.plugins.aggridenterprise.options.multifilter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.jwebmp.plugins.aggrid.options.filters.IFilterParams;

/**
 * Definition of a child filter inside a Multi Filter.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface IMultiFilterDef<J extends IMultiFilterDef<J>> {

    @JsonProperty("display")
    MultiFilterDisplay getDisplay();
    J setDisplay(MultiFilterDisplay display);

    @JsonProperty("title")
    String getTitle();
    J setTitle(String title);

    /**
     * Child filter key or component name, e.g. "agTextColumnFilter", "agSetColumnFilter".
     */
    @JsonProperty("filter")
    String getFilter();
    J setFilter(String filterKey);

    /**
     * Parameters specific to the child filter.
     */
    @JsonProperty("filterParams")
    IFilterParams<?> getFilterParams();
    J setFilterParams(IFilterParams<?> params);

    /**
     * Floating filter component to use (component key string).
     */
    @JsonProperty("floatingFilterComponent")
    String getFloatingFilterComponent();
    J setFloatingFilterComponent(String componentKey);

    /**
     * Raw JavaScript params object for floating filter component.
     */
    @JsonProperty("floatingFilterComponentParams")
    @JsonRawValue
    String getFloatingFilterComponentParams();
    J setFloatingFilterComponentParams(String paramsRawJs);

    /**
     * Value getter specific to this child filter (overrides colDef.filterValueGetter).
     */
    @JsonProperty("filterValueGetter")
    com.jwebmp.plugins.aggrid.options.selectors.FieldSelector getFilterValueGetter();
    J setFilterValueGetter(com.jwebmp.plugins.aggrid.options.selectors.FieldSelector filterValueGetter);
}
