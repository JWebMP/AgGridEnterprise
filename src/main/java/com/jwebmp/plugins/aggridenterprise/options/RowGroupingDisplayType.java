package com.jwebmp.plugins.aggridenterprise.options;

/**
 * Enum for row grouping display types in AG Grid Enterprise.
 * Controls how grouped rows are rendered.
 */
public enum RowGroupingDisplayType
{
    SINGLE_COLUMN("singleColumn"),
    MULTIPLE_COLUMNS("multipleColumns"),
    GROUP_ROWS("groupRows"),
    CUSTOM("custom");

    private final String json;

    RowGroupingDisplayType(String json)
    {
        this.json = json;
    }

    public String getJson()
    {
        return json;
    }

    @Override
    public String toString()
    {
        return json;
    }
}
