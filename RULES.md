# RULES — AgGridEnterprise Project Rules & Stacks

**Version:** 2.0.0-SNAPSHOT  
**Adopted:** 2025-12-02 (Rules Repository Integration, Stage 1)

---

## 1. Project Scope

**AgGridEnterprise** is a JWebMP plugin extending the community AgGrid plugin to expose AG Grid Enterprise features in a type-safe, fluent Java API.

- **Repository URL:** https://github.com/JWebMP/AgGridEnterprise.git
- **License:** Apache 2.0
- **Organization:** JWebMP
- **Parent POM:** `com.jwebmp:parent:2.0.0-SNAPSHOT`

---

## 2. Technology Stack

### JVM & Build

- **Java Version:** Java 25 LTS (per Rules Repository selection)
- **Build Tool:** Maven 3.9+
- **Build Command:** `mvn clean install`
- **Artifact Coordinates:** `com.jwebmp.plugins:aggrid-enterprise:${version}`

### Core Dependencies

| Artifact | Version | Purpose |
|----------|---------|---------|
| `com.jwebmp.core:jwebmp-core` | from BOM | Component model, TS generation |
| `com.jwebmp.plugins:aggrid` | from BOM | Community AgGrid plugin |
| `com.jwebmp.plugins:typescript-client` | from BOM | TypeScript client support |
| `com.guicedee.services:mapstruct` | from BOM | MapStruct support via GuicedEE |
| `org.projectlombok:lombok` | from BOM | Provided scope; annotations only |

### DI & Service Discovery

- **Framework:** GuicedEE dependency injection
- **Service Interfaces:** `IPageConfigurator` (boot), `IGuiceScanModuleInclusions` (classpath discovery)
- **Registration:** Java `@Service` on implementation classes (auto-discovered by GuicedEE)

### Serialization

- **JSON Library:** Jackson (from jwebmp-bom)
- **Mapping:** MapStruct with generated mappers (Lombok & MapStruct binding included)
- **Configuration:** `@JsonAutoDetect(fieldVisibility = FIELD)`, `@JsonInclude(Include.NON_NULL)`

### Frontend

- **Angular Framework:** ng-core (from JWebMP TS generation)
- **Grid Library:** ag-grid-community (base), ag-grid-enterprise (enterprise features)
- **npm Dependency:** `ag-grid-enterprise` (added by Page Configurator)

### Testing

- **Framework:** JUnit 5 (Jupiter)
- **Test Library:** jwebmp-testlib
- **Coverage:** Jacoco (from parent BOM)

---

## 3. Architecture Rules

### Design Patterns

1. **CRTP for Fluent API** — All component setters/builders return `this` (self-type generic) for method chaining
   - No Lombok `@Builder` (conflicts with CRTP)
   - Manual fluent setters required; annotation `@SuppressWarnings("unchecked")` where needed
   - Example: `AgGridEnterprise<T>` where T extends AgGridEnterprise

2. **Composition over Inheritance** — Options POJOs composed into parent, not via monolithic inheritance
   - `AgGridEnterpriseOptions` extends `AgGridOptions`
   - Feature options (ChartOptions, RangeSelectionOptions, etc.) composed as fields
   - Rationale: clean feature separation, independent testing

3. **Typed Enums & POJOs** — Replace raw Object/Map with strongly-typed classes
   - All enums serialized to AG Grid string format via MapStruct
   - All DTOs (SideBarDef, StatusBarDef, etc.) generated with Jackson annotations
   - Rationale: compile-time safety, IDE autocomplete, JSON schema validation

4. **Dependency Injection via GuicedEE** — Service discovery and boot-time configuration
   - Implement `IPageConfigurator` (boots before TS generation)
   - Implement `IGuiceScanModuleInclusions` (module discovery)
   - Declare via `@Service` (auto-discovered by GuicedEE)

### Code Organization

