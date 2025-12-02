# AgGridEnterprise

The JWebMP extension that provides AG Grid Enterprise support on top of the community AgGrid plugin. It mirrors how WebAwesomePro builds on WebAwesome and how FullCalendarPro builds on FullCalendar.

## Status

✅ **Phase 2 Complete (Dec 2, 2025)** — Modular restructuring of AgGridEnterpriseOptions completed using @JsonUnwrapped pattern with 8 focused feature-area modules. See [PHASE_2_MODULAR_RESTRUCTURING.md](PHASE_2_MODULAR_RESTRUCTURING.md) and [PHASE_2_COMPLETE.md](PHASE_2_COMPLETE.md) for details.

---

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

## Documentation

This project adopts the **Rules Repository** (https://github.com/GuicedEE/ai-rules.git) for architecture guidance, design patterns, and code generation rules.

### Core Documents (Architecture & Specification)

- **[PACT.md](PACT.md)** — Product architecture and contract; design decisions; integration points
- **[RULES.md](RULES.md)** — Project rules, tech stack (Java 25, Maven, CRTP Fluent API), behavioral rules
- **[GLOSSARY.md](GLOSSARY.md)** — Terminology index; topic-first composition linking to Rules Repository glossaries
- **[GUIDES.md](GUIDES.md)** — How-to guides; usage patterns; feature examples; adding new features
- **[IMPLEMENTATION.md](IMPLEMENTATION.md)** — Module structure, code layout, build configuration, design patterns (updated Phase 2)

### Phase 2 Documentation (Dec 2, 2025)

- **[PHASE_2_COMPLETE.md](PHASE_2_COMPLETE.md)** — Phase 2 completion summary: 8 modules created (1,842 lines), main class reduced 2,168 → 1,433 lines (-735/-34%), 100% JSON backward compatible
- **[PHASE_2_MODULAR_RESTRUCTURING.md](PHASE_2_MODULAR_RESTRUCTURING.md)** — Phase 2 detailed implementation plan with complete results and next steps for Phase 2D (testing) and Phase 2E (documentation)

### Planning & Progress

- **[AgGridEnterprise-Plan.md](AgGridEnterprise-Plan.md)** — Execution plan, checklist, and continuation prompts
- **[docs/PROMPT_REFERENCE.md](docs/PROMPT_REFERENCE.md)** — Configuration reference for future prompts; selected stacks, glossary composition

### Rules Repository Submodule

The enterprise Rules Repository is linked as a Git submodule at `rules/`. See **[rules/README.md](rules/README.md)** for the complete rule set, topic guides, and glossaries.

**Key Topics:**
- Language: Java 25 LTS, Maven, Build Tooling
- Patterns: Fluent API (CRTP), MapStruct, Lombok
- Structure: JWebMP Core, AgGrid, Angular Integration
- Architecture: SDD (Specification-Driven Design), TDD (Test-Driven Docs)
- Framework: Logging (Log4j2), JSpecify (null-safety)

---

## Quick Start

### 1. Add Dependency

```xml
<dependency>
    <groupId>com.jwebmp.plugins</groupId>
    <artifactId>aggrid-enterprise</artifactId>
    <version>${jwebmp.version}</version>
</dependency>
```

Or use JWebMP BOM:

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
```

### 2. Create Grid Component

```java
public class SalesGrid extends AgGridEnterprise<SalesGrid> {
    public SalesGrid() {
        setID("salesGrid");
        
        // Enable enterprise features (fluent API)
        enableCharts()
            .enableRangeSelection()
            .sideBarFiltersAndColumns()
            .showRowGroupPanel();
        
        // Configure columns and data
        getOptions().setColumnDefs(List.of(
            new AgGridColumnDef<>("country"),
            new AgGridColumnDef<>("sales"),
            new AgGridColumnDef<>("year")
        ));
        
        getOptions().setPagination(true);
        getOptions().setPaginationPageSize(50);
    }
}
```

### 3. Use in Page

Grid is automatically bound to Angular template via JWebMP code generation. Enterprise features (Charts, Range, Row Groups, etc.) are available at runtime.

---

## Features

### Enterprise Features Included

- ✅ **Charts** — Render charts from grid data; configurable themes (ag-default, ag-vivid, ag-material, etc.)
- ✅ **Range Selection** — Select and copy cell ranges
- ✅ **Side Bar** — Columns and Filters panels with user toggle
- ✅ **Status Bar** — Row count, selection count, aggregation metrics
- ✅ **Row Grouping** — Group by one or multiple columns; expandable groups; custom hierarchies
- ✅ **Pivot Tables** — Row and column pivots with value aggregation
- ✅ **Server-Side Row Model** — Lazy-load large datasets; backend provides rows on-demand
- ✅ **Row Numbers** — Official AG Grid row numbering option with helper method

### Architecture Highlights

- **Type-Safe Fluent API** — CRTP pattern for compile-time safe method chaining; no Lombok @Builder
- **Strongly-Typed Options** — Enums and POJOs replace raw Object/Map; full IDE autocomplete
- **Modular Composition (Phase 2)** — 8 focused modules (@JsonUnwrapped) organizing 83 properties into feature areas:
  - ChartsOptions (10 properties)
  - ServerSideRowModelOptions (17 properties)
  - RowGroupingOptions (22 properties)
  - AggregationOptions (7 properties)
  - PivotingOptions (11 properties)
  - AdvancedFilteringOptions (6 properties)
  - SideBarAndStatusBarOptions (3 properties)
  - RangeSelectionOptions (1 property)
- **Boot-Time Wiring** — GuicedEE `IPageConfigurator` auto-registers enterprise npm dependencies and Angular modules
- **Clean Serialization** — Jackson with field-level visibility and null suppression; MapStruct enum transformations
- **100% JSON Backward Compatible** — @JsonUnwrapped pattern ensures identical JSON output; no breaking changes to API consumers
- **Forward-Only Policy** — No legacy code; all docs and references kept in sync

---

## Contributing

When adding new enterprise features or fixes:

1. **Review GUIDES.md** — Understand patterns and adding new features section
2. **Follow RULES.md** — Tech stack, naming conventions, behavioral rules
3. **Update documentation** — PACT/RULES/GLOSSARY/GUIDES/IMPLEMENTATION as needed
4. **Run tests** — `mvn clean install` to build and test
5. **Use forward-only policy** — No breaking changes; deprecate if needed

See [GUIDES.md - Adding New Features](GUIDES.md#adding-new-features) for detailed instructions.

---

## License

Apache 2.0 — See [LICENSE](LICENSE)

---

## Related Projects

- **JWebMP** — Java Web UI Framework (https://github.com/JWebMP/JWebMP)
- **AgGrid Community Plugin** — Community AgGrid plugin for JWebMP (https://github.com/JWebMP/AgGrid)
- **Rules Repository** — Enterprise AI rules and design patterns (https://github.com/GuicedEE/ai-rules)
