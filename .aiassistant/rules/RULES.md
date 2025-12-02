# AI Assistant Workspace Rules — AgGridEnterprise Plugin

**Focused constraints for GitHub Copilot AI Assistant and compatible agents**

---

## Quick Start

This project enforces **documentation-first, forward-only development**. All changes require:

1. **Update docs first** (PACT.md, RULES.md, GLOSSARY.md, enterprise rules)
2. **Follow CRTP pattern** (no Lombok @Builder; fluent API required)
3. **No legacy stubs** (remove old code completely; breaking changes allowed)
4. **Jackson serialization** (@JsonAutoDetect, @JsonInclude)
5. **MapStruct enums** (Java enum → AG Grid string format)
6. **GuicedEE wiring** (@Service on page configurators)

---

## Behavioral Rules (Extract from RULES.md § 4)

### Language & Consistency

- **Use terminology from GLOSSARY.md** — Topic-first composition
- **No backwards compatibility** — Forward-only policy only
- **Attribute decisions** — Reference PACT.md, RULES.md, GUIDES.md, IMPLEMENTATION.md

### Transparency

- **State constraints upfront** — Explain why a pattern is required (e.g., "CRTP enforced for fluent API; no Lombok @Builder")
- **Stage gates** — Documentation precedes code
- **Incorporate feedback immediately** — Update all references in same change

---

## Technical Rules (Extract from RULES.md § 5)

### Serialization: Jackson

```java
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonInclude(Include.NON_NULL)
public class MyOptions {
    private SomeOption option;
    // No getters; Jackson reads fields directly
}
```

### Fluent API: CRTP (Mandatory)

```java
public abstract class BaseOptions<T extends BaseOptions<T>> {
    public T enableFeature() {
        // ... set fields
        return self();
    }
    
    protected abstract T self();
}

public class ChartOptions extends BaseOptions<ChartOptions> {
    @Override
    protected ChartOptions self() {
        return this;
    }
}
```

**NO Lombok @Builder** — Use CRTP only.

### Enum Mapping: MapStruct

Enums convert to lowercase AG Grid format:

```
ChartTheme.AG_VIVID       → "ag-vivid"
PanelShow.ALWAYS          → "always"
SeriesColorType.SOLID     → "solid"
```

MapStruct mapper auto-generates transformations.

### Logging: Log4j2

```java
@Log4j2
public class MyClass {
    public void doSomething() {
        log.debug("Starting");      // Development
        log.info("Config loaded");  // State changes
        log.warn("Feature X deprecated");  // Backwards-compat breaks
        log.error("Failed", ex);    // Failures
    }
}
```

---

## Document Modularity

### Where Files Live

- **Root docs** — `PACT.md`, `RULES.md`, `GLOSSARY.md`, `GUIDES.md`, `IMPLEMENTATION.md`
- **Architecture docs** — `docs/architecture/` (C4 diagrams, sequences, ERD)
- **Enterprise feature rules** — `rules/generative/frontend/jwebmp/aggrid-enterprise/` (Git submodule)
  - **Parent index** — `README.md` (links to 8 features, examples, checklists)
  - **Feature modules** — `charts.rules.md`, `row-grouping.rules.md`, etc.
  - **Examples** — `examples/chart-integration-example.md`, `examples/server-side-row-model-example.md`
  - **Topic glossary** — `GLOSSARY.md` (links to topic glossaries; no duplication)

### Topic-First Glossary

Enterprise `GLOSSARY.md` does NOT duplicate root GLOSSARY.md. Instead:
- Lists enterprise-specific terms (ChartOptions, SeriesColorType, etc.)
- Links to topic glossaries for general terms (fluent-api, mapstruct, jwebmp, logging)
- Includes LLM prompt interpretation guidance

---

## Forward-Only Change Policy

### When Changing Code

1. **No Refactoring Without Deprecation** — Don't rename/remove without documented migration path
2. **No Legacy Stubs** — Remove old classes/methods completely; don't leave aliases
3. **Update All References** — One change; update all docs/tests/links in same commit

### Before Removing Class/Method

- [ ] Documented in MIGRATION.md (if risky)
- [ ] All test usages updated
- [ ] All docs updated (PACT/RULES/GUIDES/IMPLEMENTATION)
- [ ] All links verified (`grep` to confirm no orphans)

---

## Code Organization

Follows IMPLEMENTATION.md structure:

```
src/main/java/com/jwebmp/plugins/aggridenterprise/
├── AgGridEnterprise<T>                 # Main component
├── options/
│   ├── AgGridEnterpriseOptions         # Root options (@JsonUnwrapped)
│   ├── ChartOptions                    # Feature module
│   ├── RowGroupingOptions
│   ├── ServerSideRowModelOptions
│   ├── SideBarOptions
│   ├── RangeSelectionOptions
│   ├── PivotTableOptions
│   ├── AdvancedFilterOptions
│   ├── DynamicSeriesColoringOptions
│   ├── enums/                          # MapStruct enum targets
│   └── mapping/                        # DTOs
├── charts/
├── AgGridEnterprisePageConfigurator    # Boot wiring (@Service)
└── implementations/
```

