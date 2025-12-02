package com.jwebmp.plugins.aggridenterprise.options.modules;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * AG Grid Enterprise: Aggregation Functions Configuration Module
 *
 * Encapsulates aggregation function configuration including custom functions,
 * filtering behavior, and aggregation callbacks.
 *
 * @param <J> Generic type for fluent builder pattern (CRTP)
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AggregationOptions<J extends AggregationOptions<J>>
{
    private J parent;

    /**
     * Map of custom aggregation functions. Key is function name, value is the function implementation.
     */
    @JsonProperty("aggFuncs")
    private Map<String, Object> aggFuncs;

    /**
     * Only recalculate aggregation for columns that have changed. Optimization for large datasets.
     */
    @JsonProperty("aggregateOnlyChangedColumns")
    private Boolean aggregateOnlyChangedColumns;

    /**
     * Aggregate all rows, not just those that match the current filter.
     * Set suppressSetFilterByDefault to prevent default set filter from applying.
     */
    @JsonProperty("suppressAggFilteredOnly")
    private Boolean suppressAggFilteredOnly;

    /**
     * Aggregate data before or after filtering is applied. Options: "before" | "after"
     */
    @JsonProperty("groupAggFiltering")
    private String groupAggFiltering;

    /**
     * Always aggregate at the root level, even if no grouping is configured.
     * Useful for showing totals for all rows.
     */
    @JsonProperty("alwaysAggregateAtRootLevel")
    private Boolean alwaysAggregateAtRootLevel;

    /**
     * Callback to get custom aggregation value for a group row.
     */
    @JsonProperty("getGroupRowAgg")
    private Object getGroupRowAgg;

    /**
     * Don't set filter on initialization. Lets user explicitly configure filters.
     */
    @JsonProperty("suppressSetFilterByDefault")
    private Boolean suppressSetFilterByDefault;

    // ===== Constructors & Parent Management =====

    public AggregationOptions()
    {
    }

    public AggregationOptions(J parent)
    {
        this.parent = parent;
    }

    /**
     * Get the parent AgGridEnterpriseOptions instance for fluent chaining.
     *
     * @return parent options object
     */
    public J parent()
    {
        return parent;
    }

    public J getParent()
    {
        return parent;
    }

    public AggregationOptions<J> setParent(J parent)
    {
        this.parent = parent;
        return this;
    }

    // ===== Property Getters & Fluent Setters =====

    public Map<String, Object> getAggFuncs()
    {
        return aggFuncs;
    }

    public AggregationOptions<J> setAggFuncs(Map<String, Object> aggFuncs)
    {
        this.aggFuncs = aggFuncs;
        return this;
    }

    public Boolean getAggregateOnlyChangedColumns()
    {
        return aggregateOnlyChangedColumns;
    }

    public AggregationOptions<J> setAggregateOnlyChangedColumns(Boolean aggregateOnlyChangedColumns)
    {
        this.aggregateOnlyChangedColumns = aggregateOnlyChangedColumns;
        return this;
    }

    public Boolean getSuppressAggFilteredOnly()
    {
        return suppressAggFilteredOnly;
    }

    public AggregationOptions<J> setSuppressAggFilteredOnly(Boolean suppressAggFilteredOnly)
    {
        this.suppressAggFilteredOnly = suppressAggFilteredOnly;
        return this;
    }

    public String getGroupAggFiltering()
    {
        return groupAggFiltering;
    }

    public AggregationOptions<J> setGroupAggFiltering(String groupAggFiltering)
    {
        this.groupAggFiltering = groupAggFiltering;
        return this;
    }

    public Boolean getAlwaysAggregateAtRootLevel()
    {
        return alwaysAggregateAtRootLevel;
    }

    public AggregationOptions<J> setAlwaysAggregateAtRootLevel(Boolean alwaysAggregateAtRootLevel)
    {
        this.alwaysAggregateAtRootLevel = alwaysAggregateAtRootLevel;
        return this;
    }

    public Object getGetGroupRowAgg()
    {
        return getGroupRowAgg;
    }

    public AggregationOptions<J> setGetGroupRowAgg(Object getGroupRowAgg)
    {
        this.getGroupRowAgg = getGroupRowAgg;
        return this;
    }

    public Boolean getSuppressSetFilterByDefault()
    {
        return suppressSetFilterByDefault;
    }

    public AggregationOptions<J> setSuppressSetFilterByDefault(Boolean suppressSetFilterByDefault)
    {
        this.suppressSetFilterByDefault = suppressSetFilterByDefault;
        return this;
    }
}
