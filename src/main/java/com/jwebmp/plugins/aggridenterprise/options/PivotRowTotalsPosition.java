package com.jwebmp.plugins.aggridenterprise.options;

/**
 * Enum for pivot row totals positioning in AG Grid Enterprise.
 * Defines where pivot row totals are displayed.
 */
public enum PivotRowTotalsPosition
{
    BEFORE("before"),
    AFTER("after");

    private final String json;

    PivotRowTotalsPosition(String j)
    {
        this.json = j;
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