---

## Testing

### Unit Tests (Options/Serialization)

```java
@Test
public void testChartOptionsSerialize() {
    ChartOptions opts = new ChartOptions()
        .enableCharts()
        .setTheme(ChartTheme.AG_VIVID);
    
    String json = objectMapper.writeValueAsString(opts);
    assertThat(json).contains("\"theme\":\"ag-vivid\"");
}
```

### Integration Tests (Fluent API)

```java
@Test
public void testFluentChaining() {
    AgGridEnterprise<SalesRow> grid = new AgGridEnterprise<SalesRow>()
        .enableCharts()
        .enableRowGrouping()
        .enableServerSideRowModel();
    
    assertThat(grid.getOptions().getChartOptions()).isNotNull();
    assertThat(grid.getOptions().getRowGroupingOptions()).isNotNull();
}
```

### Target Coverage

- Minimum 80% (Jacoco)
- All options classes tested (serialization)
- All fluent chains tested (integration)

---

## Build & CI/CD

### Maven Build

```bash
mvn clean install
```

- **Java** — 25 LTS
- **Annotation processors** — Lombok → MapStruct → Compiler (order matters)
- **Output** — JAR at `target/aggrid-enterprise-2.0.0.jar`

### Test Execution

```bash
mvn test                  # All tests
mvn test -Dtest=MyTest    # Single test
mvn jacoco:report         # Coverage
```

---

## Architecture Patterns

### SDD (Specification-Driven Design)

All features start with **documentation**:
1. Document requirement in PACT.md
2. Create feature .rules.md in enterprise submodule
3. Update GLOSSARY.md with terminology
4. Write tests first (TDD)
5. Implement code

### C4 Architecture

Diagrams stored in `docs/architecture/`:
- **L1 (Context)** — System context (c4-context.md)
- **L2 (Container)** — Modules: core, charts, row-grouping, server-side, etc. (c4-container.md)
- **L3 (Component)** — Enterprise API surface (c4-component-enterprise-api.md)

---

## Enterprise Rules Reference

When implementing features, consult:

| Feature | File | Key Sections |
|---------|------|--------------|
| Charts | `charts.rules.md` | Themes, toolbar, performance, accessibility |
| Range Selection | `range-selection.rules.md` | Clipboard control, single-cell |
| Row Grouping | `row-grouping.rules.md` | Multi-level, aggregation functions, formatters |
| Server-Side Model | `server-side-row-model.rules.md` | Block caching, DataSource, sorting/filtering |
| Side Bar | `side-bar-and-status-bar.rules.md` | Tool panels, status metrics |
| Pivot Tables | `pivot-tables-and-aggregation.rules.md` | Pivot mode, aggregation functions |
| Advanced Filtering | `advanced-filtering.rules.md` | Set filters, custom predicates |
| Dynamic Series Coloring | `dynamic-series-coloring.rules.md` | 5 strategies, thresholds |

All in: `rules/generative/frontend/jwebmp/aggrid-enterprise/`

---

## Related Topic Rules

This project extends:

- **JWebMP AgGrid (Community)** — `rules/generative/frontend/jwebmp/aggrid/`
- **JWebMP Core** — `rules/generative/frontend/jwebmp/core/`
- **Fluent API (CRTP)** — `rules/generative/backend/fluent-api/crtp.rules.md`
- **MapStruct** — `rules/generative/backend/mapstruct/README.md`
- **Logging (Log4j2)** — `rules/generative/backend/logging/README.md`
- **GuicedEE** — `rules/generative/backend/guicedee/`

---

## When to Escalate

### Handle Yourself

- ✅ Implement new feature following documented patterns
- ✅ Add unit test for existing feature
- ✅ Refactor within forward-only guidelines
- ✅ Document new feature (rules.md, examples, glossary)
- ✅ Update existing code per CRTP/Jackson/MapStruct rules

### Escalate to User

- 🔴 Design decisions affecting multiple modules
- 🔴 Breaking changes or policy violations
- 🔴 Uncertain feature scope
- 🔴 Conflicts with forward-only policy

---

## Document Control

| Document | Location | Purpose | Version |
|----------|----------|---------|---------|
| RULES.md | Root | Tech stack, behavioral, technical rules | 1.0 |
| PACT.md | Root | Architecture & contract | 1.0 |
| GLOSSARY.md (root) | Root | Terminology index | 1.0 |
| GLOSSARY.md (enterprise) | rules/.../aggrid-enterprise/ | Enterprise terminology (topic-first) | 1.0 |
| QUICK_REFERENCE.md | rules/.../aggrid-enterprise/ | Templates, checklists, examples | 1.0 |
| IMPLEMENTATION.md | Root | Code layout & structure | 1.0 |
| GUIDES.md | Root | How-to guides | 1.0 |

---

**End of AI Assistant Workspace Rules**
