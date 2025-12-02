package com.jwebmp.plugins.aggridenterprise;

import com.jwebmp.core.base.angular.client.annotations.functions.NgAfterViewInit;
import com.jwebmp.core.base.angular.client.annotations.structures.NgMethod;
import com.jwebmp.core.base.angular.client.annotations.references.NgImportReference;
import com.jwebmp.core.base.interfaces.IComponentHierarchyBase;
import com.jwebmp.plugins.aggrid.AgGrid;
import com.jwebmp.plugins.aggrid.options.AgGridColumnDef;
import com.jwebmp.plugins.aggrid.options.AgGridOptions;
import com.jwebmp.plugins.aggridenterprise.options.AgGridEnterpriseColumnDef;
import com.jwebmp.plugins.aggridenterprise.options.AgGridEnterpriseOptions;
import com.jwebmp.plugins.aggridenterprise.options.mapping.AgGridColDefEnterpriseMapper;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * AG Grid Enterprise component extending the community AgGrid with enterprise-specific options
 */
@NgImportReference(value = "DefaultMenuItem", reference = "ag-grid-community")
@NgImportReference(value = "MenuItemDef", reference = "ag-grid-community")
@NgMethod("""
        getContextMenuItems(params: any): (DefaultMenuItem | MenuItemDef)[] {
          const defaultItems: DefaultMenuItem[] = [
            'copy',
            'copyWithHeaders',
            'paste',
            'separator',
            'excelExport',
            'csvExport',
            'separator',
            'pivotChart',
            'chartRange'
          ];
          return defaultItems;
        }
        """)
@NgAfterViewInit("this.applySuppressAggFuncInHeader(); this.initCrossFilterCharts(); this.initRangeCharts();")
public abstract class AgGridEnterprise<J extends AgGridEnterprise<J>> extends AgGrid<J>
{
    private AgGridEnterpriseOptions<?> enterpriseOptions;
    /**
     * When non-null, we will generate TypeScript to set the option via gridApi after view init.
     */
    private Boolean suppressAggFuncInHeaderTs;
    /**
     * When non-null, we will emit a TS method with this name that returns the full context menu items
     * and we will bind the Angular input [getContextMenuItems] to it.
     */
    private String fullContextMenuMethodName;
    /**
     * Holds the TS method body to be appended by methods() when full context menu is configured.
     */
    private String fullContextMenuMethodBody;

    // Stores cross-filter chart parameter object literals to be invoked after view init
    private final List<String> crossFilterChartsQueue = new ArrayList<>();
    // Stores range chart parameter object literals to be invoked after view init
    private final List<String> rangeChartsQueue = new ArrayList<>();

    public AgGridEnterprise()
    {
        super();
    }

    public AgGridEnterprise(String id)
    {
        super();
        setID(id);
    }

    @Override
    public java.util.List<String> fields()
    {
        java.util.List<String> fields = super.fields();
        if (this.suppressAggFuncInHeaderTs != null)
        {
            fields.add("suppressAggFuncInHeader: boolean = " + (this.suppressAggFuncInHeaderTs ? "true" : "false") + ";");
        }
        return fields;
    }

