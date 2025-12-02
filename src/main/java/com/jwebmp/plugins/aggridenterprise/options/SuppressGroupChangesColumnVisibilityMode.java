package com.jwebmp.plugins.aggridenterprise.options;

/**
 * Enum for controlling suppress group changes column visibility mode in AG Grid Enterprise.
 */
public enum SuppressGroupChangesColumnVisibilityMode
{
    SUPPRESS_HIDE_ON_GROUP("suppressHideOnGroup"),
    SUPPRESS_SHOW_ON_UNGROUP("suppressShowOnUngroup");

    private final String json;

    SuppressGroupChangesColumnVisibilityMode(String json)
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
