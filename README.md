# JWebMP AG Grid Enterprise

[![Maven Central](https://img.shields.io/maven-central/v/com.jwebmp.plugins/aggrid-enterprise)](https://central.sonatype.com/artifact/com.jwebmp.plugins/aggrid-enterprise)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue)](https://www.apache.org/licenses/LICENSE-2.0)

![Java 25+](https://img.shields.io/badge/Java-25%2B-green)
![Modular](https://img.shields.io/badge/Modular-JPMS-green)
![Angular](https://img.shields.io/badge/Angular-20-DD0031?logo=angular)
![TypeScript](https://img.shields.io/badge/TypeScript-5-3178C6?logo=typescript)

<!-- Tech icons row -->
![AG Grid Enterprise](https://img.shields.io/badge/AG_Grid_Enterprise-35.0.0-0084FF)
![AG Charts Enterprise](https://img.shields.io/badge/AG_Charts-13.0.0-0084FF)
![JWebMP](https://img.shields.io/badge/JWebMP-2.0-0A7)
![License Required](https://img.shields.io/badge/License-Required-red)

Enterprise data grid extension for JWebMP adding advanced features to AG Grid. Extends the community AG Grid plugin with integrated charts, row grouping, server-side row model, pivot tables, range selection, and Excel export.

Built on [AG Grid Enterprise 35.0.0](https://www.ag-grid.com/) · [Angular 21](https://angular.dev/) · [JWebMP AG Grid](../aggrid/) · JPMS module `com.jwebmp.plugins.aggridenterprise` · Java 25+

**Version: 35.0.0** — Complete AG Grid Enterprise API with CRTP fluent builders and modular architecture.

## 📦 Installation

```xml
<dependency>
  <groupId>com.jwebmp.plugins</groupId>
  <artifactId>aggrid-enterprise</artifactId>
  <version>2.0.0-RC5</version>
</dependency>
```

<details>
<summary>Gradle (Kotlin DSL)</summary>

```kotlin
implementation("com.jwebmp.plugins:aggrid-enterprise:2.0.0-RC5")
```
</details>

### License Requirement

**⚠️ AG Grid Enterprise requires a commercial license.** This plugin extends AG Grid Community with enterprise-only features.

- **Evaluation**: 30-day trial available from [AG Grid](https://www.ag-grid.com/license-pricing/)
- **Production**: Purchase commercial license for enterprise features
- **Setup**: Configure license key in Java application startup

```java
// Set license key in your application startup
AgGridEnterprisePageConfigurator.setAG_GRID_LICENSE_KEY("YOUR_LICENSE_KEY_HERE");
```

Or via environment variables:

```bash
# System property
-Dag.grid.license=YOUR_LICENSE_KEY

# Environment variable
export AG_GRID_LICENSE=YOUR_LICENSE_KEY
```

### NPM Dependencies

The plugin automatically includes AG Grid Enterprise dependencies:

```json
{
  "dependencies": {
    "ag-grid-enterprise": "^35.0.0",
    "ag-charts-enterprise": "^13.0.0"
  }
}
```

## ✨ Features

### Enterprise Features

- **Integrated Charts** — Render charts directly from grid data with configurable themes
- **Server-Side Row Model** — Lazy-load large datasets (millions of rows) with backend pagination
- **Row Grouping** — Group by multiple columns, custom hierarchies, expandable groups
- **Pivot Tables** — Row and column pivots with value aggregation
- **Range Selection** — Select and copy cell ranges, Excel-like behavior
- **Excel Export** — Export to Excel with styles, formulas, and formatting
- **Side Bar** — Columns and filters panels with user toggle
- **Status Bar** — Row count, selection count, aggregation metrics
- **Advanced Filtering** — Filter builder UI with complex expressions
- **Row Numbers** — Official AG Grid row numbering with helper method

### Core Features

- **Type-Safe Fluent API** — CRTP pattern for compile-time safe method chaining
- **Modular Architecture** — 8 focused feature modules with @JsonUnwrapped pattern
- **Strongly-Typed Options** — Enums and POJOs replace raw Object/Map types
- **Angular 21 Integration** — Auto-generated Angular components with change detection
- **AllEnterpriseModule** — Auto-registered via PageConfigurator boot constructor
- **100% JSON Compatible** — Backward compatible JSON serialization
- **License Management** — Static configuration or environment variable support
- **JPMS Modular** — Full Java Platform Module System support

## 🚀 Quick Start

### Basic Enterprise Grid

```java
@NgComponent
public class SalesGrid extends AgGridEnterprise<SalesGrid> {

    public SalesGrid() {
        setID("salesGrid");

        // Enable enterprise features
        enableCharts()
            .enableRangeSelection()
            .sideBarFiltersAndColumns()
            .showRowGroupPanel();

        // Configure columns
        addColumn(new AgGridColumnDef()
            .setField("country")
            .setHeaderName("Country")
            .setRowGroup(true));

        addColumn(new AgGridColumnDef()
            .setField("sales")
            .setHeaderName("Sales")
            .setAggFunc("sum"));

        // Pagination
        getOptions().setPagination(true);
        getOptions().setPaginationPageSize(50);
    }
}
```

### Server-Side Row Model

```java
public class LargeDatasetGrid extends AgGridEnterprise<LargeDatasetGrid> {

    public LargeDatasetGrid() {
        setID("largeGrid");

        // Enable server-side row model
        useServerSideRowModel();

        // Configure for large datasets
        getServerSideOptions()
            .setMaxBlocksInCache(10)
            .setCacheBlockSize(100);

        // Your columns...
    }

    @DataSource
    public Uni<List<Order>> fetchData(DataSourceRequest request) {
        return orderService.findPage(
            request.getStartRow(),
            request.getEndRow(),
            request.getFilterModel(),
            request.getSortModel()
        );
    }
}
```

### Integrated Charts

```java
public class ChartGrid extends AgGridEnterprise<ChartGrid> {

    public ChartGrid() {
        enableCharts();

        getChartsOptions()
            .setChartThemeOverrides(new ChartThemeOverrides()
                .setCommon(new ChartCommon()
                    .setTitle(new ChartTitle()
                        .setEnabled(true)
                        .setText("Sales Analysis"))));

        // Grid will have chart creation menu
    }
}
```

## 📐 Architecture

### Modular Feature Organization

The enterprise options are organized into 8 focused modules using @JsonUnwrapped pattern:

| Module | Properties | Purpose |
|---|---|---|
| **ChartsOptions** | 10 | Integrated charts configuration and themes |
| **ServerSideRowModelOptions** | 17 | Server-side data loading, caching, pagination |
| **RowGroupingOptions** | 22 | Row grouping, hierarchies, aggregation |
| **AggregationOptions** | 7 | Value aggregation functions and display |
| **PivotingOptions** | 11 | Pivot mode, column pivots, value columns |
| **AdvancedFilteringOptions** | 6 | Filter builder UI and complex expressions |
| **SideBarAndStatusBarOptions** | 3 | Side panels and status bar configuration |
| **RangeSelectionOptions** | 1 | Cell range selection and clipboard |

Total: **77 enterprise-specific properties** organized into cohesive feature areas.

### CRTP Pattern

All enterprise options extend the fluent CRTP pattern:

```java
public class AgGridEnterprise<J extends AgGridEnterprise<J>>
        extends AgGrid<J> {

    public J enableCharts() {
        getOptions().getChartsOptions().setChartThemes(...);
        return (J) this;
    }

    public J useServerSideRowModel() {
        getOptions().setRowModelType(RowModelType.SERVER_SIDE);
        return (J) this;
    }
}
```

### License Key Flow

```
┌─────────────────────────────────────────────────────────┐
│         Java Application Startup                         │
│                                                          │
│  AgGridEnterprisePageConfigurator                       │
│      .setAG_GRID_LICENSE_KEY("key")                     │
│                                                          │
│  OR                                                      │
│                                                          │
│  System.setProperty("ag.grid.license", "key")          │
│  export AG_GRID_LICENSE=key                             │
└──────────────────┬──────────────────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────────────────┐
│         PageConfigurator.configureAngular()             │
│                                                          │
│  <script>                                               │
│    window.AG_GRID_LICENSE_KEY = 'your-key';            │
│  </script>                                              │
└──────────────────┬──────────────────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────────────────┐
│         Angular Boot Constructor                         │
│                                                          │
│  LicenseManager.setLicenseKey(                          │
│      window.AG_GRID_LICENSE_KEY                         │
│  );                                                      │
└─────────────────────────────────────────────────────────┘
```

## 🔧 Configuration

### Enterprise Feature Methods

```java
// Charts
enableCharts();
getChartsOptions().setChartThemes(List.of("ag-vivid", "ag-material"));

// Range Selection
enableRangeSelection();

// Side Bar
sideBarFiltersAndColumns();
showRowGroupPanel();

// Status Bar
enableStatusBar();

// Row Numbers
enableRowNumbers();

// Server-Side Row Model
useServerSideRowModel();
getServerSideOptions().setMaxBlocksInCache(10);
```

### Row Grouping

```java
// Column configuration
addColumn(new AgGridColumnDef()
    .setField("category")
    .setRowGroup(true)
    .setRowGroupIndex(0));

addColumn(new AgGridColumnDef()
    .setField("subcategory")
    .setRowGroup(true)
    .setRowGroupIndex(1));

// Grid options
getRowGroupingOptions()
    .setGroupAllowUnbalanced(true)
    .setGroupHideParentOfSingleChild(true);
```

### Pivot Tables

```java
getOptions().setPivotMode(true);

addColumn(new AgGridColumnDef()
    .setField("year")
    .setPivot(true));

addColumn(new AgGridColumnDef()
    .setField("revenue")
    .setAggFunc("sum"));
```

## 🧪 Testing

### Running Tests

```bash
# Run all tests
mvn clean test

# Skip integration tests
mvn clean test -DskipITs=true

# Run specific test
mvn test -Dtest=AgGridEnterpriseTest
```

### Test Example

```java
@Test
public void testEnterpriseFeatures() {
    AgGridEnterprise<TestGrid> grid = new TestGrid();
    grid.enableCharts()
        .enableRangeSelection()
        .useServerSideRowModel();

    assertNotNull(grid.getOptions().getChartsOptions());
    assertTrue(grid.getOptions().getEnableRangeSelection());
    assertEquals(RowModelType.SERVER_SIDE,
        grid.getOptions().getRowModelType());
}
```

## 🗺️ Module Graph

```
com.jwebmp.plugins.aggridenterprise
 ├── com.jwebmp.plugins.aggrid           (AG Grid community base)
 ├── com.jwebmp.plugins.agchartsenterprise (AG Charts Enterprise)
 ├── com.jwebmp.core                      (JWebMP core)
 ├── com.jwebmp.core.angular              (Angular integration)
 ├── com.guicedee.guicedinjection         (Guice DI)
 └── org.mapstruct                        (Bean mapping)
```

### Exported Packages

- `com.jwebmp.plugins.aggridenterprise` — Core enterprise grid classes
- `com.jwebmp.plugins.aggridenterprise.options` — Enterprise options modules
- `com.jwebmp.plugins.aggridenterprise.options.charts` — Charts configuration
- `com.jwebmp.plugins.aggridenterprise.options.serverside` — Server-side row model
- `com.jwebmp.plugins.aggridenterprise.options.grouping` — Row grouping options

## 🧰 Troubleshooting & Best Practices

### License Issues

**Problem**: "AG Grid Enterprise license not found" error

**Solutions**:
- Set license key via `AgGridEnterprisePageConfigurator.setAG_GRID_LICENSE_KEY("key")`
- Use system property: `-Dag.grid.license=YOUR_KEY`
- Use environment variable: `AG_GRID_LICENSE=YOUR_KEY`
- Verify license is valid for AG Grid Enterprise (not just AG Charts)
- Check license covers version 35.0.0

### Server-Side Row Model Not Loading

**Problem**: Data not loading with server-side row model

**Solutions**:
- Implement `@DataSource` method returning `Uni<List<T>>`
- Check `DataSourceRequest` parameters are being used
- Verify backend returns correct row count in response
- Enable debug logging to see request/response flow
- Ensure `useServerSideRowModel()` is called

### Charts Not Rendering

**Problem**: Chart menu appears but charts don't render

**Solutions**:
- Verify AG Charts Enterprise license is set
- Check both AG Grid and AG Charts licenses are valid
- Ensure numeric columns are selected for chart data
- Review browser console for license errors
- Verify ag-charts-enterprise NPM package is loaded

### Row Grouping Not Working

**Problem**: Row grouping panel shows but grouping doesn't work

**Solutions**:
- Set `rowGroup(true)` on column definitions
- Configure `rowGroupIndex` for multi-column grouping
- Enable row group panel: `showRowGroupPanel()`
- Check data has values for grouped fields
- Verify aggregation functions are set on value columns

### Best Practices

- **License Management** — Use environment variables in production
- **Server-Side Model** — Implement proper pagination and filtering on backend
- **Row Grouping** — Limit to 2-3 levels for performance
- **Charts** — Pre-select reasonable data ranges
- **Caching** — Configure appropriate `maxBlocksInCache` for memory constraints
- **Testing** — Test with production-like data volumes
- **Module Loading** — AllEnterpriseModule auto-registered, no manual setup needed

## 📚 Documentation

### Core Resources

- **[AG Grid Enterprise Docs](https://www.ag-grid.com/documentation/)** — Official AG Grid Enterprise documentation
- **[AG Grid Angular](https://www.ag-grid.com/angular-data-grid/)** — AG Grid Angular integration guide
- **[Pricing](https://www.ag-grid.com/license-pricing/)** — AG Grid Enterprise pricing
- **[JWebMP Home](https://jwebmp.com/)** — JWebMP framework documentation

### Related Plugins

- **AG Grid Community**: [`../aggrid/README.md`](../aggrid/README.md)
- **AG Charts Enterprise**: [`../agcharts-enterprise/README.md`](../agcharts-enterprise/README.md)
- **JWebMP Core**: [`../../README.md`](../../README.md)

### External Resources

- [AG Grid Enterprise](https://www.ag-grid.com/javascript/grid-enterprise/)
- [License Pricing](https://www.ag-grid.com/license-pricing/)
- [Server-Side Row Model](https://www.ag-grid.com/javascript/server-side-model/)
- [Integrated Charts](https://www.ag-grid.com/javascript/integrated-charts/)

## 🤝 Contributing

- Follow CRTP pattern for enterprise extensions
- Test with valid AG Grid Enterprise license
- Update modular options using @JsonUnwrapped
- Maintain 100% JSON backward compatibility
- Document licensing requirements

## 📄 License

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)

**Note**: Requires commercial AG Grid Enterprise license for production. See [AG Grid Licensing](https://www.ag-grid.com/license-pricing/).

---

**JWebMP AG Grid Enterprise** — Enterprise data grid features for Java applications.

Built with ❤️ using Java 25+, AG Grid Enterprise 35.0.0, AG Charts Enterprise 13.0.0, Angular 21, and JPMS.
