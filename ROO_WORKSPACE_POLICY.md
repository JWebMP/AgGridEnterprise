# ROO_WORKSPACE_POLICY.md — AgGridEnterprise Plugin

**Workspace-scoped policy for Roo coding agent and related autonomous agents**

---

## Agent Profile

- **Framework:** Roo (autonomous multi-step coding agent)
- **Language:** Java 25 LTS
- **Context:** JWebMP AgGridEnterprise plugin (Phase 2, mature codebase)
- **Approval Mode:** Blanket approval (auto-proceed through documentation & planning stages)
- **Scope:** Enterprise UI component development following documentation-first, forward-only change policy

---

## Mandatory Constraints (Non-Negotiable)

### 1. CRTP Fluent API (No Alternatives)

**Requirement:** All builder/fluent methods must use Curiously Recurring Template Pattern (CRTP).

```java
public abstract class BaseOptions<T extends BaseOptions<T>> {
    public T enableFeatures() {
        // ... initialize
        return self();
    }
    protected abstract T self();
}

public class ChartOptions extends BaseOptions<ChartOptions> {
    @Override
    protected ChartOptions self() { return this; }
}
```

**Forbidden:** Lombok @Builder, Fluent annotation (except on build result).

**Rationale:** CRTP enables type-safe self-returning chains for composable options. Lombok @Builder breaks type safety in chains.

### 2. Jackson Serialization Pattern

**Requirement:** All Options classes must follow exact Jackson pattern.

```java
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonInclude(Include.NON_NULL)
public class MyOptions {
    private String field1;
    private Integer field2;
    // NO getters; Jackson reads fields directly
}
```

**Properties:**
- `@JsonAutoDetect(fieldVisibility = ANY)` — Serialize all fields
- `@JsonInclude(NON_NULL)` — Exclude null fields from JSON
- NO getter/setter methods
- NO Lombok @Getter/@Setter

**Rationale:** Field-based serialization reduces boilerplate; null exclusion minimizes JSON size; consistency across all options classes.

### 3. Enum → String Transformation (MapStruct Only)

**Requirement:** Use MapStruct for enum mapping to AG Grid string format.

```java
ChartTheme.AG_VIVID           → "ag-vivid"
PanelShow.ALWAYS              → "always"
SeriesColorType.SOLID         → "solid"
```

**Forbidden:** Hardcoded string mappings, manual case conversion.

**Implementation:**
```java
@Mapper(componentModel = "cdi")
public interface EnumMapper {
    default String fromEnum(ChartTheme theme) {
        return theme == null ? null : 
            theme.name().toLowerCase().replace('_', '-');
    }
}
```

**Rationale:** Single source of truth for enum mappings; auto-generation prevents typos; centralized maintenance.

### 4. GuicedEE Module Discovery

**Requirement:** Use GuicedEE @Service annotation for boot-time registration.

```java
@Service
public class AgGridEnterprisePageConfigurator implements IPageConfigurator {
    @Override
    public void configure(IGuiceConfigurator config) {
        // Auto-discovered at startup
    }
}
```

**Forbidden:** Manual registration, static initialization blocks.

**Rationale:** GuicedEE auto-discovery prevents configuration errors; declarative wiring enables feature toggling.

### 5. Log4j2 Logging (Lombok @Log4j2 Only)

**Requirement:** Use Lombok @Log4j2 annotation exclusively.

```java
@Log4j2
public class MyClass {
    log.debug("Development");
    log.info("State change");
    log.warn("Deprecated feature");
    log.error("Failure", exception);
}
```

**Forbidden:** SLF4J, Apache Commons Logging, manual logger initialization.

**Rationale:** Lombok generates at compile-time (zero runtime cost); uniform across codebase.

---

## Documentation-First Workflow (Stages 1-4)

### Stage 1: Architecture & Foundations (Complete)

**Deliverables:**
- ✅ PACT.md (product overview, specifications, principles)
- ✅ Architecture diagrams (C4 context/container/component, sequences, ERD)
- ✅ Dependency maps (module interactions)

**Gate:** Automatic pass (pre-existing, verified in prior session).

### Stage 2: Rules/Guides Design (Complete)

**Deliverables:**
- ✅ RULES.md (tech stack, behavioral/technical rules, forward-only policy)
- ✅ GUIDES.md (usage patterns, feature examples)
- ✅ GLOSSARY.md (root terminology index + topic links)
- ✅ API specifications
- ✅ Testing strategy (unit/integration/coverage)

**Gate:** Automatic pass (pre-existing, verified in prior session).

### Stage 3: Implementation Plan (Complete)

