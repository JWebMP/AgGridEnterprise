package com.jwebmp.plugins.aggridenterprise.charts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.jwebmp.core.htmlbuilder.javascript.JavaScriptPart;
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration for dynamic series coloring based on data values.
 * Supports multiple strategies: solid colors, gradients, ranges, positive/negative, and custom callbacks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeriesColorConfiguration extends JavaScriptPart<SeriesColorConfiguration>
{
    @JsonProperty("strategy")
    @Nullable
    private String strategy;

    @JsonProperty("colorPalette")
    @Nullable
    private List<String> colorPalette;

    @JsonProperty("thresholds")
    @Nullable
    private Map<Number, String> thresholds;

    @JsonProperty("minValue")
    @Nullable
    private Number minValue;

    @JsonProperty("maxValue")
    @Nullable
    private Number maxValue;

    @JsonProperty("minColor")
    @Nullable
    private String minColor;

    @JsonProperty("maxColor")
    @Nullable
    private String maxColor;

    @JsonProperty("positiveColor")
    @Nullable
    private String positiveColor;

    @JsonProperty("negativeColor")
    @Nullable
    private String negativeColor;

    @JsonProperty("zeroColor")
    @Nullable
    private String zeroColor;

    @JsonProperty("colorFunction")
    @JsonRawValue
    @Nullable
    private String colorFunctionRaw;

    public SeriesColorConfiguration()
    {
    }

    public SeriesColorConfiguration(@Nullable SeriesColorStrategy strategy)
    {
        if (strategy != null)
        {
            this.strategy = strategy.getStrategyName();
        }
    }

    @Nullable
    public String getStrategy()
    {
        return strategy;
    }

    public SeriesColorConfiguration setStrategy(@Nullable SeriesColorStrategy strategy)
    {
        if (strategy != null)
        {
            this.strategy = strategy.getStrategyName();
        }
        else
        {
            this.strategy = null;
        }
        return this;
    }

    public SeriesColorConfiguration setStrategy(@Nullable String strategy)
    {
        this.strategy = strategy;
        return this;
    }

    @Nullable
    public List<String> getColorPalette()
    {
        return colorPalette;
    }

    public SeriesColorConfiguration setColorPalette(@Nullable List<String> colorPalette)
    {
        this.colorPalette = colorPalette;
        return this;
    }

    @Nullable
    public Map<Number, String> getThresholds()
    {
        return thresholds;
    }

    public SeriesColorConfiguration addThreshold(@Nullable Number threshold, @Nullable String color)
    {
        if (threshold != null && color != null)
        {
            if (this.thresholds == null)
            {
                this.thresholds = new LinkedHashMap<>();
            }
            this.thresholds.put(threshold, color);
        }
        return this;
    }

    public SeriesColorConfiguration setThresholds(@Nullable Map<Number, String> thresholds)
    {
        this.thresholds = thresholds;
        return this;
    }

    @Nullable
    public Number getMinValue()
    {
        return minValue;
    }

    public SeriesColorConfiguration setMinValue(@Nullable Number minValue)
    {
        this.minValue = minValue;
        return this;
    }

    @Nullable
    public Number getMaxValue()
    {
        return maxValue;
    }

    public SeriesColorConfiguration setMaxValue(@Nullable Number maxValue)
    {
        this.maxValue = maxValue;
        return this;
    }

    @Nullable
    public String getMinColor()
    {
        return minColor;
    }

    public SeriesColorConfiguration setMinColor(@Nullable String minColor)
    {
        this.minColor = minColor;
        return this;
    }

    @Nullable
    public String getMaxColor()
    {
        return maxColor;
    }

    public SeriesColorConfiguration setMaxColor(@Nullable String maxColor)
    {
        this.maxColor = maxColor;
        return this;
    }

    /**
     * Convenience method to set VALUE_GRADIENT strategy with min and max colors.
     */
    public SeriesColorConfiguration setValueGradientColors(@Nullable String minColor, @Nullable String maxColor)
    {
        setStrategy(SeriesColorStrategy.VALUE_GRADIENT);
        setMinColor(minColor);
        setMaxColor(maxColor);
        return this;
    }

    /**
     * Convenience method to set VALUE_RANGE strategy with threshold-based colors.
     */
    public SeriesColorConfiguration setValueRangeColors(@Nullable Map<Number, String> thresholds)
    {
        setStrategy(SeriesColorStrategy.VALUE_RANGE);
        setThresholds(thresholds);
        return this;
    }

    @Nullable
    public String getPositiveColor()
    {
        return positiveColor;
    }

    public SeriesColorConfiguration setPositiveColor(@Nullable String positiveColor)
    {
        this.positiveColor = positiveColor;
        return this;
    }

    @Nullable
    public String getNegativeColor()
    {
        return negativeColor;
    }

    public SeriesColorConfiguration setNegativeColor(@Nullable String negativeColor)
    {
        this.negativeColor = negativeColor;
        return this;
    }

    @Nullable
    public String getZeroColor()
    {
        return zeroColor;
    }

    public SeriesColorConfiguration setZeroColor(@Nullable String zeroColor)
    {
        this.zeroColor = zeroColor;
        return this;
    }

    /**
     * Convenience method to set POSITIVE_NEGATIVE strategy with color options.
     */
    public SeriesColorConfiguration setPositiveNegativeColors(@Nullable String positiveColor, @Nullable String negativeColor)
    {
        return setPositiveNegativeColors(positiveColor, negativeColor, null);
    }

    /**
     * Convenience method to set POSITIVE_NEGATIVE strategy with all color options.
     */
    public SeriesColorConfiguration setPositiveNegativeColors(@Nullable String positiveColor, @Nullable String negativeColor, @Nullable String zeroColor)
    {
        setStrategy(SeriesColorStrategy.POSITIVE_NEGATIVE);
        setPositiveColor(positiveColor);
        setNegativeColor(negativeColor);
        setZeroColor(zeroColor);
        return this;
    }

    @Nullable
    public String getColorFunctionRaw()
    {
        return colorFunctionRaw;
    }

    public SeriesColorConfiguration setColorFunctionRaw(@Nullable String colorFunctionRaw)
    {
        this.colorFunctionRaw = colorFunctionRaw;
        return this;
    }

    /**
     * Convenience method to set CUSTOM_CALLBACK strategy with a raw JS function.
     * Example: setCustomColorFunction("(value) => value > 100 ? 'green' : 'red'")
     */
    public SeriesColorConfiguration setCustomColorFunction(@Nullable String colorFunction)
    {
        setStrategy(SeriesColorStrategy.CUSTOM_CALLBACK);
        setColorFunctionRaw(colorFunction);
        return this;
    }
}
