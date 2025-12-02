# Enterprise Features & Licensing Guide — AG Grid v34.2.0

**Purpose**: Document all AG Grid Enterprise features available in v34.2.0, licensing requirements, module registration, and forward-only adoption patterns for JWebMP integration.

**Status**: AG Grid v34.2.0 LTS; Enterprise features updated through latest release.

---

## Overview

AG Grid comes in two editions:

| Aspect | Community | Enterprise |
| --- | --- | --- |
| **License** | MIT (free, no key required) | Commercial EULA (license key required for production) |
| **Testing** | Full production use | Free testing locally; 30-day trial for production evaluation |
| **Support** | Community-driven (GitHub, forums) | Dedicated Zendesk support with SLAs |
| **Bundle Size Impact** | Minimal (~140KB gzipped) | +Advanced features (~250KB gzipped with enterprise modules) |
| **Module System** | Supports AllCommunityModule | Supports AllEnterpriseModule (includes all community + enterprise) |

---

## Community Features (Always Available)

### Core Grid Capabilities
- **Row and Column Management**: sorting, filtering, column pinning, row pinning, column spanning
- **Selection**: row selection (single/multi), cell selection, header checkbox selection
- **Editing**: cell editors, value formatters, value parsers, value getters/setters
- **Pagination**: client-side row pagination
- **Rendering**: custom cell renderers, component-based renderers, full-width rows
- **Data Updates**: client-side transactions
- **Export**: CSV export (data on-screen)
- **Themes & Styling**: pre-built themes (Quartz, Material, Balham, Alpine), CSS customization, column and row virtualization (enabled by default)
- **Keyboard Navigation** & **Accessibility**: ARIA support, keyboard shortcuts
- **Infinite Row Model**: Fetch data in blocks as user scrolls (basic server-side pagination)

### Filter Types (Community)
- **Text Filter**: pattern matching, regex support
- **Number Filter**: numeric range filtering
- **Date Filter**: date range filtering
- **Set Filter**: value list filtering (with tree list and complex object support as of v29)
- **Quick Filter**: across all visible columns

### Row Models (Community)
- **Client-Side Row Model** (default): All data in browser, sorting/filtering/grouping done client-side
- **Infinite Row Model**: Stream data from server as user scrolls

---

## Enterprise Features

### 1. **Server-Side Row Model (SSRM)** ⭐ Most Popular
**Module**: `ServerSideRowModelModule`

**Use Cases**:
- Datasets larger than browser memory (millions of rows)
- Complex server-side computations (grouping, aggregation, filtering)
- Efficient pagination with lazy-loading

**Key Capabilities**:
- Fetch data on-demand; grid provides row indexes and filter/sort state
- Server determines which rows to send back
- **Infinite scrolling enabled by default** (as of v34); disable with `suppressServerSideInfiniteScroll=true`
- Supports grouping, aggregation, and lazy-loading of groups
- **Transactions**: apply server-side updates without full data reload
- **Selection state**: `getServerSideSelectionState()` and `setServerSideSelectionState()`

**Configuration Pattern (CRTP)**:
```java
// Java-side (JWebMP): no special API change; just use normal grid config
GridOptions gridOpts = new GridOptions<MyData>()
    .setRowModelType(RowModelType.SERVER_SIDE)
    .setServerSideInitialRowCount(1000)
    .setMaxConcurrentDatasourceRequests(2);
```

**Angular Example (from MCP docs)**:
```typescript
import { ServerSideRowModelModule } from "ag-grid-enterprise";
ModuleRegistry.registerModules([ServerSideRowModelModule, ...]);
```

---

### 2. **Row Grouping & Aggregation** 
**Modules**: `RowGroupingModule`, `RowGroupingModule`

**Features**:
- Group rows by one or more columns
- Expand/collapse groups
- Server-side grouping with lazy-loading of group children
- **Aggregation functions**: sum, avg, min, max, count, custom
- Row group selection with child checkboxes
- Multi-column sorting within groups
- **Breaking change (v34)**: Unbalanced groups now disabled by default; enable via `groupAllowUnbalanced=true`

**Community Row Model Support**: Client-side only  
**Enterprise Support**: Client-side + Server-side row model

**Configuration (CRTP)**:
```java
.setRowGroupPanelShow(RowGroupPanelShow.ALWAYS)
.setRowGroups(Arrays.asList(
    new GroupItem().setField("region"),
    new GroupItem().setField("country")
))
.setAggregateOnlyChangedColumns(true)
```

---

### 3. **Pivot & Pivot Mode**
**Module**: `PivotModule`

**Features**:
- Dynamically pivot data by rows/columns
- Value aggregation in pivot cells
- Lazy-loading of pivot groups (server-side)
- Read-only pivot mode: `functionsReadOnly=true`