**Deliverables:**
- ✅ IMPLEMENTATION.md (module structure, file organization, build wiring)
- ✅ CI/deployment plan
- ✅ Versioning strategy
- ✅ Risk assessment

**Gate:** Automatic pass (pre-existing, verified in prior session).

### Stage 4: Implementation (In Progress — Roo May Execute)

**Before Code:**
1. Verify docs in Stages 1-3 reflect feature requirements
2. Create feature-specific .rules.md in `rules/generative/frontend/jwebmp/aggrid-enterprise/`
3. Update GLOSSARY.md (enterprise) with new terminology
4. Update IMPLEMENTATION.md module list
5. Add test templates to QUICK_REFERENCE.md

**Only Then:** Implement code + tests.

**Gate:** Blanket approval granted (Roo auto-proceeds).

---

## Enterprise Feature Modules (8 Total)

### Feature → Rules File Mapping

| # | Feature | File | Key Class | Status |
|---|---------|------|-----------|--------|
| 1 | **Charts** | `charts.rules.md` | ChartOptions | ✅ Complete |
| 2 | **Range Selection** | `range-selection.rules.md` | RangeSelectionOptions | ✅ Complete |
| 3 | **Row Grouping** | `row-grouping.rules.md` | RowGroupingOptions | ✅ Complete |
| 4 | **Server-Side Model** | `server-side-row-model.rules.md` | ServerSideRowModelOptions | ✅ Complete |
| 5 | **Side Bar & Status Bar** | `side-bar-and-status-bar.rules.md` | SideBarOptions | ✅ Complete |
| 6 | **Pivot Tables** | `pivot-tables-and-aggregation.rules.md` | PivotTableOptions | ✅ Complete |
| 7 | **Advanced Filtering** | `advanced-filtering.rules.md` | AdvancedFilterOptions | ✅ Complete |
| 8 | **Dynamic Coloring** | `dynamic-series-coloring.rules.md` | DynamicSeriesColoringOptions | ✅ Complete |

**Location:** `rules/generative/frontend/jwebmp/aggrid-enterprise/`

**Check before implementing:** Consult corresponding .rules.md file.

---

## Forward-Only Change Policy (Non-Negotiable)

### Rules for Code Changes

1. **No Refactoring Without Migration Path** — If renaming/restructuring class, document in MIGRATION.md
2. **No Legacy Stubs** — Don't leave old classes/methods as aliases; remove completely or don't touch
3. **Update All References** — One change; one commit; update all docs/tests/links together

### Removal Checklist

Before removing any class/method/field:

- [ ] Documented in MIGRATION.md (if users may be affected)
- [ ] All test usages updated (not removed; rewritten)
- [ ] All docs updated (PACT, RULES, GUIDES, IMPLEMENTATION, enterprise rules)
- [ ] All Javadoc links verified (grep for @link references)
- [ ] No orphaned references remain (`grep -r "OldClassName" src/`)

### Breaking Changes

**Allowed without deprecation period** — Policy explicitly permits clean breaks.
**Requirement:** Full documentation in MIGRATION.md explaining old → new mapping.

---

## Code Organization (Mandated Layout)

```
src/main/java/com/jwebmp/plugins/aggridenterprise/
├── AgGridEnterprise<T>                      # Main component class
│   └── (generic type parameter for grid row data)
│
├── options/
│   ├── AgGridEnterpriseOptions              # Root options class (@JsonUnwrapped)
│   ├── ChartOptions                         # Feature 1 module (@JsonUnwrapped)
│   ├── RowGroupingOptions                   # Feature 2 module (@JsonUnwrapped)
│   ├── ServerSideRowModelOptions            # Feature 3 module (@JsonUnwrapped)
│   ├── SideBarOptions                       # Feature 4 module (@JsonUnwrapped)
│   ├── RangeSelectionOptions                # Feature 5 module (@JsonUnwrapped)
│   ├── PivotTableOptions                    # Feature 6 module (@JsonUnwrapped)
│   ├── AdvancedFilterOptions                # Feature 7 module (@JsonUnwrapped)
│   ├── DynamicSeriesColoringOptions         # Feature 8 module (@JsonUnwrapped)
│   │
│   ├── enums/
│   │   ├── ChartTheme.java                  # MapStruct enum target
│   │   ├── PanelShow.java
│   │   ├── SeriesColorType.java
│   │   └── ... (other enums)
│   │
│   └── mapping/
│       ├── ChartThemeMapper.java            # MapStruct mapper
│       ├── EnumMappers.java                 # Shared mappers
│       └── ... (DTOs if needed)
│
├── charts/                                  # Feature-specific implementations
│   ├── ChartRenderer.java                   # Rendering logic
│   └── ... (support classes)
│
├── AgGridEnterprisePageConfigurator.java    # GuicedEE boot wiring (@Service)
│
└── implementations/                         # Interface implementations
    ├── ServerSideDataSource.java            # Lazy-load interface
    └── ... (other SPIs)
```

