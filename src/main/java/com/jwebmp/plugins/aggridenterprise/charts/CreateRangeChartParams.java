package com.jwebmp.plugins.aggridenterprise.charts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.jwebmp.core.htmlbuilder.javascript.JavaScriptPart;

import java.util.List;
import java.util.Map;

/**
 * Java representation of AG Grid's CreateRangeChartParams for JWebMP.
 * This object serialises to a JSON/TS literal that can be passed to api.createRangeChart(params).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateRangeChartParams extends JavaScriptPart<CreateRangeChartParams>
{
    /**
     * Range chart type: groupedColumn, line, area, scatter, pie, etc. See GridChartType
     */
    @JsonProperty("chartType")
    private String chartType;

    @JsonProperty("cellRange")
    private ChartParamsCellRange cellRange;

    /**
     * Optional aggregate function for series data
     */
    @JsonProperty("aggFunc")
    private String aggFunc;

    /**
     * Suppress highlighting of chart ranges in grid when chart gets focus
     */
    @JsonProperty("suppressChartRanges")
    private Boolean suppressChartRanges;

    /**
     * Theme selection and overrides
     */
    @JsonProperty("chartThemeName")
    private String chartThemeName;

    @JsonProperty("chartThemeOverrides")
    private Map<String, Object> chartThemeOverrides;

    /**
     * Combination charts use seriesChartTypes to specify per-series chart type and axis binding
     */
    @JsonProperty("seriesChartTypes")
    private List<SeriesChartTypeItem> seriesChartTypes;

    /**
     * If true, unlink from grid updates after creation
     */
    @JsonProperty("unlinkChart")
    private Boolean unlinkChart;

    /**
     * Raw TS expression for HTMLElement container, e.g. document.querySelector('#myChart') as HTMLElement
     */
    @JsonProperty("chartContainer")
    @JsonRawValue
    private String chartContainerRaw;

    /**
     * Configuration for dynamic series coloring based on data values
     */
    @JsonProperty("seriesColorConfig")
    private SeriesColorConfiguration seriesColorConfig;

    public String getChartType()
    {
        return chartType;
    }

    public CreateRangeChartParams setChartType(String chartType)
    {
        this.chartType = chartType;
        return this;
    }

    public CreateRangeChartParams setChartType(GridChartType chartType)
    {
        this.chartType = chartType == null ? null : chartType.toString();
        return this;
    }

    public ChartParamsCellRange getCellRange()
    {
        return cellRange;
    }

    public CreateRangeChartParams setCellRange(ChartParamsCellRange cellRange)
    {
        this.cellRange = cellRange;
        return this;
    }

    public String getAggFunc()
    {
        return aggFunc;
    }

    public CreateRangeChartParams setAggFunc(String aggFunc)
    {
        this.aggFunc = aggFunc;
        return this;
    }

    public Boolean getSuppressChartRanges()
    {
        return suppressChartRanges;
    }

    public CreateRangeChartParams setSuppressChartRanges(Boolean suppressChartRanges)
    {
        this.suppressChartRanges = suppressChartRanges;
        return this;
    }

    public String getChartThemeName()
    {
        return chartThemeName;
    }

    public CreateRangeChartParams setChartThemeName(String chartThemeName)
    {
        this.chartThemeName = chartThemeName;
        return this;
    }

    public Map<String, Object> getChartThemeOverrides()
    {
        return chartThemeOverrides;
    }

    public CreateRangeChartParams setChartThemeOverrides(Map<String, Object> chartThemeOverrides)
    {
        this.chartThemeOverrides = chartThemeOverrides;
        return this;
    }

    public List<SeriesChartTypeItem> getSeriesChartTypes()
    {
        return seriesChartTypes;
    }

    public CreateRangeChartParams setSeriesChartTypes(List<SeriesChartTypeItem> seriesChartTypes)
    {
        this.seriesChartTypes = seriesChartTypes;
        return this;
    }

    public Boolean getUnlinkChart()
    {
        return unlinkChart;
    }

    public CreateRangeChartParams setUnlinkChart(Boolean unlinkChart)
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
     * Example: setChartContainerTs("document.querySelector('#myChart') as HTMLElement");
     */
    public CreateRangeChartParams setChartContainerTs(String chartContainerTsExpression)
    {
        this.chartContainerRaw = chartContainerTsExpression;
        return this;
    }

    public SeriesColorConfiguration getSeriesColorConfig()
    {
        return seriesColorConfig;
    }

    public CreateRangeChartParams setSeriesColorConfig(SeriesColorConfiguration seriesColorConfig)
    {
        this.seriesColorConfig = seriesColorConfig;
        return this;
    }

    /**
     * Convenience method to enable VALUE_GRADIENT series coloring.
     * Colors smoothly transition from minColor to maxColor based on data values.
     */
    public CreateRangeChartParams setValueGradientColors(String minColor, String maxColor)
    {
        if (this.seriesColorConfig == null)
        {
            this.seriesColorConfig = new SeriesColorConfiguration();
        }
        this.seriesColorConfig.setValueGradientColors(minColor, maxColor);
        return this;
    }

    /**
     * Convenience method to enable VALUE_RANGE series coloring.
     * Maps specific value thresholds to corresponding colors.
     */
    public CreateRangeChartParams setValueRangeColors(Map<Number, String> thresholds)
    {
        if (this.seriesColorConfig == null)
        {
            this.seriesColorConfig = new SeriesColorConfiguration();
        }
        this.seriesColorConfig.setValueRangeColors(thresholds);
        return this;
    }

    /**
     * Convenience method to enable POSITIVE_NEGATIVE series coloring.
     * Uses specified colors for positive and negative values.
     */
    public CreateRangeChartParams setPositiveNegativeColors(String positiveColor, String negativeColor)
    {
        if (this.seriesColorConfig == null)
        {
            this.seriesColorConfig = new SeriesColorConfiguration();
        }
        this.seriesColorConfig.setPositiveNegativeColors(positiveColor, negativeColor);
        return this;
    }

    /**
     * Convenience method to enable POSITIVE_NEGATIVE series coloring with zero color.
     * Uses specified colors for positive, negative, and zero values.
     */
    public CreateRangeChartParams setPositiveNegativeColors(String positiveColor, String negativeColor, String zeroColor)
    {
        if (this.seriesColorConfig == null)
        {
            this.seriesColorConfig = new SeriesColorConfiguration();
        }
        this.seriesColorConfig.setPositiveNegativeColors(positiveColor, negativeColor, zeroColor);
        return this;
    }

    /**
     * Convenience method to enable CUSTOM_CALLBACK series coloring with a raw JS function.
     * Example: setCustomColorFunction("(value) => value > 100 ? 'green' : 'red'");
     */
    public CreateRangeChartParams setCustomColorFunction(String colorFunction)
    {
        if (this.seriesColorConfig == null)
        {
            this.seriesColorConfig = new SeriesColorConfiguration();
        }
        this.seriesColorConfig.setCustomColorFunction(colorFunction);
        return this;
    }
}
