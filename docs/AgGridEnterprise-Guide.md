# AgGridEnterprise Guide

Overview
AgGridEnterprise extends the community AgGrid plugin and exposes enterprise-only features in a familiar JWebMP style.

Key Enterprise Features Included
- Integrated Charts: enableCharts
- Range Selection: enableRangeSelection
- Side Bar (Columns/Filters): sideBar configuration
- Row Group Panel visibility: rowGroupPanelShow
- Pivot Panel visibility: pivotPanelShow
- Server-Side Row Model: rowModelType = serverSide
- Status Bar: statusBar configuration
- Row Numbers: official grid option `rowNumbers` (use enableRowNumbers() helper or bind [rowNumbers])

Maven Dependency
- If you use the JWebMP BOM, versions are aligned automatically.
- Otherwise, add:

```xml
<dependency>
  <groupId>com.jwebmp.plugins</groupId>
  <artifactId>aggrid-enterprise</artifactId>
  <version>${jwebmp.version}</version>
</dependency>
```

Angular/TypeScript Dependencies
- The Page Configurator adds npm dependency `ag-grid-enterprise` and registers `AllEnterpriseModule` with the `ModuleRegistry` automatically at boot.

Usage Example
```java
public class SalesGrid extends AgGridEnterprise<SalesGrid>
{
    public SalesGrid()
    {
        setID("salesGrid");

        // Enterprise features
        enableCharts();
        enableRangeSelection();
        sideBarFiltersAndColumns();
        showRowGroupPanel();
        enableRowNumbers(); // sets [rowNumbers]="true"
        useServerSideRowModel();

        // Configure columns and data like the community grid
        getOptions().setPagination(true);
        getOptions().setPaginationPageSize(50);
    }
}
```

Enterprise Options API
- AgGridEnterpriseOptions extends AgGridOptions with:
  - setEnableCharts(Boolean)
  - setEnableRangeSelection(Boolean)
  - setSideBar(Object) // boolean | preset string | JSON object
  - setRowGroupPanelShow(String) // "always" | "onlyWhenGrouping" | "never"
  - setPivotPanelShow(String) // "always" | "never"
  - setRowModelType(String) // "clientSide" | "serverSide" | "viewport" | "infinite"
  - setStatusBar(Object) // JSON object (e.g., { statusPanels: [...] })

Notes on Licensing
- If you have an AG Grid Enterprise license, you can call:
  - setEnterpriseLicenseKey("YOUR-LICENSE-KEY");
- Wire your app boot to apply the key as needed.

Troubleshooting
- Ensure your Angular build has installed `ag-grid-enterprise` (the configurator takes care of it when generating the TS index).
- If charts do not render, verify enableCharts and enableRangeSelection are active and that your theme CSS is present.

Angular binding example
```html
<ag-grid-angular class="ag-theme-alpine" 
    [rowData]="rowData"
    [columnDefs]="columnDefs"
    [rowNumbers]="true">
</ag-grid-angular>
```

Row Grouping (Enterprise)
AG Grid Enterprise supports grouping rows by one or more columns. In JWebMP, use AgGridColumnDef on your AgGridEnterprise instance and set the appropriate properties.

Enabling Row Grouping
Example: group rows by country values

<ag-grid-angular
    [columnDefs]="columnDefs"
    /* other grid options ... */ />

Java configuration:

this.getOptions().setColumnDefs(List.of(
    new AgGridColumnDef<>("country").setRowGroup(true)
    // ...other column definitions
));

Grouping by Multiple Columns
When grouping by multiple columns, either order the columns accordingly or set the rowGroupIndex on each grouped column.

this.getOptions().setColumnDefs(List.of(
    new AgGridColumnDef<>("country").setRowGroupIndex(1),
    new AgGridColumnDef<>("year").setRowGroupIndex(0)
));

Grouping on Object Data (keyCreator)
When row values are objects, provide a keyCreator so the grid can compare items. You can also provide a valueFormatter to show a human-readable value.

AgGridColumnDef<?> athlete = new AgGridColumnDef<>("athlete")
    .setRowGroup(true)
    .setKeyCreator("params => params.value.id")
    .setValueFormatter("params => params.value.name");

Grouping by Dates and Times (rowGroupingHierarchy)
Group date/time values by parts of the date by setting rowGroupingHierarchy on the column definition. Values should be ISO-8601 date strings.

new AgGridColumnDef<>("date")
    .setRowGroup(true)
    .setRowGroupingHierarchy(java.util.List.of("year", "month"));

Custom Grouping Hierarchies (groupHierarchyConfig)
Define your own grouping hierarchy components in the grid options via groupHierarchyConfig. These can then be referenced in rowGroupingHierarchy.

AgGridEnterpriseOptions<?> opts = getOptions();
java.util.Map<String, Object> cfg = new java.util.HashMap<>();
// Example custom part named "week" that groups by week number using a ColDef-like structure
cfg.put("week", java.util.Map.of(
    "headerName", "Week",
    "keyCreator", "params => new Date(params.value).getWeek?.() ?? ''"
));
opts.setGroupHierarchyConfig(cfg);

Grouping on Null and Undefined Data (groupAllowUnbalanced)
By default, null/undefined/empty string values group under (Blanks). To display these rows without a group, enable unbalanced grouping:

<ag-grid-angular [groupAllowUnbalanced]="groupAllowUnbalanced" />
this.getOptions(AgGridEnterpriseOptions.class).setGroupAllowUnbalanced(true);

Hiding Parents of Individual Rows
Hide groups that only have a single child with groupHideParentOfSingleChild. To only hide groups with a single leaf child, set to "leafGroupsOnly".

<ag-grid-angular [groupHideParentOfSingleChild]="groupHideParentOfSingleChild" />
opts.setGroupHideParentOfSingleChild(true);
// or
opts.setGroupHideParentOfSingleChildLeafGroupsOnly();

Note: groupHideParentOfSingleChild and groupHideOpenParents are mutually exclusive.

Further Reading
- AG Grid docs: https://www.ag-grid.com/
- AG Grid Row Numbers: https://www.ag-grid.com/javascript-data-grid/row-numbers/
- JWebMP AgGrid wiki: https://github.com/JWebMP/JWebMP-AgGrid/wiki
