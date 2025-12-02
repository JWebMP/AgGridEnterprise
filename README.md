# AgGridEnterprise — Open-Source Java Wrapper for AG Grid Enterprise

[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)
[![GitHub](https://img.shields.io/badge/github-JWebMP%2FAgGridEnterprise-informational)](https://github.com/JWebMP/AgGridEnterprise)
[![Maven Central](https://img.shields.io/maven-central/v/com.jwebmp.plugins/aggrid-enterprise.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/com.jwebmp.plugins/aggrid-enterprise)

> A comprehensive, type-safe Java wrapper for **AG Grid Enterprise** built on the **JWebMP** framework. Provides a modular, fluent API for building data grids with enterprise features like charts, row grouping, server-side row models, and advanced filtering—all with full IDE autocomplete and compile-time type safety.

**Key Features:**
- 🎯 **Type-Safe Fluent API** — CRTP pattern for compile-time safe method chaining
- 📊 **Enterprise Features** — Charts, range selection, row grouping, pivot tables, server-side row models
- 🔧 **Modular Architecture** — 8 focused feature modules with @JsonUnwrapped pattern
- ✨ **Full Backward Compatibility** — 100% JSON compatible; no breaking changes
- 📝 **Comprehensive Docs** — Architecture patterns, design decisions, and integration guides
- 🔄 **Forward-Only Policy** — Clean, evolving codebase with no legacy debt

## Status

✅ **Phase 2 Complete (Dec 2, 2025)** — Modular restructuring of AgGridEnterpriseOptions completed using @JsonUnwrapped pattern with 8 focused feature-area modules. See [PHASE_2_MODULAR_RESTRUCTURING.md](PHASE_2_MODULAR_RESTRUCTURING.md) and [PHASE_2_COMPLETE.md](PHASE_2_COMPLETE.md) for details.

---

## What You Get
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

We welcome contributions from the open-source community! Whether you're reporting bugs, suggesting features, or submitting pull requests, your participation helps make this library better for everyone.

### Getting Started

1. **Fork the repository** — Create your own fork on GitHub
2. **Clone locally** — `git clone https://github.com/YOUR_USERNAME/AgGridEnterprise.git`
3. **Create a feature branch** — `git checkout -b feature/your-feature-name`

### Development Workflow

When adding new enterprise features or fixes:

1. **Review GUIDES.md** — Understand patterns and the [Adding New Features](GUIDES.md#adding-new-features) section
2. **Follow RULES.md** — Tech stack, naming conventions, behavioral rules
3. **Update documentation** — PACT/RULES/GLOSSARY/GUIDES/IMPLEMENTATION as needed (forward-only policy)
4. **Write tests** — Add test cases for new functionality
5. **Run full build** — `mvn clean install` to build and test
6. **Submit PR** — Include a clear description and reference any related issues

### Code Standards

- **Type Safety** — Prefer enum/POJO types over raw Objects; leverage IDE autocomplete
- **Fluent API** — Extend CRTP pattern for method chaining; maintain backward compatibility
- **Documentation** — Keep RULES, GUIDES, and inline comments in sync with code changes
- **Forward-Only Policy** — No legacy code; deprecate rather than remove; update all references

See [GUIDES.md - Adding New Features](GUIDES.md#adding-new-features) for detailed instructions.

### Reporting Issues

Found a bug? Have a feature request?

1. **Check existing issues** — Search [GitHub Issues](https://github.com/JWebMP/AgGridEnterprise/issues) first
2. **Create a new issue** — Provide:
   - Clear description of the problem or feature
   - Steps to reproduce (for bugs)
   - Expected vs. actual behavior
   - Environment details (Java version, Maven version, etc.)

### Submitting Pull Requests

1. **Keep commits focused** — One feature or fix per PR
2. **Write descriptive messages** — Reference issues (#123) in commit messages
3. **Update CHANGELOG** — Document your changes
4. **Follow the style guide** — Consistent formatting and naming conventions
5. **Request review** — Core maintainers will review and provide feedback

---

## License

Apache 2.0 — See [LICENSE](LICENSE) for full details.

This project is open-source and freely available for commercial and personal use.

## Community & Related Projects

This is part of the **JWebMP ecosystem** — a collection of open-source Java web UI components and frameworks:

- **[JWebMP](https://github.com/JWebMP/JWebMP)** — Main Java Web UI Framework (Apache 2.0)
- **[AgGrid Community Plugin](https://github.com/JWebMP/AgGrid)** — Community-tier AgGrid plugin (Apache 2.0)
- **[Rules Repository](https://github.com/GuicedEE/ai-rules)** — Enterprise architecture patterns and design guides (Apache 2.0)
- **[GuicedEE](https://github.com/GuicedEE)** — Guice-based enterprise framework with dependency injection (Apache 2.0)

All projects are open-source and actively maintained by the community.

### Support & Discussion

- **GitHub Issues** — [Report bugs & request features](https://github.com/JWebMP/AgGridEnterprise/issues)
- **GitHub Discussions** — [Ask questions & share ideas](https://github.com/JWebMP/AgGridEnterprise/discussions)
- **Maven Central** — [View all versions](https://central.sonatype.com/artifact/com.jwebmp.plugins/aggrid-enterprise)

---

## Acknowledgments

This project extends AG Grid Enterprise with a type-safe Java wrapper, following the same patterns used in:
- **WebAwesomePro** → builds on WebAwesome
- **FullCalendarPro** → builds on FullCalendar

Special thanks to the JWebMP and GuicedEE communities for foundational work on fluent API patterns, modular architecture, and forward-only design practices.
