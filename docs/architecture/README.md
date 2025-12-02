# Architecture Documentation — AgGridEnterprise Plugin

This directory contains architecture diagrams and technical flow documentation for the AgGridEnterprise plugin, presented in Docs-as-Code format using Mermaid and PlantUML.

## Overview

AgGridEnterprise is a JWebMP plugin that extends the community AgGrid plugin to expose AG Grid Enterprise features (Charts, Range Selection, Row Grouping, Server-Side Row Models, etc.) in a familiar Java/Fluent API.

## Architecture Diagrams

### Context Diagram (C4 L1)
See **[c4-context.md](c4-context.md)** — System context showing AgGridEnterprise positioned within the JWebMP ecosystem and external dependencies (AG Grid Enterprise, Angular, browser).

### Container Diagram (C4 L2)
See **[c4-container.md](c4-container.md)** — Containers/modules: Java Enterprise Options Layer, TypeScript Code Generation, Page Configurator, Module Registry Bridge, Angular Integration.

### Component Diagrams (C4 L3)
See **[c4-component-enterprise-api.md](c4-component-enterprise-api.md)** — Core enterprise options, enums, model mappings, and fluent builder API components.

### Sequence Diagrams
- **[sequence-page-load.md](sequence-page-load.md)** — Grid initialization: Page Configurator → Module Wiring → Options Registration → Angular Bootstrap
- **[sequence-chart-render.md](sequence-chart-render.md)** — Enterprise chart rendering flow
- **[sequence-server-side-model.md](sequence-server-side-model.md)** — Server-Side Row Model data exchange

### Entity-Relationship Diagrams
See **[erd-enterprise-options.md](erd-enterprise-options.md)** — Options class hierarchy, relationships between GridOptions → EnterpriseOptions → specific feature configs (SideBar, StatusBar, Charts).

## Key Bounded Contexts

1. **Enterprise Options API** — Java fluent builders and DTOs for AG Grid Enterprise features
2. **Angular/TypeScript Integration** — TypeScript code generation and npm dependency management
3. **Page Configuration** — Boot-time wiring of AG Grid Enterprise libraries and module registration
4. **Serialization/Mapping** — Jackson JSON marshaling and MapStruct transformations

## Design Decisions

- **CRTP Fluent API** — AgGridEnterprise<T> and option setters return `this` for method chaining (no Lombok @Builder per project CRTP strategy)
- **Typed Enums & POJOs** — Enterprise options modeled as strongly-typed classes instead of raw Object/Map to ensure compile-time safety
- **Annotation-Driven Boot** — GuicedEE IPageConfigurator and ModuleRegistry pattern for dependency injection and automatic module discovery
- **MapStruct for Data Binding** — Transformations between domain models and AG Grid JSON contracts
- **Jackson Configuration** — @JsonAutoDetect field visibility, @JsonInclude NON_NULL for clean JSON serialization

## References

- **[../../../PACT.md](../../../PACT.md)** — Product architecture and contract
- **[../../../RULES.md](../../../RULES.md)** — Project rules and stacks
- **[../../../GLOSSARY.md](../../../GLOSSARY.md)** — Terminology and component names
- **[../../../GUIDES.md](../../../GUIDES.md)** — How to apply rules and patterns
- **[../../../IMPLEMENTATION.md](../../../IMPLEMENTATION.md)** — Module structure and code layout

## Mermaid MCP Integration

These diagrams were generated using Mermaid MCP for traceability and version control. Diagrams are stored as Markdown source; rendered views are not committed.

---

**Last Updated:** 2025-12-02  
**Status:** Stage 1 Architecture documentation complete