    @Override
    public java.util.List<String> methods()
    {
        var s = super.methods();
        String id = getID();
        // Always add the helper; it will no-op if API is not present or TS field was not requested
        s.add(("""
                applySuppressAggFuncInHeader() {
                    try {
                        const api = this.%s?.api;
                        if (!api) { return; }
                        %s
                    } catch (e) {
                        console.error('applySuppressAggFuncInHeader failed', e);
                    }
                }
                """
        ).formatted(id, (this.suppressAggFuncInHeaderTs != null)
                ? "api.setGridOption('suppressAggFuncInHeader', this.suppressAggFuncInHeader);"
                : "// no-op"));

        // Generate cross filter charts initializer
        s.add(("""
                initCrossFilterCharts() {
                    try {
                        const api = this.%s?.api;
                        if (!api) { return; }
                        %s
                    } catch (e) {
                        console.error('initCrossFilterCharts failed', e);
                    }
                }
                """)
                .formatted(id, crossFilterChartsQueue.isEmpty() ? "// none queued" : String.join("\n", crossFilterChartsQueue.stream().map(p -> "api.createCrossFilterChart(" + p + ");").toList())));

        // Generate range charts initializer
        s.add(("""
                initRangeCharts() {
                    try {
                        const api = this.%s?.api;
                        if (!api) { return; }
                        %s
                    } catch (e) {
                        console.error('initRangeCharts failed', e);
                    }
                }
                """)
                .formatted(id, rangeChartsQueue.isEmpty() ? "// none queued" : String.join("\n", rangeChartsQueue.stream().map(p -> "api.createRangeChart(" + p + ");").toList())));

        // Add Chart Tool Panel open/close helpers for programmatic control
        s.add( ("""
                openChartToolPanel(chartId?: string, panel?: 'settings' | 'data' | 'format') {
                    try {
                        const api = this.%s?.api;
                        if (!api || !chartId) { return; }
                        api.openChartToolPanel({ chartId: chartId, panel: panel });
                    } catch (e) {
                        console.error('openChartToolPanel failed', e);
                    }
                }
                closeChartToolPanel(chartId?: string) {
                    try {
                        const api = this.%s?.api;
                        if (!api || !chartId) { return; }
                        api.closeChartToolPanel({ chartId: chartId });
                    } catch (e) {
                        console.error('closeChartToolPanel failed', e);
                    }
                }
                """ ).formatted(id, id) );

        if (this.fullContextMenuMethodBody != null)
        {
            s.add(this.fullContextMenuMethodBody);
        }
        return s;
    }

    /**
     * Returns options as AgGridEnterpriseOptions, lazily instantiating if needed
     */
    @Override
    public AgGridEnterpriseOptions<?> getOptions()
    {
        if (enterpriseOptions == null)
        {
            enterpriseOptions = new AgGridEnterpriseOptions<>();
            super.setOptions(enterpriseOptions);
        }
        return enterpriseOptions;
    }

    /**
     * Sets the options but ensures it is of enterprise type
     */
    @NotNull
    public @org.jspecify.annotations.NonNull J setOptions(AgGridEnterpriseOptions<?> options)
    {
        if (options instanceof AgGridEnterpriseOptions)
        {
            this.enterpriseOptions = (AgGridEnterpriseOptions<?>) options;
        }
        else
        {
            // copy basic options into enterprise container if needed
            AgGridEnterpriseOptions<?> ent = new AgGridEnterpriseOptions<>();
            this.enterpriseOptions = ent;
        }
        return (J) super.setOptions(enterpriseOptions);
    }

    // Convenience helpers for enterprise features

    /**
     * Enable integrated charts (enterprise)
     */
    public J enableCharts()
    {
        getOptions().setEnableCharts(true);
        addAttribute("[enableCharts]", "true");
        return (J) this;
    }

    /**
     * Enable range selection for charts and copy
     */
    public J enableRangeSelection()
    {
        ((AgGridEnterpriseOptions<?>) getOptions()).setEnableRangeSelection(true);
        addAttribute("[enableRangeSelection]", "true");
        return (J) this;
    }

    /**
     * Configure a simple sidebar preset: filters and columns
     */
    public J sideBarFiltersAndColumns()
    {
        ((AgGridEnterpriseOptions<?>) getOptions()).setSideBar("'filters,columns'");
        addAttribute("[sideBar]", "['filters','columns']");
        return (J) this;
    }

    /**
     * Show row group panel always
     */
    public J showRowGroupPanel()
    {
        ((AgGridEnterpriseOptions<?>) getOptions()).setRowGroupPanelShow("always");
        addAttribute("rowGroupPanelShow", "always");
        return (J) this;
    }

    /**
     * Enable server-side row model
     */
    public J useServerSideRowModel()
    {
        ((AgGridEnterpriseOptions<?>) getOptions()).setRowModelType("serverSide");
        addAttribute("rowModelType", "serverSide");
        return (J) this;
    }

    /**
     * AG Grid v34.2.0: Suppress infinite scrolling in server-side row model (enabled by default).
     * Use this if you need the v33 behavior where infinite scrolling could be disabled.
     * @param suppressInfiniteScroll true to disable infinite scrolling; false or null to use default (enabled)
     * @return this
     */
    public J suppressServerSideInfiniteScroll(Boolean suppressInfiniteScroll)
    {
        ((AgGridEnterpriseOptions<?>) getOptions()).setSuppressServerSideInfiniteScroll(suppressInfiniteScroll);
        if (suppressInfiniteScroll != null)
        {
            addAttribute("[suppressServerSideInfiniteScroll]", suppressInfiniteScroll ? "true" : "false");
        }
        return (J) this;
    }