**Configuration (CRTP)**:
```java
.setPivotMode(true)
.setPivotPanelShow(PivotPanelShow.ALWAYS)
.addPivotColumns("region")
.setValueColumns("revenue")
```

---

### 4. **Advanced Filtering**
**Module**: `SetFilterModule` (enterprise-only filter types)

**Enterprise-Specific Filters**:
- **Set Filter (Advanced)**: complex object support, tree list views, multi-select with search
- **Advanced Filter Builder**: UI for building complex filter logic (AND/OR clauses)

**Module**: `AdvancedFilterModule` (for builder UI)

**Configuration**:
```typescript
ModuleRegistry.registerModules([
    SetFilterModule,
    AdvancedFilterModule
]);
```

---

### 5. **Excel Export with Advanced Options**
**Module**: `ExcelExportModule`

**Features**:
- Export with styles, colors, merged cells
- Custom headers/footers
- Export multiple sheets
- **Automatic grouping**: row group and total footer rows exported as Excel groups
- Cell formulas and computed values
- Date formatting
- Image support (logos, watermarks)

**API**:
```typescript
api.exportDataAsExcel({
    fileName: "report.xlsx",
    sheetName: "Data",
    columnWidth: 200,
    processCellCallback: (params) => {
        // Custom cell styling
        return params.value?.toUpperCase();
    }
});

// Multi-sheet export
api.exportMultipleSheetsAsExcel({
    data: [
        api.getSheetDataForExcel({ sheetName: "Summary" }),
        api.getSheetDataForExcel({ sheetName: "Details" })
    ],
    fileName: "report.xlsx"
});
```

---

### 6. **Clipboard Copy & Paste**
**Module**: `ClipboardModule`

**Features**:
- Copy cell ranges (Ctrl+C)
- Paste from Excel with formatting
- Copy/paste rows
- Custom formatters for paste values
- **Range selection highlight**: visual feedback on copy
- Excel-like behavior (Ctrl+X for cut)

**Configuration**:
```typescript
[clipboard]="true"
[copyHeadersToClipboard]="true"
[suppressCopyRowHeaders]="false"
```

---

### 7. **Range Selection**
**Module**: `RangeSelectionModule`

**Features**:
- Drag to select cell ranges
- Visual highlighting (customizable colors)
- Copy selected range
- Chart integration: ranges used for chart data selection
- **Cell Selection API**: `addCellRange()`, `getCellRanges()`, `clearRangeSelection()`

**Theming** (range colors):
```typescript
const theme = themeQuartz.withParams({
    rangeSelectionBorderColor: 'rgb(193, 0, 97)',
    rangeSelectionBackgroundColor: 'rgb(255, 0, 128, 0.1)',
    rangeSelectionHighlightColor: 'rgb(60, 188, 0, 0.3)'
});
```

---

### 8. **Master/Detail (Nested Grids)**
**Module**: `MasterDetailModule`

**Features**:
- Expand rows to show nested detail grids
- Each detail grid can have its own config
- Detail grids are independent (separate selections, sorts, etc.)
- Common pattern: master rows with transaction details, orders with line items

**Configuration (Simplified)**:
```typescript
detailCellRenderer: 'agDetailCellRenderer',
detailRowHeight: 300,
masterDetail: true,
detailGridOptions: { ... }
```

---

### 9. **Integrated Charts** (with AG Charts)
**Modules**: 
- `IntegratedChartsModule` (community charts)
- `IntegratedChartsModule.with(AgChartsEnterpriseModule)` (enterprise charts)

**Features**:
- Create range charts from grid data
- Pivot charts
- Cross-filter charts (select in chart → filter grid)
- **Enterprise chart types** (via AgCharts Enterprise):
  - Treemap, Waterfall, Funnel, Sankey, Bubble, Scatter
  - Heatmap, Radial Bar, Radial Gauge, Navigator
  - Additional series options (e.g., 3D perspectives, animations)
- Chart tool panel for customization
- Export charts as images

**Chart Integration Pattern**:
```typescript
// Range-based chart
api.createRangeChart({
    cellRange: {
        columns: ['period', 'revenue', 'profit']
    },
    chartType: 'groupedColumn',
    chartContainer: document.querySelector('#myChart')
});

// Updates
api.updateChart({
    type: 'rangeChartUpdate',
    chartId: chartRef.chartId,
    chartType: 'line'
});
```

---

### 10. **Sparklines**
**Modules**: 
- Sparklines in cells (community basic)
- Advanced sparklines with **AG Charts Enterprise**

