package com.jwebmp.plugins.aggridenterprise.options.modules;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * AG Grid Enterprise: Integrated Charts Configuration Module
 *
 * Encapsulates all chart-related options for the AG Grid Enterprise integrated charts feature.
 * This module handles chart rendering, theming, tool panels, and toolbar customization.
 *
 * @param <J> Generic type for fluent builder pattern (CRTP)
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChartsOptions<J extends ChartsOptions<J>>
{
    private J parent;

    /**
     * Enable integrated charts feature. When true, users can create charts from grid data.
     */
    @JsonProperty("enableCharts")
    private Boolean enableCharts;

    /**
     * Chart themes to use, e.g. ["ag-default", "ag-material", "ag-pastel"]
     */
    @JsonProperty("chartThemes")
    private List<String> chartThemes;

    /**
     * Optional chart theme overrides structure. Allows customizing theme properties
     * without creating a custom theme. Structure depends on selected chart theme.
     */
    @JsonProperty("chartThemeOverrides")
    private Object chartThemeOverrides;

    /**
     * Integrated Charts: configuration of tool panels in the charting UI.
     * Allows defining available panels and which one is open by default.
     */
    @JsonProperty("chartToolPanelsDef")
    private Object chartToolPanelsDef;

    /**
     * Chart groups definition. Allows organizing charts into groups
     * with custom panel configurations.
     */
    @JsonProperty("chartGroupsDef")
    private Object chartGroupsDef;

    /**
     * Suppress the chart toolbar's "Tools" button. When true, the toolbar button is hidden.
     * This is v34.2.0 related - toolbar is now visible by default (was hidden in v33).
     */
    @JsonProperty("suppressChartToolPanelsButton")
    private Boolean suppressChartToolPanelsButton;

    /**
     * Callback to customize toolbar items in the chart UI.
     * Returns array of toolbar item definitions.
     */
    @JsonProperty("getChartToolbarItems")
    private Object getChartToolbarItems;

    /**
     * Callback to create custom chart container element.
     * Allows customizing how/where charts are rendered.
     */
    @JsonProperty("createChartContainer")
    private Object createChartContainer;

    /**
     * Custom chart theme definitions. Allows defining completely custom themes
     * beyond the built-in AG Grid themes.
     */
    @JsonProperty("customChartThemes")
    private Object customChartThemes;

    /**
     * Custom menu items for chart context menu.
     * Allows adding app-specific chart operations.
     */
    @JsonProperty("chartMenuItems")
    private Object chartMenuItems;

    // ===== Constructors & Parent Management =====

    public ChartsOptions()
    {
    }

    public ChartsOptions(J parent)
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

    public ChartsOptions<J> setParent(J parent)
    {
        this.parent = parent;
        return this;
    }

    // ===== Property Getters & Fluent Setters =====

    public Boolean getEnableCharts()
    {
        return enableCharts;
    }

    public ChartsOptions<J> setEnableCharts(Boolean enableCharts)
    {
        this.enableCharts = enableCharts;
        return this;
    }

    public List<String> getChartThemes()
    {
        return chartThemes;
    }

    public ChartsOptions<J> setChartThemes(List<String> chartThemes)
    {
        this.chartThemes = chartThemes;
        return this;
    }

    public Object getChartThemeOverrides()
    {
        return chartThemeOverrides;
    }

    public ChartsOptions<J> setChartThemeOverrides(Object chartThemeOverrides)
    {
        this.chartThemeOverrides = chartThemeOverrides;
        return this;
    }

    public Object getChartToolPanelsDef()
    {
        return chartToolPanelsDef;
    }

    public ChartsOptions<J> setChartToolPanelsDef(Object chartToolPanelsDef)
    {
        this.chartToolPanelsDef = chartToolPanelsDef;
        return this;
    }

    public Object getChartGroupsDef()
    {
        return chartGroupsDef;
    }

    public ChartsOptions<J> setChartGroupsDef(Object chartGroupsDef)
    {
        this.chartGroupsDef = chartGroupsDef;
        return this;
    }

    public Boolean getSuppressChartToolPanelsButton()
    {
        return suppressChartToolPanelsButton;
    }

    public ChartsOptions<J> setSuppressChartToolPanelsButton(Boolean suppressChartToolPanelsButton)
    {
        this.suppressChartToolPanelsButton = suppressChartToolPanelsButton;
        return this;
    }

    public Object getGetChartToolbarItems()
    {
        return getChartToolbarItems;
    }

    public ChartsOptions<J> setGetChartToolbarItems(Object getChartToolbarItems)
    {
        this.getChartToolbarItems = getChartToolbarItems;
        return this;
    }

    public Object getCreateChartContainer()
    {
        return createChartContainer;
    }

    public ChartsOptions<J> setCreateChartContainer(Object createChartContainer)
    {
        this.createChartContainer = createChartContainer;
        return this;
    }

    public Object getCustomChartThemes()
    {
        return customChartThemes;
    }

    public ChartsOptions<J> setCustomChartThemes(Object customChartThemes)
    {
        this.customChartThemes = customChartThemes;
        return this;
    }

    public Object getChartMenuItems()
    {
        return chartMenuItems;
    }

    public ChartsOptions<J> setChartMenuItems(Object chartMenuItems)
    {
        this.chartMenuItems = chartMenuItems;
        return this;
    }
}