    /**
     * AG Grid v34.2.0: Allow unbalanced groups (disabled by default).
     * Use this if your data structure requires different child counts per group.
     * Migration note: v33 had this enabled by default; v34 disabled it.
     * @param allow true to enable unbalanced groups; false or null to use default (disabled)
     * @return this
     */
    public J allowUnbalancedGroups(Boolean allow)
    {
        ((AgGridEnterpriseOptions<?>) getOptions()).setGroupAllowUnbalanced(allow);
        if (allow != null)
        {
            addAttribute("[groupAllowUnbalanced]", allow ? "true" : "false");
        }
        return (J) this;
    }

    /**
     * AG Grid v34.2.0: Suppress chart tool panels button visibility (visible by default).
     * Chart toolbar is now visible by default in v34; use this to revert to v33 behavior.
     * @param suppress true to hide chart tool panels button; false or null to use default (visible)
     * @return this
     */
    public J suppressChartToolPanelsButton(Boolean suppress)
    {
        ((AgGridEnterpriseOptions<?>) getOptions()).setSuppressChartToolPanelsButton(suppress);
        if (suppress != null)
        {
            addAttribute("[suppressChartToolPanelsButton]", suppress ? "true" : "false");
        }
        return (J) this;
    }

    /**
     * Enable built-in Row Numbers feature (preferred over legacy showRowNumbers helper).
     */
    public J enableRowNumbers()
    {
        ((AgGridEnterpriseOptions<?>) getOptions()).setRowNumbers(true);
        addAttribute("[rowNumbers]", "true");
        return (J) this;
    }

    /**
     * Queue a cross-filter chart to be created after the grid view initializes.
     * Provide a TypeScript object literal string conforming to CreateCrossFilterChartParams.
     * Example: "{ chartType: 'pie', cellRange: { columns: ['salesRep','sale'] }, aggFunc: 'sum' }"
     */
    public J createCrossFilterChart(String paramsTsLiteral)
    {
        if (paramsTsLiteral != null && !paramsTsLiteral.isBlank())
        {
            crossFilterChartsQueue.add(paramsTsLiteral);
        }
        return (J) this;
    }

    /**
     * Queue a range chart to be created after the grid view initializes.
     * Provide a TypeScript object literal string conforming to CreateRangeChartParams.
     * Example: "{ chartType: 'line', cellRange: { columns: ['date','avgTemp'] }, suppressChartRanges: true }"
     */
    public J createRangeChart(String paramsTsLiteral)
    {
        if (paramsTsLiteral != null && !paramsTsLiteral.isBlank())
        {
            rangeChartsQueue.add(paramsTsLiteral);
        }
        return (J) this;
    }

    /**
     * Helper to build a simple params object literal for charts using only columns and chartType.
     * aggFunc is optional and defaults to 'sum' if not provided.
     */
    public J createCrossFilterChart(String chartType, String[] columns, String aggFunc)
    {
        String agg = (aggFunc == null || aggFunc.isBlank()) ? "'sum'" : ("'" + aggFunc.replace("'", "\\'") + "'");
        String cols = "['" + String.join("','", columns) + "']";
        String literal = "{ chartType: '" + chartType.replace("'", "\\'") + "', cellRange: { columns: " + cols + " }, aggFunc: " + agg + " }";
        return createCrossFilterChart(literal);
    }

    /**
     * Overload that accepts a full Java object hierarchy for chart params.
     */
    public J createCrossFilterChart(com.jwebmp.plugins.aggridenterprise.charts.CreateCrossFilterChartParams params)
    {
        if (params != null)
        {
            // Use JavaScriptPart JSON string (already preserves raw fields via @JsonRawValue)
            String literal = params.toString();
            if (literal != null && !literal.isBlank())
            {
                crossFilterChartsQueue.add(literal);
            }
        }
        return (J) this;
    }

    /**
     * Overload that accepts a full Java object hierarchy for RANGE chart params.
     */
    public J createRangeChart(com.jwebmp.plugins.aggridenterprise.charts.CreateRangeChartParams params)
    {
        if (params != null)
        {
            String literal = params.toString();
            if (literal != null && !literal.isBlank())
            {
                rangeChartsQueue.add(literal);
            }
        }
        return (J) this;
    }