**Rules:**
- All Options classes in `options/` package
- All enums in `options/enums/`
- All MapStruct mappers in `options/mapping/`
- One page configurator class (implements IPageConfigurator, @Service)
- Feature implementations in subfolder (optional)

---

## Testing Standards (Minimum 80% Coverage)

### Unit Tests (Jackson Serialization)

```java
@Test
public void chartOptionsSerializeToJSON() {
    ChartOptions opts = new ChartOptions()
        .enableCharts()
        .setTheme(ChartTheme.AG_VIVID);
    
    String json = objectMapper.writeValueAsString(opts);
    
    assertThat(json)
        .contains("\"chartEnable\":true")
        .contains("\"theme\":\"ag-vivid\");  // ← AG Grid format
}
```

**Coverage Expectations:**
- All Options fields serialized/deserialized
- Null fields excluded from JSON
- Enum values map to correct AG Grid strings

### Integration Tests (Fluent API)

```java
@Test
public void fluentChainedComposition() {
    AgGridEnterprise<SalesRow> grid = new AgGridEnterprise<SalesRow>()
        .enableCharts()
            .setTheme(ChartTheme.AG_VIVID)
            .enableToolbar()
        .enableRowGrouping()
            .setAutoGroupColumnDef(new ColumnDef()...)
        .enableServerSideRowModel()
            .setBlockSize(200);
    
    assertThat(grid.getOptions().getChartOptions()).isNotNull();
    assertThat(grid.getOptions().getChartOptions().getTheme())
        .isEqualTo(ChartTheme.AG_VIVID);
}
```

**Coverage Expectations:**
- All method chains return correct type
- Nested fluent calls work across modules
- Options accumulate correctly

### Run Tests

```bash
mvn clean test
mvn test -Dtest=ChartOptionsTest
mvn jacoco:report
```

**Target:** ≥80% overall coverage (Jacoco report in `target/site/jacoco/index.html`).

---

## Build & Deployment

### Maven Build

```bash
mvn clean install
```

**Configuration:**
- **Java:** 25 LTS (required)
- **Maven:** 3.9+ (required)
- **Annotation Processors:** Lombok → MapStruct → Compiler (order matters; verified in pom.xml)

**Output:** JAR artifact at `target/aggrid-enterprise-2.0.0.jar`

### Dependencies (Non-Negotiable)

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.30</version>  <!-- @Log4j2 only -->
    <scope>provided</scope>
</dependency>

<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.5.5.Final</version>  <!-- Enum mapping -->
</dependency>

<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.16.1</version>  <!-- JSON serialization -->
</dependency>

<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.22.1</version>
</dependency>

<dependency>
    <groupId>cloud.piranha.guides.jwebmp</groupId>
    <artifactId>jwebmp-core</artifactId>
    <version>CURRENT</version>  <!-- Base framework -->
</dependency>
```

---

## Logging Configuration (Log4j2)

### Configuration File (log4j2.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
    </Appenders>
    <Loggers>
        <Logger name="com.jwebmp.plugins.aggridenterprise" level="debug"/>
        <Root level="info">
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>
</Configuration>
```

### Usage (Mandatory @Log4j2)

```java
@Log4j2
public class ChartRenderer {
    public void render() {
        log.debug("Rendering chart");           // Development
        log.info("Chart rendered successfully"); // State changes
        log.warn("High data count; performance may degrade");  // Warnings
        log.error("Failed to render chart", ex);  // Errors
    }
}
```

---

## Architecture Diagrams (Mermaid/PlantUML)

All diagrams in `docs/architecture/`:

| Diagram | Purpose | File |
|---------|---------|------|
| C4 Context (L1) | System boundary | `c4-context.md` |
| C4 Container (L2) | Modules/layers | `c4-container.md` |
| C4 Component (L3) | Enterprise API surface | `c4-component-enterprise-api.md` |
| Sequence: Page Load | Startup flow | `sequence-page-load.md` |
| Sequence: Chart Render | Feature rendering | `sequence-chart-render.md` |
| Sequence: Server-Side Model | Data loading | `sequence-server-side-model.md` |
| ERD | Options hierarchy | `erd-enterprise-options.md` |

**When Adding Feature:** Update C4 diagram if module structure changes; add sequence diagram if new flow.

---

## Terminology & Naming

### Use GLOSSARY.md (Topic-First)

