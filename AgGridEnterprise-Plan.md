# AgGridEnterprise – Plan & Execution Prompt

This document tracks the plan for building the AgGridEnterprise plugin (the Enterprise layer on top of the community AgGrid plugin) and provides a ready-to-run prompt to continue the work.

## Goals
- Mirror the Community→Pro pattern (e.g., WebAwesome→WebAwesomePro, FullCalendar→FullCalendarPro).
- Provide typed Enterprise-only options on top of AgGridOptions.
- Include AG Grid Enterprise Charts options and common enterprise features (Side Bar, Status Bar, Row Group/Pivot visibility, Server-Side Row Model).
- Ensure Angular/TS dependencies load the enterprise bundle and register modules.
- Documentation and examples.

## Current Status (checkpoint)
- Basic Enterprise component, page configurator, and module wiring are in place.
- Options extended: enableCharts, enableRangeSelection, sideBar (typed overloads), statusBar (typed), rowGroupPanelShow/pivotPanelShow/rowModelType (enums overloads), chartThemes, chartThemeOverrides.
- Typed models added: SideBarDef, SideBarToolPanelDef, StatusBarDef, StatusBarPanelDef.
- Docs: README.md, docs/AgGridEnterprise-Guide.md references created.

## Plan & Checklist
1. Enterprise Options Coverage (phase 1) ✓
   - SideBar: add SideBarDef and ToolPanelDef ✓
   - StatusBar: add StatusBarDef and PanelDef ✓
   - Enums for rowGroupPanelShow/pivotPanelShow/rowModelType ✓
   - Charts: chartThemes and chartThemeOverrides ✓
   - Overloaded setters to avoid exposing raw Object in API ✓

2. Convenience API on AgGridEnterprise (phase 1.1) ✓
   - enableCharts(), enableRangeSelection(), sideBar presets, serverSide row model helper ✓

3. Angular/TS Integration ✓
   - Ensure @TsDependency for ag-grid-enterprise with ModuleRegistry registration ✓

4. Documentation ✓
   - README updated, guide file referenced ✓
   - Add examples for typed SideBar/StatusBar in guide ✓

5. Extended Enterprise Options (phase 2) 
   - Chart Toolbar items customization (getChartToolbarItems) 
   - Chart range API hooks / suppressChartToolPanelsOverlay 
   - Server-Side Row Model extra options (store type, cache, block sizes)
   - Clipboard/Range selection advanced enterprise flags

6. Tests and Samples 
   - Add example component showcasing SideBar/StatusBar + Charts
   - Smoke tests for JSON serialization of enterprise options

7. Release Readiness 
   - Verify license key integration note
   - Validate build with BOM and aggregator

## Execution Prompt
Use this prompt to continue or extend the implementation.

"""
You are enhancing the JWebMP AgGridEnterprise plugin. Tasks:
1) Add missing Enterprise-only GridOptions that are safe and commonly used:
   - Server-Side Row Model options: serverSideStoreType, cacheBlockSize, maxBlocksInCache, purgeClosedRowNodes.
   - Charts: getChartToolbarItems hook (string[] return), chartToolPanelsDef, suppressChartToolPanelsOverlay.
   - Clipboard & Range: suppressCopySingleCellRanges, suppressCopyRowsToClipboard.
   - Column Tool Panel: suppressColumnToolPanel, suppressFilterToolPanel (grid-level).

2) Implement typed enums/POJOs instead of raw Object wherever possible. Provide convenient Java overloads that accept booleans/strings when AG Grid allows unions.

3) Update docs in docs/AgGridEnterprise-Guide.md with examples for new options. Add a minimal demo snippet.

4) Build the project with the aggregator (root POM, profile `build`) to verify no regressions.

Constraints:
- Keep changes minimal and backward-compatible.
- Follow existing annotations: JsonAutoDetect field visibility, JsonInclude NON_NULL.
- Add new code under com.jwebmp.plugins.aggridenterprise.* only.
- Do not change the community plugin except for clear bugs.
"""
