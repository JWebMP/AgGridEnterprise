# Cursor IDE Workspace Rules — AgGridEnterprise Plugin

**Constraints for Cursor IDE and Ctrl+K/Cmd+K inline chat**

---

## Quick Reference

- **Pattern:** CRTP fluent API (NO Lombok @Builder)
- **Serialization:** Jackson with @JsonAutoDetect(fieldVisibility=ANY), @JsonInclude(NON_NULL)
- **Enums:** MapStruct mapped to lowercase AG Grid format ("ag-vivid", "always", "solid")
- **DI:** GuicedEE with @Service on page configurators
- **Documentation:** Precedes code (stage-gated)
- **Changes:** Forward-only (no legacy stubs, breaking allowed)
- **Language:** Java 25 LTS, Maven 3.9+

---

## Core Constraints (from RULES.md)

### 1. Fluent API (CRTP Only)

**Correct:**
```java
public class ChartOptions extends BaseOptions<ChartOptions> {
    public ChartOptions setTheme(ChartTheme theme) {
        this.theme = theme;
        return this;
    }
}
```

**Forbidden:**
```java
public class ChartOptions {
    @Builder  // ❌ NO Lombok @Builder
    public ChartOptions(...) { }
}
```

### 2. Jackson Serialization

**Always use:**
```java
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonInclude(Include.NON_NULL)
public class MyOptions {
    private String field;
    // NO getters; Jackson reads fields directly
}
```

### 3. Enum → String Conversion (MapStruct)

Enums automatically convert:
```
ChartTheme.AG_VIVID       → "ag-vivid"
PanelShow.ALWAYS          → "always"
SeriesColorType.SOLID     → "solid"
```

Use MapStruct mappers; don't hardcode strings.

### 4. Logging (Log4j2)

```java
@Log4j2
public class MyClass {
    log.debug("Development info");
    log.info("State change");
    log.warn("Deprecation/breaking");
    log.error("Failure", exception);
}
```

### 5. Module Wiring (GuicedEE)

```java
@Service
public class AgGridEnterprisePageConfigurator implements IPageConfigurator {
    @Override
    public String toString() {
        // Auto-discovered by GuicedEE at boot
        return "Enterprise Grid Configurator";
    }
}
```

---

## Documentation-First Workflow

### Stages (Blanket Approval Granted)

1. **Stage 1** — Update PACT.md, architecture diagrams (c4-*, sequence-*, erd-*)
2. **Stage 2** — Update RULES.md, GUIDES.md, GLOSSARY.md, IMPLEMENTATION.md
3. **Stage 3** — Create implementation plan (file structure, build wiring)
4. **Stage 4** — Code only after docs complete (auto-approved for this project)

### When Adding a Feature

1. Document in `rules/generative/frontend/jwebmp/aggrid-enterprise/[feature].rules.md`
2. Update GLOSSARY.md with new terms
3. Add code example to QUICK_REFERENCE.md
4. Update IMPLEMENTATION.md module list
5. Implement code + tests
6. Update docs/architecture/ diagrams if needed

---

## Project Structure

```
src/main/java/com/jwebmp/plugins/aggridenterprise/
├── AgGridEnterprise<T>                    # Main component
├── options/
│   ├── AgGridEnterpriseOptions            # Root (@JsonUnwrapped)
│   ├── ChartOptions                       # Feature 1
│   ├── RowGroupingOptions                 # Feature 2
│   ├── ServerSideRowModelOptions          # Feature 3
│   ├── SideBarOptions                     # Feature 4
│   ├── RangeSelectionOptions              # Feature 5
│   ├── PivotTableOptions                  # Feature 6
│   ├── AdvancedFilterOptions              # Feature 7
│   ├── DynamicSeriesColoringOptions       # Feature 8
│   ├── enums/                             # Enum targets
│   └── mapping/                           # DTOs
├── charts/
├── AgGridEnterprisePageConfigurator       # Boot wiring
└── implementations/
```

---

## Code Rules

### Refactoring: Forward-Only Policy

**Before removing a class:**
- [ ] Document in MIGRATION.md
- [ ] Update all test usages
- [ ] Update all docs (PACT, RULES, GUIDES, IMPLEMENTATION)
- [ ] Verify no orphaned links (grep entire codebase)

**NO legacy stubs** — Remove completely or don't remove at all.

### Testing Requirements

```bash
mvn test -Dtest=ChartOptionsTest        # Unit test
mvn test -Dtest=AgGridEnterpriseTest    # Integration test
mvn jacoco:report                        # Coverage (target ≥80%)
```