    /**
     * Convenience helper for simple range charts.
     */
    public J createRangeChart(String chartType, String[] columns)
    {
        String cols = "['" + String.join("','", columns) + "']";
        String literal = "{ chartType: '" + chartType.replace("'", "\\'") + "', cellRange: { columns: " + cols + " } }";
        return createRangeChart(literal);
    }

    // Convenience builders for common cross-filter charts
    public J pieCrossFilter(String categoryField, String seriesField, String aggFunc, String containerTs)
    {
        var range = new com.jwebmp.plugins.aggridenterprise.charts.ChartParamsCellRange().setColumns(java.util.List.of(categoryField, seriesField));
        var p = new com.jwebmp.plugins.aggridenterprise.charts.CreateCrossFilterChartParams()
                .setChartType("pie")
                .setCellRange(range)
                .setAggFunc(aggFunc == null || aggFunc.isBlank() ? "sum" : aggFunc)
                .setSort(false);
        if (containerTs != null && !containerTs.isBlank())
        {
            p.setChartContainerTs(containerTs);
        }
        return createCrossFilterChart(p);
    }

    public J barCrossFilter(String categoryField, String seriesField, String aggFunc, String containerTs)
    {
        var range = new com.jwebmp.plugins.aggridenterprise.charts.ChartParamsCellRange().setColumns(java.util.List.of(categoryField, seriesField));
        var p = new com.jwebmp.plugins.aggridenterprise.charts.CreateCrossFilterChartParams()
                .setChartType("bar")
                .setCellRange(range)
                .setAggFunc(aggFunc == null || aggFunc.isBlank() ? "sum" : aggFunc);
        if (containerTs != null && !containerTs.isBlank())
        {
            p.setChartContainerTs(containerTs);
        }
        return createCrossFilterChart(p);
    }

    public J columnCrossFilter(String categoryField, String seriesField, String aggFunc, String containerTs)
    {
        var range = new com.jwebmp.plugins.aggridenterprise.charts.ChartParamsCellRange().setColumns(java.util.List.of(categoryField, seriesField));
        var p = new com.jwebmp.plugins.aggridenterprise.charts.CreateCrossFilterChartParams()
                .setChartType("column")
                .setCellRange(range)
                .setAggFunc(aggFunc == null || aggFunc.isBlank() ? "sum" : aggFunc);
        if (containerTs != null && !containerTs.isBlank())
        {
            p.setChartContainerTs(containerTs);
        }
        return createCrossFilterChart(p);
    }

    /**
     * Set AG Grid Enterprise license key at runtime (if using licenseManager global)
     */
    public @org.jspecify.annotations.NonNull J setEnterpriseLicenseKey(String key)
    {
        // provide a field to be picked up by custom boot code in apps; not enforced here
        getOptions();
        // expose as attribute for Angular binding if a user binds it
        addAttribute("[licenseKey]", "'" + key.replace("'", "\\'") + "'");
        return (J) this;
    }

    // ----------------------------- Pivot Helpers -----------------------------

    /** Enable Pivot Mode on the grid. */
    public J enablePivotMode()
    {
        ((AgGridEnterpriseOptions<?>) getOptions()).setPivotMode(true);
        addAttribute("[pivotMode]", "true");
        return (J) this;
    }

    /**
     * Mark a column as a Row Group column. If the column def isn't enterprise, it will be converted.
     */
    public J addRowGroup(String field)
    {
        if (field == null) return (J) this;
        var col = ensureEnterpriseColDef(field);
        if (col != null)
        {
            col.setRowGroup(true);
            col.setEnableRowGroup(true);
        }
        return (J) this;
    }

    /** Mark a column as a Pivot column. */
    public J addPivot(String field)
    {
        if (field == null) return (J) this;
        var col = ensureEnterpriseColDef(field);
        if (col != null)
        {
            col.setPivot(true);
            col.setEnablePivot(true);
        }
        return (J) this;
    }

    /** Mark a column as a Value column with an aggregation function. */
    public J addValueColumn(String field, String aggFunc)
    {
        if (field == null) return (J) this;
        var col = ensureEnterpriseColDef(field);
        if (col != null)
        {
            col.setEnableValue(true);
            if (aggFunc != null && !aggFunc.isBlank())
            {
                col.setAggFunc(aggFunc); // prefer string form
            }
        }
        return (J) this;
    }

