# GLOSSARY — AgGridEnterprise Terminology

**Glossary Precedence Policy**: Topic-scoped glossaries take precedence over this root glossary for their bounded contexts. This document serves as an index and aggregator of key terms; where a term is defined in a topic glossary, a link is provided instead of duplication.

---

## A

**Aggregation Function** — Function applied to row data in pivot/grouping contexts. Examples: sum, count, avg, min, max. Used by Server-Side Row Model for data summarization.

**AG Grid** — Official JavaScript data grid library by AG Grid Ltd. Community version provides base grid, sorting, filtering, pagination. Enterprise version adds charts, range selection, row grouping, etc.

**AG Grid Enterprise** — Commercial tier of AG Grid providing enterprise features (Charts, Range Selection, Row Grouping, Server-Side Row Model, etc.). Distributed via npm as `ag-grid-enterprise`.

**AllEnterpriseModule** — Angular module provided by `ag-grid-enterprise` npm package. Contains provider plugins for all enterprise features. Registered with Angular ModuleRegistry at app bootstrap.

**AgGridEnterprise** — Main component class in this plugin; extends `AgGrid<T>` and adds enterprise-only methods. Type-safe fluent API for configuring Charts, Range Selection, Row Grouping, etc.

**AgGridEnterpriseOptions** — POJO extending `AgGridOptions` from the community plugin. Holds all enterprise-only configuration fields. Serialized to JSON and passed to ag-grid-angular component.

---

## B

**Bounded Context** — DDD term; isolated domain of responsibility with its own terminology and rules. AgGridEnterprise has bounded contexts for Options API, Angular Integration, Boot-Time Wiring, Serialization.

**Builder Pattern** — Creational pattern for constructing complex objects; alternative to CRTP for fluent APIs. Not used in this plugin; CRTP is enforced for type-safe chaining.

---

## C

**Cache Block** — Contiguous range of rows cached by Server-Side Row Model. Size controlled by `cacheBlockSize`; quantity controlled by `maxBlocksInCache`.

**Chart Engine** — Part of AG Grid Enterprise responsible for rendering charts. Aggregates data per chart config, applies theme, renders to Canvas/SVG.

**Chart Theme** — Visual theme for charts. Enum: `ag-default`, `ag-vivid`, `ag-material`, `ag-sheets`, `polychroma`. Defined by `ChartTheme` enum in this plugin.

**ChartOptions** — POJO holding chart-specific configuration. Fields: `enableCharts`, `chartThemes`, `chartThemeOverrides`, `chartToolPanelsDef`, `chartToolbarItems`.

**Column Pivot** — Arranging grid data by two dimensions: rows and columns. Row groups form left-most columns; pivot columns form headers; values aggregated at intersections.

**CRTP (Curiously Recurring Template Pattern)** — Generic pattern for type-safe method chaining. Generic class `T extends Base<T>` allows self-returning fluent methods. Used throughout this plugin for all builders.

---

## D

**Domain-Driven Design (DDD)** — Software design approach emphasizing domain model, ubiquitous language, bounded contexts. Applied to this plugin via architectural documentation and glossary composition.

**DTOs (Data Transfer Objects)** — Plain Java objects used for data transfer and serialization. Examples: `SideBarDef`, `StatusBarDef`, `SideBarToolPanelDef`. Not tied to persistence or business logic.

---

## E

**Enterprise Options** — Feature-specific configuration classes within `AgGridEnterpriseOptions`. Examples: `ChartOptions`, `RangeSelectionOptions`, `SideBarOptions`. Composed to enable selective feature use.

**Enterprise Row Model** — See Server-Side Row Model.

---

## F

**Feature-Driven Decomposition** — Architecture principle organizing options by feature (Charts, Range, SideBar, etc.). Each feature in separate typed class for separation of concerns.

**Filter Model** — JSON object representing active grid filters per column. Used by Server-Side Row Model to communicate filter state to backend.

**Fluent API** — API designed for method chaining; methods return self or chainable types. Example: `new AgGridEnterprise<T>().enableCharts().sideBarFiltersAndColumns()`.

---

## G

**GroupKey** — String identifier for a row group. Example: when grouping by country, group key is "USA", "Canada", etc. Used by Server-Side Row Model to load child rows for that group.

**GroupBy Column** — Column definition with `rowGroup: true` or `rowGroupIndex`. Rows are grouped by distinct values in this column.

