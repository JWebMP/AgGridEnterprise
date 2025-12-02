package com.jwebmp.plugins.aggridenterprise.options.modules;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AG Grid Enterprise: Row Grouping & Aggregation Configuration Module
 *
 * Encapsulates all row grouping options including hierarchical grouping, aggregation totals,
 * display customization, and rendering callbacks. This is the largest module with 22+ properties.
 *
 * @param <J> Generic type for fluent builder pattern (CRTP)
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RowGroupingOptions<J extends RowGroupingOptions<J>>
{
    private J parent;

    /**
     * Show the row group panel (for dragging columns to group). Options: "always" | "onlyWhenGrouping" | "never"
     */
    @JsonProperty("rowGroupPanelShow")
    private String rowGroupPanelShow;

    /**
     * Allow showing rows without a (Blanks) group wrapper when values are null/undefined/empty.
     */
    @JsonProperty("groupAllowUnbalanced")
    private Boolean groupAllowUnbalanced;

    /**
     * Hide parent rows that only have a single child. Can be Boolean true or string "leafGroupsOnly".
     */
    @JsonProperty("groupHideParentOfSingleChild")
    private Object groupHideParentOfSingleChild;

    /**
     * Hide open parents and show leaf nodes in a flat list under the open group.
     * Mutually exclusive with groupHideParentOfSingleChild.
     */
    @JsonProperty("groupHideOpenParents")
    private Boolean groupHideOpenParents;

    /**
     * Custom group hierarchy components. Map of component key -> ColDef usable in rowGroupingHierarchy.
     */
    @JsonProperty("groupHierarchyConfig")
    private Object groupHierarchyConfig;

    /**
     * Include a group total row at 'top' or 'bottom' of each group, or provide custom callback.
     */
    @JsonProperty("groupTotalRow")
    private Object groupTotalRow;

    /**
     * Include a grand total row positioned at 'top' | 'bottom' | 'pinnedTop' | 'pinnedBottom'.
     */
    @JsonProperty("grandTotalRow")
    private String grandTotalRow;

    /**
     * When a total row is visible, hide the group row values. Set true to show them anyway.
     */
    @JsonProperty("groupSuppressBlankHeader")
    private Boolean groupSuppressBlankHeader;

    /**
     * Suppress the aggregation function name from column headers. Hides "sum(Amount)" -> shows "Amount".
     */
    @JsonProperty("suppressAggFuncInHeader")
    private Boolean suppressAggFuncInHeader;

    /**
     * Suppress sticky behavior of total rows. Can be true (both) or "grand" | "group".
     */
    @JsonProperty("suppressStickyTotalRow")
    private Object suppressStickyTotalRow;

    /**
     * Custom renderer for group rows. Allows complete customization of group row display.
     */
    @JsonProperty("groupRowRenderer")
    private Object groupRowRenderer;

    /**
     * Parameters passed to the group row renderer.
     */
    @JsonProperty("groupRowRendererParams")
    private Object groupRowRendererParams;

    /**
     * Show the opened/expanded group row in addition to children. Useful for hierarchies.
     */
    @JsonProperty("showOpenedGroup")
    private Boolean showOpenedGroup;

    /**
     * Maintain the original order of rows within groups (don't auto-sort).
     */
    @JsonProperty("groupMaintainOrder")
    private Boolean groupMaintainOrder;

    /**
     * Number of levels to expand groups by default. -1 for all, 0 for none, 1+ for specific level.
     */
    @JsonProperty("groupDefaultExpanded")
    private Integer groupDefaultExpanded;

    /**
     * Callback to determine if a group should be open by default.
     */
    @JsonProperty("isGroupOpenByDefault")
    private Object isGroupOpenByDefault;

    /**
     * Custom comparator for initial group ordering.
     */
    @JsonProperty("initialGroupOrderComparator")
    private Object initialGroupOrderComparator;

    /**
     * Suppress sticky positioning of group rows during scrolling.
     */
    @JsonProperty("suppressGroupRowsSticky")
    private Boolean suppressGroupRowsSticky;

    /**
     * Suppress sorting in the row group panel.
     */
    @JsonProperty("rowGroupPanelSuppressSort")
    private Boolean rowGroupPanelSuppressSort;

    /**
     * Lock the group columns so they cannot be dragged out or reordered.
     */
    @JsonProperty("groupLockGroupColumns")
    private Boolean groupLockGroupColumns;

    /**
     * Don't hide columns when dragging them out of the group panel.
     */
    @JsonProperty("suppressDragLeaveHidesColumns")
    private Boolean suppressDragLeaveHidesColumns;

    /**
     * Group changes don't affect which columns are visible. Usually grouping hides the grouped column.
     */
    @JsonProperty("suppressGroupChangesColumnVisibility")
    private Boolean suppressGroupChangesColumnVisibility;

    /**
     * In SSRM, expand all affects all rows, not just visible rows.
     */
    @JsonProperty("ssrmExpandAllAffectsAllRows")
    private Boolean ssrmExpandAllAffectsAllRows;

    // ===== Constructors & Parent Management =====

    public RowGroupingOptions()
    {
    }

    public RowGroupingOptions(J parent)
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

    public RowGroupingOptions<J> setParent(J parent)
    {
        this.parent = parent;
        return this;
    }

    // ===== Property Getters & Fluent Setters =====

    public String getRowGroupPanelShow()
    {
        return rowGroupPanelShow;
    }

    public RowGroupingOptions<J> setRowGroupPanelShow(String rowGroupPanelShow)
    {
        this.rowGroupPanelShow = rowGroupPanelShow;
        return this;
    }

    public Boolean getGroupAllowUnbalanced()
    {
        return groupAllowUnbalanced;
    }

    public RowGroupingOptions<J> setGroupAllowUnbalanced(Boolean groupAllowUnbalanced)
    {
        this.groupAllowUnbalanced = groupAllowUnbalanced;
        return this;
    }

    public Object getGroupHideParentOfSingleChild()
    {
        return groupHideParentOfSingleChild;
    }

    public RowGroupingOptions<J> setGroupHideParentOfSingleChild(Object groupHideParentOfSingleChild)
    {
        this.groupHideParentOfSingleChild = groupHideParentOfSingleChild;
        return this;
    }

    public Boolean getGroupHideOpenParents()
    {
        return groupHideOpenParents;
    }

    public RowGroupingOptions<J> setGroupHideOpenParents(Boolean groupHideOpenParents)
    {
        this.groupHideOpenParents = groupHideOpenParents;
        return this;
    }

    public Object getGroupHierarchyConfig()
    {
        return groupHierarchyConfig;
    }

    public RowGroupingOptions<J> setGroupHierarchyConfig(Object groupHierarchyConfig)
    {
        this.groupHierarchyConfig = groupHierarchyConfig;
        return this;
    }

    public Object getGroupTotalRow()
    {
        return groupTotalRow;
    }

    public RowGroupingOptions<J> setGroupTotalRow(Object groupTotalRow)
    {
        this.groupTotalRow = groupTotalRow;
        return this;
    }

    public String getGrandTotalRow()
    {
        return grandTotalRow;
    }

    public RowGroupingOptions<J> setGrandTotalRow(String grandTotalRow)
    {
        this.grandTotalRow = grandTotalRow;
        return this;
    }

    public Boolean getGroupSuppressBlankHeader()
    {
        return groupSuppressBlankHeader;
    }

    public RowGroupingOptions<J> setGroupSuppressBlankHeader(Boolean groupSuppressBlankHeader)
    {
        this.groupSuppressBlankHeader = groupSuppressBlankHeader;
        return this;
    }

    public Boolean getSuppressAggFuncInHeader()
    {
        return suppressAggFuncInHeader;
    }

    public RowGroupingOptions<J> setSuppressAggFuncInHeader(Boolean suppressAggFuncInHeader)
    {
        this.suppressAggFuncInHeader = suppressAggFuncInHeader;
        return this;
    }

    public Object getSuppressStickyTotalRow()
    {
        return suppressStickyTotalRow;
    }

    public RowGroupingOptions<J> setSuppressStickyTotalRow(Object suppressStickyTotalRow)
    {
        this.suppressStickyTotalRow = suppressStickyTotalRow;
        return this;
    }

    public Object getGroupRowRenderer()
    {
        return groupRowRenderer;
    }

    public RowGroupingOptions<J> setGroupRowRenderer(Object groupRowRenderer)
    {
        this.groupRowRenderer = groupRowRenderer;
        return this;
    }

    public Object getGroupRowRendererParams()
    {
        return groupRowRendererParams;
    }

    public RowGroupingOptions<J> setGroupRowRendererParams(Object groupRowRendererParams)
    {
        this.groupRowRendererParams = groupRowRendererParams;
        return this;
    }

    public Boolean getShowOpenedGroup()
    {
        return showOpenedGroup;
    }

    public RowGroupingOptions<J> setShowOpenedGroup(Boolean showOpenedGroup)
    {
        this.showOpenedGroup = showOpenedGroup;
        return this;
    }

    public Boolean getGroupMaintainOrder()
    {
        return groupMaintainOrder;
    }

    public RowGroupingOptions<J> setGroupMaintainOrder(Boolean groupMaintainOrder)
    {
        this.groupMaintainOrder = groupMaintainOrder;
        return this;
    }

    public Integer getGroupDefaultExpanded()
    {
        return groupDefaultExpanded;
    }

    public RowGroupingOptions<J> setGroupDefaultExpanded(Integer groupDefaultExpanded)
    {
        this.groupDefaultExpanded = groupDefaultExpanded;
        return this;
    }

    public Object getIsGroupOpenByDefault()
    {
        return isGroupOpenByDefault;
    }

    public RowGroupingOptions<J> setIsGroupOpenByDefault(Object isGroupOpenByDefault)
    {
        this.isGroupOpenByDefault = isGroupOpenByDefault;
        return this;
    }

    public Object getInitialGroupOrderComparator()
    {
        return initialGroupOrderComparator;
    }

    public RowGroupingOptions<J> setInitialGroupOrderComparator(Object initialGroupOrderComparator)
    {
        this.initialGroupOrderComparator = initialGroupOrderComparator;
        return this;
    }

    public Boolean getSuppressGroupRowsSticky()
    {
        return suppressGroupRowsSticky;
    }

    public RowGroupingOptions<J> setSuppressGroupRowsSticky(Boolean suppressGroupRowsSticky)
    {
        this.suppressGroupRowsSticky = suppressGroupRowsSticky;
        return this;
    }

    public Boolean getRowGroupPanelSuppressSort()
    {
        return rowGroupPanelSuppressSort;
    }

    public RowGroupingOptions<J> setRowGroupPanelSuppressSort(Boolean rowGroupPanelSuppressSort)
    {
        this.rowGroupPanelSuppressSort = rowGroupPanelSuppressSort;
        return this;
    }

    public Boolean getGroupLockGroupColumns()
    {
        return groupLockGroupColumns;
    }

    public RowGroupingOptions<J> setGroupLockGroupColumns(Boolean groupLockGroupColumns)
    {
        this.groupLockGroupColumns = groupLockGroupColumns;
        return this;
    }

    public Boolean getSuppressDragLeaveHidesColumns()
    {
        return suppressDragLeaveHidesColumns;
    }

    public RowGroupingOptions<J> setSuppressDragLeaveHidesColumns(Boolean suppressDragLeaveHidesColumns)
    {
        this.suppressDragLeaveHidesColumns = suppressDragLeaveHidesColumns;
        return this;
    }

    public Boolean getSuppressGroupChangesColumnVisibility()
    {
        return suppressGroupChangesColumnVisibility;
    }

    public RowGroupingOptions<J> setSuppressGroupChangesColumnVisibility(Boolean suppressGroupChangesColumnVisibility)
    {
        this.suppressGroupChangesColumnVisibility = suppressGroupChangesColumnVisibility;
        return this;
    }

    public Boolean getSsrmExpandAllAffectsAllRows()
    {
        return ssrmExpandAllAffectsAllRows;
    }

    public RowGroupingOptions<J> setSsrmExpandAllAffectsAllRows(Boolean ssrmExpandAllAffectsAllRows)
    {
        this.ssrmExpandAllAffectsAllRows = ssrmExpandAllAffectsAllRows;
        return this;
    }
}
