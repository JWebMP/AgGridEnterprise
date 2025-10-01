package com.jwebmp.plugins.aggridenterprise.options.enums;

/**
 * When to show the Row Group Panel at the top of the grid.
 */
public enum RowGroupPanelShow
{
    ALWAYS("always"),
    ONLY_WHEN_GROUPING("onlyWhenGrouping"),
    NEVER("never");

    private final String json;

    RowGroupPanelShow(String json)
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
