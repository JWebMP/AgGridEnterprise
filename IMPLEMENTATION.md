# IMPLEMENTATION — Module Structure & Code Layout

**Purpose:** Describes the current module structure, component layout, and code organization of AgGridEnterprise.

**Status:** Phase 2 Complete (Dec 2, 2025) — Modular restructuring with 8 focused feature-area modules using @JsonUnwrapped pattern.

---

## Project Structure

```
aggrid-enterprise/
├── .git/                                       # Git repository
├── .gitmodules                                 # Submodule config
├── .github/                                    # GitHub Actions workflows (optional)
├── .vscode/                                    # VS Code settings
├── docs/
│   ├── AgGridEnterprise-Guide.md               # Usage guide
│   ├── architecture/
│   │   ├── README.md                           # Architecture index
│   │   ├── c4-context.md                       # C4 L1 context diagram
│   │   ├── c4-container.md                     # C4 L2 container diagram
│   │   ├── c4-component-enterprise-api.md     # C4 L3 component diagram
│   │   ├── sequence-page-load.md               # Page init sequence
│   │   ├── sequence-chart-render.md            # Chart rendering sequence
│   │   ├── sequence-server-side-model.md       # Server-side model sequence
│   │   └── erd-enterprise-options.md           # Options ERD
├── rules/                                      # Rules Repository submodule
│   ├── RULES.md                                # Enterprise rules
│   ├── GLOSSARY.md                             # Enterprise glossary
│   ├── generative/                             # Topic guides
│   └── creative/                               # Patterns & practices
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── module-info.java                # Module descriptor
│   │   │   └── com/jwebmp/plugins/aggridenterprise/
│   │   │       ├── AgGridEnterprise.java       # Main component
│   │   │       ├── options/
│   │   │       │   ├── AgGridEnterpriseOptions.java        # Root options (orchestrator, Phase 2)
│   │   │       │   ├── modules/                            # Phase 2: 8 Modular Components
│   │   │       │   │   ├── ChartsOptions.java              # Charts configuration (10 properties)
│   │   │       │   │   ├── ServerSideRowModelOptions.java  # SSRM configuration (17 properties)
│   │   │       │   │   ├── RowGroupingOptions.java         # Row grouping config (22 properties)
│   │   │       │   │   ├── AggregationOptions.java         # Aggregation functions (7 properties)
│   │   │       │   │   ├── PivotingOptions.java            # Pivot mode config (11 properties)
│   │   │       │   │   ├── AdvancedFilteringOptions.java   # Advanced filter config (6 properties)
│   │   │       │   │   ├── SideBarAndStatusBarOptions.java # UI panels config (3 properties)
│   │   │       │   │   └── RangeSelectionOptions.java      # Range selection config (1 property)
│   │   │       │   ├── ToolPanelId.java                    # Phase 2C: Extracted enum
│   │   │       │   ├── GroupTotalRowPosition.java          # Phase 2C: Extracted enum
│   │   │       │   ├── GrandTotalRowPosition.java          # Phase 2C: Extracted enum
│   │   │       │   ├── StickyTotalRowSuppression.java      # Phase 2C: Extracted enum
│   │   │       │   ├── RowGroupingDisplayType.java         # Phase 2C: Extracted enum
│   │   │       │   ├── SuppressGroupChangesColumnVisibilityMode.java  # Phase 2C: Extracted enum
│   │   │       │   ├── PivotRowTotalsPosition.java         # Phase 2C: Extracted enum
│   │   │       │   ├── mapping/
│   │   │       │   │   ├── SideBarDef.java
│   │   │       │   │   ├── SideBarToolPanelDef.java
│   │   │       │   │   ├── StatusBarDef.java
│   │   │       │   │   ├── StatusBarPanelDef.java
│   │   │       │   │   └── ChartThemeOverrides.java
│   │   │       │   ├── enums/
│   │   │       │   │   ├── ChartTheme.java
│   │   │       │   │   ├── PanelShow.java
│   │   │       │   │   └── RowModelType.java
│   │   │       │   ├── find/                   # Find/search filters
│   │   │       │   ├── setfilter/              # Set filter options
│   │   │       │   ├── multifilter/            # Multi-filter options
│   │   │       │   ├── cellselection/          # Cell selection options
│   │   │       │   └── advancedfilter/         # Advanced filter options
│   │   │       ├── charts/                     # Chart-specific classes
│   │   │       ├── AgGridEnterprisePageConfigurator.java  # IPageConfigurator
│   │   │       └── implementations/
│   │   │           └── AgGridEnterpriseModuleScanInclusion.java  # Scanner
│   │   └── resources/
│   │       └── META-INF/
│   │           └── services/                   # Service providers
│   │               ├── com.jwebmp.core.services.IPageConfigurator
│   │               └── com.guicedee.client.services.config.IGuiceScanModuleInclusions
│   └── test/
│       ├── java/
│       │   └── com/jwebmp/plugins/aggridenterprise/
│       │       ├── AgGridEnterpriseTest.java
│       │       ├── options/
│       │       │   ├── AgGridEnterpriseOptionsTest.java
│       │       │   ├── ChartOptionsTest.java
│       │       │   └── ... (other option tests)
│       │       └── (other test classes)
│       └── resources/
│           └── (test configuration files)
├── target/                                     # Build output (generated)
├── .gitignore
├── LICENSE                                     # Apache 2.0
├── README.md                                   # Project readme
├── PACT.md                                     # Product architecture
├── RULES.md                                    # Project rules
├── GLOSSARY.md                                 # Terminology
├── GUIDES.md                                   # How-to guides
├── IMPLEMENTATION.md                           # This file
├── AgGridEnterprise-Plan.md                    # Execution plan
├── PROMPT_ADOPT_EXISTING_PROJECT.md            # Adoption template
├── pom.xml                                     # Maven configuration
└── flatter.pom                                 # Flattened POM (for IDE)
```