```
src/main/java/com/jwebmp/plugins/aggridenterprise/
├── AgGridEnterprise<T>                 # Main component
├── options/
│   ├── AgGridEnterpriseOptions         # Root options POJO
│   ├── ChartOptions
│   ├── RangeSelectionOptions
│   ├── SideBarOptions
│   ├── StatusBarOptions
│   ├── RowGroupingOptions
│   ├── ServerSideOptions
│   ├── mapping/                        # DTOs for nested structures
│   │   ├── SideBarDef
│   │   ├── SideBarToolPanelDef
│   │   ├── StatusBarDef
│   │   ├── StatusBarPanelDef
│   │   └── ChartThemeOverrides
│   ├── enums/                          # Enum types
│   │   ├── ChartTheme
│   │   ├── PanelShow
│   │   └── RowModelType
│   ├── find/                           # Find filter options
│   ├── setfilter/                      # Set filter options
│   ├── multifilter/                    # Multi-filter options
│   ├── cellselection/                  # Cell selection options
│   └── advancedfilter/                 # Advanced filter options
├── charts/                             # Chart-related classes
├── AgGridEnterprisePageConfigurator    # IPageConfigurator impl
└── implementations/
    └── AgGridEnterpriseModuleScanInclusion # IGuiceScanModuleInclusions impl
```

### Module-Info Exports

All public APIs exported via `module-info.java`:
- `com.jwebmp.plugins.aggridenterprise` — Main component
- `com.jwebmp.plugins.aggridenterprise.options.*` — All options packages
- `com.jwebmp.plugins.aggridenterprise.charts` — Chart classes
- `com.jwebmp.plugins.aggridenterprise.options.enums` — Enums
- Service provides: `IPageConfigurator`, `IGuiceScanModuleInclusions`
- Opens: packages to Google Guice, Jackson, MapStruct

---

## 4. Behavioral Rules

### onGridReady Logic Injection
Inherited from the community `AgGrid` component, `AgGridEnterprise` allows injecting custom TypeScript logic into the `onGridReady` method.

- **Java Override**: Override `public List<String> onGridReady()` in your `AgGridEnterprise` subclass.
- **Content**: Return a list of TypeScript strings.
- **Enterprise Features**: Common enterprise initializations (like charts) are often handled via specific methods, but `onGridReady` remains the primary hook for custom API interactions.

### API Stability
- **No Breaking Changes** — Maintain backward compatibility with community AgGrid plugin
- **Additive Only** — New features added without removing/renaming existing options
- **Deprecation Path** — If option renamed, old name deprecated (marked @Deprecated) for one major version before removal

### TypeScript Enterprise Methods
`AgGridEnterprise` extends the community component with additional TypeScript methods for enterprise-specific features:
- `applySuppressAggFuncInHeader()`: Applies the `suppressAggFuncInHeader` option to the Grid API.
- `initCrossFilterCharts()`: Initializes any queued cross-filter charts using the Grid API.
- `initRangeCharts()`: Initializes any queued range charts using the Grid API.
- `openChartToolPanel(chartId?: string, panel?: 'settings' | 'data' | 'format')`: Programmatically opens a chart's tool panel.
- `closeChartToolPanel(chartId?: string)`: Programmatically closes a chart's tool panel.

### Naming Conventions

- **Enum Values** — Map to AG Grid JS string format via MapStruct
  - Example: `ChartTheme.AG_DEFAULT` → `"ag-default"`
  - Pattern: JAVA_CONSTANT → "ag-kebab-case"
- **Fluent Methods** — Start with enable/set/show verbs; return `this`
  - Examples: `enableCharts()`, `sideBarFiltersAndColumns()`, `useServerSideRowModel()`
- **Class Names** — Use `*Options` suffix for POJOs, `*Def` for DTOs, `*Theme`/`*Type`/`*Show` for enums

### Error Handling

- **Serialization Errors** — Log via Log4j2; provide context (field name, expected type)
- **Missing npm Dependency** — Page Configurator logs warning if ag-grid-enterprise not installed
- **Module Not Registered** — Log error if AllEnterpriseModule not in TS index
- **Graceful Degradation** — Enterprise features not available if npm dep missing; grid still works with community features

### Logging

- **Framework:** Log4j2 (configured at application level)
- **Pattern:** Use Lombok's `@Log4j2` annotation (not other Lombok logging annotations)
- **Levels:** DEBUG for development, INFO for configuration, WARN for deprecations, ERROR for failures

---

## 5. Technical Rules

### Serialization Contract (Jackson)

All options POJOs decorated with:
```java
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonInclude(Include.NON_NULL)
public class YourOptions {
    // fields directly; no getters needed
}
```

Rationale: Field-level visibility enables private fields; NON_NULL removes clutter from JSON.

### Enum Transformation (MapStruct)

All enums mapped to AG Grid string format:
```java
@Mapper
public interface EnumMapper {
    default String map(ChartTheme value) {
        return value == null ? null : value.getAgGridValue();
    }
}
```