**Features**:
- Inline mini-charts in cells
- Crosshair tooltip
- Interactive hover effects
- Enterprise: more chart types and customization

---

### 11. **Tool Panels & Sidebars**
**Modules**: 
- `ColumnMenuModule` (column-specific menus)
- `ContextMenuModule` (right-click context menus)

**Panels**:
- **Columns Panel**: manage visibility, order, grouping
- **Filters Panel**: configure active filters
- **Custom Panels**: create your own side panel UI

**API**:
```typescript
api.setSideBarVisible(true);
api.openToolPanel('columns');
api.getToolPanelInstance('columns');
```

---

### 12. **Viewport Row Model**
**Module**: `ViewportRowModelModule`

**Use**: When server needs exact knowledge of visible rows (e.g., real-time data push)

**Difference from Server-Side**:
- Server gets viewport dimensions (row indexes visible on screen)
- Server can push updates only for visible rows

---

### 13. **Status Bar**
**Module**: Built-in (no separate module)

**Features**:
- Display row counts, sums, averages
- Custom status panels
- Link to grid state

---

### 14. **Full-Text Search (Find)**
**Features** (Community + Enterprise):
- `api.findNext()`, `api.findPrevious()`
- `api.findGetTotalMatches()`
- Highlights matching cells

---

### 15. **Immutable Data Updates**
**Features**:
- Mark rows as immutable: `immutableData=true`
- Get `delta` changes: `api.getRenderedNodes()` returns only changed rows

---

## Removed / Deprecated Features (Forward-Only)

### v34.2.0 Changes
- **`serverSideInfiniteScroll` removed**: Infinite scrolling now enabled by default for SSRM
- **`ServerSideGroupLevelState.infiniteScroll` replaced by `suppressInfiniteScroll`**
- **Unbalanced groups disabled by default**: Set `groupAllowUnbalanced=true` to revert
- **Integrated Charts toolbar**: Chart tool button now default (hamburger hidden); set `suppressChartToolPanelsButton=true` for old behavior
- **Treemap labels**: Disabled by default; enable via `labelKey`

**Migration Pattern**:
```typescript
// Old (v33)
serverSideInfiniteScroll: true  ❌ Remove this

// New (v34+)
suppressServerSideInfiniteScroll: false  // Default; set true to disable
```

---

## Module Registration Patterns

### Pattern 1: Use AllEnterpriseModule (Everything)
```typescript
import { AllEnterpriseModule } from 'ag-grid-enterprise';
import { ModuleRegistry } from 'ag-grid-community';

ModuleRegistry.registerModules([AllEnterpriseModule]);
// Now all enterprise features available
```

### Pattern 2: Pick & Choose Modules (Optimize Bundle)
```typescript
import {
    ClientSideRowModelModule,
    CsvExportModule,
    ExcelExportModule,
    ServerSideRowModelModule,
    RowGroupingModule,
    IntegratedChartsModule
} from 'ag-grid-enterprise';

ModuleRegistry.registerModules([
    ClientSideRowModelModule,
    CsvExportModule,
    ExcelExportModule,
    ServerSideRowModelModule,
    RowGroupingModule,
    IntegratedChartsModule
]);
```

### Pattern 3: Development Validation (Optional)
```typescript
import { ValidationModule } from 'ag-grid-community';

if (process.env.NODE_ENV !== 'production') {
    ModuleRegistry.registerModules([ValidationModule]);
}
// Helpful warnings in dev; removed in prod
```

### Pattern 4: Provide Modules to Individual Grids
```typescript
@Component({
    template: `<ag-grid-angular [modules]="modules" ... />`
})
export class MyGridComponent {
    modules = [
        ClientSideRowModelModule,
        ExcelExportModule
    ];
}
```

---

## Licensing & Activation

### Community Edition
- No license key required
- Can be used in production indefinitely
- Free for everyone
- MIT licensed

### Enterprise Edition

**Trial / Evaluation**:
- No license key needed for **local/dev testing**
- Enterprise features show **watermark** and console warnings without key
- Request a **30-day trial** for production evaluation from AG Grid website

**Production License**:
- License key required
- Key is per-developer or per-deployment (review pricing page)
- Perpetual license + 1 year support/updates
- Cannot commit license keys to source control

**License Initialization** (On Client):

**Option A: Global Window Variable**
```typescript
(window as any).AG_GRID_LICENSE_KEY = 'your-license-key-here';
```

**Option B: Server-Injected Script** (Recommended for Secrets)
```java
// Java/JWebMP Pattern (via Page Configurator)
String agLicense = secretsProvider.get("AG_GRID_ENTERPRISE_LICENSE");
if (agLicense != null) {
    page.add(new Script<>()
        .add("window.AG_GRID_LICENSE_KEY = '" + 
             JsUtils.escapeJs(agLicense) + "';\n"));
}
```

