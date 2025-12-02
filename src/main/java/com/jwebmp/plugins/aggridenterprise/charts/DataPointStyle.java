package com.jwebmp.plugins.aggridenterprise.charts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jwebmp.core.htmlbuilder.javascript.JavaScriptPart;
import org.jspecify.annotations.Nullable;

/**
 * Styling for individual data points in a chart series.
 * Supports color, size, opacity, and custom styling.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataPointStyle extends JavaScriptPart<DataPointStyle>
{
    @JsonProperty("fill")
    @Nullable
    private String fill;

    @JsonProperty("stroke")
    @Nullable
    private String stroke;

    @JsonProperty("opacity")
    @Nullable
    private Double opacity;

    @JsonProperty("strokeWidth")
    @Nullable
    private Integer strokeWidth;

    @JsonProperty("size")
    @Nullable
    private Integer size;

    public DataPointStyle()
    {
    }

    public DataPointStyle(@Nullable String fill)
    {
        this.fill = fill;
    }

    @Nullable
    public String getFill()
    {
        return fill;
    }

    public DataPointStyle setFill(@Nullable String fill)
    {
        this.fill = fill;
        return this;
    }

    @Nullable
    public String getStroke()
    {
        return stroke;
    }

    public DataPointStyle setStroke(@Nullable String stroke)
    {
        this.stroke = stroke;
        return this;
    }

    @Nullable
    public Double getOpacity()
    {
        return opacity;
    }

    public DataPointStyle setOpacity(@Nullable Double opacity)
    {
        this.opacity = opacity;
        return this;
    }

    @Nullable
    public Integer getStrokeWidth()
    {
        return strokeWidth;
    }

    public DataPointStyle setStrokeWidth(@Nullable Integer strokeWidth)
    {
        this.strokeWidth = strokeWidth;
        return this;
    }

    @Nullable
    public Integer getSize()
    {
        return size;
    }

    public DataPointStyle setSize(@Nullable Integer size)
    {
        this.size = size;
        return this;
    }
}
