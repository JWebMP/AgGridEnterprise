package com.jwebmp.plugins.aggridenterprise.options.enums;

/**
 * Visibility for the Pivot Panel.
 */
public enum PivotPanelShow
{
    ALWAYS("always"),
    NEVER("never");

    private final String json;

    PivotPanelShow(String json)
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
