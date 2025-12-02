package com.jwebmp.plugins.aggridenterprise.options;

/**
 * Enum for controlling sticky behavior suppression of total rows in AG Grid Enterprise.
 */
public enum StickyTotalRowSuppression
{
    GRAND("grand"),
    GROUP("group");

    private final String json;

    StickyTotalRowSuppression(String json)
    {
        this.json = json;
    }

    public String getJson()
    {
        return json;
    }
}
