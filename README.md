# AgGridEnterprise

The JWebMP extension that provides AG Grid Enterprise support on top of the community AgGrid plugin. It mirrors how WebAwesomePro builds on WebAwesome and how FullCalendarPro builds on FullCalendar.

What you get:
- Integrated Charts support (enableCharts)
- Range selection (enableRangeSelection)
- Side Bar (columns and filters panels)
- Row Group and Pivot panels visibility options
- Server-Side Row Model convenience
- Status Bar configuration
- Row Numbers via official grid option (rowNumbers) with helper enableRowNumbers()
- Row Grouping options: columnDef.rowGroup / rowGroupIndex / keyCreator / valueFormatter / rowGroupingHierarchy; grid options groupAllowUnbalanced, groupHideParentOfSingleChild (or "leafGroupsOnly"), groupHideOpenParents, groupHierarchyConfig
- Proper Angular TypeScript dependencies and module registration for ag-grid-enterprise

Installation
- Maven: add `com.jwebmp.plugins:aggrid-enterprise:${version}`. If you import the JWebMP BOM the version aligns automatically.
- This module depends on the community plugin `com.jwebmp.plugins:aggrid`.

Usage
- Use AgGridEnterprise as you would AgGrid, with extra options available:

```java
public class MyEnterpriseGrid extends AgGridEnterprise<MyEnterpriseGrid>
{
    public MyEnterpriseGrid()
    {
        setID("myEntGrid");
        // Enterprise goodies
        enableCharts();
        enableRangeSelection();
        sideBarFiltersAndColumns();
        showRowGroupPanel();
        enableRowNumbers();
        useServerSideRowModel();

        // Define your columns & data exactly like AgGrid
        getOptions().setPagination(true);
        getOptions().setPaginationPageSize(25);
    }
}
```

Notes
- The Page Configurator pulls in the `ag-grid-enterprise` npm package and registers `AllEnterpriseModule` with the `ModuleRegistry`.
- If you use AG Grid licensing, you can call `setEnterpriseLicenseKey("your-key")` and wire it up on your app side.

Documentation
- See docs/AgGridEnterprise-Guide.md for the full set of added options and examples.

Progress & Plan
- See AgGridEnterprise-Plan.md for the plan, checklist, and an execution prompt to track progress.
