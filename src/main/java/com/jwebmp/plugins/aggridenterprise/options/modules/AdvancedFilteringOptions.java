package com.jwebmp.plugins.aggridenterprise.options.modules;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AG Grid Enterprise: Advanced Filtering Configuration Module
 *
 * Encapsulates advanced filter builder and set filter options for enterprise filtering UI.
 *
 * @param <J> Generic type for fluent builder pattern (CRTP)
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdvancedFilteringOptions<J extends AdvancedFilteringOptions<J>>
{
    private J parent;

    /**
     * Enable the advanced filter builder UI. Provides visual filter construction interface.
     */
    @JsonProperty("enableAdvancedFilter")
    private Boolean enableAdvancedFilter;

    /**
     * Include hidden columns in the advanced filter builder. By default, hidden columns are excluded.
     */
    @JsonProperty("includeHiddenColumnsInAdvancedFilter")
    private Boolean includeHiddenColumnsInAdvancedFilter;

    /**
     * DOM element or selector to insert the advanced filter builder into.
     * If not specified, uses a default location.
     */
    @JsonProperty("advancedFilterParent")
    private Object advancedFilterParent;

    /**
     * Configuration parameters for the advanced filter builder UI.
     * Controls behavior, appearance, and validation.
     */
    @JsonProperty("advancedFilterBuilderParams")
    private Object advancedFilterBuilderParams;

    /**
     * Initial advanced filter parameters. Sets the starting filter state.
     */
    @JsonProperty("advancedFilterParams")
    private Object advancedFilterParams;

    /**
     * Enterprise cell selection configuration. Controls how cells can be selected in the grid.
     * Can be Boolean true/false or configuration object.
     */
    @JsonProperty("cellSelection")
    private Object cellSelection;

    // ===== Constructors & Parent Management =====

    public AdvancedFilteringOptions()
    {
    }

    public AdvancedFilteringOptions(J parent)
    {
        this.parent = parent;
    }

    /**
     * Get the parent AgGridEnterpriseOptions instance for fluent chaining.
     *
     * @return parent options object
     */
    public J parent()
    {
        return parent;
    }

    public J getParent()
    {
        return parent;
    }

    public AdvancedFilteringOptions<J> setParent(J parent)
    {
        this.parent = parent;
        return this;
    }

    // ===== Property Getters & Fluent Setters =====

    public Boolean getEnableAdvancedFilter()
    {
        return enableAdvancedFilter;
    }

    public AdvancedFilteringOptions<J> setEnableAdvancedFilter(Boolean enableAdvancedFilter)
    {
        this.enableAdvancedFilter = enableAdvancedFilter;
        return this;
    }

    public Boolean getIncludeHiddenColumnsInAdvancedFilter()
    {
        return includeHiddenColumnsInAdvancedFilter;
    }

    public AdvancedFilteringOptions<J> setIncludeHiddenColumnsInAdvancedFilter(Boolean includeHiddenColumnsInAdvancedFilter)
    {
        this.includeHiddenColumnsInAdvancedFilter = includeHiddenColumnsInAdvancedFilter;
        return this;
    }

    public Object getAdvancedFilterParent()
    {
        return advancedFilterParent;
    }

    public AdvancedFilteringOptions<J> setAdvancedFilterParent(Object advancedFilterParent)
    {
        this.advancedFilterParent = advancedFilterParent;
        return this;
    }

    public Object getAdvancedFilterBuilderParams()
    {
        return advancedFilterBuilderParams;
    }

    public AdvancedFilteringOptions<J> setAdvancedFilterBuilderParams(Object advancedFilterBuilderParams)
    {
        this.advancedFilterBuilderParams = advancedFilterBuilderParams;
        return this;
    }

    public Object getAdvancedFilterParams()
    {
        return advancedFilterParams;
    }

    public AdvancedFilteringOptions<J> setAdvancedFilterParams(Object advancedFilterParams)
    {
        this.advancedFilterParams = advancedFilterParams;
        return this;
    }

    public Object getCellSelection()
    {
        return cellSelection;
    }

    public AdvancedFilteringOptions<J> setCellSelection(Object cellSelection)
    {
        this.cellSelection = cellSelection;
        return this;
    }
}
