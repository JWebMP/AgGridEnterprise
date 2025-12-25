package com.jwebmp.plugins.aggridenterprise.options;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jwebmp.plugins.aggrid.options.AgGridColumnDef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Enterprise Column Definition that extends the Community Column Def with Enterprise options.
 * <p>
 * Adds aggregation-specific properties and related Enterprise-only flags.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("unchecked")
public class AgGridEnterpriseColumnDef<J extends AgGridEnterpriseColumnDef<J>> extends AgGridColumnDef<J>
{
    /**
     * Same as aggFunc, except only applied when creating a new column. Not applied when updating colDefs.
     */
    @JsonProperty("initialAggFunc")
    private Object initialAggFunc;

    /**
     * Set to true if you want to be able to aggregate by this column via the GUI.
     */
    @JsonProperty("enableValue")
    private Boolean enableValue;

    /**
     * Set to true to allow this column to be used in row grouping via the GUI.
     */
    @JsonProperty("enableRowGroup")
    private Boolean enableRowGroup;

    /**
     * Explicitly set the data type for this column's cells. Accepts one of the built-in types
     * or a custom string, or Boolean false to disable data type inference.
     */
    @JsonProperty("cellDataType")
    private Object cellDataType;

    /**
     * Aggregation functions allowed on this column e.g. ['sum', 'avg'].
     */
    @JsonProperty("allowedAggFuncs")
    private List<String> allowedAggFuncs;

    /**
     * The name of the aggregation function to use for this column when enabled via the GUI.
     */
    @JsonProperty("defaultAggFunc")
    private String defaultAggFunc;

    /**
     * When provided, an extra row group total row will be inserted at the specified position.
     * Values: 'top' | 'bottom' | UseGroupTotalRow (callback). We support enum for positions.
     */
    @JsonProperty("groupTotalRow")
    private Object groupTotalRow;

    /**
     * When provided, an extra grand total row will be inserted at the specified position.
     * Values: 'top' | 'bottom' | 'pinnedTop' | 'pinnedBottom'.
     */
    @JsonProperty("grandTotalRow")
    private Object grandTotalRow;

    /**
     * When true, column headers won't include the aggFunc name.
     */
    @JsonProperty("suppressAggFuncInHeader")
    private Boolean suppressAggFuncInHeader;

    /**
     * When using change detection, only the updated column will be re-aggregated.
     */
    @JsonProperty("aggregateOnlyChangedColumns")
    private Boolean aggregateOnlyChangedColumns;

    /**
     * Set to true so that aggregations are not impacted by filtering.
     */
    @JsonProperty("suppressAggFilteredOnly")
    private Boolean suppressAggFilteredOnly;

    /**
     * Set to determine whether filters should be applied on aggregated group values.
     * In AG Grid can be boolean or callback; we support boolean here for simplicity.
     */
    @JsonProperty("groupAggFiltering")
    private Object groupAggFiltering;

    /**
     * If true, and showing footer, aggregate data will always be displayed at both header and footer levels.
     */
    @JsonProperty("groupSuppressBlankHeader")
    private Boolean groupSuppressBlankHeader;

    /**
     * Suppress the sticky behaviour of the total rows. Can be true, or restricted: 'grand' | 'group'.
     */
    @JsonProperty("suppressStickyTotalRow")
    private Object suppressStickyTotalRow;

    /**
     * When using aggregations, the grid will always calculate the root level aggregation value.
     */
    @JsonProperty("alwaysAggregateAtRootLevel")
    private Boolean alwaysAggregateAtRootLevel;

    /**
     * Callback to use when you need access to more than the current column for aggregation.
     * Provide raw JavaScript function string; serialized without quotes.
     */
    @JsonProperty("getGroupRowAgg")
    @JsonRawValue
    private String getGroupRowAgg;

    public AgGridEnterpriseColumnDef()
    {
        super();
    }

    public AgGridEnterpriseColumnDef(String field)
    {
        super(field);
    }

    public AgGridEnterpriseColumnDef(String field, String headerName)
    {
        super(field, headerName);
    }

    // ===== Enums & typed helpers =====

    /**
     * Built-in aggregation functions. Serialized to lowercase per AG Grid API.
     */
    public enum AggFunc
    {
        SUM("sum"),
        MIN("min"),
        MAX("max"),
        COUNT("count"),
        AVG("avg"),
        FIRST("first"),
        LAST("last");

        private final String value;

        AggFunc(String value)
        {
            this.value = value;
        }

        @Override
        @JsonSerialize(using = ToStringSerializer.class)
        public String toString()
        {
            return value;
        }
    }

    public enum GroupTotalRowPosition
    {
        TOP("top"),
        BOTTOM("bottom");
        private final String value;