    /** Clears pivot-specific flags on all columns. */
    public J clearPivot()
    {
        if (getOptions().getColumnDefs() != null)
        {
            for (AgGridColumnDef<?> c : getOptions().getColumnDefs())
            {
                AgGridEnterpriseColumnDef<?> ent = (c instanceof AgGridEnterpriseColumnDef)
                        ? (AgGridEnterpriseColumnDef<?>) c
                        : AgGridColDefEnterpriseMapper.INSTANCE.toEnterpriseColDef(c);
                ent.setPivot(null);
                ent.setEnablePivot(null);
                ent.setEnableRowGroup(null);
                ent.setEnableValue(null);
                ent.setAggFunc((String) null);
            }
        }
        return (J) this;
    }

    /**
     * Utility: ensure the column def for the given field is an enterprise column def, replacing the list entry if necessary.
     */
    private AgGridEnterpriseColumnDef<?> ensureEnterpriseColDef(String field)
    {
        if (getOptions().getColumnDefs() == null) return null;
        List<AgGridColumnDef<?>> defs = getOptions().getColumnDefs();
        for (int i = 0; i < defs.size(); i++)
        {
            AgGridColumnDef<?> c = defs.get(i);
            if (field.equals(c.getField()))
            {
                if (c instanceof AgGridEnterpriseColumnDef)
                {
                    return (AgGridEnterpriseColumnDef<?>) c;
                }
                AgGridEnterpriseColumnDef<?> ent = AgGridColDefEnterpriseMapper.INSTANCE.toEnterpriseColDef(c);
                defs.set(i, (AgGridColumnDef<?>) ent);
                return ent;
            }
        }
        return null;
    }

    /**
     * Prepend a standard Row Number column to the grid.
     * This is a convenience; AG Grid supports row numbers via the official rowNumbers option in newer versions.
     *
     * @return this
     */
    public J showRowNumbers()
    {
        return showRowNumbers(true, 60);
    }

    /**
     * Prepend a standard Row Number column to the grid with options.
     *
     * @param pinnedLeft whether to pin the column to the left
     * @param widthPx    width in pixels (approx)
     * @return this
     */
    public J showRowNumbers(boolean pinnedLeft, int widthPx)
    {
        AgGridColumnDef<?> rn = new AgGridColumnDef<>()
                .setHeaderName("#")
                .setSortable(false)
                .setFilter(false)
                .setResizable(false)
                .setWidth(widthPx)
                .setCellClass("ag-row-number-cell");
        rn.setValueGetterExpression("node.rowIndex + 1");
        if (pinnedLeft)
        {
            rn.setPinned("left");
            rn.setLockPosition("left");
            rn.setLockPinned(true);
        }
        // Prepend to columnDefs
        var cols = getOptions().getColumnDefs();
        if (cols != null)
        {
            cols.add(0, rn);
        }
        return (J) this;
    }

    public @org.jspecify.annotations.NonNull J setShowDefaultContextMenu()
    {
        // Bind to the annotated TS method via Angular HTML property
        addAttribute("[getContextMenuItems]", "getContextMenuItems");
        // Keep JS option for backward compatibility (harmless if Angular input takes precedence)
        getOptions().setGetContextMenuItemsRaw("this.getContextMenuItems.bind(this)");
        return (J) this;
    }

