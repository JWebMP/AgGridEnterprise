package com.jwebmp.plugins.aggridenterprise.options.multifilter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.jwebmp.plugins.aggrid.options.filters.IFilterParams;

/**
 * Concrete implementation of a child filter definition inside a Multi Filter.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class MultiFilterDef implements IMultiFilterDef<MultiFilterDef> {

    @JsonProperty("display")
    private MultiFilterDisplay display;

    @JsonProperty("title")
    private String title;

    @JsonProperty("filter")
    private String filter;

    @JsonProperty("filterParams")
    private IFilterParams<?> filterParams;

    @JsonProperty("floatingFilterComponent")
    private String floatingFilterComponent;

    @JsonProperty("floatingFilterComponentParams")
    private String floatingFilterComponentParams; // raw JS serialized via field visibility

    @JsonProperty("filterValueGetter")
    private com.jwebmp.plugins.aggrid.options.selectors.FieldSelector filterValueGetter;

    @Override
    public MultiFilterDisplay getDisplay() {
        return display;
    }

    @Override
    public MultiFilterDef setDisplay(MultiFilterDisplay display) {
        this.display = display;
        return this;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public MultiFilterDef setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public String getFilter() {
        return filter;
    }

    @Override
    public MultiFilterDef setFilter(String filterKey) {
        this.filter = filterKey;
        return this;
    }

    @Override
    public IFilterParams<?> getFilterParams() {
        return filterParams;
    }

    @Override
    public MultiFilterDef setFilterParams(IFilterParams<?> params) {
        this.filterParams = params;
        return this;
    }

    @Override
    public String getFloatingFilterComponent() {
        return floatingFilterComponent;
    }

    @Override
    public MultiFilterDef setFloatingFilterComponent(String componentKey) {
        this.floatingFilterComponent = componentKey;
        return this;
    }

    @Override
    public String getFloatingFilterComponentParams() {
        return floatingFilterComponentParams;
    }

    @Override
    public MultiFilterDef setFloatingFilterComponentParams(String paramsRawJs) {
        this.floatingFilterComponentParams = paramsRawJs;
        return this;
    }

    @Override
    public com.jwebmp.plugins.aggrid.options.selectors.FieldSelector getFilterValueGetter() {
        return filterValueGetter;
    }

    @Override
    public MultiFilterDef setFilterValueGetter(com.jwebmp.plugins.aggrid.options.selectors.FieldSelector filterValueGetter) {
        this.filterValueGetter = filterValueGetter;
        return this;
    }
}