        GroupTotalRowPosition(String v) {this.value = v;}

        @Override
        public String toString() {return value;}
    }

    public enum GrandTotalRowPosition
    {
        TOP("top"),
        BOTTOM("bottom"),
        PINNED_TOP("pinnedTop"),
        PINNED_BOTTOM("pinnedBottom");
        private final String value;

        GrandTotalRowPosition(String v) {this.value = v;}

        @Override
        public String toString() {return value;}
    }

    public enum StickyTotalRowSuppression
    {
        GRAND("grand"),
        GROUP("group");
        private final String value;

        StickyTotalRowSuppression(String v) {this.value = v;}

        @Override
        public String toString() {return value;}
    }

    /**
     * Built-in cell data types supported by AG Grid. Serialized to the lowercase string values
     * expected by the grid. See docs: cellDataType can also be a custom string, or false to disable.
     */
    public enum CellDataType
    {
        TEXT("text"),
        NUMBER("number"),
        BOOLEAN("boolean"),
        DATE("date"),
        DATE_STRING("dateString"),
        DATE_TIME("dateTime"),
        OBJECT("object"),
        ARRAY("array");
        private final String value;

        CellDataType(String v) {this.value = v;}

        @Override
        @JsonSerialize(using = ToStringSerializer.class)
        public String toString() {return value;}
    }

    // ===== Getters / Setters (fluent) =====

    @Override
    public Object getAggFunc()
    {
        return super.getAggFunc();
    }

    public @org.jspecify.annotations.NonNull J setAggFunc(AggFunc aggFunc)
    {
        super.setAggFunc(aggFunc == null ? null : aggFunc.toString());
        return (J) this;
    }

    public @org.jspecify.annotations.NonNull J setAggFunc(String aggFuncName)
    {
        super.setAggFunc(aggFuncName);
        return (J) this;
    }

    public Object getInitialAggFunc()
    {
        return initialAggFunc;
    }

    public @org.jspecify.annotations.NonNull J setInitialAggFunc(AggFunc initial)
    {
        this.initialAggFunc = initial == null ? null : initial.toString();
        return (J) this;
    }

    public @org.jspecify.annotations.NonNull J setInitialAggFunc(String initial)
    {
        this.initialAggFunc = initial;
        return (J) this;
    }

    public Boolean getEnableValue()
    {
        return enableValue;
    }

    public @org.jspecify.annotations.NonNull J setEnableValue(Boolean enableValue)
    {
        this.enableValue = enableValue;
        return (J) this;
    }

    public Boolean getEnableRowGroup()
    {
        return enableRowGroup;
    }

    public @org.jspecify.annotations.NonNull J setEnableRowGroup(Boolean enableRowGroup)
    {
        this.enableRowGroup = enableRowGroup;
        return (J) this;
    }

    @Override
    public Boolean getEnablePivot()
    {
        return super.getEnablePivot();
    }

    @Override
    public @org.jspecify.annotations.NonNull J setEnablePivot(Boolean enablePivot)
    {
        super.setEnablePivot(enablePivot);
        return (J) this;
    }

    @Override
    public Boolean getPivot()
    {
        return super.getPivot();
    }

    @Override
    public @org.jspecify.annotations.NonNull J setPivot(Boolean pivot)
    {
        super.setPivot(pivot);
        return (J) this;
    }

    /**
     * Returns the configured cell data type. This may be a String (built-in or custom)
     * or the Boolean value false to disable data type inference.
     */
    public Object getCellDataType()
    {
        return cellDataType;
    }

    /**
     * Set a built-in cell data type using the enum.
     */
    public @org.jspecify.annotations.NonNull J setCellDataType(CellDataType type)
    {
        this.cellDataType = type == null ? null : type.toString();
        return (J) this;
    }

    /**
     * Set a custom cell data type by name (e.g. "myCustomType").
     */
    public @org.jspecify.annotations.NonNull J setCellDataType(String customType)
    {
        this.cellDataType = customType;
        return (J) this;
    }

    /**
     * Disable cell data type inference for this column. Equivalent to setting cellDataType = false in AG Grid.
     */
    public J disableCellDataTypeInference()
    {
        this.cellDataType = Boolean.FALSE;
        return (J) this;
    }

    public List<String> getAllowedAggFuncs()
    {
        return allowedAggFuncs;
    }

    public @org.jspecify.annotations.NonNull J setAllowedAggFuncs(List<String> allowedAggFuncs)
    {
        this.allowedAggFuncs = allowedAggFuncs;
        return (J) this;
    }

