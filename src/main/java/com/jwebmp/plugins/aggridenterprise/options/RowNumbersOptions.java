package com.jwebmp.plugins.aggridenterprise.options;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Options for the AG Grid Row Numbers feature.
 * According to AG Grid docs, grid option "rowNumbers" accepts boolean | RowNumbersOptions.
 * Keep this POJO minimal and extend as needed.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RowNumbersOptions
{
    /** Width of the row number column in pixels (if supported). */
    @JsonProperty("width")
    private Integer width;

    /** Whether the row number column is pinned to the left or right. Values: "left" | "right". */
    @JsonProperty("pinned")
    private String pinned;

    /** Optional header name to display instead of default. */
    @JsonProperty("headerName")
    private String headerName;

    public Integer getWidth()
    {
        return width;
    }

    public RowNumbersOptions setWidth(Integer width)
    {
        this.width = width;
        return this;
    }

    public String getPinned()
    {
        return pinned;
    }

    public RowNumbersOptions setPinned(String pinned)
    {
        this.pinned = pinned;
        return this;
    }

    public String getHeaderName()
    {
        return headerName;
    }

    public RowNumbersOptions setHeaderName(String headerName)
    {
        this.headerName = headerName;
        return this;
    }
}
