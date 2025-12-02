package com.jwebmp.plugins.aggridenterprise.options;

/**
 * Enum for grand total row positioning in AG Grid Enterprise.
 * Defines where grand total rows are displayed in the grid.
 */
public enum GrandTotalRowPosition
{
    TOP("top"),
    BOTTOM("bottom"),
    PINNED_TOP("pinnedTop"),
    PINNED_BOTTOM("pinnedBottom");

    private final String json;

    GrandTotalRowPosition(String json)
    {
        this.json = json;
    }

    public String getJson()
    {
        return json;
    }
}