    /**
     * Configure a full context menu with all documented DefaultMenuItem entries that are applicable in
     * AG Grid v34.2.0 with AG Charts v12.2.0. This enables charts and range selection (required for
     * charting and clipboard actions) and supplies a comprehensive getContextMenuItems implementation.
     * <p>
     * Notes and considerations:
     * - Unknown or inapplicable items are ignored by AG Grid at runtime.
     * - Uses 'collapseAll' (not the older 'contractAll').
     * - Includes both 'pivotChart' and 'chartRange' so either pivot or range charting is available when enabled.
     * - Keeps separators clean (no duplicates, no leading/trailing).
     *
     * @return this
     */
    public J configureFullContextMenu()
    {
        // Ensure features commonly required by menu items are enabled
        enableCharts();
        enableRangeSelection();

        // Generate a TS method and bind Angular HTML input to it
        this.fullContextMenuMethodName = "getContextMenuItemsFull";
        this.fullContextMenuMethodBody = ("""
                %s(params: any): (DefaultMenuItem | MenuItemDef)[] {
                  const items: DefaultMenuItem[] = [
                    'copy',
                    'copyWithHeaders',
                    'copyWithGroupHeaders',
                    'paste',
                    'separator',
                    'autoSizeThis',
                    'autoSizeAll',
                    'resetColumns',
                    'separator',
                    'sortAscending',
                    'sortDescending',
                    'separator',
                    'pinSubMenu',
                    'valueAggSubMenu',
                    'rowGroup',
                    'rowUnGroup',
                    'separator',
                    'expandAll',
                    'separator',
                    'excelExport',
                    'csvExport',
                    'separator',
                    'pivotChart',
                    'chartRange'
                  ];
                  const cleaned: (DefaultMenuItem | MenuItemDef)[] = [];
                  for (const i of items) {
                    if (!i) { continue; }
                    if (i === 'separator') {
                      if (cleaned.length === 0 || cleaned[cleaned.length - 1] === 'separator') { continue; }
                    }
                    cleaned.push(i);
                  }
                  if (cleaned[cleaned.length - 1] === 'separator') { cleaned.pop(); }
                  return cleaned;
                }
                """
        ).formatted(this.fullContextMenuMethodName);
        addAttribute("[getContextMenuItems]", this.fullContextMenuMethodName);
        return (J) this;
    }

    /**
     * Configure a full context menu with all documented DefaultMenuItem entries that are applicable in
     * AG Grid v34.2.0 with AG Charts v12.2.0. This enables charts and range selection (required for
     * charting and clipboard actions) and supplies a comprehensive getContextMenuItems implementation.
     * <p>
     * Notes and considerations:
     * - Unknown or inapplicable items are ignored by AG Grid at runtime.
     * - Uses 'collapseAll' (not the older 'contractAll').
     * - Includes both 'pivotChart' and 'chartRange' so either pivot or range charting is available when enabled.
     * - Keeps separators clean (no duplicates, no leading/trailing).
     *
     * @return this
     */
    public J configureUserFeaturedContextMenu()
    {
        // Ensure features commonly required by menu items are enabled
        enableCharts();
        enableRangeSelection();

        // Generate a TS method and bind Angular HTML input to it
        this.fullContextMenuMethodName = "getContextMenuItemsFull";
        this.fullContextMenuMethodBody = ("""
                %s(params: any): (DefaultMenuItem | MenuItemDef)[] {
                  const items: DefaultMenuItem[] = [
                   'excelExport',
                    'separator',
                    'pivotChart',
                    'chartRange',
                     'separator',
                    'valueAggSubMenu',
                    'expandAll'
                  ];
                  const cleaned: (DefaultMenuItem | MenuItemDef)[] = [];
                  for (const i of items) {
                    if (!i) { continue; }
                    if (i === 'separator') {
                      if (cleaned.length === 0 || cleaned[cleaned.length - 1] === 'separator') { continue; }
                    }
                    cleaned.push(i);
                  }
                  if (cleaned[cleaned.length - 1] === 'separator') { cleaned.pop(); }
                  return cleaned;
                }
                """
        ).formatted(this.fullContextMenuMethodName);
        addAttribute("[getContextMenuItems]", this.fullContextMenuMethodName);
        return (J) this;
    }

    /**
     * Bind suppressAggFuncInHeader as an Angular attribute on the ag-grid-angular tag and mirror to options.
     * Example rendered attribute: [suppressAggFuncInHeader]="true|false"
     *
     * @param value true to suppress agg func names in headers; false to show; null removes the binding attribute
     * @return this
     */
    public J bindSuppressAggFuncInHeader(Boolean value)
    {
        ((AgGridEnterpriseOptions<?>) getOptions()).setSuppressAggFuncInHeader(value);
        this.suppressAggFuncInHeaderTs = value;
        String attrKey = "[suppressAggFuncInHeader]";
        if (value == null)
        {
            removeAttribute(attrKey);
        }
        else
        {
            // Bind as a literal true/false per requirement
            addAttribute(attrKey, value ? "true" : "false");
        }
        return (J) this;
    }
}
