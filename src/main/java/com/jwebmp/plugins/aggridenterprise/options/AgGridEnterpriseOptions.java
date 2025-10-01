package com.jwebmp.plugins.aggridenterprise.options;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jwebmp.plugins.aggrid.options.AgGridOptions;
import com.jwebmp.plugins.aggridenterprise.options.enums.PivotPanelShow;
import com.jwebmp.plugins.aggridenterprise.options.enums.RowGroupPanelShow;
import com.jwebmp.plugins.aggridenterprise.options.enums.RowModelTypeEnterprise;
import com.jwebmp.plugins.aggridenterprise.options.find.IFindOptions;

import java.util.List;

/**
 * Enterprise-only options layered on top of community AgGridOptions
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgGridEnterpriseOptions<J extends AgGridEnterpriseOptions<J>> extends AgGridOptions<J>
{
    @JsonProperty("enableCharts")
    private Boolean enableCharts;

    @JsonProperty("enableRangeSelection")
    private Boolean enableRangeSelection;

    /** Can be boolean, string preset, or object. Using Object to allow flexibility. */
    @JsonProperty("sideBar")
    private Object sideBar;

    /** always | onlyWhenGrouping | never */
    @JsonProperty("rowGroupPanelShow")
    private String rowGroupPanelShow;

    /** always | never */
    @JsonProperty("pivotPanelShow")
    private String pivotPanelShow;

    /** clientSide | serverSide | viewport | infinite */
    @JsonProperty("rowModelType")
    private String rowModelType;

    /** status bar config object */
    @JsonProperty("statusBar")
    private Object statusBar;

    /** Chart themes to use, e.g. ["ag-default", "ag-material"] */
    @JsonProperty("chartThemes")
    private List<String> chartThemes;

    /** Optional chart theme overrides structure (as per AG Grid docs). */
    @JsonProperty("chartThemeOverrides")
    private Object chartThemeOverrides;

    /** Row Numbers feature: boolean | RowNumbersOptions */
    @JsonProperty("rowNumbers")
    private Object rowNumbers;

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
     * When true, use community default filters based on data type instead of Set Filter by default.
     */
    @JsonProperty("suppressSetFilterByDefault")
    private Boolean suppressSetFilterByDefault;

    /**
     * Find: search value to look for across the grid.
     */
    @JsonProperty("findSearchValue")
    private String findSearchValue;

    /**
     * Find: options controlling find behaviour.
     */
    @JsonProperty("findOptions")
    private IFindOptions<?> findOptions;

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
        return sideBar;
    }

    /**
     * Accepts: Boolean (enable/disable), String preset (e.g. "columns"), or a {@link SideBarDef}
     */
    public J setSideBar(Object sideBar)
    {
        this.sideBar = sideBar;
        return (J) this;
    }

    /** Convenience: set a full side bar definition */
    public J setSideBar(SideBarDef sideBar)
    {
        this.sideBar = sideBar;
        return (J) this;
    }

    /** Convenience: enable/disable sidebar */
    public J setSideBarEnabled(boolean enabled)
    {
        this.sideBar = enabled;
        return (J) this;
    }

    /** Convenience: use a preset string such as "columns" or "filters" or "columns,filters" */
    public J setSideBarPreset(String preset)
    {
        this.sideBar = preset;
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

    /** Enum convenience overload */
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

    /** Enum convenience overload */
    public J setPivotPanelShow(PivotPanelShow show)
    {
        this.pivotPanelShow = show == null ? null : show.getJson();
        return (J) this;
    }

    public String getRowModelType()
    {
        return rowModelType;
    }

    public J setRowModelType(String rowModelType)
    {
        this.rowModelType = rowModelType;
        return (J) this;
    }

    /** Enum convenience overload */
    public J setRowModelType(RowModelTypeEnterprise type)
    {
        this.rowModelType = type == null ? null : type.getJson();
        return (J) this;
    }

    public Object getStatusBar()
    {
        return statusBar;
    }

    /** Accepts {@link StatusBarDef} or Boolean false to disable */
    public J setStatusBar(Object statusBar)
    {
        this.statusBar = statusBar;
        return (J) this;
    }

    public J setStatusBar(StatusBarDef statusBar)
    {
        this.statusBar = statusBar;
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

    /** Returns the rowNumbers configuration (Boolean or RowNumbersOptions) */
    public Object getRowNumbers()
    {
        return rowNumbers;
    }

    /** Set rowNumbers using a boolean */
    public J setRowNumbers(boolean enabled)
    {
        this.rowNumbers = enabled;
        return (J) this;
    }

    /** Set rowNumbers using a RowNumbersOptions object */
    public J setRowNumbers(RowNumbersOptions options)
    {
        this.rowNumbers = options;
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
    public String getFindSearchValue() {
        return findSearchValue;
    }

    /**
     * Find: set the search value to look for across the grid.
     */
    public J setFindSearchValue(String findSearchValue) {
        this.findSearchValue = findSearchValue;
        return (J) this;
    }

    /**
     * Find: get options controlling find behaviour.
     */
    public IFindOptions<?> getFindOptions() {
        return findOptions;
    }

    /**
     * Find: set options controlling find behaviour.
     */
    public J setFindOptions(IFindOptions<?> findOptions) {
        this.findOptions = findOptions;
        return (J) this;
    }

    /**
     * Cell Selection: get current configuration.
     */
    public com.jwebmp.plugins.aggridenterprise.options.cellselection.ICellSelectionOptions<?> getCellSelection() {
        return cellSelection;
    }

    /**
     * Cell Selection: set configuration object.
     */
    public J setCellSelection(com.jwebmp.plugins.aggridenterprise.options.cellselection.ICellSelectionOptions<?> cellSelection) {
        this.cellSelection = cellSelection;
        return (J) this;
    }

    /**
     * Convenience: enable Cell Selection with default options.
     */
    public J enableCellSelection() {
        this.cellSelection = new com.jwebmp.plugins.aggridenterprise.options.cellselection.CellSelectionOptions();
        return (J) this;
    }

    /**
     * Convenience: enable Cell Selection Range Handle (handle: { mode: 'range' }).
     */
    public J enableRangeHandle() {
        if (this.cellSelection == null) {
            this.cellSelection = new com.jwebmp.plugins.aggridenterprise.options.cellselection.CellSelectionOptions();
        }
        this.cellSelection.setHandle(new com.jwebmp.plugins.aggridenterprise.options.cellselection.RangeHandleOptions());
        return (J) this;
    }

    /**
     * Convenience: enable Cell Selection Fill Handle (handle: { mode: 'fill' }).
     */
    public J enableFillHandle() {
        if (this.cellSelection == null) {
            this.cellSelection = new com.jwebmp.plugins.aggridenterprise.options.cellselection.CellSelectionOptions();
        }
        this.cellSelection.setHandle(new com.jwebmp.plugins.aggridenterprise.options.cellselection.FillHandleOptions());
        return (J) this;
    }
}