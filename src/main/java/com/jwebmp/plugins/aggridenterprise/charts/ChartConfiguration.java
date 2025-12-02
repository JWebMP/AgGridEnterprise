package com.jwebmp.plugins.aggridenterprise.charts;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * Configuration metadata for an AG Charts Enterprise instance linked to AG Grid data.
 *
 * Encapsulates chart type, data source mapping, themes, and linking metadata
 * for coordinating between grid and chart.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChartConfiguration
{
    /**
     * Unique identifier for the chart within the grid context.
     */
    @JsonProperty("chartId")
    private String chartId;

    /**
     * Chart type: bar, line, area, pie, scatter, bubble, gauge, waterfall, etc.
     */
    @JsonProperty("chartType")
    private String chartType;

    /**
     * Identifier of the data bridge providing grid data to this chart.
     * References a registered IChartDataBridge instance.
     */
    @JsonProperty("dataBridgeId")
    private String dataBridgeId;

    /**
     * Grid ID this chart is linked to. Used for cross-filtering and selection sync.
     */
    @JsonProperty("linkedGridId")
    private String linkedGridId;

    /**
     * Field mapping: grid column name → chart data property.
     * Example: {"region": "x", "sales": "y", "productId": "id"}
     */
    @JsonProperty("fieldMapping")
    private Map<String, String> fieldMapping;

    /**
     * Chart title displayed in the UI.
     */
    @JsonProperty("title")
    private String title;

    /**
     * Theme names to apply: ["ag-default", "ag-material"] etc.
     */
    @JsonProperty("themes")
    private java.util.List<String> themes;

    /**
     * Theme overrides as a JSON object or map.
     */
    @JsonProperty("themeOverrides")
    private Object themeOverrides;

    /**
     * Enable cross-filtering: when true, chart selection filters the grid.
     */
    @JsonProperty("enableCrossFiltering")
    private Boolean enableCrossFiltering = false;

    /**
     * Enable selection sync: when true, grid selection highlights chart data.
     */
    @JsonProperty("enableSelectionSync")
    private Boolean enableSelectionSync = false;

    /**
     * Enable chart toolbar: allows user customization of chart display.
     */
    @JsonProperty("enableToolbar")
    private Boolean enableToolbar = true;

    /**
     * Container element ID where the chart will be rendered.
     * If null, assumes default chart container from grid.
     */
    @JsonProperty("containerId")
    private String containerId;

    /**
     * Additional custom options passed directly to AG Charts.
     */
    @JsonProperty("customOptions")
    private Object customOptions;

    // ===== Constructors =====

    public ChartConfiguration()
    {
    }

    public ChartConfiguration(String chartId, String chartType)
    {
        this.chartId = chartId;
        this.chartType = chartType;
    }

    // ===== Getters & Setters (Fluent) =====

    public String getChartId()
    {
        return chartId;
    }

    public ChartConfiguration setChartId(String chartId)
    {
        this.chartId = chartId;
        return this;
    }

    public String getChartType()
    {
        return chartType;
    }

    public ChartConfiguration setChartType(String chartType)
    {
        this.chartType = chartType;
        return this;
    }

    public String getDataBridgeId()
    {
        return dataBridgeId;
    }

    public ChartConfiguration setDataBridgeId(String dataBridgeId)
    {
        this.dataBridgeId = dataBridgeId;
        return this;
    }

    public String getLinkedGridId()
    {
        return linkedGridId;
    }

    public ChartConfiguration setLinkedGridId(String linkedGridId)
    {
        this.linkedGridId = linkedGridId;
        return this;
    }

    public Map<String, String> getFieldMapping()
    {
        return fieldMapping;
    }

    public ChartConfiguration setFieldMapping(Map<String, String> fieldMapping)
    {
        this.fieldMapping = fieldMapping;
        return this;
    }

    public String getTitle()
    {
        return title;
    }

    public ChartConfiguration setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public java.util.List<String> getThemes()
    {
        return themes;
    }

    public ChartConfiguration setThemes(java.util.List<String> themes)
    {
        this.themes = themes;
        return this;
    }

    public Object getThemeOverrides()
    {
        return themeOverrides;
    }

    public ChartConfiguration setThemeOverrides(Object themeOverrides)
    {
        this.themeOverrides = themeOverrides;
        return this;
    }

    public Boolean getEnableCrossFiltering()
    {
        return enableCrossFiltering;
    }

    public ChartConfiguration setEnableCrossFiltering(Boolean enableCrossFiltering)
    {
        this.enableCrossFiltering = enableCrossFiltering;
        return this;
    }

    public Boolean getEnableSelectionSync()
    {
        return enableSelectionSync;
    }

    public ChartConfiguration setEnableSelectionSync(Boolean enableSelectionSync)
    {
        this.enableSelectionSync = enableSelectionSync;
        return this;
    }

    public Boolean getEnableToolbar()
    {
        return enableToolbar;
    }

    public ChartConfiguration setEnableToolbar(Boolean enableToolbar)
    {
        this.enableToolbar = enableToolbar;
        return this;
    }

    public String getContainerId()
    {
        return containerId;
    }

    public ChartConfiguration setContainerId(String containerId)
    {
        this.containerId = containerId;
        return this;
    }

    public Object getCustomOptions()
    {
        return customOptions;
    }

    public ChartConfiguration setCustomOptions(Object customOptions)
    {
        this.customOptions = customOptions;
        return this;
    }
}