**Option C: Via Angular Environment**
```typescript
// In main.ts or app init
if (environment.agGridLicense) {
    (window as any).AG_GRID_LICENSE_KEY = environment.agGridLicense;
}
```

### Enterprise Bundle (Grid + Charts)
- Includes both AG Grid Enterprise and AG Charts Enterprise
- Single license covers both products
- Recommended for dashboards with integrated charting

---

## Required Dependencies (v34.2.0)

### Community
```json
{
  "ag-grid-community": "^34.2.0",
  "ag-grid-angular": "^34.2.0"
}
```

### Enterprise (Adds)
```json
{
  "ag-grid-enterprise": "^34.2.0"
}
```

### With Integrated Charts Enterprise
```json
{
  "ag-grid-enterprise": "^34.2.0",
  "ag-charts-enterprise": "^10.0.0",
  "ag-charts-angular": "^10.0.0"
}
```

### JWebMP Integration
- Community: `com.jwebmp.plugins:ag-grid:${version}`
- Enterprise: Create `com.jwebmp.plugins:ag-grid-enterprise:${version}` with Page Configurator
- Use JWebMP BOM for version alignment

---

## Performance Characteristics

### Bundle Size Impact
- **ag-grid-community**: ~140 KB gzipped
- **ag-grid-enterprise**: +~110 KB gzipped (additional modules)
- **With Integrated Charts Enterprise**: +~250 KB gzipped (AG Charts library)

### Rendering Performance
- Virtual scrolling (community): handles 100k+ rows, only ~40 visible rows rendered
- Server-side row model (enterprise): efficient for millions of rows (data fetched on demand)
- Memory: browser memory limit typically hit before grid performance degrades

### Data Update Performance
- Transactions: O(n) for n changed rows
- Batch edits: group updates to minimize re-renders
- Async transactions: non-blocking updates (async+callback)

---

## Validation Checklist for Enterprise Setup

- [ ] AG Grid Enterprise module imported and registered
- [ ] License key installed (if required by deployment)
- [ ] Angular dependency versions aligned (ag-grid-angular ^34.2.0)
- [ ] Bundle size verified (tree-shaking active in prod build)
- [ ] No console warnings about unregistered modules
- [ ] Integrated Charts working (if using IntegratedChartsModule)
- [ ] Row model matches data size (Server-Side for large datasets)
- [ ] Server API prepared (for SSRM, expects grid state in request)

---

## Troubleshooting Enterprise Features

### Issue: Enterprise features show watermark, console errors
**Cause**: License key not installed or invalid  
**Fix**: Verify license key on window at `window.AG_GRID_LICENSE_KEY` before grid init

### Issue: Module not found errors (e.g., `ServerSideRowModelModule`)
**Cause**: Enterprise module not registered  
**Fix**: Import and register in `ModuleRegistry.registerModules([...])`

### Issue: Bundle size larger than expected
**Cause**: All modules included (AllEnterpriseModule)  
**Fix**: Use selective module imports; enable tree-shaking in build

### Issue: Server-side row model not paginating correctly
**Cause**: `suppressServerSideInfiniteScroll` not set correctly  
**Fix**: Verify setting; infinite scroll is default in v34

### Issue: Charts not rendering with enterprise data
**Cause**: IntegratedChartsModule not registered with AgCharts Enterprise  
**Fix**: Use `IntegratedChartsModule.with(AgChartsEnterpriseModule)`

---

## References

- **Official Docs**: https://www.ag-grid.com/angular-data-grid/community-vs-enterprise/
- **Modules Reference**: https://www.ag-grid.com/angular-data-grid/modules/
- **License Installation**: https://www.ag-grid.com/angular-data-grid/license-install/
- **Pricing**: https://www.ag-grid.com/license-pricing/
- **Migration Guide**: https://www.ag-grid.com/angular-data-grid/migration/

---

## LLM / AI Guidance

When using AG Grid Enterprise with JWebMP:

1. **Always ask**: Is this feature community or enterprise? Check module requirement.
2. **Licensing**: Never hardcode license keys. Use secrets provider (env vars, vault).
3. **Forward-Only**: If recommending a breaking change from v33→v34, note the migration path.
4. **Bundle Impact**: For tree-heavy dashboards, consider selective module imports.
5. **Server-Side Data**: For >100k rows, prefer Server-Side Row Model over infinite scrolling.
6. **Validation**: Run `api.isModuleRegistered(moduleName)` to confirm module availability.

---

**Status**: ✅ Complete for v34.2.0. Review and update when AG Grid releases v35+.
