package com.jwebmp.plugins.aggridenterprise.options;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.jwebmp.plugins.aggrid.options.AgGridColumnDef;
import com.jwebmp.plugins.aggrid.options.AgGridOptions;
import com.jwebmp.plugins.aggridenterprise.options.enums.PivotPanelShow;
import com.jwebmp.plugins.aggridenterprise.options.enums.RowGroupPanelShow;
import com.jwebmp.plugins.aggridenterprise.options.enums.RowModelTypeEnterprise;
import com.jwebmp.plugins.aggridenterprise.options.find.IFindOptions;
import com.jwebmp.plugins.aggridenterprise.options.mapping.AgGridColDefEnterpriseMapper;

import java.util.List;

/**
 * Enterprise-only options layered on top of community AgGridOptions
 */
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

    @JsonProperty("enableCharts")
    private Boolean enableCharts;

    @JsonProperty("enableRangeSelection")
    private Boolean enableRangeSelection;


    /**
     * always | onlyWhenGrouping | never
     */
    @JsonProperty("rowGroupPanelShow")
    private String rowGroupPanelShow;

    /**
     * always | never
     */
    @JsonProperty("pivotPanelShow")
    private String pivotPanelShow;


    /**
     * Allow reordering and pinning columns by dragging from the Columns Tool Panel.
     * ColumnsToolPanelModule (Enterprise).
     */
    @JsonProperty("allowDragFromColumnsToolPanel")
    private Boolean allowDragFromColumnsToolPanel;

    /**
     * Chart themes to use, e.g. ["ag-default", "ag-material"]
     */
    @JsonProperty("chartThemes")
    private List<String> chartThemes;

    /**
     * Optional chart theme overrides structure (as per AG Grid docs).
     */
    @JsonProperty("chartThemeOverrides")
    private Object chartThemeOverrides;

    /**
     * Integrated Charts: configuration of tool panels in the charting UI.
     * Allows defining available panels and which one is open by default.
     */
    @JsonProperty("chartToolPanelsDef")
    private ChartToolPanelsDef chartToolPanelsDef;

    // Enterprise-only: Status Bar and Side Bar moved here from community options
    @JsonProperty("statusBar")
    private Object statusBarEnterprise;

    @JsonProperty("sideBar")
    private Object sideBarEnterprise;


    /**
     * Grouping: when true, rows with null/undefined/empty values are shown without a (Blanks) group.
     */
    @JsonProperty("groupAllowUnbalanced")
    private Boolean groupAllowUnbalanced;

    /**
     * Grouping: hide parents that only have a single child. Accepts true or the string "leafGroupsOnly".
     * Using Object to allow both Boolean and String values.
     */
    @JsonProperty("groupHideParentOfSingleChild")
    private Object groupHideParentOfSingleChild;

    /**
     * Grouping: hide open parents to show leaf nodes in a flat list under the open group.
     * Mutually exclusive with groupHideParentOfSingleChild.
     */
    @JsonProperty("groupHideOpenParents")
    private Boolean groupHideOpenParents;

    /**
     * Custom group hierarchy components. Map of key -> ColDef definition usable within rowGroupingHierarchy.
     */
    @JsonProperty("groupHierarchyConfig")
    private Object groupHierarchyConfig;

    /**
     * Aggregation total rows: include a group total row at 'top' or 'bottom', or provide a callback.
     * For callback support we allow a raw JS function via an internal JavascriptFunction wrapper.
     */
    @JsonProperty("groupTotalRow")
    private Object groupTotalRow;

    /**
     * Aggregation total rows: include a grand total row positioned at 'top' | 'bottom' | 'pinnedTop' | 'pinnedBottom'.
     */
    @JsonProperty("grandTotalRow")
    private String grandTotalRow;

    /**
     * When a total row is visible, the group row values are hidden. Prevent this by setting true.
     */
    @JsonProperty("groupSuppressBlankHeader")
    private Boolean groupSuppressBlankHeader;

    /**
     * When true, column headers won't include the aggFunc name in header labels.
     * Grid option (enterprise): suppresses e.g. 'sum(Bank Balance)' -> 'Bank Balance'.
     */
    @JsonProperty("suppressAggFuncInHeader")
    private Boolean suppressAggFuncInHeader;

    /**
     * Suppress sticky behaviour of total rows. Can be true to suppress both, or a restricted string 'grand' | 'group'.
     */
    @JsonProperty("suppressStickyTotalRow")
    private Object suppressStickyTotalRow;

    /**
     * When true, use community default filters based on data type instead of Set Filter by default.
     */
    @JsonProperty("suppressSetFilterByDefault")
    private Boolean suppressSetFilterByDefault;


    /**
     * Cell Selection: Excel-like cell range selection configuration.
     */
    @JsonProperty("cellSelection")
    private com.jwebmp.plugins.aggridenterprise.options.cellselection.ICellSelectionOptions<?> cellSelection;

    public Boolean getEnableCharts()
    {
        return enableCharts;
    }

    public J setEnableCharts(Boolean enableCharts)
    {
        this.enableCharts = enableCharts;
        return (J) this;
    }

    public Boolean getEnableRangeSelection()
    {
        return enableRangeSelection;
    }

    public J setEnableRangeSelection(Boolean enableRangeSelection)
    {
        this.enableRangeSelection = enableRangeSelection;
        return (J) this;
    }

    public Object getSideBar()
    {
        return sideBarEnterprise;
    }

    /**
     * Accepts: Boolean (enable/disable), String preset (e.g. "columns"), or a {@link SideBarDef}
     */
    public J setSideBar(Object sideBar)
    {
        this.sideBarEnterprise = sideBar;
        return (J) this;
    }

    /**
     * Convenience: set a full side bar definition
     */
    public J setSideBar(SideBarDef sideBar)
    {
        this.sideBarEnterprise = sideBar;
        return (J) this;
    }

    /**
     * Convenience: enable/disable sidebar
     */
    public J setSideBarEnabled(boolean enabled)
    {
        this.sideBarEnterprise = enabled;
        return (J) this;
    }

    /**
     * Convenience: use a preset string such as "columns" or "filters" or "columns,filters"
     */
    public J setSideBarPreset(String preset)
    {
        this.sideBarEnterprise = preset;
        return (J) this;
    }

    public String getRowGroupPanelShow()
    {
        return rowGroupPanelShow;
    }

    public J setRowGroupPanelShow(String rowGroupPanelShow)
    {
        this.rowGroupPanelShow = rowGroupPanelShow;
        return (J) this;
    }

    /**
     * Enum convenience overload
     */
    public J setRowGroupPanelShow(RowGroupPanelShow show)
    {
        this.rowGroupPanelShow = show == null ? null : show.getJson();
        return (J) this;
    }

    public String getPivotPanelShow()
    {
        return pivotPanelShow;
    }

    public J setPivotPanelShow(String pivotPanelShow)
    {
        this.pivotPanelShow = pivotPanelShow;
        return (J) this;
    }

    /**
     * Enum convenience overload
     */
    public J setPivotPanelShow(PivotPanelShow show)
    {
        this.pivotPanelShow = show == null ? null : show.getJson();
        return (J) this;
    }

    public String getRowModelType()
    {
        return super.getRowModelType();
    }

    public J setRowModelType(String rowModelType)
    {
        super.setRowModelType(rowModelType);
        return (J) this;
    }

    /**
     * Enum convenience overload
     */
    public J setRowModelType(RowModelTypeEnterprise type)
    {
        super.setRowModelType(type == null ? null : type.getJson());
        return (J) this;
    }

    public Object getStatusBar()
    {
        return statusBarEnterprise;
    }

    /**
     * Accepts {@link StatusBarDef} or Boolean false to disable
     */
    public J setStatusBar(Object statusBar)
    {
        this.statusBarEnterprise = statusBar;
        return (J) this;
    }

    public J setStatusBar(StatusBarDef statusBar)
    {
        this.statusBarEnterprise = statusBar;
        return (J) this;
    }

    /**
     * @return whether dragging columns from the Columns Tool Panel is allowed
     */
    public Boolean getAllowDragFromColumnsToolPanel()
    {
        return allowDragFromColumnsToolPanel;
    }

    /**
     * Allow reordering and pinning columns by dragging from the Columns Tool Panel (Enterprise).
     */
    public J setAllowDragFromColumnsToolPanel(Boolean allowDragFromColumnsToolPanel)
    {
        this.allowDragFromColumnsToolPanel = allowDragFromColumnsToolPanel;
        return (J) this;
    }

    public List<String> getChartThemes()
    {
        return chartThemes;
    }

    public J setChartThemes(List<String> chartThemes)
    {
        this.chartThemes = chartThemes;
        return (J) this;
    }

    public Object getChartThemeOverrides()
    {
        return chartThemeOverrides;
    }

    public J setChartThemeOverrides(Object chartThemeOverrides)
    {
        this.chartThemeOverrides = chartThemeOverrides;
        return (J) this;
    }

    /**
     * Get the chart tool panels definition.
     */
    public ChartToolPanelsDef getChartToolPanelsDef()
    {
        return chartToolPanelsDef;
    }

    /**
     * Set the chart tool panels definition object.
     */
    public J setChartToolPanelsDef(ChartToolPanelsDef chartToolPanelsDef)
    {
        this.chartToolPanelsDef = chartToolPanelsDef;
        return (J) this;
    }

    /**
     * Convenience: set the available panels by enum ids only.
     */
    public J withChartToolPanels(java.util.List<ToolPanelId> panelIds)
    {
        if (panelIds == null)
        {
            this.chartToolPanelsDef = null;
        }
        else
        {
            if (this.chartToolPanelsDef == null)
            {
                this.chartToolPanelsDef = new ChartToolPanelsDef();
            }
            java.util.ArrayList<ChartToolPanelsDef.ToolPanel> list = new java.util.ArrayList<>();
            for (ToolPanelId id : panelIds)
            {
                if (id != null)
                {
                    list.add(new ChartToolPanelsDef.ToolPanel().setId(id));
                }
            }
            this.chartToolPanelsDef.setPanels(list);
        }
        return (J) this;
    }

    /**
     * Convenience: define default tool panel to open.
     */
    public J withDefaultChartToolPanel(ToolPanelId id)
    {
        if (id == null)
        {
            if (this.chartToolPanelsDef != null)
            {
                this.chartToolPanelsDef.setDefaultToolPanel((String) null);
            }
            return (J) this;
        }
        if (this.chartToolPanelsDef == null)
        {
            this.chartToolPanelsDef = new ChartToolPanelsDef();
        }
        this.chartToolPanelsDef.setDefaultToolPanel(id);
        return (J) this;
    }

    /**
     * Returns the rowNumbers configuration (Boolean or RowNumbersOptions)
     */
    public Object getRowNumbers()
    {
        return super.getRowNumbers();
    }

    /**
     * Set rowNumbers using a boolean
     */
    public J setRowNumbers(boolean enabled)
    {
        super.setRowNumbers(enabled);
        return (J) this;
    }

    /**
     * Set rowNumbers using a RowNumbersOptions object
     */
    public J setRowNumbers(RowNumbersOptions options)
    {
        super.setRowNumbers(options);
        return (J) this;
    }

    // --- Row Grouping (Enterprise) ---

    /**
     * @return whether unbalanced grouping is allowed (null => default)
     */
    public Boolean getGroupAllowUnbalanced()
    {
        return groupAllowUnbalanced;
    }

    /**
     * When true, rows with null/undefined/empty values are displayed without a (Blanks) group.
     */
    public J setGroupAllowUnbalanced(Boolean groupAllowUnbalanced)
    {
        this.groupAllowUnbalanced = groupAllowUnbalanced;
        return (J) this;
    }

    /**
     * @return value for hiding parents of single child groups. May be Boolean or the String "leafGroupsOnly".
     */
    public Object getGroupHideParentOfSingleChild()
    {
        return groupHideParentOfSingleChild;
    }

    /**
     * Set to true to hide all parents that have a single child.
     */
    public J setGroupHideParentOfSingleChild(boolean hide)
    {
        this.groupHideParentOfSingleChild = hide;
        return (J) this;
    }

    /**
     * Set to "leafGroupsOnly" to hide only parents where the single child is a leaf node.
     */
    public J setGroupHideParentOfSingleChildLeafGroupsOnly()
    {
        this.groupHideParentOfSingleChild = "leafGroupsOnly";
        return (J) this;
    }

    /**
     * Advanced setter allowing either a Boolean or String value.
     */
    public J setGroupHideParentOfSingleChild(Object value)
    {
        this.groupHideParentOfSingleChild = value;
        return (J) this;
    }

    /**
     * @return whether open parents are hidden (mutually exclusive with groupHideParentOfSingleChild)
     */
    public Boolean getGroupHideOpenParents()
    {
        return groupHideOpenParents;
    }

    public J setGroupHideOpenParents(Boolean groupHideOpenParents)
    {
        this.groupHideOpenParents = groupHideOpenParents;
        return (J) this;
    }

    /**
     * @return custom grouping hierarchy configuration
     */
    public Object getGroupHierarchyConfig()
    {
        return groupHierarchyConfig;
    }

    /**
     * Accepts a Map<String, AgGridColumnDef<?>> or any object that serializes to the same structure.
     */
    public J setGroupHierarchyConfig(Object groupHierarchyConfig)
    {
        this.groupHierarchyConfig = groupHierarchyConfig;
        return (J) this;
    }

    /**
     * @return whether Set Filter is suppressed by default when filter=true (community defaults used instead)
     */
    public Boolean getSuppressSetFilterByDefault()
    {
        return suppressSetFilterByDefault;
    }

    /**
     * Set to true to use Text/Number/Date filters based on data type when filter=true, instead of Set Filter.
     */
    public J setSuppressSetFilterByDefault(Boolean suppressSetFilterByDefault)
    {
        this.suppressSetFilterByDefault = suppressSetFilterByDefault;
        return (J) this;
    }

    /**
     * Find: get the current search value.
     */
    public String getFindSearchValue()
    {
        return super.getFindSearchValue();
    }

    /**
     * Find: set the search value to look for across the grid.
     */
    public J setFindSearchValue(String findSearchValue)
    {
        super.setFindSearchValue(findSearchValue);
        return (J) this;
    }

    /**
     * Find: get options controlling find behaviour.
     */
    public IFindOptions<?> getFindOptions()
    {
        return (IFindOptions<?>) super.getFindOptions();
    }

    /**
     * Find: set options controlling find behaviour.
     */
    public J setFindOptions(IFindOptions<?> findOptions)
    {
        super.setFindOptions(findOptions);
        return (J) this;
    }

    /**
     * Cell Selection: get current configuration.
     */
    public com.jwebmp.plugins.aggridenterprise.options.cellselection.ICellSelectionOptions<?> getCellSelection()
    {
        return cellSelection;
    }

    /**
     * Cell Selection: set configuration object.
     */
    public J setCellSelection(com.jwebmp.plugins.aggridenterprise.options.cellselection.ICellSelectionOptions<?> cellSelection)
    {
        this.cellSelection = cellSelection;
        return (J) this;
    }

    /**
     * Convenience: enable Cell Selection with default options.
     */
    public J enableCellSelection()
    {
        this.cellSelection = new com.jwebmp.plugins.aggridenterprise.options.cellselection.CellSelectionOptions();
        return (J) this;
    }

    /**
     * Convenience: enable Cell Selection Range Handle (handle: { mode: 'range' }).
     */
    public J enableRangeHandle()
    {
        if (this.cellSelection == null)
        {
            this.cellSelection = new com.jwebmp.plugins.aggridenterprise.options.cellselection.CellSelectionOptions();
        }
        this.cellSelection.setHandle(new com.jwebmp.plugins.aggridenterprise.options.cellselection.RangeHandleOptions());
        return (J) this;
    }

    /**
     * Convenience: enable Cell Selection Fill Handle (handle: { mode: 'fill' }).
     */
    public J enableFillHandle()
    {
        if (this.cellSelection == null)
        {
            this.cellSelection = new com.jwebmp.plugins.aggridenterprise.options.cellselection.CellSelectionOptions();
        }
        this.cellSelection.setHandle(new com.jwebmp.plugins.aggridenterprise.options.cellselection.FillHandleOptions());
        return (J) this;
    }

    public String getGetContextMenuItems()
    {
        return super.getGetContextMenuItems();
    }

    public J setGetContextMenuItems(String getContextMenuItems)
    {
        super.setGetContextMenuItemsRaw(getContextMenuItems);
        return (J) this;
    }
    // ===== Aggregate Total Rows & Sticky Behaviour =====

    public Object getGroupTotalRow()
    {
        return groupTotalRow;
    }

    /**
     * Set group total row position via enum.
     */
    public J setGroupTotalRow(GroupTotalRowPosition pos)
    {
        this.groupTotalRow = pos == null ? null : pos.getJson();
        return (J) this;
    }

    /**
     * Provide a raw JavaScript callback for selective group total rows.
     * Example: params => (params.node && params.node.level === 1) ? 'bottom' : undefined
     */
    public J setGroupTotalRowCallback(String rawJsFunction)
    {
        this.groupTotalRow = rawJsFunction == null ? null : new RawJsFunction(rawJsFunction);
        return (J) this;
    }

    public String getGrandTotalRow()
    {
        return grandTotalRow;
    }

    public J setGrandTotalRow(GrandTotalRowPosition pos)
    {
        this.grandTotalRow = pos == null ? null : pos.getJson();
        return (J) this;
    }

    public Boolean getGroupSuppressBlankHeader()
    {
        return groupSuppressBlankHeader;
    }

    public J setGroupSuppressBlankHeader(Boolean groupSuppressBlankHeader)
    {
        this.groupSuppressBlankHeader = groupSuppressBlankHeader;
        return (J) this;
    }

    public Boolean getSuppressAggFuncInHeader()
    {
        return suppressAggFuncInHeader;
    }

    public J setSuppressAggFuncInHeader(Boolean suppressAggFuncInHeader)
    {
        this.suppressAggFuncInHeader = suppressAggFuncInHeader;
        return (J) this;
    }

    public Object getSuppressStickyTotalRow()
    {
        return suppressStickyTotalRow;
    }

    public J setSuppressStickyTotalRow(Boolean suppress)
    {
        this.suppressStickyTotalRow = suppress;
        return (J) this;
    }

    public J setSuppressStickyTotalRow(StickyTotalRowSuppression which)
    {
        this.suppressStickyTotalRow = which == null ? null : which.getJson();
        return (J) this;
    }

    // ===== Row Aggregation (Enterprise Grid Options) =====
    /**
     * Map of aggregation function name to function. Accepts object map or raw JS via setAggFuncsRaw.
     */
    @JsonProperty("aggFuncs")
    private Object aggFuncs;

    /**
     * When true, only the updated column will be re-aggregated when using change detection.
     */
    @JsonProperty("aggregateOnlyChangedColumns")
    private Boolean aggregateOnlyChangedColumns;

    /**
     * Set to true so that aggregations are not impacted by filtering.
     */
    @JsonProperty("suppressAggFilteredOnly")
    private Boolean suppressAggFilteredOnly;

    /**
     * Determine whether filters should be applied on aggregated group values. Boolean or callback.
     */
    @JsonProperty("groupAggFiltering")
    private Object groupAggFiltering;

    /**
     * Always calculate the root level aggregation value.
     */
    @JsonProperty("alwaysAggregateAtRootLevel")
    private Boolean alwaysAggregateAtRootLevel;

    /**
     * Callback to use when you need access to more than the current column for aggregation.
     */
    @JsonProperty("getGroupRowAgg")
    @JsonRawValue
    private String getGroupRowAgg;

    public Object getAggFuncs() {return aggFuncs;}

    /**
     * Set aggFuncs using an object map.
     */
    public J setAggFuncs(Object aggFuncs)
    {
        this.aggFuncs = aggFuncs;
        return (J) this;
    }

    /**
     * Provide raw JS object for aggFuncs, e.g., "{ mySum: (params)=>{...} }"
     */
    public J setAggFuncsRaw(String rawJs)
    {
        this.aggFuncs = rawJs == null ? null : new RawJsFunction(rawJs);
        return (J) this;
    }

    public Boolean getAggregateOnlyChangedColumns() {return aggregateOnlyChangedColumns;}

    public J setAggregateOnlyChangedColumns(Boolean v)
    {
        this.aggregateOnlyChangedColumns = v;
        return (J) this;
    }

    public Boolean getSuppressAggFilteredOnly() {return suppressAggFilteredOnly;}

    public J setSuppressAggFilteredOnly(Boolean v)
    {
        this.suppressAggFilteredOnly = v;
        return (J) this;
    }

    public Object getGroupAggFiltering() {return groupAggFiltering;}

    public J setGroupAggFiltering(Boolean v)
    {
        this.groupAggFiltering = v;
        return (J) this;
    }

    /**
     * Supply raw JS predicate: e.g., "(params) => true"
     */
    public J setGroupAggFilteringRaw(String rawJs)
    {
        this.groupAggFiltering = rawJs == null ? null : new RawJsFunction(rawJs);
        return (J) this;
    }

    public Boolean getAlwaysAggregateAtRootLevel() {return alwaysAggregateAtRootLevel;}

    public J setAlwaysAggregateAtRootLevel(Boolean v)
    {
        this.alwaysAggregateAtRootLevel = v;
        return (J) this;
    }

    public String getGetGroupRowAgg() {return getGroupRowAgg;}

    /**
     * Set raw JS for getGroupRowAgg: e.g., "params => ({ total: 0 })"
     */
    public J setGetGroupRowAggRaw(String rawJs)
    {
        this.getGroupRowAgg = rawJs;
        return (J) this;
    }

    // ===== Chart Tool Panels (Integrated Charts) =====

    /**
     * Known tool panel identifiers for Integrated Charts UI.
     */
    public enum ToolPanelId
    {
        DATA("data"),
        FORMAT("format"),
        SETTINGS("settings");
        private final String json;

        ToolPanelId(String json) {this.json = json;}

        public String getJson() {return json;}

        @Override
        public String toString() {return json;}
    }

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

        public java.util.List<String> getPanels()
        {
            return panels;
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
    }

    // ===== Internal helpers & enums for this options class =====

    public enum GroupTotalRowPosition
    {
        TOP("top"),
        BOTTOM("bottom");
        private final String json;

        GroupTotalRowPosition(String json) {this.json = json;}

        public String getJson() {return json;}
    }

    public enum GrandTotalRowPosition
    {
        TOP("top"),
        BOTTOM("bottom"),
        PINNED_TOP("pinnedTop"),
        PINNED_BOTTOM("pinnedBottom");
        private final String json;

        GrandTotalRowPosition(String json) {this.json = json;}

        public String getJson() {return json;}
    }

    public enum StickyTotalRowSuppression
    {
        GRAND("grand"),
        GROUP("group");
        private final String json;

        StickyTotalRowSuppression(String json) {this.json = json;}

        public String getJson() {return json;}
    }

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

    // ===== Enums for new options =====

    public enum RowGroupingDisplayType
    {
        SINGLE_COLUMN("singleColumn"),
        MULTIPLE_COLUMNS("multipleColumns"),
        GROUP_ROWS("groupRows"),
        CUSTOM("custom");
        private final String json;

        RowGroupingDisplayType(String json) {this.json = json;}

        public String getJson() {return json;}

        @Override
        public String toString() {return json;}
    }

    public enum SuppressGroupChangesColumnVisibilityMode
    {
        SUPPRESS_HIDE_ON_GROUP("suppressHideOnGroup"),
        SUPPRESS_SHOW_ON_UNGROUP("suppressShowOnUngroup");
        private final String json;

        SuppressGroupChangesColumnVisibilityMode(String json) {this.json = json;}

        public String getJson() {return json;}

        @Override
        public String toString() {return json;}
    }

    // ===== Getters / Setters for new options =====

    public String getGroupDisplayType()
    {
        return groupDisplayType;
    }

    public J setGroupDisplayType(String type)
    {
        this.groupDisplayType = type;
        return (J) this;
    }

    public J setGroupDisplayType(RowGroupingDisplayType type)
    {
        this.groupDisplayType = type == null ? null : type.getJson();
        return (J) this;
    }

    public Object getGroupRowRenderer()
    {
        return groupRowRenderer;
    }

    public J setGroupRowRenderer(Object renderer)
    {
        this.groupRowRenderer = renderer;
        return (J) this;
    }

    public Object getGroupRowRendererParams()
    {
        return groupRowRendererParams;
    }

    public J setGroupRowRendererParams(Object params)
    {
        this.groupRowRendererParams = params;
        return (J) this;
    }

    public Boolean getShowOpenedGroup()
    {
        return showOpenedGroup;
    }

    public J setShowOpenedGroup(Boolean showOpenedGroup)
    {
        this.showOpenedGroup = showOpenedGroup;
        return (J) this;
    }

    public Boolean getGroupMaintainOrder()
    {
        return groupMaintainOrder;
    }

    public J setGroupMaintainOrder(Boolean groupMaintainOrder)
    {
        this.groupMaintainOrder = groupMaintainOrder;
        return (J) this;
    }

    public Integer getGroupDefaultExpanded()
    {
        return groupDefaultExpanded;
    }

    public J setGroupDefaultExpanded(Integer groupDefaultExpanded)
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
    public J setIsGroupOpenByDefault(String rawJsFunction)
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
    public J setInitialGroupOrderComparator(String rawJsFunction)
    {
        this.initialGroupOrderComparator = rawJsFunction == null ? null : new RawJsFunction(rawJsFunction);
        return (J) this;
    }

    public Boolean getSuppressGroupRowsSticky()
    {
        return suppressGroupRowsSticky;
    }

    public J setSuppressGroupRowsSticky(Boolean suppressGroupRowsSticky)
    {
        this.suppressGroupRowsSticky = suppressGroupRowsSticky;
        return (J) this;
    }

    public Boolean getRowGroupPanelSuppressSort()
    {
        return rowGroupPanelSuppressSort;
    }

    public J setRowGroupPanelSuppressSort(Boolean rowGroupPanelSuppressSort)
    {
        this.rowGroupPanelSuppressSort = rowGroupPanelSuppressSort;
        return (J) this;
    }

    public Integer getGroupLockGroupColumns()
    {
        return groupLockGroupColumns;
    }

    public J setGroupLockGroupColumns(Integer groupLockGroupColumns)
    {
        this.groupLockGroupColumns = groupLockGroupColumns;
        return (J) this;
    }

    public Boolean getSuppressDragLeaveHidesColumns()
    {
        return suppressDragLeaveHidesColumns;
    }

    public J setSuppressDragLeaveHidesColumns(Boolean suppressDragLeaveHidesColumns)
    {
        this.suppressDragLeaveHidesColumns = suppressDragLeaveHidesColumns;
        return (J) this;
    }

    public Object getSuppressGroupChangesColumnVisibility()
    {
        return suppressGroupChangesColumnVisibility;
    }

    public J setSuppressGroupChangesColumnVisibility(boolean value)
    {
        this.suppressGroupChangesColumnVisibility = value;
        return (J) this;
    }

    public J setSuppressGroupChangesColumnVisibility(SuppressGroupChangesColumnVisibilityMode mode)
    {
        this.suppressGroupChangesColumnVisibility = mode == null ? null : mode.getJson();
        return (J) this;
    }

    public Boolean getSsrmExpandAllAffectsAllRows()
    {
        return ssrmExpandAllAffectsAllRows;
    }

    public J setSsrmExpandAllAffectsAllRows(Boolean ssrmExpandAllAffectsAllRows)
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

    public String getServerSideDatasource() {return serverSideDatasource;}

    public J setServerSideDatasourceRaw(String rawJs)
    {
        this.serverSideDatasource = rawJs;
        return (J) this;
    }

    public Integer getSsrmCacheBlockSize() {return ssrmCacheBlockSize;}

    public J setSsrmCacheBlockSize(Integer v)
    {
        this.ssrmCacheBlockSize = v;
        return (J) this;
    }

    public Integer getSsrmMaxBlocksInCache() {return ssrmMaxBlocksInCache;}

    public J setSsrmMaxBlocksInCache(Integer v)
    {
        this.ssrmMaxBlocksInCache = v;
        return (J) this;
    }

    public Integer getSsrmMaxConcurrentDatasourceRequests() {return ssrmMaxConcurrentDatasourceRequests;}

    public J setSsrmMaxConcurrentDatasourceRequests(Integer v)
    {
        this.ssrmMaxConcurrentDatasourceRequests = v;
        return (J) this;
    }

    public Integer getBlockLoadDebounceMillis() {return blockLoadDebounceMillis;}

    public J setBlockLoadDebounceMillis(Integer v)
    {
        this.blockLoadDebounceMillis = v;
        return (J) this;
    }

    public Boolean getSuppressServerSideFullWidthLoadingRow() {return suppressServerSideFullWidthLoadingRow;}

    public J setSuppressServerSideFullWidthLoadingRow(Boolean v)
    {
        this.suppressServerSideFullWidthLoadingRow = v;
        return (J) this;
    }

    public Boolean getPurgeClosedRowNodes() {return purgeClosedRowNodes;}

    public J setPurgeClosedRowNodes(Boolean v)
    {
        this.purgeClosedRowNodes = v;
        return (J) this;
    }

    public String getServerSidePivotResultFieldSeparator() {return serverSidePivotResultFieldSeparator;}

    public J setServerSidePivotResultFieldSeparator(String v)
    {
        this.serverSidePivotResultFieldSeparator = v;
        return (J) this;
    }

    public Boolean getServerSideSortAllLevels() {return serverSideSortAllLevels;}

    public J setServerSideSortAllLevels(Boolean v)
    {
        this.serverSideSortAllLevels = v;
        return (J) this;
    }

    public Boolean getServerSideEnableClientSideSort() {return serverSideEnableClientSideSort;}

    public J setServerSideEnableClientSideSort(Boolean v)
    {
        this.serverSideEnableClientSideSort = v;
        return (J) this;
    }

    public Boolean getServerSideOnlyRefreshFilteredGroups() {return serverSideOnlyRefreshFilteredGroups;}

    public J setServerSideOnlyRefreshFilteredGroups(Boolean v)
    {
        this.serverSideOnlyRefreshFilteredGroups = v;
        return (J) this;
    }

    public Integer getServerSideInitialRowCount() {return serverSideInitialRowCount;}

    public J setServerSideInitialRowCount(Integer v)
    {
        this.serverSideInitialRowCount = v;
        return (J) this;
    }

    public String getGetChildCount() {return getChildCount;}

    public J setGetChildCountRaw(String rawJs)
    {
        this.getChildCount = rawJs;
        return (J) this;
    }

    public String getGetServerSideGroupLevelParams() {return getServerSideGroupLevelParams;}

    public J setGetServerSideGroupLevelParamsRaw(String rawJs)
    {
        this.getServerSideGroupLevelParams = rawJs;
        return (J) this;
    }

    public String getIsServerSideGroupOpenByDefault() {return isServerSideGroupOpenByDefault;}

    public J setIsServerSideGroupOpenByDefaultRaw(String rawJs)
    {
        this.isServerSideGroupOpenByDefault = rawJs;
        return (J) this;
    }

    public String getIsApplyServerSideTransaction() {return isApplyServerSideTransaction;}

    public J setIsApplyServerSideTransactionRaw(String rawJs)
    {
        this.isApplyServerSideTransaction = rawJs;
        return (J) this;
    }

    public String getIsServerSideGroup() {return isServerSideGroup;}

    public J setIsServerSideGroupRaw(String rawJs)
    {
        this.isServerSideGroup = rawJs;
        return (J) this;
    }

    public String getGetServerSideGroupKey() {return getServerSideGroupKey;}

    public J setGetServerSideGroupKeyRaw(String rawJs)
    {
        this.getServerSideGroupKey = rawJs;
        return (J) this;
    }

    // ===== Pivoting (enterprise) =====
    @JsonProperty("pivotMode")
    private Boolean pivotMode;

    @JsonProperty("pivotDefaultExpanded")
    private Integer pivotDefaultExpanded;

    public enum PivotRowTotalsPosition
    {
        BEFORE("before"), AFTER("after");
        private final String json;

        PivotRowTotalsPosition(String j) {this.json = j;}

        public String getJson() {return json;}

        @Override
        public String toString() {return json;}
    }

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

    public J setPivotMode(Boolean v)
    {
        this.pivotMode = v;
        return (J) this;
    }

    public Integer getPivotDefaultExpanded() {return pivotDefaultExpanded;}

    public J setPivotDefaultExpanded(Integer v)
    {
        this.pivotDefaultExpanded = v;
        return (J) this;
    }

    public String getPivotRowTotals() {return pivotRowTotals;}

    public J setPivotRowTotals(String v)
    {
        this.pivotRowTotals = v;
        return (J) this;
    }

    public J setPivotRowTotals(PivotRowTotalsPosition pos)
    {
        this.pivotRowTotals = pos == null ? null : pos.getJson();
        return (J) this;
    }

    public Boolean getPivotSuppressAutoColumn() {return pivotSuppressAutoColumn;}

    public J setPivotSuppressAutoColumn(Boolean v)
    {
        this.pivotSuppressAutoColumn = v;
        return (J) this;
    }

    public Integer getPivotMaxGeneratedColumns() {return pivotMaxGeneratedColumns;}

    public J setPivotMaxGeneratedColumns(Integer v)
    {
        this.pivotMaxGeneratedColumns = v;
        return (J) this;
    }

    public String getOnPivotMaxColumnsExceeded() {return onPivotMaxColumnsExceeded;}

    public J setOnPivotMaxColumnsExceededRaw(String rawJs)
    {
        this.onPivotMaxColumnsExceeded = rawJs;
        return (J) this;
    }

    public String getProcessPivotResultColDef() {return processPivotResultColDef;}

    public J setProcessPivotResultColDefRaw(String rawJs)
    {
        this.processPivotResultColDef = rawJs;
        return (J) this;
    }

    public String getProcessPivotResultColGroupDef() {return processPivotResultColGroupDef;}

    public J setProcessPivotResultColGroupDefRaw(String rawJs)
    {
        this.processPivotResultColGroupDef = rawJs;
        return (J) this;
    }

    public Boolean getSuppressExpandablePivotGroups() {return suppressExpandablePivotGroups;}

    public J setSuppressExpandablePivotGroups(Boolean v)
    {
        this.suppressExpandablePivotGroups = v;
        return (J) this;
    }

    public Boolean getFunctionsReadOnly() {return functionsReadOnly;}

    public J setFunctionsReadOnly(Boolean v)
    {
        this.functionsReadOnly = v;
        return (J) this;
    }

    public Boolean getRemovePivotHeaderRowWhenSingleValueColumn() {return removePivotHeaderRowWhenSingleValueColumn;}

    public J setRemovePivotHeaderRowWhenSingleValueColumn(Boolean v)
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

    public J setEnableAdvancedFilter(Boolean enableAdvancedFilter)
    {
        this.enableAdvancedFilter = enableAdvancedFilter;
        return (J) this;
    }

    public Boolean getIncludeHiddenColumnsInAdvancedFilter() {return includeHiddenColumnsInAdvancedFilter;}

    public J setIncludeHiddenColumnsInAdvancedFilter(Boolean includeHiddenColumnsInAdvancedFilter)
    {
        this.includeHiddenColumnsInAdvancedFilter = includeHiddenColumnsInAdvancedFilter;
        return (J) this;
    }

    public Object getAdvancedFilterParent() {return advancedFilterParent;}

    public J setAdvancedFilterParent(Object advancedFilterParent)
    {
        this.advancedFilterParent = advancedFilterParent;
        return (J) this;
    }

    public Object getAdvancedFilterBuilderParams() {return advancedFilterBuilderParams;}

    public J setAdvancedFilterBuilderParams(Object advancedFilterBuilderParams)
    {
        this.advancedFilterBuilderParams = advancedFilterBuilderParams;
        return (J) this;
    }

    /**
     * Typed overload for Advanced Filter Builder params.
     */
    public J setAdvancedFilterBuilderParams(com.jwebmp.plugins.aggridenterprise.options.advancedfilter.IAdvancedFilterBuilderParams params)
    {
        this.advancedFilterBuilderParams = params;
        return (J) this;
    }

    public Object getAdvancedFilterParams() {return advancedFilterParams;}

    public J setAdvancedFilterParams(Object advancedFilterParams)
    {
        this.advancedFilterParams = advancedFilterParams;
        return (J) this;
    }

    /**
     * Typed overload for Advanced Filter params.
     */
    public J setAdvancedFilterParams(com.jwebmp.plugins.aggridenterprise.options.advancedfilter.IAdvancedFilterParams params)
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

    public J setGetChartToolbarItemsRaw(String rawJs)
    {
        this.getChartToolbarItems = rawJs;
        return (J) this;
    }

    public String getCreateChartContainer() {return createChartContainer;}

    public J setCreateChartContainerRaw(String rawJs)
    {
        this.createChartContainer = rawJs;
        return (J) this;
    }

    public Object getCustomChartThemes() {return customChartThemes;}

    public J setCustomChartThemes(Object customChartThemes)
    {
        this.customChartThemes = customChartThemes;
        return (J) this;
    }

    public Object getChartMenuItems() {return chartMenuItems;}

    /**
     * Accepts either an array of items or a raw JavaScript function (via setChartMenuItemsRaw).
     */
    public J setChartMenuItems(Object chartMenuItems)
    {
        this.chartMenuItems = chartMenuItems;
        return (J) this;
    }

    /**
     * Provide a raw JavaScript function for chartMenuItems.
     */
    public J setChartMenuItemsRaw(String rawJs)
    {
        this.chartMenuItems = rawJs == null ? null : new RawJsFunction(rawJs);
        return (J) this;
    }
}