**Grouping Hierarchy** — Multi-level grouping structure. Example: date grouped by year → quarter → month. Configured via `rowGroupingHierarchy` on column or `groupHierarchyConfig` on grid.

**GuicedEE** — Dependency injection framework built on Google Guice. This plugin uses `IPageConfigurator` and `IGuiceScanModuleInclusions` for boot-time wiring.

---

## I

**i18n (Internationalization)** — Multi-language support. Side bar tool panel labels use i18n keys (e.g., "ag.sideBar.columns") for localization.

---

## J

**Jackson** — Java JSON serialization library. Used for converting options POJOs to JSON. Configuration: `@JsonAutoDetect(fieldVisibility = ANY)`, `@JsonInclude(NON_NULL)`.

**JWebMP** — Java Web UI framework providing fluent component model and TypeScript code generation. This plugin is a JWebMP plugin extending the community AgGrid plugin.

---

## L

**Lazy Loading** — Loading row data on-demand (e.g., when user scrolls). Server-Side Row Model supports lazy loading via `serverSideStoreType: "partial"`.

---

## M

**MapStruct** — Java annotation processor for generating type-safe mappers (transformations between objects). Used to convert domain models (POJOs) ↔ JSON contracts; enum transformations.

**Module Registry** — Angular service for registering feature modules. AllEnterpriseModule registered with ModuleRegistry at app bootstrap to enable enterprise features.

**Multi-Column Grouping** — Grouping by multiple columns simultaneously. Each column gets `rowGroupIndex` to define nesting order.

---

## N

**npm Package** — Node Package Manager distribution. `ag-grid-enterprise` npm package provides AG Grid Enterprise features; added to `package.json` by Page Configurator.

---

## P

**PageConfigurator** — GuicedEE `IPageConfigurator` implementation. Runs at boot time to configure application pages. In this plugin: adds npm dependency, registers enterprise module.

**PanelShow** — Enum controlling visibility of grouping/pivot panels. Values: `ALWAYS`, `ONLY_WHEN_GROUPING`, `NEVER`. Mapped to AG Grid strings: "always", "onlyWhenGrouping", "never".

**Pivot Column** — Column definition marked for pivot aggregation. In pivot mode, distinct values of pivot columns become column headers.

**POJO (Plain Old Java Object)** — Simple Java class with getters/setters, no special framework dependencies. Used for options and DTOs.

---

## R

**RangeSelectionOptions** — POJO holding range selection configuration. Fields: `enableRangeSelection`, `suppressCopyRowsToClipboard`, `suppressCopySingleCellRanges`.

**RowGroupingOptions** — POJO holding row grouping configuration. Fields: `rowGroupPanelShow`, `pivotPanelShow`, `groupHierarchyConfig`, `groupAllowUnbalanced`, `groupHideParentOfSingleChild`.

**RowModelType** — Enum specifying grid row model. Values: `CLIENT_SIDE`, `SERVER_SIDE`, `VIEWPORT`, `INFINITE`. Mapped to AG Grid strings: "clientSide", "serverSide", etc.

---

## S

**Server-Side Row Model** — Enterprise feature enabling lazy-loading of large datasets. Backend provides rows on-demand via REST/gRPC. Grid calls `ServerSideDataSource.getRows(params)` when user scrolls/sorts/filters.

**ServerSideOptions** — POJO holding server-side row model configuration. Fields: `rowModelType`, `serverSideStoreType`, `cacheBlockSize`, `maxBlocksInCache`, `purgeClosedRowNodes`.

**SideBarDef** — POJO defining side bar structure. Contains list of `SideBarToolPanelDef` (columns, filters panels) and position ("left"/"right").

**SideBarOptions** — POJO holding side bar configuration. Fields: `sideBar`, `enableSideBar`, `toolPanels`.

**SideBarToolPanelDef** — POJO defining individual side bar tool panel (columns, filters). Fields: `id`, `labelKey`, `labelDefault`, `iconKey`.

**StatusBarDef** — POJO defining status bar structure. Contains list of `StatusBarPanelDef`.

**StatusBarOptions** — POJO holding status bar configuration. Field: `statusBar`.

**StatusBarPanelDef** — POJO defining individual status bar panel (row count, selection count, custom). Fields: `statusPanelId`, `statusPanelClass`, `statusPanelComp`, `align`.

