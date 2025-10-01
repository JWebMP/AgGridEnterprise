package com.jwebmp.plugins.aggridenterprise.options.enums;

/**
 * Supported AG Grid row model types. Enterprise adds serverSide.
 */
public enum RowModelTypeEnterprise
{
    CLIENT_SIDE("clientSide"),
    SERVER_SIDE("serverSide"),
    VIEWPORT("viewport"),
    INFINITE("infinite");

    private final String json;

    RowModelTypeEnterprise(String json)
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