    public @org.jspecify.annotations.NonNull J setAllowedAggFuncs(AggFunc... funcs)
    {
        if (funcs == null)
        {
            this.allowedAggFuncs = null;
        }
        else
        {
            this.allowedAggFuncs = new ArrayList<>();
            Arrays.stream(funcs)
                  .filter(Objects::nonNull)
                  .forEach(f -> this.allowedAggFuncs.add(f.toString()));
        }
        return (J) this;
    }

    public String getDefaultAggFunc()
    {
        return defaultAggFunc;
    }

    public @org.jspecify.annotations.NonNull J setDefaultAggFunc(String defaultAggFunc)
    {
        this.defaultAggFunc = defaultAggFunc;
        return (J) this;
    }

    public @org.jspecify.annotations.NonNull J setDefaultAggFunc(AggFunc defaultAggFunc)
    {
        this.defaultAggFunc = defaultAggFunc == null ? null : defaultAggFunc.toString();
        return (J) this;
    }

    public Object getGroupTotalRow()
    {
        return groupTotalRow;
    }

    public @org.jspecify.annotations.NonNull J setGroupTotalRow(GroupTotalRowPosition pos)
    {
        this.groupTotalRow = pos == null ? null : pos.toString();
        return (J) this;
    }

    public @org.jspecify.annotations.NonNull J setGroupTotalRowRawCallback(String rawCallback)
    {
        this.groupTotalRow = rawCallback; // left as raw string; user can provide callback signature string
        return (J) this;
    }

    public Object getGrandTotalRow()
    {
        return grandTotalRow;
    }

    public @org.jspecify.annotations.NonNull J setGrandTotalRow(GrandTotalRowPosition pos)
    {
        this.grandTotalRow = pos == null ? null : pos.toString();
        return (J) this;
    }

    public Boolean getSuppressAggFuncInHeader()
    {
        return suppressAggFuncInHeader;
    }

    public @org.jspecify.annotations.NonNull J setSuppressAggFuncInHeader(Boolean suppressAggFuncInHeader)
    {
        this.suppressAggFuncInHeader = suppressAggFuncInHeader;
        return (J) this;
    }

    public Boolean getAggregateOnlyChangedColumns()
    {
        return aggregateOnlyChangedColumns;
    }

    public @org.jspecify.annotations.NonNull J setAggregateOnlyChangedColumns(Boolean aggregateOnlyChangedColumns)
    {
        this.aggregateOnlyChangedColumns = aggregateOnlyChangedColumns;
        return (J) this;
    }

    public Boolean getSuppressAggFilteredOnly()
    {
        return suppressAggFilteredOnly;
    }

    public @org.jspecify.annotations.NonNull J setSuppressAggFilteredOnly(Boolean suppressAggFilteredOnly)
    {
        this.suppressAggFilteredOnly = suppressAggFilteredOnly;
        return (J) this;
    }

    public Object getGroupAggFiltering()
    {
        return groupAggFiltering;
    }

    public @org.jspecify.annotations.NonNull J setGroupAggFiltering(Boolean groupAggFiltering)
    {
        this.groupAggFiltering = groupAggFiltering;
        return (J) this;
    }

    public Boolean getGroupSuppressBlankHeader()
    {
        return groupSuppressBlankHeader;
    }

    public @org.jspecify.annotations.NonNull J setGroupSuppressBlankHeader(Boolean groupSuppressBlankHeader)
    {
        this.groupSuppressBlankHeader = groupSuppressBlankHeader;
        return (J) this;
    }

    public Object getSuppressStickyTotalRow()
    {
        return suppressStickyTotalRow;
    }

    public @org.jspecify.annotations.NonNull J setSuppressStickyTotalRow(Boolean suppress)
    {
        this.suppressStickyTotalRow = suppress;
        return (J) this;
    }

    public @org.jspecify.annotations.NonNull J setSuppressStickyTotalRow(StickyTotalRowSuppression which)
    {
        this.suppressStickyTotalRow = which == null ? null : which.toString();
        return (J) this;
    }

    public Boolean getAlwaysAggregateAtRootLevel()
    {
        return alwaysAggregateAtRootLevel;
    }

    public @org.jspecify.annotations.NonNull J setAlwaysAggregateAtRootLevel(Boolean alwaysAggregateAtRootLevel)
    {
        this.alwaysAggregateAtRootLevel = alwaysAggregateAtRootLevel;
        return (J) this;
    }

    public String getGetGroupRowAgg()
    {
        return getGroupRowAgg;
    }

    /**
     * Set a raw JavaScript function for getGroupRowAgg, serialized without quotes.
     * Example: "params => { return { sum: params.nodes.reduce((a,n)=>a+n.data.value,0) }; }"
     */
    public @org.jspecify.annotations.NonNull J setGetGroupRowAgg(String getGroupRowAggRaw)
    {
        this.getGroupRowAgg = getGroupRowAggRaw;
        return (J) this;
    }
}
