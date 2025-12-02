package com.jwebmp.plugins.aggridenterprise.options;

/**
 * Enum for group total row positioning in AG Grid Enterprise.
 * Defines where group total rows are displayed relative to group data.
 */
public enum GroupTotalRowPosition
{
    TOP("top"),
    BOTTOM("bottom");

    private final String json;

    GroupTotalRowPosition(String json)
    {
        this.json = json;
    }

    public String getJson()
    {
        return json;
    }
}
