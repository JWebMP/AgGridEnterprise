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
import org.jspecify.annotations.Nullable;

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
        // Retrieve base definition (may be community or enterprise). If none, return null for parity
        // with community behavior and to avoid introducing side-effects.
        AgGridColumnDef<?> base = super.getDefaultColDef();
        if (base == null)
        {
										super.setDefaultColDef(new AgGridEnterpriseColumnDef<>());
										base = super.getDefaultColDef();
        }

        AgGridEnterpriseColumnDef<?> ent;
        if (base instanceof AgGridEnterpriseColumnDef<?> enterprise)
        {
            ent = enterprise;
        }
        else
        {
            // Convert community defaultColDef into an enterprise one using MapStruct mapper
            ent = AgGridColDefEnterpriseMapper.INSTANCE.toEnterpriseColDef(base);
        }

        // Defensive copy: return a new instance so callers cannot mutate internal state
        try
        {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            byte[] json = mapper.writeValueAsBytes(ent);
            return mapper.readValue(json, AgGridEnterpriseColumnDef.class);
        }
        catch (Exception e)
        {
            return new AgGridEnterpriseColumnDef<>();
        }
    }

    /**
     * Convenience getter mirroring legacy suppressAggHeader flag at grid level for Enterprise.
     * Delegates to enterprise RowGroupingOptions (flattened via @JsonUnwrapped).
     */
    public Boolean getSuppressAggFuncInHeader()
    {
        return rowGroupingOptions() != null ? rowGroupingOptions().getSuppressAggFuncInHeader() : null;
    }

    /**
     * Convenience setter mirroring legacy suppressAggHeader flag at grid level for Enterprise.
     */
    @SuppressWarnings("unchecked")
    public J setSuppressAggFuncInHeader(Boolean suppress)
    {
        rowGroupingOptions().setSuppressAggFuncInHeader(suppress);
        return (J) this;
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

    public @Nullable String getGroupDisplayType()
    {
        return groupDisplayType;
    }

    public @org.jspecify.annotations.NonNull J setGroupDisplayType(@Nullable String type)
    {
        this.groupDisplayType = type;
        return (J) this;
    }

    public @org.jspecify.annotations.NonNull J setGroupDisplayType(RowGroupingDisplayType type)
    {
        this.groupDisplayType = type == null ? null : type.getJson();
        return (J) this;
    }

    public @Nullable Object getGroupRowRenderer()
    {
        return groupRowRenderer;
    }

    public @org.jspecify.annotations.NonNull J setGroupRowRenderer(@Nullable Object renderer)
    {
        this.groupRowRenderer = renderer;
        return (J) this;
    }

    public @Nullable Object getGroupRowRendererParams()
    {
        return groupRowRendererParams;
    }

    public @org.jspecify.annotations.NonNull J setGroupRowRendererParams(@Nullable Object params)
    {
        this.groupRowRendererParams = params;
        return (J) this;
    }

    public @Nullable Boolean getShowOpenedGroup()
    {
        return showOpenedGroup;
    }

    public @org.jspecify.annotations.NonNull J setShowOpenedGroup(@Nullable Boolean showOpenedGroup)
    {
        this.showOpenedGroup = showOpenedGroup;
        return (J) this;
    }

    public @Nullable Boolean getGroupMaintainOrder()
    {
        return groupMaintainOrder;
    }

    public @org.jspecify.annotations.NonNull J setGroupMaintainOrder(@Nullable Boolean groupMaintainOrder)
    {
        this.groupMaintainOrder = groupMaintainOrder;
        return (J) this;
    }

    public @Nullable Integer getGroupDefaultExpanded()
    {
        return groupDefaultExpanded;
    }

    public @org.jspecify.annotations.NonNull J setGroupDefaultExpanded(@Nullable Integer groupDefaultExpanded)
    {
        this.groupDefaultExpanded = groupDefaultExpanded;
        return (J) this;
    }

    public @Nullable Object getIsGroupOpenByDefault()
    {
        return isGroupOpenByDefault;
    }

    /**
     * Set a raw JavaScript function for isGroupOpenByDefault.
     * Example: "params => params.rowNode.level < 1".
     */
    public @org.jspecify.annotations.NonNull J setIsGroupOpenByDefault(@Nullable String rawJsFunction)
    {
        this.isGroupOpenByDefault = rawJsFunction == null ? null : new RawJsFunction(rawJsFunction);
        return (J) this;
    }

    public @Nullable Object getInitialGroupOrderComparator()
    {
        return initialGroupOrderComparator;
    }

    /**
     * Set a raw JavaScript comparator for initial group order.
     * Example: "(a,b) => a.key.localeCompare(b.key)".
     */
    public @org.jspecify.annotations.NonNull J setInitialGroupOrderComparator(@Nullable String rawJsFunction)
    {
        this.initialGroupOrderComparator = rawJsFunction == null ? null : new RawJsFunction(rawJsFunction);
        return (J) this;
    }

    public @Nullable Boolean getSuppressGroupRowsSticky()
    {
        return suppressGroupRowsSticky;
    }

    public @org.jspecify.annotations.NonNull J setSuppressGroupRowsSticky(@Nullable Boolean suppressGroupRowsSticky)
    {
        this.suppressGroupRowsSticky = suppressGroupRowsSticky;
        return (J) this;
    }

    public @Nullable Boolean getRowGroupPanelSuppressSort()
    {
        return rowGroupPanelSuppressSort;
    }

    public @org.jspecify.annotations.NonNull J setRowGroupPanelSuppressSort(@Nullable Boolean rowGroupPanelSuppressSort)
    {
        this.rowGroupPanelSuppressSort = rowGroupPanelSuppressSort;
        return (J) this;
    }

    public @Nullable Integer getGroupLockGroupColumns()
    {
        return groupLockGroupColumns;
    }

    public @org.jspecify.annotations.NonNull J setGroupLockGroupColumns(@Nullable Integer groupLockGroupColumns)
    {
        this.groupLockGroupColumns = groupLockGroupColumns;
        return (J) this;
    }

    public @Nullable Boolean getSuppressDragLeaveHidesColumns()
    {
        return suppressDragLeaveHidesColumns;
    }

    public @org.jspecify.annotations.NonNull J setSuppressDragLeaveHidesColumns(@Nullable Boolean suppressDragLeaveHidesColumns)
    {
        this.suppressDragLeaveHidesColumns = suppressDragLeaveHidesColumns;
        return (J) this;
    }

    public @Nullable Object getSuppressGroupChangesColumnVisibility()
    {
        return suppressGroupChangesColumnVisibility;
    }

    public @org.jspecify.annotations.NonNull J setSuppressGroupChangesColumnVisibility(boolean value)
    {
        this.suppressGroupChangesColumnVisibility = value;
        return (J) this;
    }

    public @org.jspecify.annotations.NonNull J setSuppressGroupChangesColumnVisibility(@Nullable SuppressGroupChangesColumnVisibilityMode mode)
    {
        this.suppressGroupChangesColumnVisibility = mode == null ? null : mode.getJson();
        return (J) this;
    }

    public @Nullable Boolean getSsrmExpandAllAffectsAllRows()
    {
        return ssrmExpandAllAffectsAllRows;
    }

    public @org.jspecify.annotations.NonNull J setSsrmExpandAllAffectsAllRows(@Nullable Boolean ssrmExpandAllAffectsAllRows)
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

    public @Nullable String getServerSideDatasource() {return serverSideDatasource;}

    public @org.jspecify.annotations.NonNull J setServerSideDatasourceRaw(@Nullable String rawJs)
    {
        this.serverSideDatasource = rawJs;
        return (J) this;
    }

    public @Nullable Integer getSsrmCacheBlockSize() {return ssrmCacheBlockSize;}

    public @org.jspecify.annotations.NonNull J setSsrmCacheBlockSize(@Nullable Integer v)
    {
        this.ssrmCacheBlockSize = v;
        return (J) this;
    }

    public @Nullable Integer getSsrmMaxBlocksInCache() {return ssrmMaxBlocksInCache;}

    public @org.jspecify.annotations.NonNull J setSsrmMaxBlocksInCache(@Nullable Integer v)
    {
        this.ssrmMaxBlocksInCache = v;
        return (J) this;
    }

    public @Nullable Integer getSsrmMaxConcurrentDatasourceRequests() {return ssrmMaxConcurrentDatasourceRequests;}

    public @org.jspecify.annotations.NonNull J setSsrmMaxConcurrentDatasourceRequests(@Nullable Integer v)
    {
        this.ssrmMaxConcurrentDatasourceRequests = v;
        return (J) this;
    }

    public @Nullable Integer getBlockLoadDebounceMillis() {return blockLoadDebounceMillis;}

    public @org.jspecify.annotations.NonNull J setBlockLoadDebounceMillis(@Nullable Integer v)
    {
        this.blockLoadDebounceMillis = v;
        return (J) this;
    }

    public @Nullable Boolean getSuppressServerSideFullWidthLoadingRow() {return suppressServerSideFullWidthLoadingRow;}

    public @org.jspecify.annotations.NonNull J setSuppressServerSideFullWidthLoadingRow(@Nullable Boolean v)
    {
        this.suppressServerSideFullWidthLoadingRow = v;
        return (J) this;
    }

    public @Nullable Boolean getPurgeClosedRowNodes() {return purgeClosedRowNodes;}

    public @org.jspecify.annotations.NonNull J setPurgeClosedRowNodes(@Nullable Boolean v)
    {
        this.purgeClosedRowNodes = v;
        return (J) this;
    }

    public @Nullable String getServerSidePivotResultFieldSeparator() {return serverSidePivotResultFieldSeparator;}

    public @org.jspecify.annotations.NonNull J setServerSidePivotResultFieldSeparator(@Nullable String v)
    {
        this.serverSidePivotResultFieldSeparator = v;
        return (J) this;
    }

    public @Nullable Boolean getServerSideSortAllLevels() {return serverSideSortAllLevels;}

    public @org.jspecify.annotations.NonNull J setServerSideSortAllLevels(@Nullable Boolean v)
    {
        this.serverSideSortAllLevels = v;
        return (J) this;
    }

    public @Nullable Boolean getServerSideEnableClientSideSort() {return serverSideEnableClientSideSort;}

    public @org.jspecify.annotations.NonNull J setServerSideEnableClientSideSort(@Nullable Boolean v)
    {
        this.serverSideEnableClientSideSort = v;
        return (J) this;
    }

    public @Nullable Boolean getServerSideOnlyRefreshFilteredGroups() {return serverSideOnlyRefreshFilteredGroups;}

    public @org.jspecify.annotations.NonNull J setServerSideOnlyRefreshFilteredGroups(@Nullable Boolean v)
    {
        this.serverSideOnlyRefreshFilteredGroups = v;
        return (J) this;
    }

    public @Nullable Integer getServerSideInitialRowCount() {return serverSideInitialRowCount;}

    public @org.jspecify.annotations.NonNull J setServerSideInitialRowCount(@Nullable Integer v)
    {
        this.serverSideInitialRowCount = v;
        return (J) this;
    }

    public @Nullable String getGetChildCount() {return getChildCount;}

    public @org.jspecify.annotations.NonNull J setGetChildCountRaw(@Nullable String rawJs)
    {
        this.getChildCount = rawJs;
        return (J) this;
    }

    public @Nullable String getGetServerSideGroupLevelParams() {return getServerSideGroupLevelParams;}

    public @org.jspecify.annotations.NonNull J setGetServerSideGroupLevelParamsRaw(@Nullable String rawJs)
    {
        this.getServerSideGroupLevelParams = rawJs;
        return (J) this;
    }

    public @Nullable String getIsServerSideGroupOpenByDefault() {return isServerSideGroupOpenByDefault;}

    public @org.jspecify.annotations.NonNull J setIsServerSideGroupOpenByDefaultRaw(@Nullable String rawJs)
    {
        this.isServerSideGroupOpenByDefault = rawJs;
        return (J) this;
    }

    public @Nullable String getIsApplyServerSideTransaction() {return isApplyServerSideTransaction;}

    public @org.jspecify.annotations.NonNull J setIsApplyServerSideTransactionRaw(@Nullable String rawJs)
    {
        this.isApplyServerSideTransaction = rawJs;
        return (J) this;
    }

    public @Nullable String getIsServerSideGroup() {return isServerSideGroup;}

    public @org.jspecify.annotations.NonNull J setIsServerSideGroupRaw(@Nullable String rawJs)
    {
        this.isServerSideGroup = rawJs;
        return (J) this;
    }

    public @Nullable String getGetServerSideGroupKey() {return getServerSideGroupKey;}

    public @org.jspecify.annotations.NonNull J setGetServerSideGroupKeyRaw(@Nullable String rawJs)
    {
        this.getServerSideGroupKey = rawJs;
        return (J) this;
    }

    /**
     * AG Grid v34.2.0: Suppress infinite scrolling in server-side row model (enabled by default).
     */
    public @Nullable Boolean getSuppressServerSideInfiniteScroll() {return suppressServerSideInfiniteScroll;}

    public @org.jspecify.annotations.NonNull J setSuppressServerSideInfiniteScroll(@Nullable Boolean v)
    {
        this.suppressServerSideInfiniteScroll = v;
        return (J) this;
    }

    /**
     * AG Grid v34.2.0: Suppress chart tool panels button visibility (visible by default in v34).
     */
    public @Nullable Boolean getSuppressChartToolPanelsButton() {return suppressChartToolPanelsButton;}

    public @org.jspecify.annotations.NonNull J setSuppressChartToolPanelsButton(@Nullable Boolean v)
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

    public @Nullable Boolean getPivotMode() {return pivotMode;}

    public @org.jspecify.annotations.NonNull J setPivotMode(@Nullable Boolean v)
    {
        this.pivotMode = v;
        return (J) this;
    }

    public @Nullable Integer getPivotDefaultExpanded() {return pivotDefaultExpanded;}

    public @org.jspecify.annotations.NonNull J setPivotDefaultExpanded(@Nullable Integer v)
    {
        this.pivotDefaultExpanded = v;
        return (J) this;
    }

    public @Nullable String getPivotRowTotals() {return pivotRowTotals;}

    public @org.jspecify.annotations.NonNull J setPivotRowTotals(@Nullable String v)
    {
        this.pivotRowTotals = v;
        return (J) this;
    }

    public @org.jspecify.annotations.NonNull J setPivotRowTotals(@Nullable PivotRowTotalsPosition pos)
    {
        this.pivotRowTotals = pos == null ? null : pos.getJson();
        return (J) this;
    }

    public @Nullable Boolean getPivotSuppressAutoColumn() {return pivotSuppressAutoColumn;}

    public @org.jspecify.annotations.NonNull J setPivotSuppressAutoColumn(@Nullable Boolean v)
    {
        this.pivotSuppressAutoColumn = v;
        return (J) this;
    }

    public @Nullable Integer getPivotMaxGeneratedColumns() {return pivotMaxGeneratedColumns;}

    public @org.jspecify.annotations.NonNull J setPivotMaxGeneratedColumns(@Nullable Integer v)
    {
        this.pivotMaxGeneratedColumns = v;
        return (J) this;
    }

    public @Nullable String getOnPivotMaxColumnsExceeded() {return onPivotMaxColumnsExceeded;}

    public @org.jspecify.annotations.NonNull J setOnPivotMaxColumnsExceededRaw(@Nullable String rawJs)
    {
        this.onPivotMaxColumnsExceeded = rawJs;
        return (J) this;
    }

    public @Nullable String getProcessPivotResultColDef() {return processPivotResultColDef;}

    public @org.jspecify.annotations.NonNull J setProcessPivotResultColDefRaw(@Nullable String rawJs)
    {
        this.processPivotResultColDef = rawJs;
        return (J) this;
    }

    public @Nullable String getProcessPivotResultColGroupDef() {return processPivotResultColGroupDef;}

    public @org.jspecify.annotations.NonNull J setProcessPivotResultColGroupDefRaw(@Nullable String rawJs)
    {
        this.processPivotResultColGroupDef = rawJs;
        return (J) this;
    }

    public @Nullable Boolean getSuppressExpandablePivotGroups() {return suppressExpandablePivotGroups;}

    public @org.jspecify.annotations.NonNull J setSuppressExpandablePivotGroups(@Nullable Boolean v)
    {
        this.suppressExpandablePivotGroups = v;
        return (J) this;
    }

    public @Nullable Boolean getFunctionsReadOnly() {return functionsReadOnly;}

    public @org.jspecify.annotations.NonNull J setFunctionsReadOnly(@Nullable Boolean v)
    {
        this.functionsReadOnly = v;
        return (J) this;
    }

    public @Nullable Boolean getRemovePivotHeaderRowWhenSingleValueColumn() {return removePivotHeaderRowWhenSingleValueColumn;}

    public @org.jspecify.annotations.NonNull J setRemovePivotHeaderRowWhenSingleValueColumn(@Nullable Boolean v)
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

    public @Nullable Boolean getEnableAdvancedFilter() {return enableAdvancedFilter;}

    public @org.jspecify.annotations.NonNull J setEnableAdvancedFilter(@Nullable Boolean enableAdvancedFilter)
    {
        this.enableAdvancedFilter = enableAdvancedFilter;
        return (J) this;
    }

    public @Nullable Boolean getIncludeHiddenColumnsInAdvancedFilter() {return includeHiddenColumnsInAdvancedFilter;}

    public @org.jspecify.annotations.NonNull J setIncludeHiddenColumnsInAdvancedFilter(@Nullable Boolean includeHiddenColumnsInAdvancedFilter)
    {
        this.includeHiddenColumnsInAdvancedFilter = includeHiddenColumnsInAdvancedFilter;
        return (J) this;
    }

    public @Nullable Object getAdvancedFilterParent() {return advancedFilterParent;}

    public @org.jspecify.annotations.NonNull J setAdvancedFilterParent(@Nullable Object advancedFilterParent)
    {
        this.advancedFilterParent = advancedFilterParent;
        return (J) this;
    }

    public @Nullable Object getAdvancedFilterBuilderParams() {return advancedFilterBuilderParams;}

    public @org.jspecify.annotations.NonNull J setAdvancedFilterBuilderParams(@Nullable Object advancedFilterBuilderParams)
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

    public @Nullable Object getAdvancedFilterParams() {return advancedFilterParams;}

    public @org.jspecify.annotations.NonNull J setAdvancedFilterParams(@Nullable Object advancedFilterParams)
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

    public @Nullable String getGetChartToolbarItems() {return getChartToolbarItems;}

    public @org.jspecify.annotations.NonNull J setGetChartToolbarItemsRaw(@Nullable String rawJs)
    {
        this.getChartToolbarItems = rawJs;
        return (J) this;
    }

    public @Nullable String getCreateChartContainer() {return createChartContainer;}

    public @org.jspecify.annotations.NonNull J setCreateChartContainerRaw(@Nullable String rawJs)
    {
        this.createChartContainer = rawJs;
        return (J) this;
    }

    public @Nullable Object getCustomChartThemes() {return customChartThemes;}

    public @org.jspecify.annotations.NonNull J setCustomChartThemes(@Nullable Object customChartThemes)
    {
        this.customChartThemes = customChartThemes;
        return (J) this;
    }

    /**
     * Provide custom chart themes as a raw TypeScript/JSON literal string mapping theme names to themes.
     * This preserves callbacks in overrides and allows referencing AG Charts options directly.
     */
    public @org.jspecify.annotations.NonNull J setCustomChartThemesRaw(@Nullable String rawLiteral)
    {
        this.customChartThemes = rawLiteral == null ? null : new RawJsFunction(rawLiteral);
        return (J) this;
    }

    public @Nullable Object getChartMenuItems() {return chartMenuItems;}

    /**
     * Accepts either an array of items or a raw JavaScript function (via setChartMenuItemsRaw).
     */
    public @org.jspecify.annotations.NonNull J setChartMenuItems(@Nullable Object chartMenuItems)
    {
        this.chartMenuItems = chartMenuItems;
        return (J) this;
    }

    /**
     * Provide a raw JavaScript function for chartMenuItems.
     */
    public @org.jspecify.annotations.NonNull J setChartMenuItemsRaw(@Nullable String rawJs)
    {
        this.chartMenuItems = rawJs == null ? null : new RawJsFunction(rawJs);
        return (J) this;
    }
}
