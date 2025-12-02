package com.jwebmp.plugins.aggridenterprise.options;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.jwebmp.plugins.aggrid.options.AgGridColumnDef;
import com.jwebmp.plugins.aggrid.options.AgGridOptions;
import com.jwebmp.plugins.aggridenterprise.options.mapping.AgGridColDefEnterpriseMapper;
import com.jwebmp.plugins.aggridenterprise.options.modules.AdvancedFilteringOptions;
import com.jwebmp.plugins.aggridenterprise.options.modules.AggregationOptions;
import com.jwebmp.plugins.aggridenterprise.options.modules.ChartsOptions;
import com.jwebmp.plugins.aggridenterprise.options.modules.PivotingOptions;
import com.jwebmp.plugins.aggridenterprise.options.modules.RangeSelectionOptions;
import com.jwebmp.plugins.aggridenterprise.options.modules.RowGroupingOptions;
import com.jwebmp.plugins.aggridenterprise.options.modules.ServerSideRowModelOptions;
import com.jwebmp.plugins.aggridenterprise.options.modules.SideBarAndStatusBarOptions;

/**
 * Enterprise-only options layered on top of community AgGridOptions
 */
@SuppressWarnings("unchecked")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgGridEnterpriseOptions<J extends AgGridEnterpriseOptions<J>> extends AgGridOptions<J>
{

    @Override
    public AgGridEnterpriseColumnDef<?> getDefaultColDef()
    {
        AgGridColumnDef<?> base = super.getDefaultColDef();
        if (base == null)
        {
            return null;
        }
        if (base instanceof AgGridEnterpriseColumnDef<?> enterprise)
        {
            return enterprise;
        }
        // Convert community defaultColDef into an enterprise one using MapStruct mapper
        AgGridEnterpriseColumnDef<?> ent = AgGridColDefEnterpriseMapper.INSTANCE.toEnterpriseColDef(base);
        setDefaultColDef(ent);
        return ent;
    }

    // ===== PHASE 2: MODULAR ENTERPRISE OPTIONS (8 Modules) =====
    // Using @JsonUnwrapped for backward-compatible JSON serialization (all properties flatten to parent)

    /**
     * Integrated Charts Configuration Module
     * Encapsulates all chart-related options (enableCharts, chartThemes, suppressChartToolPanelsButton, etc.)
     */
    @JsonUnwrapped
    private ChartsOptions<?> charts = new ChartsOptions<>();

    /**
     * Server-Side Row Model Configuration Module
     * Encapsulates all SSRM options (serverSideDatasource, cacheBlockSize, suppressServerSideInfiniteScroll, etc.)
     */
    @JsonUnwrapped
    private ServerSideRowModelOptions<?> serverSideRowModel = new ServerSideRowModelOptions<>();

    /**
     * Row Grouping & Aggregation Configuration Module
     * Encapsulates all grouping options (groupAllowUnbalanced, groupTotalRow, groupRowRenderer, etc.)
     */
    @JsonUnwrapped
    private RowGroupingOptions<?> rowGrouping = new RowGroupingOptions<>();

    /**
     * Aggregation Functions Configuration Module
     * Encapsulates aggregation configuration (aggFuncs, aggregateOnlyChangedColumns, groupAggFiltering, etc.)
     */
    @JsonUnwrapped
    private AggregationOptions<?> aggregation = new AggregationOptions<>();

    /**
     * Pivot Mode Configuration Module
     * Encapsulates all pivot options (pivotMode, pivotRowTotals, processPivotResultColDef, etc.)
     */
    @JsonUnwrapped
    private PivotingOptions<?> pivoting = new PivotingOptions<>();

    /**
     * Advanced Filtering Configuration Module
     * Encapsulates advanced filter builder and cell selection options
     */
    @JsonUnwrapped
    private AdvancedFilteringOptions<?> advancedFiltering = new AdvancedFilteringOptions<>();

    /**
     * SideBar & StatusBar Configuration Module
     * Encapsulates UI panel configuration (sideBar, statusBar, allowDragFromColumnsToolPanel)
     */
    @JsonUnwrapped
    private SideBarAndStatusBarOptions<?> sideBarAndStatusBar = new SideBarAndStatusBarOptions<>();

    /**
     * Range Selection Configuration Module
     * Encapsulates range selection options (enableRangeSelection)
     */
    @JsonUnwrapped
    private RangeSelectionOptions<?> rangeSelection = new RangeSelectionOptions<>();

    // ===== Module Convenience Accessor Methods =====

    /**
     * Configure integrated charts options.
     *
     * @return ChartsOptions module for fluent chaining
     */
    public ChartsOptions<?> configureCharts()
    {
        return charts;
    }

    /**
     * Configure server-side row model options.
     *
     * @return ServerSideRowModelOptions module for fluent chaining
     */
    public ServerSideRowModelOptions<?> serverSideRowModelOptions()
    {
        return serverSideRowModel;
    }

    /**
     * Configure row grouping and aggregation options.
     *
     * @return RowGroupingOptions module for fluent chaining
     */
    public RowGroupingOptions<?> rowGroupingOptions()
    {
        return rowGrouping;
    }

    /**
     * Configure aggregation function options.
     *
     * @return AggregationOptions module for fluent chaining
     */
    public AggregationOptions<?> configureAggregation()
    {
        return aggregation;
    }

    /**
     * Configure pivot mode options.
     *
     * @return PivotingOptions module for fluent chaining
     */
    public PivotingOptions<?> configurePivoting()
    {
        return pivoting;
    }

    /**
     * Configure advanced filtering options.
     *
     * @return AdvancedFilteringOptions module for fluent chaining
     */
    public AdvancedFilteringOptions<?> configureAdvancedFiltering()
    {
        return advancedFiltering;
    }

    /**
     * Configure sidebar and statusbar options.
     *
     * @return SideBarAndStatusBarOptions module for fluent chaining
     */
    public SideBarAndStatusBarOptions<?> configureSideBarAndStatusBar()
    {
        return sideBarAndStatusBar;
    }

    /**
     * Configure range selection options.
     *
     * @return RangeSelectionOptions module for fluent chaining
     */
    public RangeSelectionOptions<?> configureRangeSelection()
    {
        return rangeSelection;
    }

    // ===== END PHASE 2 MODULAR OPTIONS =====
    // All 83 enterprise properties replaced by 8 modular components above
    // JSON serialization remains identical via @JsonUnwrapped pattern

    // ===== Inner Classes & Enums (extracted to separate files) =====

    /**
     * Strict object type for chartToolPanelsDef.
     * Mirrors AG Grid structure allowing panel ordering and default panel selection.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ChartToolPanelsDef
    {
        @JsonProperty("panels")
        private java.util.List<String> panels;

        @JsonProperty("defaultToolPanel")
        private String defaultToolPanel;

        @JsonProperty("settingsPanel")
        private SettingsPanel settingsPanel;

        @JsonProperty("dataPanel")
        private DataPanel dataPanel;

        @JsonProperty("formatPanel")
        private FormatPanel formatPanel;
        
        public java.util.List<String> getPanels()
        {
            return panels;
        }

        public SettingsPanel getSettingsPanel() {return settingsPanel;}

        public ChartToolPanelsDef setSettingsPanel(SettingsPanel settingsPanel)
        {
            this.settingsPanel = settingsPanel;
            return this;
        }

        public DataPanel getDataPanel() {return dataPanel;}

        public ChartToolPanelsDef setDataPanel(DataPanel dataPanel)
        {
            this.dataPanel = dataPanel;
            return this;
        }

        public FormatPanel getFormatPanel() {return formatPanel;}

        public ChartToolPanelsDef setFormatPanel(FormatPanel formatPanel)
        {
            this.formatPanel = formatPanel;
            return this;
        }

        /**
         * Backward-compatible: accept a list of ToolPanel objects but store only their id strings for serialization.
         */
        public ChartToolPanelsDef setPanels(java.util.List<ToolPanel> panels)
        {
            if (panels == null)
            {
                this.panels = null;
            }
            else
            {
                java.util.ArrayList<String> ids = new java.util.ArrayList<>(panels.size());
                for (ToolPanel p : panels)
                {
                    if (p != null && p.getId() != null)
                    {
                        ids.add(p.getId());
                    }
                }
                this.panels = ids;
            }
            return this;
        }

        /**
         * Preferred: set panels by their string ids directly, e.g., ['settings','data','format'].
         */
        public ChartToolPanelsDef setPanelsByIds(java.util.List<String> ids)
        {
            this.panels = ids;
            return this;
        }

        /**
         * Convenience: set panels by enum ids.
         */
        public ChartToolPanelsDef setPanelsByEnumIds(java.util.List<ToolPanelId> panelIds)
        {
            if (panelIds == null)
            {
                this.panels = null;
            }
            else
            {
                java.util.ArrayList<String> ids = new java.util.ArrayList<>(panelIds.size());
                for (ToolPanelId id : panelIds)
                {
                    if (id != null)
                    {
                        ids.add(id.getJson());
                    }
                }
                this.panels = ids;
            }
            return this;
        }

        public String getDefaultToolPanel()
        {
            return defaultToolPanel;
        }

        public ChartToolPanelsDef setDefaultToolPanel(ToolPanelId id)
        {
            this.defaultToolPanel = id == null ? null : id.getJson();
            return this;
        }

        public ChartToolPanelsDef setDefaultToolPanel(String id)
        {
            this.defaultToolPanel = id;
            return this;
        }

        /**
         * Definition of a single tool panel. You can provide custom labels/icons if desired.
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class ToolPanel
        {
            @JsonProperty("id")
            private String id;

            @JsonProperty("labelDefault")
            private String labelDefault;

            @JsonProperty("labelKey")
            private String labelKey;

            @JsonProperty("icon")
            private String icon;

            public String getId() {return id;}

            public ToolPanel setId(ToolPanelId id)
            {
                this.id = id == null ? null : id.getJson();
                return this;
            }

            public ToolPanel setId(String id)
            {
                this.id = id;
                return this;
            }

            public String getLabelDefault() {return labelDefault;}

            public ToolPanel setLabelDefault(String labelDefault)
            {
                this.labelDefault = labelDefault;
                return this;
            }

            public String getLabelKey() {return labelKey;}

            public ToolPanel setLabelKey(String labelKey)
            {
                this.labelKey = labelKey;
                return this;
            }

            public String getIcon() {return icon;}

            public ToolPanel setIcon(String icon)
            {
                this.icon = icon;
                return this;
            }
        }

        // ===== Nested panel configurations =====
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class SettingsPanel
        {
            @JsonProperty("chartGroupsDef")
            private ChartGroupsDef chartGroupsDef;

            public ChartGroupsDef getChartGroupsDef() {return chartGroupsDef;}

            public SettingsPanel setChartGroupsDef(ChartGroupsDef def)
            {
                this.chartGroupsDef = def;
                return this;
            }
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class ChartGroupsDef
        {
            // Use string lists to keep flexible and aligned with AG Grid string keys
            @JsonProperty("columnGroup")
            private java.util.List<String> columnGroup;
            @JsonProperty("barGroup")
            private java.util.List<String> barGroup;
            @JsonProperty("pieGroup")
            private java.util.List<String> pieGroup;
            @JsonProperty("lineGroup")
            private java.util.List<String> lineGroup;
            @JsonProperty("areaGroup")
            private java.util.List<String> areaGroup;
            @JsonProperty("scatterGroup")
            private java.util.List<String> scatterGroup;
            @JsonProperty("combinationGroup")
            private java.util.List<String> combinationGroup;
            @JsonProperty("polarGroup")
            private java.util.List<String> polarGroup;
            @JsonProperty("statisticalGroup")
            private java.util.List<String> statisticalGroup;
            @JsonProperty("hierarchicalGroup")
            private java.util.List<String> hierarchicalGroup;
            @JsonProperty("specializedGroup")
            private java.util.List<String> specializedGroup;
            @JsonProperty("funnelGroup")
            private java.util.List<String> funnelGroup;

            public java.util.List<String> getColumnGroup() {return columnGroup;}

            public ChartGroupsDef setColumnGroup(java.util.List<String> v)
            {
                this.columnGroup = v;
                return this;
            }

            public java.util.List<String> getBarGroup() {return barGroup;}

            public ChartGroupsDef setBarGroup(java.util.List<String> v)
            {
                this.barGroup = v;
                return this;
            }

            public java.util.List<String> getPieGroup() {return pieGroup;}

            public ChartGroupsDef setPieGroup(java.util.List<String> v)
            {
                this.pieGroup = v;
                return this;
            }

            public java.util.List<String> getLineGroup() {return lineGroup;}

            public ChartGroupsDef setLineGroup(java.util.List<String> v)
            {
                this.lineGroup = v;
                return this;
            }

            public java.util.List<String> getAreaGroup() {return areaGroup;}

            public ChartGroupsDef setAreaGroup(java.util.List<String> v)
            {
                this.areaGroup = v;
                return this;
            }

            public java.util.List<String> getScatterGroup() {return scatterGroup;}

            public ChartGroupsDef setScatterGroup(java.util.List<String> v)
            {
                this.scatterGroup = v;
                return this;
            }

            public java.util.List<String> getCombinationGroup() {return combinationGroup;}

            public ChartGroupsDef setCombinationGroup(java.util.List<String> v)
            {
                this.combinationGroup = v;
                return this;
            }

            public java.util.List<String> getPolarGroup() {return polarGroup;}

            public ChartGroupsDef setPolarGroup(java.util.List<String> v)
            {
                this.polarGroup = v;
                return this;
            }

            public java.util.List<String> getStatisticalGroup() {return statisticalGroup;}

            public ChartGroupsDef setStatisticalGroup(java.util.List<String> v)
            {
                this.statisticalGroup = v;
                return this;
            }

            public java.util.List<String> getHierarchicalGroup() {return hierarchicalGroup;}

            public ChartGroupsDef setHierarchicalGroup(java.util.List<String> v)
            {
                this.hierarchicalGroup = v;
                return this;
            }

            public java.util.List<String> getSpecializedGroup() {return specializedGroup;}

            public ChartGroupsDef setSpecializedGroup(java.util.List<String> v)
            {
                this.specializedGroup = v;
                return this;
            }

            public java.util.List<String> getFunnelGroup() {return funnelGroup;}

            public ChartGroupsDef setFunnelGroup(java.util.List<String> v)
            {
                this.funnelGroup = v;
                return this;
            }
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class DataPanel
        {
            @JsonProperty("groups")
            private java.util.List<DataGroup> groups;

            public java.util.List<DataGroup> getGroups() {return groups;}

            public DataPanel setGroups(java.util.List<DataGroup> groups)
            {
                this.groups = groups;
                return this;
            }

            @JsonInclude(JsonInclude.Include.NON_NULL)
            public static class DataGroup
            {
                @JsonProperty("type")
                private String type;
                @JsonProperty("isOpen")
                private Boolean isOpen;

                public String getType() {return type;}

                public DataGroup setType(String type)
                {
                    this.type = type;
                    return this;
                }

                public Boolean getIsOpen() {return isOpen;}

                public DataGroup setIsOpen(Boolean isOpen)
                {
                    this.isOpen = isOpen;
                    return this;
                }
            }
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class FormatPanel
        {
            @JsonProperty("groups")
            private java.util.List<FormatGroup> groups;

            public java.util.List<FormatGroup> getGroups() {return groups;}

            public FormatPanel setGroups(java.util.List<FormatGroup> groups)
            {
                this.groups = groups;
                return this;
            }

            @JsonInclude(JsonInclude.Include.NON_NULL)
            public static class FormatGroup
            {
                @JsonProperty("type")
                private String type;
                @JsonProperty("isOpen")
                private Boolean isOpen;

                public String getType() {return type;}

                public FormatGroup setType(String type)
                {
                    this.type = type;
                    return this;
                }

                public Boolean getIsOpen() {return isOpen;}

                public FormatGroup setIsOpen(Boolean isOpen)
                {
                    this.isOpen = isOpen;
                    return this;
                }
            }
        }
    }

    // ===== Internal helpers & enums for this options class =====
    // (All enums extracted to separate files for better organization)

    /**
     * Simple wrapper to serialize raw JavaScript using the existing JavascriptFunction infrastructure.
     */
    static class RawJsFunction extends com.jwebmp.core.htmlbuilder.javascript.JavascriptFunction<RawJsFunction>
    {
        private final String raw;

        RawJsFunction(String raw) {this.raw = raw;}

        @Override
        public String renderFunction()
        {
            return raw == null ? "null" : raw;
        }
    }

    // ===== Row Grouping - Additional Grid Options =====

    /**
     * How grouped rows are displayed.
     * Options: 'singleColumn' | 'multipleColumns' | 'groupRows' | 'custom'.
     */
    @JsonProperty("groupDisplayType")
    private String groupDisplayType;


    /**
     * Provide the Cell Renderer to use when groupDisplayType = 'groupRows'.
     */
    @JsonProperty("groupRowRenderer")
    private Object groupRowRenderer;

    /**
     * Customise the parameters provided to the groupRowRenderer component.
     */
    @JsonProperty("groupRowRendererParams")
    private Object groupRowRendererParams;

    /**
     * Shows the open group in the group column for non-group rows.
     */
    @JsonProperty("showOpenedGroup")
    private Boolean showOpenedGroup;

    /**
     * When true, preserves the current group order when sorting on non-group columns.
     */
    @JsonProperty("groupMaintainOrder")
    private Boolean groupMaintainOrder;

    /**
     * If grouping, set to the number of levels to expand by default.
     */
    @JsonProperty("groupDefaultExpanded")
    private Integer groupDefaultExpanded;

    /**
     * Allows groups to be open by default (client-side row model only). Raw JS function.
     */
    @JsonProperty("isGroupOpenByDefault")
    private Object isGroupOpenByDefault;

    /**
     * Allows default sorting of groups. Raw JS comparator function.
     */
    @JsonProperty("initialGroupOrderComparator")
    private Object initialGroupOrderComparator;

    /**
     * Set to true to prevent Group Rows from sticking to the top of the grid.
     */
    @JsonProperty("suppressGroupRowsSticky")
    private Boolean suppressGroupRowsSticky;

    /**
     * Set to true to suppress sort indicators/actions from the row group panel.
     */
    @JsonProperty("rowGroupPanelSuppressSort")
    private Boolean rowGroupPanelSuppressSort;

    /**
     * If grouping, locks the group settings of a number of columns.
     */
    @JsonProperty("groupLockGroupColumns")
    private Integer groupLockGroupColumns;

    /**
     * Prevent hiding the column when dragging to Row Group Panel.
     */
    @JsonProperty("suppressDragLeaveHidesColumns")
    private Boolean suppressDragLeaveHidesColumns;

    /**
     * Enable to prevent column visibility changing when grouped columns are changed.
     * Accepts boolean or restricted strings 'suppressHideOnGroup' | 'suppressShowOnUngroup'.
     */
    @JsonProperty("suppressGroupChangesColumnVisibility")
    private Object suppressGroupChangesColumnVisibility;

    /**
     * Server-side Row Model only: controls expand/collapse operations scope.
     */
    @JsonProperty("ssrmExpandAllAffectsAllRows")
    private Boolean ssrmExpandAllAffectsAllRows;

    // ===== Enums for new options (extracted to separate files) =====

    // ===== Getters / Setters for new options =====

    public String getGroupDisplayType()
    {
        return groupDisplayType;
    }

    public @org.jspecify.annotations.NonNull J setGroupDisplayType(String type)
    {
        this.groupDisplayType = type;
        return (J) this;
    }

    public @org.jspecify.annotations.NonNull J setGroupDisplayType(RowGroupingDisplayType type)
    {
        this.groupDisplayType = type == null ? null : type.getJson();
        return (J) this;
    }

    public Object getGroupRowRenderer()
    {
        return groupRowRenderer;
    }

    public @org.jspecify.annotations.NonNull J setGroupRowRenderer(Object renderer)
    {
        this.groupRowRenderer = renderer;
        return (J) this;
    }

    public Object getGroupRowRendererParams()
    {
        return groupRowRendererParams;
    }

    public @org.jspecify.annotations.NonNull J setGroupRowRendererParams(Object params)
    {
        this.groupRowRendererParams = params;
        return (J) this;
    }

    public Boolean getShowOpenedGroup()
    {
        return showOpenedGroup;
    }

    public @org.jspecify.annotations.NonNull J setShowOpenedGroup(Boolean showOpenedGroup)
    {
        this.showOpenedGroup = showOpenedGroup;
        return (J) this;
    }

    public Boolean getGroupMaintainOrder()
    {
        return groupMaintainOrder;
    }

    public @org.jspecify.annotations.NonNull J setGroupMaintainOrder(Boolean groupMaintainOrder)
    {
        this.groupMaintainOrder = groupMaintainOrder;
        return (J) this;
    }

    public Integer getGroupDefaultExpanded()
    {
        return groupDefaultExpanded;
    }

    public @org.jspecify.annotations.NonNull J setGroupDefaultExpanded(Integer groupDefaultExpanded)
    {
        this.groupDefaultExpanded = groupDefaultExpanded;
        return (J) this;
    }

    public Object getIsGroupOpenByDefault()
    {
        return isGroupOpenByDefault;
    }

    /**
     * Set a raw JavaScript function for isGroupOpenByDefault.
     * Example: "params => params.rowNode.level < 1".
     */
    public @org.jspecify.annotations.NonNull J setIsGroupOpenByDefault(String rawJsFunction)
    {
        this.isGroupOpenByDefault = rawJsFunction == null ? null : new RawJsFunction(rawJsFunction);
        return (J) this;
    }

    public Object getInitialGroupOrderComparator()
    {
        return initialGroupOrderComparator;
    }

    /**
     * Set a raw JavaScript comparator for initial group order.
     * Example: "(a,b) => a.key.localeCompare(b.key)".
     */
    public @org.jspecify.annotations.NonNull J setInitialGroupOrderComparator(String rawJsFunction)
    {
        this.initialGroupOrderComparator = rawJsFunction == null ? null : new RawJsFunction(rawJsFunction);
        return (J) this;
    }

    public Boolean getSuppressGroupRowsSticky()
    {
        return suppressGroupRowsSticky;
    }

    public @org.jspecify.annotations.NonNull J setSuppressGroupRowsSticky(Boolean suppressGroupRowsSticky)
    {
        this.suppressGroupRowsSticky = suppressGroupRowsSticky;
        return (J) this;
    }

    public Boolean getRowGroupPanelSuppressSort()
    {
        return rowGroupPanelSuppressSort;
    }

    public @org.jspecify.annotations.NonNull J setRowGroupPanelSuppressSort(Boolean rowGroupPanelSuppressSort)
    {
        this.rowGroupPanelSuppressSort = rowGroupPanelSuppressSort;
        return (J) this;
    }

    public Integer getGroupLockGroupColumns()
    {
        return groupLockGroupColumns;
    }

    public @org.jspecify.annotations.NonNull J setGroupLockGroupColumns(Integer groupLockGroupColumns)
    {
        this.groupLockGroupColumns = groupLockGroupColumns;
        return (J) this;
    }

    public Boolean getSuppressDragLeaveHidesColumns()
    {
        return suppressDragLeaveHidesColumns;
    }

    public @org.jspecify.annotations.NonNull J setSuppressDragLeaveHidesColumns(Boolean suppressDragLeaveHidesColumns)
    {
        this.suppressDragLeaveHidesColumns = suppressDragLeaveHidesColumns;
        return (J) this;
    }

    public Object getSuppressGroupChangesColumnVisibility()
    {
        return suppressGroupChangesColumnVisibility;
    }

    public @org.jspecify.annotations.NonNull J setSuppressGroupChangesColumnVisibility(boolean value)
    {
        this.suppressGroupChangesColumnVisibility = value;
        return (J) this;
    }

    public @org.jspecify.annotations.NonNull J setSuppressGroupChangesColumnVisibility(SuppressGroupChangesColumnVisibilityMode mode)
    {
        this.suppressGroupChangesColumnVisibility = mode == null ? null : mode.getJson();
        return (J) this;
    }

    public Boolean getSsrmExpandAllAffectsAllRows()
    {
        return ssrmExpandAllAffectsAllRows;
    }

    public @org.jspecify.annotations.NonNull J setSsrmExpandAllAffectsAllRows(Boolean ssrmExpandAllAffectsAllRows)
    {
        this.ssrmExpandAllAffectsAllRows = ssrmExpandAllAffectsAllRows;
        return (J) this;
    }

    // ===== Row Model: Server-Side (enterprise) =====
    @JsonProperty("serverSideDatasource")
    @JsonRawValue
    private String serverSideDatasource;

    @JsonProperty("cacheBlockSize")
    private Integer ssrmCacheBlockSize;

    @JsonProperty("maxBlocksInCache")
    private Integer ssrmMaxBlocksInCache;

    @JsonProperty("maxConcurrentDatasourceRequests")
    private Integer ssrmMaxConcurrentDatasourceRequests;

    @JsonProperty("blockLoadDebounceMillis")
    private Integer blockLoadDebounceMillis;

    @JsonProperty("suppressServerSideFullWidthLoadingRow")
    private Boolean suppressServerSideFullWidthLoadingRow;

    @JsonProperty("purgeClosedRowNodes")
    private Boolean purgeClosedRowNodes;

    @JsonProperty("serverSidePivotResultFieldSeparator")
    private String serverSidePivotResultFieldSeparator;

    @JsonProperty("serverSideSortAllLevels")
    private Boolean serverSideSortAllLevels;

    @JsonProperty("serverSideEnableClientSideSort")
    private Boolean serverSideEnableClientSideSort;

    @JsonProperty("serverSideOnlyRefreshFilteredGroups")
    private Boolean serverSideOnlyRefreshFilteredGroups;

    @JsonProperty("serverSideInitialRowCount")
    private Integer serverSideInitialRowCount;

    @JsonProperty("getChildCount")
    @JsonRawValue
    private String getChildCount;

    @JsonProperty("getServerSideGroupLevelParams")
    @JsonRawValue
    private String getServerSideGroupLevelParams;

    @JsonProperty("isServerSideGroupOpenByDefault")
    @JsonRawValue
    private String isServerSideGroupOpenByDefault;

    @JsonProperty("isApplyServerSideTransaction")
    @JsonRawValue
    private String isApplyServerSideTransaction;

    @JsonProperty("isServerSideGroup")
    @JsonRawValue
    private String isServerSideGroup;

    @JsonProperty("getServerSideGroupKey")
    @JsonRawValue
    private String getServerSideGroupKey;

    /**
     * AG Grid v34.2.0: Suppress infinite scrolling in server-side row model (enabled by default).
     * Set to true to disable infinite scrolling behavior introduced in v34.
     */
    @JsonProperty("suppressServerSideInfiniteScroll")
    private Boolean suppressServerSideInfiniteScroll;

    /**
     * AG Grid v34.2.0: Suppress the chart tool panels button (hamburger menu) visibility.
     * Chart toolbar is now visible by default in v34; set true to revert to v33 behavior.
     */
    @JsonProperty("suppressChartToolPanelsButton")
    private Boolean suppressChartToolPanelsButton;

    public String getServerSideDatasource() {return serverSideDatasource;}

    public @org.jspecify.annotations.NonNull J setServerSideDatasourceRaw(String rawJs)
    {
        this.serverSideDatasource = rawJs;
        return (J) this;
    }

    public Integer getSsrmCacheBlockSize() {return ssrmCacheBlockSize;}

    public @org.jspecify.annotations.NonNull J setSsrmCacheBlockSize(Integer v)
    {
        this.ssrmCacheBlockSize = v;
        return (J) this;
    }

    public Integer getSsrmMaxBlocksInCache() {return ssrmMaxBlocksInCache;}

    public @org.jspecify.annotations.NonNull J setSsrmMaxBlocksInCache(Integer v)
    {
        this.ssrmMaxBlocksInCache = v;
        return (J) this;
    }

    public Integer getSsrmMaxConcurrentDatasourceRequests() {return ssrmMaxConcurrentDatasourceRequests;}

    public @org.jspecify.annotations.NonNull J setSsrmMaxConcurrentDatasourceRequests(Integer v)
    {
        this.ssrmMaxConcurrentDatasourceRequests = v;
        return (J) this;
    }

    public Integer getBlockLoadDebounceMillis() {return blockLoadDebounceMillis;}

    public @org.jspecify.annotations.NonNull J setBlockLoadDebounceMillis(Integer v)
    {
        this.blockLoadDebounceMillis = v;
        return (J) this;
    }

    public Boolean getSuppressServerSideFullWidthLoadingRow() {return suppressServerSideFullWidthLoadingRow;}

    public @org.jspecify.annotations.NonNull J setSuppressServerSideFullWidthLoadingRow(Boolean v)
    {
        this.suppressServerSideFullWidthLoadingRow = v;
        return (J) this;
    }

    public Boolean getPurgeClosedRowNodes() {return purgeClosedRowNodes;}

    public @org.jspecify.annotations.NonNull J setPurgeClosedRowNodes(Boolean v)
    {
        this.purgeClosedRowNodes = v;
        return (J) this;
    }

    public String getServerSidePivotResultFieldSeparator() {return serverSidePivotResultFieldSeparator;}

    public @org.jspecify.annotations.NonNull J setServerSidePivotResultFieldSeparator(String v)
    {
        this.serverSidePivotResultFieldSeparator = v;
        return (J) this;
    }

    public Boolean getServerSideSortAllLevels() {return serverSideSortAllLevels;}

    public @org.jspecify.annotations.NonNull J setServerSideSortAllLevels(Boolean v)
    {
        this.serverSideSortAllLevels = v;
        return (J) this;
    }

    public Boolean getServerSideEnableClientSideSort() {return serverSideEnableClientSideSort;}

    public @org.jspecify.annotations.NonNull J setServerSideEnableClientSideSort(Boolean v)
    {
        this.serverSideEnableClientSideSort = v;
        return (J) this;
    }

    public Boolean getServerSideOnlyRefreshFilteredGroups() {return serverSideOnlyRefreshFilteredGroups;}

    public @org.jspecify.annotations.NonNull J setServerSideOnlyRefreshFilteredGroups(Boolean v)
    {
        this.serverSideOnlyRefreshFilteredGroups = v;
        return (J) this;
    }

    public Integer getServerSideInitialRowCount() {return serverSideInitialRowCount;}

    public @org.jspecify.annotations.NonNull J setServerSideInitialRowCount(Integer v)
    {
        this.serverSideInitialRowCount = v;
        return (J) this;
    }

    public String getGetChildCount() {return getChildCount;}

    public @org.jspecify.annotations.NonNull J setGetChildCountRaw(String rawJs)
    {
        this.getChildCount = rawJs;
        return (J) this;
    }

    public String getGetServerSideGroupLevelParams() {return getServerSideGroupLevelParams;}

    public @org.jspecify.annotations.NonNull J setGetServerSideGroupLevelParamsRaw(String rawJs)
    {
        this.getServerSideGroupLevelParams = rawJs;
        return (J) this;
    }

    public String getIsServerSideGroupOpenByDefault() {return isServerSideGroupOpenByDefault;}

    public @org.jspecify.annotations.NonNull J setIsServerSideGroupOpenByDefaultRaw(String rawJs)
    {
        this.isServerSideGroupOpenByDefault = rawJs;
        return (J) this;
    }

    public String getIsApplyServerSideTransaction() {return isApplyServerSideTransaction;}

    public @org.jspecify.annotations.NonNull J setIsApplyServerSideTransactionRaw(String rawJs)
    {
        this.isApplyServerSideTransaction = rawJs;
        return (J) this;
    }

    public String getIsServerSideGroup() {return isServerSideGroup;}

    public @org.jspecify.annotations.NonNull J setIsServerSideGroupRaw(String rawJs)
    {
        this.isServerSideGroup = rawJs;
        return (J) this;
    }

    public String getGetServerSideGroupKey() {return getServerSideGroupKey;}

    public @org.jspecify.annotations.NonNull J setGetServerSideGroupKeyRaw(String rawJs)
    {
        this.getServerSideGroupKey = rawJs;
        return (J) this;
    }

    /**
     * AG Grid v34.2.0: Suppress infinite scrolling in server-side row model (enabled by default).
     */
    public Boolean getSuppressServerSideInfiniteScroll() {return suppressServerSideInfiniteScroll;}

    public @org.jspecify.annotations.NonNull J setSuppressServerSideInfiniteScroll(Boolean v)
    {
        this.suppressServerSideInfiniteScroll = v;
        return (J) this;
    }

    /**
     * AG Grid v34.2.0: Suppress chart tool panels button visibility (visible by default in v34).
     */
    public Boolean getSuppressChartToolPanelsButton() {return suppressChartToolPanelsButton;}

    public @org.jspecify.annotations.NonNull J setSuppressChartToolPanelsButton(Boolean v)
    {
        this.suppressChartToolPanelsButton = v;
        return (J) this;
    }

    // ===== Pivoting (enterprise) =====
    @JsonProperty("pivotMode")
    private Boolean pivotMode;

    @JsonProperty("pivotDefaultExpanded")
    private Integer pivotDefaultExpanded;

    @JsonProperty("pivotRowTotals")
    private String pivotRowTotals;

    @JsonProperty("pivotSuppressAutoColumn")
    private Boolean pivotSuppressAutoColumn;

    @JsonProperty("pivotMaxGeneratedColumns")
    private Integer pivotMaxGeneratedColumns;

    @JsonProperty("onPivotMaxColumnsExceeded")
    @JsonRawValue
    private String onPivotMaxColumnsExceeded;

    @JsonProperty("processPivotResultColDef")
    @JsonRawValue
    private String processPivotResultColDef;

    @JsonProperty("processPivotResultColGroupDef")
    @JsonRawValue
    private String processPivotResultColGroupDef;

    @JsonProperty("suppressExpandablePivotGroups")
    private Boolean suppressExpandablePivotGroups;

    @JsonProperty("functionsReadOnly")
    private Boolean functionsReadOnly;

    @JsonProperty("removePivotHeaderRowWhenSingleValueColumn")
    private Boolean removePivotHeaderRowWhenSingleValueColumn;

    public Boolean getPivotMode() {return pivotMode;}

    public @org.jspecify.annotations.NonNull J setPivotMode(Boolean v)
    {
        this.pivotMode = v;
        return (J) this;
    }

    public Integer getPivotDefaultExpanded() {return pivotDefaultExpanded;}

    public @org.jspecify.annotations.NonNull J setPivotDefaultExpanded(Integer v)
    {
        this.pivotDefaultExpanded = v;
        return (J) this;
    }

    public String getPivotRowTotals() {return pivotRowTotals;}

    public @org.jspecify.annotations.NonNull J setPivotRowTotals(String v)
    {
        this.pivotRowTotals = v;
        return (J) this;
    }

    public @org.jspecify.annotations.NonNull J setPivotRowTotals(PivotRowTotalsPosition pos)
    {
        this.pivotRowTotals = pos == null ? null : pos.getJson();
        return (J) this;
    }

    public Boolean getPivotSuppressAutoColumn() {return pivotSuppressAutoColumn;}

    public @org.jspecify.annotations.NonNull J setPivotSuppressAutoColumn(Boolean v)
    {
        this.pivotSuppressAutoColumn = v;
        return (J) this;
    }

    public Integer getPivotMaxGeneratedColumns() {return pivotMaxGeneratedColumns;}

    public @org.jspecify.annotations.NonNull J setPivotMaxGeneratedColumns(Integer v)
    {
        this.pivotMaxGeneratedColumns = v;
        return (J) this;
    }

    public String getOnPivotMaxColumnsExceeded() {return onPivotMaxColumnsExceeded;}

    public @org.jspecify.annotations.NonNull J setOnPivotMaxColumnsExceededRaw(String rawJs)
    {
        this.onPivotMaxColumnsExceeded = rawJs;
        return (J) this;
    }

    public String getProcessPivotResultColDef() {return processPivotResultColDef;}

    public @org.jspecify.annotations.NonNull J setProcessPivotResultColDefRaw(String rawJs)
    {
        this.processPivotResultColDef = rawJs;
        return (J) this;
    }

    public String getProcessPivotResultColGroupDef() {return processPivotResultColGroupDef;}

    public @org.jspecify.annotations.NonNull J setProcessPivotResultColGroupDefRaw(String rawJs)
    {
        this.processPivotResultColGroupDef = rawJs;
        return (J) this;
    }

    public Boolean getSuppressExpandablePivotGroups() {return suppressExpandablePivotGroups;}

    public @org.jspecify.annotations.NonNull J setSuppressExpandablePivotGroups(Boolean v)
    {
        this.suppressExpandablePivotGroups = v;
        return (J) this;
    }

    public Boolean getFunctionsReadOnly() {return functionsReadOnly;}

    public @org.jspecify.annotations.NonNull J setFunctionsReadOnly(Boolean v)
    {
        this.functionsReadOnly = v;
        return (J) this;
    }

    public Boolean getRemovePivotHeaderRowWhenSingleValueColumn() {return removePivotHeaderRowWhenSingleValueColumn;}

    public @org.jspecify.annotations.NonNull J setRemovePivotHeaderRowWhenSingleValueColumn(Boolean v)
    {
        this.removePivotHeaderRowWhenSingleValueColumn = v;
        return (J) this;
    }

    // ===== Advanced Filter (Enterprise) =====
    /**
     * Enable the Advanced Filter.
     */
    @JsonProperty("enableAdvancedFilter")
    private Boolean enableAdvancedFilter;

    /**
     * Include hidden columns in the Advanced Filter.
     */
    @JsonProperty("includeHiddenColumnsInAdvancedFilter")
    private Boolean includeHiddenColumnsInAdvancedFilter;

    /**
     * DOM element parent for Advanced Filter to allow rendering outside of the grid; null => inside grid.
     */
    @JsonProperty("advancedFilterParent")
    private Object advancedFilterParent;

    /**
     * Parameters passed to the Advanced Filter Builder.
     */
    @JsonProperty("advancedFilterBuilderParams")
    private Object advancedFilterBuilderParams;

    /**
     * Parameters passed to Advanced Filter.
     */
    @JsonProperty("advancedFilterParams")
    private Object advancedFilterParams;

    public Boolean getEnableAdvancedFilter() {return enableAdvancedFilter;}

    public @org.jspecify.annotations.NonNull J setEnableAdvancedFilter(Boolean enableAdvancedFilter)
    {
        this.enableAdvancedFilter = enableAdvancedFilter;
        return (J) this;
    }

    public Boolean getIncludeHiddenColumnsInAdvancedFilter() {return includeHiddenColumnsInAdvancedFilter;}

    public @org.jspecify.annotations.NonNull J setIncludeHiddenColumnsInAdvancedFilter(Boolean includeHiddenColumnsInAdvancedFilter)
    {
        this.includeHiddenColumnsInAdvancedFilter = includeHiddenColumnsInAdvancedFilter;
        return (J) this;
    }

    public Object getAdvancedFilterParent() {return advancedFilterParent;}

    public @org.jspecify.annotations.NonNull J setAdvancedFilterParent(Object advancedFilterParent)
    {
        this.advancedFilterParent = advancedFilterParent;
        return (J) this;
    }

    public Object getAdvancedFilterBuilderParams() {return advancedFilterBuilderParams;}

    public @org.jspecify.annotations.NonNull J setAdvancedFilterBuilderParams(Object advancedFilterBuilderParams)
    {
        this.advancedFilterBuilderParams = advancedFilterBuilderParams;
        return (J) this;
    }

    /**
     * Typed overload for Advanced Filter Builder params.
     */
    public @org.jspecify.annotations.NonNull J setAdvancedFilterBuilderParams(com.jwebmp.plugins.aggridenterprise.options.advancedfilter.IAdvancedFilterBuilderParams params)
    {
        this.advancedFilterBuilderParams = params;
        return (J) this;
    }

    public Object getAdvancedFilterParams() {return advancedFilterParams;}

    public @org.jspecify.annotations.NonNull J setAdvancedFilterParams(Object advancedFilterParams)
    {
        this.advancedFilterParams = advancedFilterParams;
        return (J) this;
    }

    /**
     * Typed overload for Advanced Filter params.
     */
    public @org.jspecify.annotations.NonNull J setAdvancedFilterParams(com.jwebmp.plugins.aggridenterprise.options.advancedfilter.IAdvancedFilterParams params)
    {
        this.advancedFilterParams = params;
        return (J) this;
    }

    // ===== Integrated Charts extras (Enterprise) =====
    /**
     * Callback to customise chart toolbar items.
     */
    @JsonProperty("getChartToolbarItems")
    @JsonRawValue
    private String getChartToolbarItems;

    /**
     * Callback to render charts in an alternative container.
     */
    @JsonProperty("createChartContainer")
    @JsonRawValue
    private String createChartContainer;

    /**
     * Map of custom chart themes.
     */
    @JsonProperty("customChartThemes")
    private Object customChartThemes;

    /**
     * Chart menu items or a callback to provide items.
     */
    @JsonProperty("chartMenuItems")
    private Object chartMenuItems;

    public String getGetChartToolbarItems() {return getChartToolbarItems;}

    public @org.jspecify.annotations.NonNull J setGetChartToolbarItemsRaw(String rawJs)
    {
        this.getChartToolbarItems = rawJs;
        return (J) this;
    }

    public String getCreateChartContainer() {return createChartContainer;}

    public @org.jspecify.annotations.NonNull J setCreateChartContainerRaw(String rawJs)
    {
        this.createChartContainer = rawJs;
        return (J) this;
    }

    public Object getCustomChartThemes() {return customChartThemes;}

    public @org.jspecify.annotations.NonNull J setCustomChartThemes(Object customChartThemes)
    {
        this.customChartThemes = customChartThemes;
        return (J) this;
    }

    /**
     * Provide custom chart themes as a raw TypeScript/JSON literal string mapping theme names to themes.
     * This preserves callbacks in overrides and allows referencing AG Charts options directly.
     */
    public @org.jspecify.annotations.NonNull J setCustomChartThemesRaw(String rawLiteral)
    {
        this.customChartThemes = rawLiteral == null ? null : new RawJsFunction(rawLiteral);
        return (J) this;
    }

    public Object getChartMenuItems() {return chartMenuItems;}

    /**
     * Accepts either an array of items or a raw JavaScript function (via setChartMenuItemsRaw).
     */
    public @org.jspecify.annotations.NonNull J setChartMenuItems(Object chartMenuItems)
    {
        this.chartMenuItems = chartMenuItems;
        return (J) this;
    }

    /**
     * Provide a raw JavaScript function for chartMenuItems.
     */
    public @org.jspecify.annotations.NonNull J setChartMenuItemsRaw(String rawJs)
    {
        this.chartMenuItems = rawJs == null ? null : new RawJsFunction(rawJs);
        return (J) this;
    }
}