---

## Core Components

### 1. AgGridEnterprise Component

**File:** `src/main/java/com/jwebmp/plugins/aggridenterprise/AgGridEnterprise.java`

**Purpose:** Main component class; extends AgGrid from community plugin; provides fluent enterprise API.

**Key Methods:**
- `enableCharts()` — Enable charts feature
- `enableRangeSelection()` — Enable range selection
- `sideBarFiltersAndColumns()` — Preset: filters + columns side bar
- `showRowGroupPanel()` — Show row grouping panel
- `useServerSideRowModel()` — Enable server-side row model
- `getOptions()` — Access AgGridEnterpriseOptions for detailed config

**Type Safety:** Generic `<T extends AgGridEnterprise<T>>` enables self-returning fluent methods (CRTP).

### 2. Options Classes

#### AgGridEnterpriseOptions
**File:** `src/main/java/.../options/AgGridEnterpriseOptions.java`

Extends `AgGridOptions` (from community plugin). Root POJO holding all enterprise configuration.

├── README.md                                   # Project readme
├── PACT.md                                     # Product architecture
├── RULES.md                                    # Project rules
├── GLOSSARY.md                                 # Terminology
├── GUIDES.md                                   # How-to guides
├── IMPLEMENTATION.md                           # This file
├── PHASE_2_COMPLETE.md                         # Phase 2 completion summary
├── PHASE_2_MODULAR_RESTRUCTURING.md            # Phase 2 detailed plan & results
├── AgGridEnterprise-Plan.md                    # Execution plan
├── PROMPT_ADOPT_EXISTING_PROJECT.md            # Adoption template
├── pom.xml                                     # Maven configuration
└── flatter.pom                                 # Flattened POM (for IDE)
```

---

## Core Components

### 1. AgGridEnterprise Component

**File:** `src/main/java/com/jwebmp/plugins/aggridenterprise/AgGridEnterprise.java`

**Purpose:** Main component class; extends AgGrid from community plugin; provides fluent enterprise API.

**Key Methods:**
- `enableCharts()` — Enable charts feature
- `enableRangeSelection()` — Enable range selection
- `sideBarFiltersAndColumns()` — Preset: filters + columns side bar
- `showRowGroupPanel()` — Show row grouping panel
- `useServerSideRowModel()` — Enable server-side row model
- `getOptions()` — Access AgGridEnterpriseOptions for detailed config

**Type Safety:** Generic `<T extends AgGridEnterprise<T>>` enables self-returning fluent methods (CRTP).

### 2. Options Architecture (Phase 2)

#### AgGridEnterpriseOptions (Orchestrator)
**File:** `src/main/java/.../options/AgGridEnterpriseOptions.java`

**Status:** Phase 2 restructured as orchestrator class (1,433 lines, reduced from 2,168)

Extends `AgGridOptions` (from community plugin). Root POJO orchestrating 8 modular components.

**Phase 2 Structure:**
- 8 @JsonUnwrapped module fields organizing 83 properties into feature areas
- 8 convenience accessor methods for fluent configuration
- Pattern: @JsonUnwrapped ensures 100% JSON backward compatible

**Accessor Methods:**
```java
// Convenience methods to access modules
public ChartsOptions<?> configureCharts() { return charts; }
public ServerSideRowModelOptions<?> configureServerSideRowModel() { return ssrm; }
public RowGroupingOptions<?> configureRowGrouping() { return grouping; }
public AggregationOptions<?> configureAggregation() { return aggregation; }
public PivotingOptions<?> configurePivoting() { return pivoting; }
public AdvancedFilteringOptions<?> configureAdvancedFilter() { return advancedFilter; }
public SideBarAndStatusBarOptions<?> configureSideBarAndStatusBar() { return sideBarStatusBar; }
public RangeSelectionOptions<?> configureRangeSelection() { return rangeSelection; }
```

**Usage:**
```java
// Type-safe, modular configuration
options.configureCharts()
    .setEnableCharts(true)
    .setChartThemes(Arrays.asList("ag-default"))
    .parent();

