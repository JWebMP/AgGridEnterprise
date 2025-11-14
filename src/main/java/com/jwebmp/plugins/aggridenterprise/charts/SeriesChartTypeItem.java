package com.jwebmp.plugins.aggridenterprise.charts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jwebmp.core.htmlbuilder.javascript.JavaScriptPart;

/**
 * Represents one item in the seriesChartTypes array for combination (customCombo) charts.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeriesChartTypeItem extends JavaScriptPart<SeriesChartTypeItem>
{
    /**
     * Column id for the series (colId in AG Grid)
     */
    @JsonProperty("colId")
    private String colId;

    /**
     * Chart type for this series. Use GridChartType or raw string.
     */
    @JsonProperty("chartType")
    private String chartType;

    /**
     * Whether to bind this series to the secondary axis (for dual-axis charts)
     */
    @JsonProperty("secondaryAxis")
    private Boolean secondaryAxis;

    public String getColId()
    {
        return colId;
    }

    public SeriesChartTypeItem setColId(String colId)
    {
        this.colId = colId;
        return this;
    }

    public String getChartType()
    {
        return chartType;
    }

    public SeriesChartTypeItem setChartType(String chartType)
    {
        this.chartType = chartType;
        return this;
    }

    public SeriesChartTypeItem setChartType(GridChartType chartType)
    {
        this.chartType = chartType == null ? null : chartType.toString();
        return this;
    }

    public Boolean getSecondaryAxis()
    {
        return secondaryAxis;
    }

    public SeriesChartTypeItem setSecondaryAxis(Boolean secondaryAxis)
    {
        this.secondaryAxis = secondaryAxis;
        return this;
    }
}
