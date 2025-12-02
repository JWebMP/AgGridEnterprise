# PACT — Product Architecture and Contract
## AgGridEnterprise Plugin for JWebMP

**Version:** 2.0.0-SNAPSHOT  
**Date:** 2025-12-02  
**Status:** Architecture & Foundations (Stage 1 Complete)

---

## Product Overview

**AgGridEnterprise** is a JWebMP plugin that extends the community AgGrid plugin to expose AG Grid Enterprise features (Charts, Range Selection, Row Grouping, Server-Side Row Models, Status Bar, Side Bar) in a familiar, type-safe Java Fluent API.

### Product Goals

1. **Seamless Extension** — Mirror the community→pro pattern (like WebAwesome→WebAwesomePro)
2. **Type Safety** — Replace raw Object/Map with strongly-typed POJOs and enums
3. **Fluent API** — CRTP-based chaining for intuitive developer experience
4. **Automatic Wiring** — GuicedEE-based boot-time module discovery and registration
5. **Clean Serialization** — Jackson-based JSON marshaling with MapStruct transformations
6. **Full Feature Coverage** — All commonly-used enterprise features supported

---

## Architecture Principles

### Design Patterns

- **CRTP (Curiously Recurring Template Pattern)** — All fluent builders return `this` (self type) for method chaining; enforced for type-safe chains
- **Composition over Inheritance** — Enterprise options composed into `AgGridEnterpriseOptions` (extends `AgGridOptions`), not via monolithic base
- **Dependency Injection** — GuicedEE `IPageConfigurator` for boot-time wiring; `IGuiceScanModuleInclusions` for classpath discovery
- **Configuration as Code** — All options/enums generated from AG Grid JS API surface; forward-compatible with AG Grid updates
- **Single Responsibility** — Each feature (Charts, Range, SideBar, etc.) in separate typed class; composed into main options

### Serialization Strategy

- **Jackson with Field Visibility** — `@JsonAutoDetect(fieldVisibility = Visibility.ANY)`
- **Null Suppression** — `@JsonInclude(Include.NON_NULL)` for clean JSON output
- **MapStruct Mappers** — Domain models (POJO) ↔ JSON contracts; enum transformations (Java → AG Grid string format)

### Boot-Time Wiring

- **Page Configurator** — Invoked before TypeScript generation
- **Actions** — Adds `ag-grid-enterprise` npm dependency; ensures `AllEnterpriseModule` registered in TS index
- **Module Registry** — Angular loads `AllEnterpriseModule` providers at startup; Charts, Range, Row Groups, etc. available

---

## Functional Specification

### Core Features

#### 1. Charts (`ChartOptions`)
- **Enable** — `enableCharts()` fluent method
- **Themes** — `ChartTheme` enum (ag-default, ag-vivid, ag-material, ag-sheets, polychroma)
- **Theme Overrides** — Custom CSS/styling via `chartThemeOverrides` map
- **Toolbar Items** — Configure available chart tools (download, tool panel, etc.)
- **Tool Panel** — Grid-level panel for chart management

#### 2. Range Selection (`RangeSelectionOptions`)
- **Enable** — `enableRangeSelection()` fluent method
- **Clipboard Control** — Suppress copy-to-clipboard options per enterprise policy
- **Single-Cell Range** — Suppress single-cell range copy if not needed

#### 3. Side Bar (`SideBarOptions`)
- **Presets** — `sideBarFiltersAndColumns()` convenience method
- **Tool Panels** — Columns, Filters panels with i18n labels and icons
- **Position** — "left" or "right"
- **Visibility** — Always visible or only when grouping

#### 4. Status Bar (`StatusBarOptions`)
- **Panels** — Total/filtered row count, selected row count, custom panels
- **Alignment** — Left, center, right positioning
- **Component Integration** — Support for custom status bar components

#### 5. Row Grouping (`RowGroupingOptions`)
- **Panel Visibility** — `PanelShow` enum (always, onlyWhenGrouping, never)
- **Hierarchy Config** — Custom grouping hierarchies (e.g., "year", "quarter", "month" for dates)
- **Unbalanced Groups** — Allow groups with null/undefined values
- **Parent Hiding** — Hide groups with single child (or only leaf single-child)
- **Pivot Mode** — Support for pivot tables with aggregation

#### 6. Server-Side Row Model (`ServerSideOptions`)
- **Row Model Type** — `RowModelType.SERVER_SIDE` enum
- **Store Type** — "full" (all rows loaded) or "partial" (lazy-load on scroll)
- **Block Caching** — `cacheBlockSize`, `maxBlocksInCache` for memory management
- **Purge Behavior** — Free memory when groups closed (`purgeClosedRowNodes`)
- **Lazy Loading** — Grid calls backend via `ServerSideDataSource` for row blocks

### Non-Functional Requirements

- **Backward Compatibility** — No breaking changes to community AgGrid API
- **Performance** — Efficient JSON serialization; minimal boot overhead
- **Testability** — All classes independently testable via unit tests
- **Observability** — Logging via Log4j2; clear error messages at build/runtime
- **Extensibility** — New enterprise features can be added without API breaking changes

---

## Technical Specification

### Modules & Packages

