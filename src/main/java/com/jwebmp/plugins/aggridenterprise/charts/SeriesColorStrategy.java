package com.jwebmp.plugins.aggridenterprise.charts;

/**
 * Strategy for applying dynamic colors to chart series based on values.
 * Allows conditional coloring without needing stacked bars workaround.
 */
public enum SeriesColorStrategy
{
    /**
     * Single solid color per series (default)
     */
    SOLID("solid"),

    /**
     * Color varies based on value magnitude (gradient from low to high)
     */
    VALUE_GRADIENT("valueGradient"),

    /**
     * Color based on value range (good/warning/bad thresholds)
     */
    VALUE_RANGE("valueRange"),

    /**
     * Color alternates based on positive/negative values
     */
    POSITIVE_NEGATIVE("positiveNegative"),

    /**
     * Custom callback function determines color per data point
     */
    CUSTOM_CALLBACK("customCallback");

    private final String strategyName;

    SeriesColorStrategy(String strategyName)
    {
        this.strategyName = strategyName;
    }

    public String getStrategyName()
    {
        return strategyName;
    }

    @Override
    public String toString()
    {
        return strategyName;
    }
}