---

## T

**TypeScript** — JavaScript superset with static typing. JWebMP generates TypeScript for Angular apps; this plugin's Page Configurator ensures enterprise modules registered in TS index.

---

## V

**Value Aggregation** — Aggregating row data using functions (sum, count, avg, etc.). Used in pivot mode and grouping contexts.

---

## Enums Summary

### ChartTheme
| Java | AG Grid String |
|------|---|
| `AG_DEFAULT` | `"ag-default"` |
| `AG_VIVID` | `"ag-vivid"` |
| `AG_MATERIAL` | `"ag-material"` |
| `AG_SHEETS` | `"ag-sheets"` |
| `POLYCHROMA` | `"polychroma"` |

### PanelShow
| Java | AG Grid String |
|------|---|
| `ALWAYS` | `"always"` |
| `ONLY_WHEN_GROUPING` | `"onlyWhenGrouping"` |
| `NEVER` | `"never"` |

### RowModelType
| Java | AG Grid String |
|------|---|
| `CLIENT_SIDE` | `"clientSide"` |
| `SERVER_SIDE` | `"serverSide"` |
| `VIEWPORT` | `"viewport"` |
| `INFINITE` | `"infinite"` |

---

## Package Structure Glossary

| Term | Definition |
|------|---|
| `com.jwebmp.plugins.aggridenterprise` | Root package for plugin |
| `*.options` | Enterprise options classes (ChartOptions, RangeSelectionOptions, etc.) |
| `*.options.mapping` | DTOs for complex nested structures (SideBarDef, StatusBarDef, etc.) |
| `*.options.enums` | Enum types (ChartTheme, PanelShow, RowModelType, etc.) |
| `*.options.find` | Find/search filter options |
| `*.options.setfilter` | Set filter options |
| `*.options.multifilter` | Multi-filter options |
| `*.options.cellselection` | Cell selection options |
| `*.options.advancedfilter` | Advanced filter options |
| `*.charts` | Chart-specific classes |
| `AgGridEnterprisePageConfigurator` | Boot-time configurator (IPageConfigurator) |
| `AgGridEnterpriseModuleScanInclusion` | Module scanner (IGuiceScanModuleInclusions) |

---

---

## AG Grid v34.2.0 Features & Breaking Changes

**AG Grid Version:** 34.2.0 LTS (as documented in enterprise-features.rules.md)

### Key v34 Changes
- **Infinite Scrolling Default** — Server-Side Row Model now enables infinite scrolling by default; set `suppressServerSideInfiniteScroll=true` to disable
- **Unbalanced Groups** — Disabled by default in v34; enable with `groupAllowUnbalanced=true`
- **Chart Toolbar** — Chart tool button now visible by default; set `suppressChartToolPanelsButton=true` for old behavior
- **Migration Reference** — See enterprise-features.rules.md § "Removed / Deprecated Features"

### Enterprise Modules Availability
- **Server-Side Row Model** — ServerSideRowModelModule (most popular for large datasets)
- **Row Grouping & Aggregation** — RowGroupingModule, PivotModule
- **Advanced Filtering** — SetFilterModule (enterprise-only), AdvancedFilterModule
- **Excel Export** — ExcelExportModule (with styles, formulas, merged cells)
- **Integrated Charts** — IntegratedChartsModule (+ AgChartsEnterpriseModule for advanced charts)
- **Master/Detail** — MasterDetailModule (nested grids)
- **Clipboard** — ClipboardModule (copy/paste with formatting)
- **Range Selection** — RangeSelectionModule (for chart data selection)
- **Viewport Row Model** — ViewportRowModelModule (for real-time data)

See **enterprise-features.rules.md** for complete feature matrix and module registration patterns.

---

- **PACT.md** — Product architecture and contract
- **RULES.md** — Project rules and stacks
- **GUIDES.md** — How to apply patterns
- **IMPLEMENTATION.md** — Module structure and code layout
- **docs/AgGridEnterprise-Guide.md** — Usage guide and examples
- **docs/architecture/README.md** — Architecture diagrams (C4, sequence, ERD)
- **enterprise-features.rules.md** — Complete AG Grid v34.2.0 enterprise features reference
- **rules/** — Enterprise Rules Repository submodule; see `/rules/GLOSSARY.md` for framework-wide terms

