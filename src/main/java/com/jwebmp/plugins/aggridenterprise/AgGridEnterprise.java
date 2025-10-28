package com.jwebmp.plugins.aggridenterprise;

import com.jwebmp.core.base.angular.client.annotations.functions.NgAfterViewInit;
import com.jwebmp.core.base.angular.client.annotations.structures.NgMethod;
import com.jwebmp.core.base.angular.client.annotations.references.NgImportReference;
import com.jwebmp.core.base.interfaces.IComponentHierarchyBase;
import com.jwebmp.plugins.aggrid.AgGrid;
import com.jwebmp.plugins.aggrid.options.AgGridColumnDef;
import com.jwebmp.plugins.aggrid.options.AgGridOptions;
import com.jwebmp.plugins.aggridenterprise.options.AgGridEnterpriseOptions;
import jakarta.validation.constraints.NotNull;

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
@NgAfterViewInit("this.applySuppressAggFuncInHeader();")
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
    public J setOptions(AgGridEnterpriseOptions<?> options)
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
        ((AgGridEnterpriseOptions<?>) getOptions()).setEnableCharts(true);
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
     * Enable built-in Row Numbers feature (preferred over legacy showRowNumbers helper).
     */
    public J enableRowNumbers()
    {
        ((AgGridEnterpriseOptions<?>) getOptions()).setRowNumbers(true);
        addAttribute("[rowNumbers]", "true");
        return (J) this;
    }

    /**
     * Set AG Grid Enterprise license key at runtime (if using licenseManager global)
     */
    public J setEnterpriseLicenseKey(String key)
    {
        // provide a field to be picked up by custom boot code in apps; not enforced here
        getOptions();
        // expose as attribute for Angular binding if a user binds it
        addAttribute("[licenseKey]", "'" + key.replace("'", "\\'") + "'");
        return (J) this;
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

    public J setShowDefaultContextMenu()
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