**Root GLOSSARY.md** contains:
- JWebMP core terms (Component, Page, Configuration)
- Linking to topic glossaries (fluent-api, mapstruct, jwebmp, logging, angular)

**Enterprise GLOSSARY.md** contains:
- AgGridEnterprise-specific terms (ChartOptions, ServerSideDataSource, etc.)
- Enum types (ChartTheme, PanelShow, SeriesColorType)
- LLM interpretation guidance (how to understand requirements for charts, pivot tables, etc.)

**Naming Conventions:**
- Classes: PascalCase (ChartOptions, RowGroupingOptions)
- Methods: camelCase (enableCharts(), setTheme())
- Constants: UPPER_SNAKE_CASE (AG_VIVID)
- Enums: Descriptive, self-documenting (POSITIVE_NEGATIVE, VALUE_GRADIENT)

---

## Decision Log (Rationale for Constraints)

| Constraint | Reason | Alternatives Rejected |
|-----------|--------|----------------------|
| CRTP (no @Builder) | Type-safe self-returning chains | Lombok @Builder (breaks type in chains) |
| Jackson (@JsonAutoDetect.ANY) | Minimal boilerplate | Getter/setter methods (verbose) |
| MapStruct enums | Single source of truth for mappings | Hardcoded strings (error-prone), manual conversion (duplication) |
| GuicedEE @Service | Auto-discovery prevents config errors | Manual registration, static blocks (fragile) |
| Log4j2 @Log4j2 | Compile-time generation, zero runtime cost | SLF4J facade (extra abstraction), manual initialization (verbose) |
| Forward-only changes | Clean break philosophy (no legacy code) | Deprecation paths (technical debt), legacy stubs (confusing) |

---

## When to Escalate (Beyond Roo Authority)

### Roo Can Handle Autonomously

✅ Implement feature following documented patterns  
✅ Add/modify options class (Jackson/MapStruct/CRTP rules)  
✅ Create tests (unit for serialization, integration for fluent chains)  
✅ Update documentation (rules.md, GLOSSARY.md, QUICK_REFERENCE.md)  
✅ Refactor within forward-only guidelines  
✅ Fix bugs (update docs + code + tests in same commit)

### Escalate to Human (User)

🔴 Design decisions affecting multiple feature modules  
🔴 Breaking changes to public API (beyond forward-only scope)  
🔴 New architectural patterns (e.g., changing from GuicedEE to CDI directly)  
🔴 Uncertain feature scope (clarify in PACT.md first)  
🔴 Conflicts with constraints (e.g., "Can we use Lombok @Builder?" → Answer: No)

---

## Related Rules Repository Topics

This project extends:

- **JWebMP AgGrid (Community)** — `rules/generative/frontend/jwebmp/aggrid/`
- **JWebMP Core** — `rules/generative/frontend/jwebmp/core/`
- **Fluent API (CRTP)** — `rules/generative/backend/fluent-api/crtp.rules.md`
- **MapStruct** — `rules/generative/backend/mapstruct/README.md`
- **Logging (Log4j2)** — `rules/generative/backend/logging/README.md`
- **GuicedEE** — `rules/generative/backend/guicedee/`
- **JSpecify** — `rules/generative/backend/jspecify/` (nullability)

---

## Document Control & Versioning

| Document | Location | Version | Last Updated |
|----------|----------|---------|--------------|
| ROO_WORKSPACE_POLICY.md | Root | 1.0 | 2025-12-02 |
| RULES.md | Root | 1.0 | 2025-12-02 |
| PACT.md | Root | 1.0 | 2025-12-02 |
| GLOSSARY.md (root) | Root | 1.0 | 2025-12-02 |
| GLOSSARY.md (enterprise) | rules/.../aggrid-enterprise/ | 1.0 | 2025-12-02 |
| QUICK_REFERENCE.md | rules/.../aggrid-enterprise/ | 1.0 | 2025-12-02 |
| IMPLEMENTATION.md | Root | 1.0 | 2025-12-02 |
| GUIDES.md | Root | 1.0 | 2025-12-02 |

---

## Quick Links

| Purpose | Link |
|---------|------|
| Start Here | README.md |
| Rules & Constraints | RULES.md |
| Architecture | docs/architecture/README.md |
| Enterprise Features | rules/generative/frontend/jwebmp/aggrid-enterprise/README.md |
| Code Examples | QUICK_REFERENCE.md |
| Terminology | GLOSSARY.md (root + enterprise) |
| Implementation | IMPLEMENTATION.md |
| How-Tos | GUIDES.md |

---

**End of ROO_WORKSPACE_POLICY.md**
