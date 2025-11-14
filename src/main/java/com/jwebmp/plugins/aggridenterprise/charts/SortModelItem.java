package com.jwebmp.plugins.aggridenterprise.charts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jwebmp.core.htmlbuilder.javascript.JavaScriptPart;

/**
 * Represents AG Grid sort model item used for chart sorting.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SortModelItem extends JavaScriptPart<SortModelItem>
{
    @JsonProperty("colId")
    private String colId;

    /** sort: 'asc' | 'desc' */
    @JsonProperty("sort")
    private String sort;

    public String getColId()
    {
        return colId;
    }

    public SortModelItem setColId(String colId)
    {
        this.colId = colId;
        return this;
    }

    public String getSort()
    {
        return sort;
    }

    public SortModelItem setSort(String sort)
    {
        this.sort = sort;
        return this;
    }
}
