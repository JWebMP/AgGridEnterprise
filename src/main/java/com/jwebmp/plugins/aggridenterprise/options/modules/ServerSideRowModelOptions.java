package com.jwebmp.plugins.aggridenterprise.options.modules;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AG Grid Enterprise: Server-Side Row Model Configuration Module
 *
 * Encapsulates all Server-Side Row Model (SSRM) configuration options including
 * virtual scrolling, infinite scrolling, caching, and real-time data sync callbacks.
 *
 * @param <J> Generic type for fluent builder pattern (CRTP)
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerSideRowModelOptions<J extends ServerSideRowModelOptions<J>>
{
    private J parent;

    /**
     * Callback to fetch rows from server. Called when grid needs data for a block of rows.
     * This is the core datasource for SSRM.
     */
    @JsonProperty("serverSideDatasource")
    private Object serverSideDatasource;

    /**
     * Number of rows in each virtual block. Default 100.
     * Larger values = fewer requests but more initial data transfer.
     * Smaller values = more requests but lower latency per request.
     */
    @JsonProperty("cacheBlockSize")
    private Integer cacheBlockSize;

    /**
     * Maximum number of blocks to hold in memory. Default 10.
     * When limit exceeded, oldest blocks are purged to free memory.
     */
    @JsonProperty("maxBlocksInCache")
    private Integer maxBlocksInCache;

    /**
     * Maximum concurrent datasource requests. Default 2.
     * Prevents overwhelming the server with too many simultaneous requests.
     */
    @JsonProperty("maxConcurrentDatasourceRequests")
    private Integer maxConcurrentDatasourceRequests;

    /**
     * Debounce time in milliseconds before loading new blocks. Default 0.
     * Useful when user scrolls rapidly - waits for scroll to settle before requesting.
     */
    @JsonProperty("blockLoadDebounceMillis")
    private Integer blockLoadDebounceMillis;

    /**
     * Suppress the loading row that appears while a block is loading from the server.
     */
    @JsonProperty("suppressServerSideFullWidthLoadingRow")
    private Boolean suppressServerSideFullWidthLoadingRow;

    /**
     * Purge node data when row nodes are closed. Saves memory by not retaining data
     * for closed/collapsed groups.
     */
    @JsonProperty("purgeClosedRowNodes")
    private Boolean purgeClosedRowNodes;

    /**
     * Character used to separate field names in pivot result field names.
     * Default: '_' (e.g., "sum_value_by_region").
     */
    @JsonProperty("serverSidePivotResultFieldSeparator")
    private String serverSidePivotResultFieldSeparator;

    /**
     * Sort all levels when sorting. If false, only the top level sorts.
     * Important for hierarchical SSRM data.
     */
    @JsonProperty("serverSideSortAllLevels")
    private Boolean serverSideSortAllLevels;

    /**
     * Allow client-side sorting within cached blocks. Sorts data that's already
     * loaded without requesting fresh data from server.
     */
    @JsonProperty("serverSideEnableClientSideSort")
    private Boolean serverSideEnableClientSideSort;

    /**
     * Only refresh groups that have changes from filter/sort operations.
     * Optimization to avoid refreshing entire tree when only subset changed.
     */
    @JsonProperty("serverSideOnlyRefreshFilteredGroups")
    private Boolean serverSideOnlyRefreshFilteredGroups;

    /**
     * Initial row count for virtual viewport. Default 1000.
     * Determines initial viewport size for virtual scrolling.
     */
    @JsonProperty("serverSideInitialRowCount")
    private Integer serverSideInitialRowCount;

    /**
     * Callback to get the child row count for a group. Called to determine
     * how many children a group node has (for virtual scrolling).
     */
    @JsonProperty("getChildCount")
    private Object getChildCount;

    /**
     * Callback to get group-level specific parameters.
     * Allows customizing request per hierarchy level.
     */
    @JsonProperty("getServerSideGroupLevelParams")
    private Object getServerSideGroupLevelParams;

    /**
     * Callback to determine if server-side group should be open by default.
     */
    @JsonProperty("isServerSideGroupOpenByDefault")
    private Object isServerSideGroupOpenByDefault;

    /**
     * Callback to determine if a transaction should be applied server-side.
     * Return true to handle on server, false to handle on client.
     */
    @JsonProperty("isApplyServerSideTransaction")
    private Object isApplyServerSideTransaction;

    /**
     * Callback to determine if a row node is a server-side group.
     * Used by grid to understand data hierarchy structure.
     */
    @JsonProperty("isServerSideGroup")
    private Object isServerSideGroup;

    /**
     * Callback to get the group key for a server-side group row.
     * Uniquely identifies the group for hierarchy navigation.
     */
    @JsonProperty("getServerSideGroupKey")
    private Object getServerSideGroupKey;

    /**
     * Suppress infinite scrolling in SSRM. When true, virtual scrolling is disabled
     * and all rows are loaded. v34.2.0 change: infinite scroll now default (enabled).
     */
    @JsonProperty("suppressServerSideInfiniteScroll")
    private Boolean suppressServerSideInfiniteScroll;

    // ===== Constructors & Parent Management =====

    public ServerSideRowModelOptions()
    {
    }

    public ServerSideRowModelOptions(J parent)
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

    public ServerSideRowModelOptions<J> setParent(J parent)
    {
        this.parent = parent;
        return this;
    }

    // ===== Property Getters & Fluent Setters =====

    public Object getServerSideDatasource()
    {
        return serverSideDatasource;
    }

    public ServerSideRowModelOptions<J> setServerSideDatasource(Object serverSideDatasource)
    {
        this.serverSideDatasource = serverSideDatasource;
        return this;
    }

    public Integer getCacheBlockSize()
    {
        return cacheBlockSize;
    }

    public ServerSideRowModelOptions<J> setCacheBlockSize(Integer cacheBlockSize)
    {
        this.cacheBlockSize = cacheBlockSize;
        return this;
    }

    public Integer getMaxBlocksInCache()
    {
        return maxBlocksInCache;
    }

    public ServerSideRowModelOptions<J> setMaxBlocksInCache(Integer maxBlocksInCache)
    {
        this.maxBlocksInCache = maxBlocksInCache;
        return this;
    }

    public Integer getMaxConcurrentDatasourceRequests()
    {
        return maxConcurrentDatasourceRequests;
    }

    public ServerSideRowModelOptions<J> setMaxConcurrentDatasourceRequests(Integer maxConcurrentDatasourceRequests)
    {
        this.maxConcurrentDatasourceRequests = maxConcurrentDatasourceRequests;
        return this;
    }

    public Integer getBlockLoadDebounceMillis()
    {
        return blockLoadDebounceMillis;
    }

    public ServerSideRowModelOptions<J> setBlockLoadDebounceMillis(Integer blockLoadDebounceMillis)
    {
        this.blockLoadDebounceMillis = blockLoadDebounceMillis;
        return this;
    }

    public Boolean getSuppressServerSideFullWidthLoadingRow()
    {
        return suppressServerSideFullWidthLoadingRow;
    }

    public ServerSideRowModelOptions<J> setSuppressServerSideFullWidthLoadingRow(Boolean suppressServerSideFullWidthLoadingRow)
    {
        this.suppressServerSideFullWidthLoadingRow = suppressServerSideFullWidthLoadingRow;
        return this;
    }

    public Boolean getPurgeClosedRowNodes()
    {
        return purgeClosedRowNodes;
    }

    public ServerSideRowModelOptions<J> setPurgeClosedRowNodes(Boolean purgeClosedRowNodes)
    {
        this.purgeClosedRowNodes = purgeClosedRowNodes;
        return this;
    }

    public String getServerSidePivotResultFieldSeparator()
    {
        return serverSidePivotResultFieldSeparator;
    }

    public ServerSideRowModelOptions<J> setServerSidePivotResultFieldSeparator(String serverSidePivotResultFieldSeparator)
    {
        this.serverSidePivotResultFieldSeparator = serverSidePivotResultFieldSeparator;
        return this;
    }

    public Boolean getServerSideSortAllLevels()
    {
        return serverSideSortAllLevels;
    }

    public ServerSideRowModelOptions<J> setServerSideSortAllLevels(Boolean serverSideSortAllLevels)
    {
        this.serverSideSortAllLevels = serverSideSortAllLevels;
        return this;
    }

    public Boolean getServerSideEnableClientSideSort()
    {
        return serverSideEnableClientSideSort;
    }

    public ServerSideRowModelOptions<J> setServerSideEnableClientSideSort(Boolean serverSideEnableClientSideSort)
    {
        this.serverSideEnableClientSideSort = serverSideEnableClientSideSort;
        return this;
    }

    public Boolean getServerSideOnlyRefreshFilteredGroups()
    {
        return serverSideOnlyRefreshFilteredGroups;
    }

    public ServerSideRowModelOptions<J> setServerSideOnlyRefreshFilteredGroups(Boolean serverSideOnlyRefreshFilteredGroups)
    {
        this.serverSideOnlyRefreshFilteredGroups = serverSideOnlyRefreshFilteredGroups;
        return this;
    }

    public Integer getServerSideInitialRowCount()
    {
        return serverSideInitialRowCount;
    }

    public ServerSideRowModelOptions<J> setServerSideInitialRowCount(Integer serverSideInitialRowCount)
    {
        this.serverSideInitialRowCount = serverSideInitialRowCount;
        return this;
    }

    public Object getGetChildCount()
    {
        return getChildCount;
    }

    public ServerSideRowModelOptions<J> setGetChildCount(Object getChildCount)
    {
        this.getChildCount = getChildCount;
        return this;
    }

    public Object getGetServerSideGroupLevelParams()
    {
        return getServerSideGroupLevelParams;
    }

    public ServerSideRowModelOptions<J> setGetServerSideGroupLevelParams(Object getServerSideGroupLevelParams)
    {
        this.getServerSideGroupLevelParams = getServerSideGroupLevelParams;
        return this;
    }

    public Object getIsServerSideGroupOpenByDefault()
    {
        return isServerSideGroupOpenByDefault;
    }

    public ServerSideRowModelOptions<J> setIsServerSideGroupOpenByDefault(Object isServerSideGroupOpenByDefault)
    {
        this.isServerSideGroupOpenByDefault = isServerSideGroupOpenByDefault;
        return this;
    }

    public Object getIsApplyServerSideTransaction()
    {
        return isApplyServerSideTransaction;
    }

    public ServerSideRowModelOptions<J> setIsApplyServerSideTransaction(Object isApplyServerSideTransaction)
    {
        this.isApplyServerSideTransaction = isApplyServerSideTransaction;
        return this;
    }

    public Object getIsServerSideGroup()
    {
        return isServerSideGroup;
    }

    public ServerSideRowModelOptions<J> setIsServerSideGroup(Object isServerSideGroup)
    {
        this.isServerSideGroup = isServerSideGroup;
        return this;
    }

    public Object getGetServerSideGroupKey()
    {
        return getServerSideGroupKey;
    }

    public ServerSideRowModelOptions<J> setGetServerSideGroupKey(Object getServerSideGroupKey)
    {
        this.getServerSideGroupKey = getServerSideGroupKey;
        return this;
    }

    public Boolean getSuppressServerSideInfiniteScroll()
    {
        return suppressServerSideInfiniteScroll;
    }

    public ServerSideRowModelOptions<J> setSuppressServerSideInfiniteScroll(Boolean suppressServerSideInfiniteScroll)
    {
        this.suppressServerSideInfiniteScroll = suppressServerSideInfiniteScroll;
        return this;
    }
}
