package com.jwebmp.plugins.aggridenterprise.options.modules;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AG Grid Enterprise: Pivot Mode Configuration Module
 *
 * Encapsulates all pivot mode and pivoting configuration options including
 * auto-column generation, total rows, and pivot result processing.
 *
 * @param <J> Generic type for fluent builder pattern (CRTP)
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PivotingOptions<J extends PivotingOptions<J>>
{
    private J parent;

    /**
     * Show the pivot panel (for dragging columns to pivot). Options: "always" | "never"
     */
    @JsonProperty("pivotPanelShow")
    private String pivotPanelShow;

    /**
     * Enable pivot mode. When true, grid enters pivot mode with pivoted columns and rows.
     */
    @JsonProperty("pivotMode")
    private Boolean pivotMode;

    /**
     * Number of levels to expand pivots by default. -1 for all, 0 for none, 1+ for specific level.
     */
    @JsonProperty("pivotDefaultExpanded")
    private Integer pivotDefaultExpanded;

    /**
     * Include row totals in pivot. Options: null | "before" | "after"
     */
    @JsonProperty("pivotRowTotals")
    private String pivotRowTotals;

    /**
     * Don't auto-generate columns from pivot values. Requires manual column configuration.
     */
    @JsonProperty("pivotSuppressAutoColumn")
    private Boolean pivotSuppressAutoColumn;

    /**
     * Maximum number of columns to generate automatically. If exceeded, callback is triggered.
     */
    @JsonProperty("pivotMaxGeneratedColumns")
    private Integer pivotMaxGeneratedColumns;

    /**
     * Callback triggered when max generated columns exceeded. Can handle error or auto-remove columns.
     */
    @JsonProperty("onPivotMaxColumnsExceeded")
    private Object onPivotMaxColumnsExceeded;

    /**
     * Callback to process/customize each generated pivot column definition.
     */
    @JsonProperty("processPivotResultColDef")
    private Object processPivotResultColDef;

    /**
     * Callback to process/customize each generated pivot column group definition.
     */
    @JsonProperty("processPivotResultColGroupDef")
    private Object processPivotResultColGroupDef;

    /**
     * Don't make pivot groups expandable. Groups are shown flat without expand/collapse.
     */
    @JsonProperty("suppressExpandablePivotGroups")
    private Boolean suppressExpandablePivotGroups;

    /**
     * Make pivot functions read-only. Users cannot change aggregation functions in pivot mode.
     */
    @JsonProperty("functionsReadOnly")
    private Boolean functionsReadOnly;

    /**
     * Remove the pivot header row when there's only a single value column.
     */
    @JsonProperty("removePivotHeaderRowWhenSingleValueColumn")
    private Boolean removePivotHeaderRowWhenSingleValueColumn;

    // ===== Constructors & Parent Management =====

    public PivotingOptions()
    {
    }

    public PivotingOptions(J parent)
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

    public PivotingOptions<J> setParent(J parent)
    {
        this.parent = parent;
        return this;
    }

    // ===== Property Getters & Fluent Setters =====

    public String getPivotPanelShow()
    {
        return pivotPanelShow;
    }

    public PivotingOptions<J> setPivotPanelShow(String pivotPanelShow)
    {
        this.pivotPanelShow = pivotPanelShow;
        return this;
    }

    public Boolean getPivotMode()
    {
        return pivotMode;
    }

    public PivotingOptions<J> setPivotMode(Boolean pivotMode)
    {
        this.pivotMode = pivotMode;
        return this;
    }

    public Integer getPivotDefaultExpanded()
    {
        return pivotDefaultExpanded;
    }

    public PivotingOptions<J> setPivotDefaultExpanded(Integer pivotDefaultExpanded)
    {
        this.pivotDefaultExpanded = pivotDefaultExpanded;
        return this;
    }

    public String getPivotRowTotals()
    {
        return pivotRowTotals;
    }

    public PivotingOptions<J> setPivotRowTotals(String pivotRowTotals)
    {
        this.pivotRowTotals = pivotRowTotals;
        return this;
    }

    public Boolean getPivotSuppressAutoColumn()
    {
        return pivotSuppressAutoColumn;
    }

    public PivotingOptions<J> setPivotSuppressAutoColumn(Boolean pivotSuppressAutoColumn)
    {
        this.pivotSuppressAutoColumn = pivotSuppressAutoColumn;
        return this;
    }

    public Integer getPivotMaxGeneratedColumns()
    {
        return pivotMaxGeneratedColumns;
    }

    public PivotingOptions<J> setPivotMaxGeneratedColumns(Integer pivotMaxGeneratedColumns)
    {
        this.pivotMaxGeneratedColumns = pivotMaxGeneratedColumns;
        return this;
    }

    public Object getOnPivotMaxColumnsExceeded()
    {
        return onPivotMaxColumnsExceeded;
    }

    public PivotingOptions<J> setOnPivotMaxColumnsExceeded(Object onPivotMaxColumnsExceeded)
    {
        this.onPivotMaxColumnsExceeded = onPivotMaxColumnsExceeded;
        return this;
    }

    public Object getProcessPivotResultColDef()
    {
        return processPivotResultColDef;
    }

    public PivotingOptions<J> setProcessPivotResultColDef(Object processPivotResultColDef)
    {
        this.processPivotResultColDef = processPivotResultColDef;
        return this;
    }

    public Object getProcessPivotResultColGroupDef()
    {
        return processPivotResultColGroupDef;
    }

    public PivotingOptions<J> setProcessPivotResultColGroupDef(Object processPivotResultColGroupDef)
    {
        this.processPivotResultColGroupDef = processPivotResultColGroupDef;
        return this;
    }

    public Boolean getSuppressExpandablePivotGroups()
    {
        return suppressExpandablePivotGroups;
    }

    public PivotingOptions<J> setSuppressExpandablePivotGroups(Boolean suppressExpandablePivotGroups)
    {
        this.suppressExpandablePivotGroups = suppressExpandablePivotGroups;
        return this;
    }

    public Boolean getFunctionsReadOnly()
    {
        return functionsReadOnly;
    }

    public PivotingOptions<J> setFunctionsReadOnly(Boolean functionsReadOnly)
    {
        this.functionsReadOnly = functionsReadOnly;
        return this;
    }

    public Boolean getRemovePivotHeaderRowWhenSingleValueColumn()
    {
        return removePivotHeaderRowWhenSingleValueColumn;
    }

    public PivotingOptions<J> setRemovePivotHeaderRowWhenSingleValueColumn(Boolean removePivotHeaderRowWhenSingleValueColumn)
    {
        this.removePivotHeaderRowWhenSingleValueColumn = removePivotHeaderRowWhenSingleValueColumn;
        return this;
    }
}
