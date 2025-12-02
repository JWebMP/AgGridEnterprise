package com.jwebmp.plugins.aggridenterprise.options;

/**
 * Enum for AG Grid Enterprise chart tool panel identifiers.
 * Identifies which tool panel to display in the charting UI.
 */
public enum ToolPanelId
{
    DATA("data"),
    FORMAT("format"),
    SETTINGS("settings");

    private final String json;

    ToolPanelId(String json)
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
