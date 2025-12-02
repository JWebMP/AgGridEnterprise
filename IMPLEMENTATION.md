# IMPLEMENTATION — Module Structure & Code Layout

**Purpose:** Describes the current module structure, component layout, and code organization of AgGridEnterprise.

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
│   │   │       │   ├── AgGridEnterpriseOptions.java        # Root options
│   │   │       │   ├── ChartOptions.java
│   │   │       │   ├── RangeSelectionOptions.java
│   │   │       │   ├── SideBarOptions.java
│   │   │       │   ├── StatusBarOptions.java
│   │   │       │   ├── RowGroupingOptions.java
│   │   │       │   ├── ServerSideOptions.java
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

**Fields:**
- `enableCharts: Boolean`
- `chartThemes: List<ChartTheme>`
- `enableRangeSelection: Boolean`
- `sideBar: SideBarDef`
- `statusBar: StatusBarDef`
- `rowGroupPanelShow: PanelShow`
- `pivotPanelShow: PanelShow`
- `rowModelType: RowModelType`
- `serverSideStoreType: String`
- `cacheBlockSize: Integer`
- `maxBlocksInCache: Integer`
- `purgeClosedRowNodes: Boolean`
- ... (other enterprise fields)

**Serialization:** `@JsonAutoDetect(fieldVisibility = FIELD)` + `@JsonInclude(NON_NULL)`

#### Feature-Specific Options

- **ChartOptions** — Charts config (themes, toolbar, tool panel)
- **RangeSelectionOptions** — Range selection config
- **SideBarOptions** — Side bar config
- **StatusBarOptions** — Status bar config
- **RowGroupingOptions** — Row grouping config
- **ServerSideOptions** — Server-side row model config

Each can be instantiated standalone or via parent getters:

```java
grid.getOptions().getChartOptions().enableCharts();
```

### 3. Enums

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