Or use MapStruct's enum mapping directly.

### Annotation Processor Pipeline

pom.xml compiler plugin configuration:
1. Lombok first (processes @Data, @Getter, etc.)
2. MapStruct after (generates mappers using Lombok-processed classes)
3. Lombok-mapstruct-binding included for compatibility

### Module Scanning (GuicedEE)

Implement `IGuiceScanModuleInclusions`:
```java
@Service
public class AgGridEnterpriseModuleScanInclusion 
    implements IGuiceScanModuleInclusions {
    // GuicedEE discovers and registers automatically
}
```

### Page Configurator (Boot)

Implement `IPageConfigurator`:
```java
@Service
public class AgGridEnterprisePageConfigurator 
    implements IPageConfigurator {
    @Override
    public void configure(IGuiceContext context) {
        // Add ag-grid-enterprise npm dep
        // Ensure AllEnterpriseModule registered in TS index
    }
}
```

---

## 6. Forward-Only Change Policy

- **No Refactoring** — Do not rename/restructure existing classes without documented deprecation
- **No Legacy Stubs** — Remove old classes completely; no backward-compat aliases
- **Update All References** — When a change is made, update all inbound links/docs immediately
- **Checklist:** Before removing a class or method, ensure:
  1. Documented in MIGRATION.md (if risky)
  2. All usages in tests updated
  3. All docs updated
  4. All links verified

---

## 7. Document Modularity Policy

- **Host Repo Docs** — All project-specific docs stored outside `rules/` submodule
- **Submodule Content** — Only Rules Repository content in `rules/`; referenced, not copied
- **Topic-First Glossary** — Compose GLOSSARY.md from topic-scoped glossaries; link to topic files instead of duplicating

Artifacts placement:
- `PACT.md` — Root (not in rules/)
- `RULES.md` — Root (not in rules/)
- `GLOSSARY.md` — Root (not in rules/)
- `GUIDES.md` — Root (not in rules/)
- `IMPLEMENTATION.md` — Root (not in rules/)
- `docs/architecture/` — Architecture diagrams (not in rules/)
- `docs/AgGridEnterprise-Guide.md` — Usage guide (not in rules/)

---

## 8. Related Rules Topics

Link to enterprise Rules Repository for applicable topics:

- **Java 25 LTS** — rules/generative/language/java/java-25.rules.md
- **Maven Build Tooling** — rules/generative/language/java/build-tooling.md
- **Fluent API / CRTP** — rules/generative/backend/fluent-api/crtp.rules.md
- **Lombok** — rules/generative/backend/lombok/README.md
- **MapStruct** — rules/generative/backend/mapstruct/README.md
- **Logging (Log4j2)** — rules/generative/backend/logging/README.md
- **JSpecify** — rules/generative/backend/jspecify/README.md
- **JWebMP Core** — rules/generative/frontend/jwebmp/README.md
- **JWebMP AgGrid** — rules/generative/frontend/jwebmp/aggrid/README.md
- **JWebMP Client** — rules/generative/frontend/jwebmp/client/README.md
- **JWebMP TypeScript** — rules/generative/frontend/jwebmp/typescript/README.md
- **Testing (JUnit 5, Jacoco)** — rules/generative/architecture/tdd/README.md
- **Observable Code (Logging, OTel)** — rules/generative/platform/observability/README.md

---

## 9. Version Management

- **Project Version:** 2.0.0-SNAPSHOT (from pom.xml)
- **Parent BOM:** Manages dependency versions
- **Release Process:** Maven release plugin; tag as `v2.0.0` on GitHub
- **Snapshots:** Published to OSSRH (Sonatype)

---

## Checklist for New Contributors

When adding new enterprise features:

- [ ] Create typed POJO for options (extends parent options class if nested)
- [ ] Add enums if needed (map to AG Grid string format)
- [ ] Add MapStruct mapper for transformations
- [ ] Update module-info.java exports
- [ ] Add fluent setter/builder method on AgGridEnterprise<T>
- [ ] Add Jackson serialization test
- [ ] Update GUIDES.md with usage example
- [ ] Update GLOSSARY.md with new terms
- [ ] Update docs/AgGridEnterprise-Guide.md with description
- [ ] Update docs/architecture/ diagrams if applicable
- [ ] Run full Maven build: `mvn clean install`
- [ ] Run tests: `mvn test`