options.configureRowGrouping()
    .setGroupAllowUnbalanced(false)
    .parent();
```

### 2B. Phase 2 Modular Components

Located in `src/main/java/.../options/modules/`

#### ChartsOptions
**File:** `src/main/java/.../options/modules/ChartsOptions.java`  
**Properties:** 10  
**Lines:** 220  
**CRTP Pattern:** `<J extends ChartsOptions<J>>`

Fields:
- `enableCharts: Boolean`
- `chartThemes: List<String>`
- `chartThemeOverrides: Object`
- `chartToolPanelsDef: ChartToolPanelsDef`
- `chartGroupsDef: ChartGroupsDef`
- `suppressChartToolPanelsButton: Boolean`
- `getChartToolbarItems: Object`
- `createChartContainer: Object`
- `customChartThemes: Object`
- `chartMenuItems: Object`

#### ServerSideRowModelOptions
**File:** `src/main/java/.../options/modules/ServerSideRowModelOptions.java`  
**Properties:** 17  
**Lines:** 320  

Manages virtual scrolling and lazy loading through server-side callbacks.

#### RowGroupingOptions
**File:** `src/main/java/.../options/modules/RowGroupingOptions.java`  
**Properties:** 22  
**Lines:** 370  

Largest module; handles grouping, hierarchy, and display options.

#### AggregationOptions
**File:** `src/main/java/.../options/modules/AggregationOptions.java`  
**Properties:** 7  
**Lines:** 140  

Aggregation functions and filtering behavior.

#### PivotingOptions
**File:** `src/main/java/.../options/modules/PivotingOptions.java`  
**Properties:** 11  
**Lines:** 230  

Pivot mode and cross-tabulation configuration.

#### AdvancedFilteringOptions
**File:** `src/main/java/.../options/modules/AdvancedFilteringOptions.java`  
**Properties:** 6  
**Lines:** 130  

Advanced filter builder and cell selection.

#### SideBarAndStatusBarOptions
**File:** `src/main/java/.../options/modules/SideBarAndStatusBarOptions.java`  
**Properties:** 3  
**Lines:** 100  

UI panel configuration for sidebar and status bar.

#### RangeSelectionOptions
**File:** `src/main/java/.../options/modules/RangeSelectionOptions.java`  
**Properties:** 1  
**Lines:** 80  

Range selection and clipboard integration.

### 2C. Phase 2C Extracted Enums

Located in `src/main/java/.../options/`

All enums follow consistent pattern with JSON string values:

- **ToolPanelId.java** — Available tool panel identifiers
- **GroupTotalRowPosition.java** — Total row positioning for groups
- **GrandTotalRowPosition.java** — Grand total row positioning
- **StickyTotalRowSuppression.java** — Sticky row behavior control
- **RowGroupingDisplayType.java** — Row grouping display mode
- **SuppressGroupChangesColumnVisibilityMode.java** — Column visibility on group changes
- **PivotRowTotalsPosition.java** — Pivot row totals positioning

### 2D. Serialization (Phase 2)

**Pattern:** @JsonUnwrapped composition

```java
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgGridEnterpriseOptions ... {
    @JsonUnwrapped
    private ChartsOptions<?> charts = new ChartsOptions<>();
    
    @JsonUnwrapped
    private ServerSideRowModelOptions<?> ssrm = new ServerSideRowModelOptions<>();
    
    // ... 6 more @JsonUnwrapped modules
}
```

**Result:** All 83 properties serialize flat to JSON (identical to monolithic structure), but code is organized into 8 focused modules.

**Backward Compatibility:** ✅ 100% JSON output unchanged; old consuming code works without modification.

### 3. Enums (Legacy + Phase 2)

All enums in `src/main/java/.../options/enums/`

#### ChartTheme
```java
public enum ChartTheme {
    AG_DEFAULT("ag-default"),
    AG_VIVID("ag-vivid"),
    AG_MATERIAL("ag-material"),
    AG_SHEETS("ag-sheets"),
    POLYCHROMA("polychroma");
}
```

Implements `@JsonValue` for Jackson serialization to string.

#### PanelShow
```java
public enum PanelShow {
    ALWAYS("always"),
    ONLY_WHEN_GROUPING("onlyWhenGrouping"),
    NEVER("never");
}
```

#### RowModelType
```java
public enum RowModelType {
    CLIENT_SIDE("clientSide"),
    SERVER_SIDE("serverSide"),
    VIEWPORT("viewport"),
    INFINITE("infinite");
}
```

### 4. Data Transfer Objects (DTOs)

Located in `src/main/java/.../options/mapping/`

#### SideBarDef
```java
@JsonAutoDetect
@JsonInclude(NON_NULL)
public class SideBarDef {
    private List<SideBarToolPanelDef> toolPanels;
    private String position;  // "left" or "right"
}
```

#### SideBarToolPanelDef
```java
@JsonAutoDetect
@JsonInclude(NON_NULL)
public class SideBarToolPanelDef {
    private String id;              // "columns", "filters"
    private String labelKey;        // i18n key
    private String labelDefault;    // fallback label
    private String iconKey;         // icon identifier
}
```

#### StatusBarDef
```java
@JsonAutoDetect
@JsonInclude(NON_NULL)
public class StatusBarDef {
    private List<StatusBarPanelDef> statusPanels;
}
```

#### StatusBarPanelDef
```java
@JsonAutoDetect
@JsonInclude(NON_NULL)
public class StatusBarPanelDef {
    private String statusPanelId;    // component name
    private String statusPanelClass; // CSS class
    private Object statusPanelComp;  // component reference
    private String align;             // "left", "center", "right"
}
```

All DTOs include fluent setters returning `this`.

### 5. Boot-Time Wiring

#### AgGridEnterprisePageConfigurator
**File:** `src/main/java/.../AgGridEnterprisePageConfigurator.java`

Implements `IPageConfigurator` from JWebMP.

**Purpose:** Boot-time configuration to prepare enterprise features.

**Actions in configure():**
1. Add `ag-grid-enterprise` npm dependency to generated `package.json`
2. Ensure `AllEnterpriseModule` registered in generated TypeScript index
3. Log debug info for diagnostics

**Service Registration:** Declared via `@Service`; auto-discovered by GuicedEE.

#### AgGridEnterpriseModuleScanInclusion
**File:** `src/main/java/.../implementations/AgGridEnterpriseModuleScanInclusion.java`

Implements `IGuiceScanModuleInclusions` from GuicedEE.

**Purpose:** Enable classpath scanning and module discovery.

**Service Registration:** Declared via `@Service`; auto-discovered by GuicedEE.

### 6. Service Declarations

**File:** `src/main/resources/META-INF/services/`

Java Service Provider Interface files:

- `com.jwebmp.core.services.IPageConfigurator` — Lists `AgGridEnterprisePageConfigurator`
- `com.guicedee.client.services.config.IGuiceScanModuleInclusions` — Lists `AgGridEnterpriseModuleScanInclusion`

These enable auto-discovery by JWebMP and GuicedEE.

### 7. Module Declaration

**File:** `src/main/java/module-info.java`

JPMS module descriptor.

**Exports:**
```java
exports com.jwebmp.plugins.aggridenterprise;
exports com.jwebmp.plugins.aggridenterprise.options;
exports com.jwebmp.plugins.aggridenterprise.options.mapping;
exports com.jwebmp.plugins.aggridenterprise.options.enums;
exports com.jwebmp.plugins.aggridenterprise.charts;
// ... other exports
```

**Requires:**
```java
requires transitive com.jwebmp.plugins.aggrid;
requires com.jwebmp.core;
requires com.guicedee.guicedinjection;
// ... other requires
```

**Opens (for reflection):**
```java
opens com.jwebmp.plugins.aggridenterprise to 
    com.google.guice, 
    com.fasterxml.jackson.databind, 
    com.jwebmp.core, 
    org.mapstruct;