```
com.jwebmp.plugins.aggridenterprise
├── AgGridEnterprise<T>                    # Main component (extends AgGrid)
├── options/
│   ├── AgGridEnterpriseOptions            # POJO extending AgGridOptions
│   ├── ChartOptions                       # Charts config
│   ├── RangeSelectionOptions              # Range selection config
│   ├── SideBarOptions                     # Side bar config
│   ├── StatusBarOptions                   # Status bar config
│   ├── RowGroupingOptions                 # Row grouping config
│   ├── ServerSideOptions                  # Server-side row model config
│   └── mapping/
│       ├── SideBarDef, SideBarToolPanelDef
│       ├── StatusBarDef, StatusBarPanelDef
│       └── ChartThemeOverrides
├── options/enums/
│   ├── ChartTheme                         # ag-default, ag-vivid, etc.
│   ├── PanelShow                          # always, onlyWhenGrouping, never
│   └── RowModelType                       # clientSide, serverSide, viewport, infinite
├── options/find/                          # Find options (advanced filters)
├── options/setfilter/                     # Set filter options
├── options/multifilter/                   # Multi-filter options
├── options/cellselection/                 # Cell selection options
├── options/advancedfilter/                # Advanced filter options
├── charts/                                # Chart-related classes
├── AgGridEnterprisePageConfigurator       # IPageConfigurator implementation
└── implementations/
    └── AgGridEnterpriseModuleScanInclusion # IGuiceScanModuleInclusions impl
```

### Fluent API Examples

```java
// Charts
AgGridEnterprise<T> grid = new AgGridEnterprise<T>()
    .enableCharts()
    .setChartThemes(List.of(ChartTheme.AG_DEFAULT, ChartTheme.AG_VIVID));

// Range Selection
grid.enableRangeSelection();

// Side Bar
grid.sideBarFiltersAndColumns();  // convenience method
grid.showRowGroupPanel();         // show row grouping panel

// Status Bar
grid.setStatusBar(new StatusBarDef()
    .setStatusPanels(List.of(
        new StatusBarPanelDef().setStatusPanelId("agTotalAndFilteredRowCountComponent")
    )));

// Server-Side Row Model
grid.useServerSideRowModel()
    .setCacheBlockSize(100)
    .setMaxBlocksInCache(5);

// Method chaining
grid.enableCharts()
    .enableRangeSelection()
    .sideBarFiltersAndColumns()
    .showRowGroupPanel()
    .useServerSideRowModel();
```

### Serialization Contract

All options serialized to JSON via Jackson:

```json
{
  "enableCharts": true,
  "chartThemes": ["ag-default", "ag-vivid"],
  "enableRangeSelection": true,
  "sideBar": {
    "toolPanels": [...],
    "position": "left"
  },
  "statusBar": {...},
  "rowGroupPanelShow": "always",
  "rowModelType": "serverSide",
  "cacheBlockSize": 100
}
```

Key attributes:
- **Field Visibility**: `@JsonAutoDetect(fieldVisibility = Visibility.ANY)`
- **Null Filtering**: `@JsonInclude(Include.NON_NULL)` (omit null fields)
- **Enum Mapping**: ChartTheme.AG_DEFAULT → "ag-default" (via MapStruct)

---

## Integration Points

### JWebMP Core
- Depends on: `jwebmp-core` (Component model, TS generation)
- Depends on: `aggrid` (community plugin)

### GuicedEE
- Implements: `IPageConfigurator` (boot-time configuration)
- Implements: `IGuiceScanModuleInclusions` (module discovery)
- Requires: Dependency injection container

### Angular
- Page Configurator adds npm dependency `ag-grid-enterprise`
- Generated TS index imports and registers `AllEnterpriseModule`
- ag-grid-angular component receives options as input bindings

### AG Grid JS
- Deserializes options JSON at runtime
- Activates enterprise features (Charts, Range, Row Groups, etc.)

---

## Testing Strategy

- **Unit Tests** — Each options class independently testable
- **JSON Serialization** — Verify Jackson output matches expected contracts
- **Integration Tests** — Page Configurator boot, module registration
- **Smoke Tests** — Full grid initialization with enterprise options
- **Compatibility** — Ensure no regressions in community AgGrid features

---

## Risk & Mitigation

### Risk: Breaking AG Grid API Changes
**Mitigation** — Monitor AG Grid JS releases; update options/enums accordingly; forward-compatible design.

### Risk: MapStruct Transformation Bugs
**Mitigation** — Comprehensive MapStruct tests; validate enum mappings.

### Risk: Missing npm Dependency
**Mitigation** — Page Configurator enforces `ag-grid-enterprise` addition; warning logs if not installed.

### Risk: Module Registry Not Loaded
**Mitigation** — Verify `AllEnterpriseModule` in generated TS index; unit tests for configurator.

---

## Success Criteria

- ✅ All enterprise options support CRTP fluent API
- ✅ JSON serialization produces AG Grid-compliant contracts
- ✅ Page Configurator auto-wires enterprise npm dependency
- ✅ Angular module registration works seamlessly
- ✅ Charts, Range Selection, Row Grouping, Server-Side Model fully functional
- ✅ Backward-compatible with community AgGrid plugin
- ✅ Comprehensive documentation and examples
- ✅ All tests passing (unit, integration, smoke)

---

## References

- Architecture Diagrams: [docs/architecture/README.md](docs/architecture/README.md)
- Glossary: [GLOSSARY.md](GLOSSARY.md)
- Rules: [RULES.md](RULES.md)
- Guides: [GUIDES.md](GUIDES.md)
- Implementation: [IMPLEMENTATION.md](IMPLEMENTATION.md)
- Guide Details: [docs/AgGridEnterprise-Guide.md](docs/AgGridEnterprise-Guide.md)
- **Enterprise Features Reference** — [enterprise-features.rules.md](enterprise-features.rules.md) (AG Grid v34.2.0 complete feature list, module registration, licensing, and patterns)

