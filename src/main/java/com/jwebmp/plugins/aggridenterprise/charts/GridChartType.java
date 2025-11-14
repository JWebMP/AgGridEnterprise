package com.jwebmp.plugins.aggridenterprise.charts;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * Enumeration of AG Grid Integrated Chart types.
 * Values serialise to the exact string keys expected by the AG Grid API.
 *
 * Includes range-chart types from the documentation (Column/Bar, Pie/Donut,
 * Line/Area, Scatter/Bubble, Polar, Statistical, Hierarchical, Specialized, Combination).
 */
public enum GridChartType {
    // Column / Bar
    GROUPED_COLUMN("groupedColumn"),
    STACKED_COLUMN("stackedColumn"),
    NORMALIZED_COLUMN("normalizedColumn"),
    GROUPED_BAR("groupedBar"),
    STACKED_BAR("stackedBar"),
    NORMALIZED_BAR("normalizedBar"),

    // Pie / Donut
    PIE("pie"),
    DONUT("donut"),

    // Line / Area
    LINE("line"),
    STACKED_LINE("stackedLine"),
    NORMALIZED_LINE("normalizedLine"),
    AREA("area"),
    STACKED_AREA("stackedArea"),
    NORMALIZED_AREA("normalizedArea"),

    // Scatter / Bubble
    SCATTER("scatter"),
    BUBBLE("bubble"),

    // Polar
    RADAR_LINE("radarLine"),
    RADAR_AREA("radarArea"),
    NIGHTINGALE("nightingale"),
    RADIAL_COLUMN("radialColumn"),
    RADIAL_BAR("radialBar"),

    // Statistical
    BOX_PLOT("boxPlot"),
    HISTOGRAM("histogram"),
    RANGE_BAR("rangeBar"),
    RANGE_AREA("rangeArea"),

    // Hierarchical
    TREEMAP("treemap"),
    SUNBURST("sunburst"),

    // Specialized
    HEATMAP("heatmap"),
    WATERFALL("waterfall"),

    // Funnel family
    FUNNEL("funnel"),
    CONE_FUNNEL("coneFunnel"),
    PYRAMID("pyramid"),

    // Combination
    COLUMN_LINE_COMBO("columnLineCombo"),
    AREA_COLUMN_COMBO("areaColumnCombo"),
    CUSTOM_COMBO("customCombo");

    private final String value;

    GridChartType(String value) {
        this.value = value;
    }

    @Override
    @JsonSerialize(using = ToStringSerializer.class)
    public String toString() {
        return value;
    }
}
