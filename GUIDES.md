# GUIDES — How to Apply AgGridEnterprise Patterns

**Purpose:** This guide explains how to apply the architectural patterns, APIs, and rules documented in PACT.md, RULES.md, and GLOSSARY.md.

---

## Table of Contents

1. [Setup & Installation](#setup--installation)
2. [Fluent API Usage](#fluent-api-usage)
3. [Enterprise Features](#enterprise-features)
4. [Adding New Features](#adding-new-features)
5. [Testing](#testing)
6. [Troubleshooting](#troubleshooting)

---

## Setup & Installation

### Maven Dependency

Add to your `pom.xml`:

```xml
<dependency>
    <groupId>com.jwebmp.plugins</groupId>
    <artifactId>aggrid-enterprise</artifactId>
    <version>${jwebmp.version}</version>
</dependency>
```

Or use the JWebMP BOM for automatic version alignment:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.jwebmp</groupId>
            <artifactId>jwebmp-bom</artifactId>
            <version>${jwebmp.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <dependency>
        <groupId>com.jwebmp.plugins</groupId>
        <artifactId>aggrid-enterprise</artifactId>
    </dependency>
</dependencies>
```

### npm & Frontend

The Page Configurator automatically adds `ag-grid-enterprise` to your `package.json` during application startup. No manual npm install needed.

---

## Fluent API Usage

### Basic Component Declaration

```java
import com.jwebmp.plugins.aggridenterprise.AgGridEnterprise;

public class SalesDataGrid extends AgGridEnterprise<SalesDataGrid> {
    
    public SalesDataGrid() {
        setID("salesGrid");
        
        // Enable enterprise features using fluent API
        enableCharts()
            .enableRangeSelection()
            .sideBarFiltersAndColumns()
            .showRowGroupPanel();
        
        // Configure columns
        getOptions().setColumnDefs(List.of(
            new AgGridColumnDef<>("country"),
            new AgGridColumnDef<>("product"),
            new AgGridColumnDef<>("sales"),
            new AgGridColumnDef<>("quantity")
        ));
        
        // Enable pagination
        getOptions().setPagination(true);
        getOptions().setPaginationPageSize(50);
    }
}
```

### Method Chaining (CRTP Pattern)

All fluent methods return `this` (self-type) for chainable calls:

```java
SalesDataGrid grid = new SalesDataGrid()
    .enableCharts()
    .enableRangeSelection()
    .sideBarFiltersAndColumns()
    .showRowGroupPanel()
    .useServerSideRowModel();

// Equivalent to individual calls:
SalesDataGrid grid2 = new SalesDataGrid();
grid2.enableCharts();
grid2.enableRangeSelection();
grid2.sideBarFiltersAndColumns();
```

---

## Enterprise Features

### 1. Charts

**Concept:** Render charts from grid data; select chart type, theme, toolbar options.

**Enable Charts:**

```java
grid.enableCharts();

// Or with custom themes:
grid.getOptions().setChartThemes(List.of(
    ChartTheme.AG_DEFAULT,
    ChartTheme.AG_VIVID
));

// Or disable specific chart types:
grid.getOptions().setChartToolbarItems(List.of(
    "chartDownload",
    "chartOpenChartToolPanel",
    "chartEdit"
));
```

**Available Themes:**

| Theme | Enum | Use Case |
|-------|------|----------|
| ag-default | `ChartTheme.AG_DEFAULT` | Standard AG Grid colors |
| ag-vivid | `ChartTheme.AG_VIVID` | Bright, saturated colors |
| ag-material | `ChartTheme.AG_MATERIAL` | Material Design palette |
| ag-sheets | `ChartTheme.AG_SHEETS` | Google Sheets style |
| polychroma | `ChartTheme.POLYCHROMA` | Professional palette |

**Example Usage in HTML:**

```html
<!-- From Java: define the component -->
<!-- From Angular template: -->
<ag-grid-angular 
    class="ag-theme-alpine"
    [gridOptions]="gridOptions"
    [rowData]="rowData">
</ag-grid-angular>

<!-- User creates chart via toolbar button; theme applied automatically -->
```

---

### 2. Range Selection

**Concept:** Users can select contiguous cell ranges; copy to clipboard (with options to suppress).

**Enable Range Selection:**

```java
grid.enableRangeSelection();

// Or with clipboard suppression:
RangeSelectionOptions rangeOpts = new RangeSelectionOptions()
    .setEnableRangeSelection(true)
    .setSuppressCopyRowsToClipboard(false)  // allow full row copy
    .setSuppressCopySingleCellRanges(false); // allow single-cell copy
getOptions().setRangeSelectionOptions(rangeOpts);
```

**Features:**
- Select cell ranges by dragging
- Copy selection to clipboard
- Suppressible per policy

---

### 3. Side Bar

**Concept:** Filters and Columns panels on the left/right edge; user can toggle visibility.

**Enable Side Bar with Preset:**

```java
// Preset: Filters + Columns panels
grid.sideBarFiltersAndColumns();

// Or custom definition:
SideBarDef sideBarDef = new SideBarDef()
    .setToolPanels(List.of(
        new SideBarToolPanelDef()
            .setId("columns")
            .setLabelKey("ag.sideBar.columns")
            .setLabelDefault("Columns")
            .setIconKey("columns"),
        new SideBarToolPanelDef()
            .setId("filters")
            .setLabelKey("ag.sideBar.filters")
            .setLabelDefault("Filters")
            .setIconKey("filter")
    ))
    .setPosition("left");

getOptions().getSideBarOptions().setSideBar(sideBarDef);
```

---

### 4. Status Bar

**Concept:** Bottom bar showing row counts, selected row count, custom metrics.

**Enable Status Bar:**

```java
StatusBarDef statusBar = new StatusBarDef()
    .setStatusPanels(List.of(
        new StatusBarPanelDef()
            .setStatusPanelId("agTotalAndFilteredRowCountComponent")
            .setAlign("left"),
        new StatusBarPanelDef()
            .setStatusPanelId("agSelectedRowCountComponent")
            .setAlign("center")
    ));

getOptions().getStatusBarOptions().setStatusBar(statusBar);
```

**Built-in Panels:**
- `agTotalAndFilteredRowCountComponent` — Total rows + filtered count
- `agSelectedRowCountComponent` — Selected row count
- `agAggregationComponent` — Aggregation results (sum, avg, etc.)

---

### 5. Row Grouping

**Concept:** Group rows by column values; expand/collapse groups; multi-level grouping.

**Enable Grouping UI:**

```java
grid.showRowGroupPanel();  // Show grouping panel

// Configure grouping behavior:
RowGroupingOptions groupOpts = new RowGroupingOptions()
    .setRowGroupPanelShow(PanelShow.ALWAYS)
    .setPivotPanelShow(PanelShow.ONLY_WHEN_GROUPING)
    .setGroupAllowUnbalanced(true)
    .setGroupHideParentOfSingleChild("leafGroupsOnly");

getOptions().setRowGroupingOptions(groupOpts);
```

**Column Configuration for Grouping:**

```java
getOptions().setColumnDefs(List.of(
    new AgGridColumnDef<>("country")
        .setRowGroup(true)
        .setRowGroupIndex(0),          // Primary grouping
    new AgGridColumnDef<>("region")
        .setRowGroup(true)
        .setRowGroupIndex(1),          // Secondary grouping
    new AgGridColumnDef<>("sales")
        .setAggFunc("sum")             // Aggregation function
));
```

**Grouping Hierarchy (Dates):**

```java
new AgGridColumnDef<>("date")
    .setRowGroup(true)
    .setRowGroupingHierarchy(List.of("year", "month", "day"));
```

**PanelShow Options:**

| Value | Behavior |
|-------|----------|
| `ALWAYS` | Panel always visible |
| `ONLY_WHEN_GROUPING` | Panel hidden when no grouping active |
| `NEVER` | Panel always hidden |

---

### 6. Server-Side Row Model

**Concept:** Backend provides row data on-demand; grid requests blocks as user scrolls/sorts/filters.

**Enable Server-Side Model:**

```java
grid.useServerSideRowModel();

// Or with custom cache settings:
ServerSideOptions ssOpts = new ServerSideOptions()
    .setRowModelType(RowModelType.SERVER_SIDE)
    .setServerSideStoreType("partial")    // lazy-load on scroll
    .setCacheBlockSize(100)               // rows per block
    .setMaxBlocksInCache(5)               // blocks in memory
    .setPurgeClosedRowNodes(true);        // free memory on collapse

getOptions().setServerSideOptions(ssOpts);
```

**Backend Implementation (REST Example):**

```java
@PostMapping("/api/grid/rows")
public Map<String, Object> getRows(@RequestBody GridRequest request) {
    int startRow = request.getStartRow();
    int endRow = request.getEndRow();
    FilterModel filterModel = request.getFilterModel();
    List<SortModel> sortModel = request.getSortModel();
    
    // Build query with sort/filter/pagination
    List<Row> rows = repository.findRows(startRow, endRow, filterModel, sortModel);
    int totalCount = repository.count(filterModel);
    
    return Map.of(
        "successStatus", 0,
        "rowData", rows,
        "rowCount", totalCount
    );
}
```

**Request Parameters:**

```typescript
{
  startRow: 0,              // 0-based
  endRow: 100,              // exclusive
  rowGroupCols: [],         // grouping columns
  valueCols: [],            // aggregation columns
  filterModel: {...},       // active filters
  sortModel: [{...}],       // active sorts
  groupKeys: []             // for nested groups
}
```

---

## Adding New Features

### Pattern: Adding a New Enterprise Option

Suppose you want to add a new feature: Clipboard Custom Config.

**Step 1: Create Options POJO**

```java
package com.jwebmp.plugins.aggridenterprise.options;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonInclude(Include.NON_NULL)
public class ClipboardOptions {
    private Boolean enableClipboard;
    private Boolean suppressCopyRowsToClipboard;
    private Boolean suppressCopySingleCellRanges;
    private String clipboardDelimiter;
    private Boolean prependNewLineWhenPasting;
    
    // Fluent setters (return this)
    public ClipboardOptions setEnableClipboard(Boolean value) {
        this.enableClipboard = value;
        return this;
    }
    // ... other setters
}
```

**Step 2: Add to AgGridEnterpriseOptions**

```java
public class AgGridEnterpriseOptions extends AgGridOptions {
    private ClipboardOptions clipboardOptions;
    
    public ClipboardOptions getClipboardOptions() {
        if (clipboardOptions == null) {
            clipboardOptions = new ClipboardOptions();
        }
        return clipboardOptions;
    }
    
    public AgGridEnterpriseOptions setClipboardOptions(ClipboardOptions opts) {
        this.clipboardOptions = opts;
        return this;
    }
}
```

**Step 3: Add Fluent Helper (Optional)**

```java
public class AgGridEnterprise<T extends AgGridEnterprise<T>> extends AgGrid<T> {
    @SuppressWarnings("unchecked")
    public T enableClipboard() {
        getOptions().getClipboardOptions().setEnableClipboard(true);
        return (T) this;
    }
}
```

**Step 4: Update module-info.java**

```java
exports com.jwebmp.plugins.aggridenterprise.options;  // if not already
```

**Step 5: Add MapStruct Mapper (if enums)**

```java
@Mapper
public interface ClipboardMapper {
    Map<String, Object> toMap(ClipboardOptions opts);
}
```

**Step 6: Update Docs**

- Add to GLOSSARY.md
- Add usage example to GUIDES.md
- Update docs/AgGridEnterprise-Guide.md

**Step 7: Test**

```java
@Test
public void testClipboardSerialization() throws Exception {
    AgGridEnterprise<?> grid = new AgGridEnterprise<>()
        .enableClipboard();
    
    String json = mapper.writeValueAsString(grid.getOptions());
    assertTrue(json.contains("\"enableClipboard\":true"));
}
```

---

## Testing

### Unit Tests for Serialization

```java
@Test
public void testChartOptionsSerialization() throws Exception {
    AgGridEnterpriseOptions opts = new AgGridEnterpriseOptions();
    opts.getChartOptions()
        .setEnableCharts(true)
        .setChartThemes(List.of(ChartTheme.AG_DEFAULT));
    
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(opts);
    
    assertTrue(json.contains("\"enableCharts\":true"));
    assertTrue(json.contains("\"ag-default\""));
}
```

### Integration Tests

```java
@Test
public void testPageConfiguratorRegistersModule() {
    IGuiceContext context = GuicedEE.startup();
    
    // Verify Page Configurator was discovered
    IPageConfigurator config = context.getInstance(AgGridEnterprisePageConfigurator.class);
    assertNotNull(config);
    
    // Verify npm dependency added (mock test; actual would check package.json)
}
```

---

## Troubleshooting

### Charts Not Rendering

1. **Verify enableCharts() called**
   ```java
   grid.enableCharts();  // or setChartOptions(new ChartOptions()...)
   ```

2. **Check npm package installed**
   - Verify `ag-grid-enterprise` in `package.json` (added by Page Configurator)
   - Run `npm install`

3. **Verify AllEnterpriseModule registered**
   - Check generated TS index includes: `ModuleRegistry.registerModules([AllEnterpriseModule])`

4. **Check theme CSS loaded**
   - Ensure `ag-theme-alpine` (or chosen theme) applied to grid DOM

### Server-Side Row Model Not Paging

1. **Verify backend returns correct response**
   ```json
   {
     "successStatus": 0,
     "rowData": [...],
     "rowCount": 1000
   }
   ```

2. **Check cache settings**
   - `cacheBlockSize` should match expected block size (100 typical)
   - `maxBlocksInCache` should be > 1 to allow scrolling

3. **Verify sort/filter params passed to backend**
   - Add logging in `ServerSideDataSource.getRows(params)` to inspect params

### Enum Serialization Issues

1. **Wrong string value in JSON**
   - Verify MapStruct mapper includes enum transformation
   - Check enum value mapping (e.g., `AG_DEFAULT` → `"ag-default"`)

2. **Jackson includes enum name instead of value**
   - Ensure `@JsonValue` on enum getter:
     ```java
     public enum ChartTheme {
         AG_DEFAULT("ag-default");
         
         @JsonValue
         public String getAgGridValue() { return agGridValue; }
     }
     ```

---

## Related Resources

### Project Documentation
- **PACT.md** — Product architecture and contract
- **RULES.md** — Project rules and stacks
- **GLOSSARY.md** — Terminology
- **IMPLEMENTATION.md** — Module structure
- **docs/AgGridEnterprise-Guide.md** — Usage guide and examples
- **docs/architecture/README.md** — Architecture diagrams
- **enterprise-features.rules.md** — Complete AG Grid v34.2.0 enterprise features reference, modules, licensing, and patterns

### External References
- **AG Grid Enterprise Docs** — https://www.ag-grid.com/
- **AG Grid Modules** — https://www.ag-grid.com/angular-data-grid/modules/
- **AG Grid License & Activation** — https://www.ag-grid.com/angular-data-grid/license-install/
- **JWebMP Docs** — https://github.com/JWebMP/JWebMP