Minimum expectations:
- Options classes: serialization tests (Jackson roundtrip)
- Fluent API: chaining tests (integration)
- Feature modules: integration tests

### Build & Compile

```bash
mvn clean install
# Compiler order: Lombok → MapStruct → Javac
# Java 25 LTS, Maven 3.9+
```

---

## Terminology (Topic-First)

Use terms from:
- **Root GLOSSARY.md** — General JWebMP terms
- **Enterprise GLOSSARY.md** — Enterprise-specific terms
- **Topic glossaries** — fluent-api, mapstruct, logging, jwebmp, angular

**Don't duplicate** — Link to topic glossaries instead.

---

## Enterprise Features (8 Total)

| Feature | Rules File | Key Classes |
|---------|-----------|-------------|
| **Charts** | `charts.rules.md` | ChartOptions, ChartTheme |
| **Range Selection** | `range-selection.rules.md` | RangeSelectionOptions |
| **Row Grouping** | `row-grouping.rules.md` | RowGroupingOptions |
| **Server-Side Model** | `server-side-row-model.rules.md` | ServerSideRowModelOptions |
| **Side Bar** | `side-bar-and-status-bar.rules.md` | SideBarOptions |
| **Pivot Tables** | `pivot-tables-and-aggregation.rules.md` | PivotTableOptions |
| **Advanced Filtering** | `advanced-filtering.rules.md` | AdvancedFilterOptions |
| **Dynamic Coloring** | `dynamic-series-coloring.rules.md` | DynamicSeriesColoringOptions |

All rules: `rules/generative/frontend/jwebmp/aggrid-enterprise/`

---

## Common Patterns

### Fluent Chaining

```java
AgGridEnterprise<SalesRow> grid = new AgGridEnterprise<SalesRow>()
    .enableCharts()
        .setTheme(ChartTheme.AG_VIVID)
    .enableRowGrouping()
        .setAutoGroupColumnDef(new ColumnDef()...)
    .enableServerSideRowModel();
```

### Options Composition (@JsonUnwrapped)

```java
@JsonAutoDetect(fieldVisibility = ANY)
@JsonInclude(NON_NULL)
public class AgGridEnterpriseOptions extends AgGridOptions {
    @JsonUnwrapped
    private ChartOptions chartOptions;
    
    @JsonUnwrapped
    private RowGroupingOptions rowGroupingOptions;
    
    // ... 6 more feature options
}
```

### MapStruct Enum Mapping

```java
@Mapper
public interface ChartThemeMapper {
    default String toAGGridString(ChartTheme theme) {
        if (theme == null) return null;
        return theme.name().toLowerCase().replace('_', '-');
    }
}
```

---

## Performance Guidelines

### Charts

- Max 500 data rows for client-side rendering
- For >500 rows, use server-side row model
- Use asynchronous data loading

### Row Grouping

- Max 10 levels for hierarchies
- Use pivots for 3+ dimension analysis
- Test aggregation performance at 1M rows

### Server-Side Model

- Block size: 100-500 rows (test performance)
- Use database indexing for sorting/filtering
- Lazy-load only visible data
- Target: <50ms response time

---

## Troubleshooting

### Serialization Issues

**Problem:** `@JsonMissingPropertyException`
**Solution:** Add `@JsonSetter` or ensure field visibility is `ANY`

### Fluent API Type Mismatch

**Problem:** Chaining breaks type in method chain
**Solution:** Verify CRTP self-type: `public T method() { return self(); }`

### Enum Mapping Fails

**Problem:** String format doesn't match AG Grid expectation
**Solution:** Check `GLOSSARY.md` for enum → string mappings; verify MapStruct `@Named` annotation

### Build Fails on Annotation Processors

**Problem:** `NoClassDefFoundError` for mapper
**Solution:** Ensure pom.xml order: Lombok → MapStruct → Compiler

---

## Links & References

- **Architecture** — `docs/architecture/` (C4, sequences, ERD)
- **Enterprise Rules** — `rules/generative/frontend/jwebmp/aggrid-enterprise/`
- **Quick Start** — `rules/generative/frontend/jwebmp/aggrid-enterprise/QUICK_REFERENCE.md`
- **Terminology** — `GLOSSARY.md` (root) + enterprise GLOSSARY.md
- **Examples** — `examples/chart-integration-example.md`, `examples/server-side-row-model-example.md`
- **Build** — `pom.xml` (Java 25, Maven 3.9+, Lombok → MapStruct → Compiler)
- **Related Rules** — See root RULES.md § Related Topic Rules

---

**End of Cursor IDE Workspace Rules**