```

---

## Build Configuration

### pom.xml

**Parent:** `com.jwebmp:parent:2.0.0-SNAPSHOT`

**Key Sections:**

**Dependencies:**
```xml
<dependency>
    <groupId>com.jwebmp.core</groupId>
    <artifactId>jwebmp-core</artifactId>
</dependency>
<dependency>
    <groupId>com.jwebmp.plugins</groupId>
    <artifactId>aggrid</artifactId>
</dependency>
<dependency>
    <groupId>com.guicedee.services</groupId>
    <artifactId>mapstruct</artifactId>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <scope>provided</scope>
</dependency>
```

**Annotation Processor Pipeline:**
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <annotationProcessorPaths>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
            </path>
            <path>
                <groupId>org.mapstruct</groupId>
                <artifactId>mapstruct-processor</artifactId>
            </path>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok-mapstruct-binding</artifactId>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

### Build Profiles

None defined in base plugin; parent pom may define profiles for aggregator builds.

---

## Testing Structure

**Test Base:** `src/test/java/com/jwebmp/plugins/aggridenterprise/`

**Test Categories:**

1. **Unit Tests** — Individual class serialization, enum mapping, etc.
2. **Integration Tests** — Page Configurator boot, module discovery
3. **Smoke Tests** — Full grid initialization with enterprise options

**Example Test:**

```java
@Test
public void testChartOptionsJsonSerialization() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(Include.NON_NULL);
    
    AgGridEnterpriseOptions opts = new AgGridEnterpriseOptions();
    opts.getChartOptions()
        .setEnableCharts(true)
        .setChartThemes(List.of(ChartTheme.AG_DEFAULT));
    
    String json = mapper.writeValueAsString(opts);
    
    assertThat(json).contains("\"enableCharts\":true");
    assertThat(json).contains("\"ag-default\"");
}
```

---

## Dependency Graph

```
aggrid-enterprise
├── jwebmp-core                          (Component model)
│   ├── jwebmp-base                      (Base utilities)
│   └── jwebmp-angular                   (Angular integration)
├── aggrid                               (Community AgGrid plugin)
│   └── ag-grid-community (npm)
├── typescript-client                    (TypeScript support)
├── guicedee-guicedinjection             (Dependency injection)
├── jackson-databind                     (JSON serialization)
├── mapstruct                            (Object mapping)
└── lombok                               (Annotation processor)

