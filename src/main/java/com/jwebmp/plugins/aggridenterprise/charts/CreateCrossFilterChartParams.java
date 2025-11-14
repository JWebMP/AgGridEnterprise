package com.jwebmp.plugins.aggridenterprise.charts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.jwebmp.core.htmlbuilder.javascript.JavaScriptPart;

import java.util.List;
import java.util.Map;

/**
 * Java representation of AG Grid's CreateCrossFilterChartParams for JWebMP.
 * This object serialises to a JSON/TS literal that can be passed to api.createCrossFilterChart(params).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCrossFilterChartParams extends JavaScriptPart<CreateCrossFilterChartParams>
{
    /**
     * Cross-filter chart type: 'column' | 'bar' | 'line' | 'area' | 'pie' | 'donut' | 'bubble' ...
     */
    @JsonProperty("chartType")
    private String chartType;

    @JsonProperty("cellRange")
    private ChartParamsCellRange cellRange;

    /**
     * aggFunc can be built-in name: 'sum' | 'min' | 'max' | 'count' | 'avg' | 'first' | 'last'.
     */
    @JsonProperty("aggFunc")
    private String aggFunc;

    /**
     * sort can be a boolean or a list of SortModelItem
     */
    @JsonProperty("sort")
    private Object sort;

    @JsonProperty("suppressChartRanges")
    private Boolean suppressChartRanges;

    @JsonProperty("chartThemeName")
    private String chartThemeName;

    /**
     * Theme overrides object. Use a Map to keep it flexible.
     */
    @JsonProperty("chartThemeOverrides")
    private Map<String, Object> chartThemeOverrides;

    @JsonProperty("unlinkChart")
    private Boolean unlinkChart;

    /**
     * Raw TS expression for HTMLElement container, e.g. document.querySelector('#pieChart') as any
     */
    @JsonProperty("chartContainer")
    @JsonRawValue
    private String chartContainerRaw;

    public String getChartType()
    {
        return chartType;
    }

    public CreateCrossFilterChartParams setChartType(String chartType)
    {
        this.chartType = chartType;
        return this;
    }

    /**
     * Overload to set chart type using the GridChartType enum.
     */
    public CreateCrossFilterChartParams setChartType(GridChartType chartType)
    {
        this.chartType = chartType == null ? null : chartType.toString();
        return this;
    }

    public ChartParamsCellRange getCellRange()
    {
        return cellRange;
    }

    public CreateCrossFilterChartParams setCellRange(ChartParamsCellRange cellRange)
    {
        this.cellRange = cellRange;
        return this;
    }

    public String getAggFunc()
    {
        return aggFunc;
    }

    public CreateCrossFilterChartParams setAggFunc(String aggFunc)
    {
        this.aggFunc = aggFunc;
        return this;
    }

    public Object getSort()
    {
        return sort;
    }

    public CreateCrossFilterChartParams setSort(Boolean sort)
    {
        this.sort = sort;
        return this;
    }

    public CreateCrossFilterChartParams setSort(List<SortModelItem> sortItems)
    {
        this.sort = sortItems;
        return this;
    }

    public Boolean getSuppressChartRanges()
    {
        return suppressChartRanges;
    }

    public CreateCrossFilterChartParams setSuppressChartRanges(Boolean suppressChartRanges)
    {
        this.suppressChartRanges = suppressChartRanges;
        return this;
    }

    public String getChartThemeName()
    {
        return chartThemeName;
    }

    public CreateCrossFilterChartParams setChartThemeName(String chartThemeName)
    {
        this.chartThemeName = chartThemeName;
        return this;
    }

    public Map<String, Object> getChartThemeOverrides()
    {
        return chartThemeOverrides;
    }

    public CreateCrossFilterChartParams setChartThemeOverrides(Map<String, Object> chartThemeOverrides)
    {
        this.chartThemeOverrides = chartThemeOverrides;
        return this;
    }

    public Boolean getUnlinkChart()
    {
        return unlinkChart;
    }

    public CreateCrossFilterChartParams setUnlinkChart(Boolean unlinkChart)
    {
        this.unlinkChart = unlinkChart;
        return this;
    }

    public String getChartContainerRaw()
    {
        return chartContainerRaw;
    }

    /**
     * Set a raw TS expression to identify the container element.
     * Example: setChartContainerTs("document.querySelector('#pieChart') as any");
     */
    public CreateCrossFilterChartParams setChartContainerTs(String chartContainerTsExpression)
    {
        this.chartContainerRaw = chartContainerTsExpression;
        return this;
    }
}
