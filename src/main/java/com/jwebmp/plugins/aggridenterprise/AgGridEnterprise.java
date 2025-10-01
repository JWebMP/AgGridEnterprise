package com.jwebmp.plugins.aggridenterprise;

import com.jwebmp.plugins.aggrid.AgGrid;
import com.jwebmp.plugins.aggrid.options.AgGridColumnDef;
import com.jwebmp.plugins.aggrid.options.AgGridOptions;
import com.jwebmp.plugins.aggridenterprise.options.AgGridEnterpriseOptions;
import jakarta.validation.constraints.NotNull;

/**
 * AG Grid Enterprise component extending the community AgGrid with enterprise-specific options
 */
public abstract class AgGridEnterprise<J extends AgGridEnterprise<J>> extends AgGrid<J>
{
    private AgGridEnterpriseOptions<?> enterpriseOptions;

    public AgGridEnterprise()
    {
        super();
    }

    public AgGridEnterprise(String id)
    {
        super();
        setID(id);
    }

    /**
     * Returns options as AgGridEnterpriseOptions, lazily instantiating if needed
     */
    @Override
    public AgGridOptions<?> getOptions()
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
    @Override
    public J setOptions(AgGridOptions<?> options)
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

    /** Enable integrated charts (enterprise) */
    public J enableCharts()
    {
        ((AgGridEnterpriseOptions<?>) getOptions()).setEnableCharts(true);
        addAttribute("[enableCharts]", "true");
        return (J) this;
    }

    /** Enable range selection for charts and copy */
    public J enableRangeSelection()
    {
        ((AgGridEnterpriseOptions<?>) getOptions()).setEnableRangeSelection(true);
        addAttribute("[enableRangeSelection]", "true");
        return (J) this;
    }

    /** Configure a simple sidebar preset: filters and columns */
    public J sideBarFiltersAndColumns()
    {
        ((AgGridEnterpriseOptions<?>) getOptions()).setSideBar("'filters,columns'");
        addAttribute("[sideBar]", "['filters','columns']");
        return (J) this;
    }

    /** Show row group panel always */
    public J showRowGroupPanel()
    {
        ((AgGridEnterpriseOptions<?>) getOptions()).setRowGroupPanelShow("always");
        addAttribute("rowGroupPanelShow", "always");
        return (J) this;
    }

    /** Enable server-side row model */
    public J useServerSideRowModel()
    {
        ((AgGridEnterpriseOptions<?>) getOptions()).setRowModelType("serverSide");
        addAttribute("rowModelType", "serverSide");
        return (J) this;
    }

    /** Enable built-in Row Numbers feature (preferred over legacy showRowNumbers helper). */
    public J enableRowNumbers()
    {
        ((AgGridEnterpriseOptions<?>) getOptions()).setRowNumbers(true);
        addAttribute("[rowNumbers]", "true");
        return (J) this;
    }

    /** Set AG Grid Enterprise license key at runtime (if using licenseManager global) */
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
}