Frontend (generated):
├── ag-grid-angular                      (Angular component wrapper)
├── ag-grid-enterprise (npm)             (Enterprise features)
├── AllEnterpriseModule                  (Angular module)
└── Angular runtime
```

---

## Build & Release

### Local Build

```bash
mvn clean install
```

Produces:
- JAR: `target/aggrid-enterprise-${version}.jar`
- Test Results: `target/surefire-reports/`
- Coverage: `target/site/jacoco/`

### CI/CD

(Optional) GitHub Actions workflow for:
- Compile & test on PR
- Publish snapshots to OSSRH
- Release to Maven Central

### Version Management

- **Current Version:** 2.0.0-SNAPSHOT (in pom.xml)
- **Release Process:** Maven release plugin; tag as `v2.0.0`
- **Publishing:** OSSRH/Maven Central via parent pom config

---

## Design Patterns & Practices

### CRTP (Curiously Recurring Template Pattern)
All fluent builders use generic self-type for type-safe method chaining:
```java
public class AgGridEnterprise<T extends AgGridEnterprise<T>> {
    @SuppressWarnings("unchecked")
    public T enableCharts() {
        // configure
        return (T) this;
    }
}
```

### Composition over Inheritance
Feature options composed into parent:
```java
public class AgGridEnterpriseOptions extends AgGridOptions {
    private ChartOptions chartOptions;
    private RangeSelectionOptions rangeOptions;
    // ... other features
}
```

### Jackson Serialization
All options use field-level visibility and null suppression:
```java
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonInclude(Include.NON_NULL)
public class YourOptions { ... }
```

### Service Discovery (GuicedEE)
All wiring classes use `@Service` annotation for auto-discovery:
```java
@Service
public class AgGridEnterprisePageConfigurator implements IPageConfigurator { ... }
```

---

## References

- **[PACT.md](PACT.md)** — Product architecture
- **[RULES.md](RULES.md)** — Project rules
- **[GLOSSARY.md](GLOSSARY.md)** — Terminology
- **[GUIDES.md](GUIDES.md)** — How-to guides
- **[enterprise-features.rules.md](enterprise-features.rules.md)** — AG Grid v34.2.0 features and modules
- **[docs/architecture/README.md](docs/architecture/README.md)** — Architecture diagrams
- **[docs/AgGridEnterprise-Guide.md](docs/AgGridEnterprise-Guide.md)** — Usage guide
- **[pom.xml](pom.xml)** — Maven configuration

