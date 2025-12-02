package com.jwebmp.plugins.aggridenterprise.options.modules;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AG Grid Enterprise: Range Selection Configuration Module
 *
 * Encapsulates range selection options for selecting multiple cells at once.
 * Extensible for future clipboard and export integration.
 *
 * @param <J> Generic type for fluent builder pattern (CRTP)
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RangeSelectionOptions<J extends RangeSelectionOptions<J>>
{
    private J parent;

    /**
     * Enable range selection. Allows users to select multiple cells by dragging or shift-clicking.
     */
    @JsonProperty("enableRangeSelection")
    private Boolean enableRangeSelection;

    // ===== Constructors & Parent Management =====

    public RangeSelectionOptions()
    {
    }

    public RangeSelectionOptions(J parent)
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

    public RangeSelectionOptions<J> setParent(J parent)
    {
        this.parent = parent;
        return this;
    }

    // ===== Property Getters & Fluent Setters =====

    public Boolean getEnableRangeSelection()
    {
        return enableRangeSelection;
    }

    public RangeSelectionOptions<J> setEnableRangeSelection(Boolean enableRangeSelection)
    {
        this.enableRangeSelection = enableRangeSelection;
        return this;
    }
}
