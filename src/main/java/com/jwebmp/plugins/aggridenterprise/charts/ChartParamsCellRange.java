package com.jwebmp.plugins.aggridenterprise.charts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jwebmp.core.htmlbuilder.javascript.JavaScriptPart;

import java.util.List;

/**
 * Represents the cell range for cross filter chart creation.
 * For cross-filter charts AG Grid charts all rows by default; typically you only need to specify columns.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChartParamsCellRange extends JavaScriptPart<ChartParamsCellRange>
{
    @JsonProperty("columns")
    private List<String> columns;

    // Reserved for potential future use; AG Grid cross-filter ignores row range.
    @JsonProperty("rowStartIndex")
    private Integer rowStartIndex;

    @JsonProperty("rowEndIndex")
    private Integer rowEndIndex;

    public List<String> getColumns()
    {
        return columns;
    }

    public ChartParamsCellRange setColumns(List<String> columns)
    {
        this.columns = columns;
        return this;
    }

    public Integer getRowStartIndex()
    {
        return rowStartIndex;
    }

    public ChartParamsCellRange setRowStartIndex(Integer rowStartIndex)
    {
        this.rowStartIndex = rowStartIndex;
        return this;
    }

    public Integer getRowEndIndex()
    {
        return rowEndIndex;
    }

    public ChartParamsCellRange setRowEndIndex(Integer rowEndIndex)
    {
        this.rowEndIndex = rowEndIndex;
        return this;
    }
}
