# Sequence Diagram — Grid Initialization & Page Load

Flow from Java bean declaration through Angular bootstrap to grid rendering.

```mermaid
sequenceDiagram
    actor Dev as Developer
    participant Java as Java<br/>AgGridEnterprise
    participant Conf as PageConfigurator<br/>Boot
    participant GuicedEE as GuicedEE<br/>Module Discovery
    participant TS as TypeScript<br/>Code Gen
    participant Angular as Angular<br/>Runtime
    participant Registry as ModuleRegistry<br/>AllEnterpriseModule
    participant Grid as ag-grid-angular<br/>Component
    participant Browser as Browser<br/>AG Grid JS Engine

    Dev->>Java: extends AgGridEnterprise<br/>enableCharts()<br/>sideBarFiltersAndColumns()
    Note over Java: Enterprise options configured
    
    Java->>Conf: PageConfigurator auto-discovered<br/>(IPageConfigurator provider)
    activate Conf
    
    Conf->>GuicedEE: Scan for module inclusions<br/>IGuiceScanModuleInclusions
    activate GuicedEE
    GuicedEE-->>Conf: AgGridEnterpriseModuleScanInclusion
    deactivate GuicedEE
    
    Conf->>TS: Add npm dependency<br/>ag-grid-enterprise
    activate TS
    Conf->>TS: Generate TS index file<br/>register AllEnterpriseModule
    TS->>Angular: npm install ag-grid-enterprise
    TS-->>Conf: index.ts generated<br/>ModuleRegistry.register(AllEnterpriseModule)
    deactivate TS
    deactivate Conf
    
    Angular->>Registry: Module bootstrap<br/>AllEnterpriseModule loaded
    activate Registry
    Registry->>Browser: Register enterprise providers<br/>Charts, Range, Row Groups, etc.
    deactivate Registry
    
    Angular->>Grid: Instantiate ag-grid-angular
    activate Grid
    
    Java->>Grid: Pass AgGridEnterpriseOptions<br/>as input bindings
    Note over Java,Grid: Options serialized to JSON
    
    Grid->>Browser: Deserialize options<br/>gridOptions parameter
    activate Browser
    Browser->>Browser: Initialize AG Grid Engine<br/>with enterprise features
    Browser-->>Grid: Grid ready
    deactivate Browser
    
    Grid-->>Angular: Template rendered
    deactivate Grid
    
    Angular-->>Dev: Interactive grid displayed<br/>Charts, Range, SideBar ready
    Note over Dev,Browser: User can interact with<br/>enterprise features
```

## Sequence Flow Explanation

### 1. Development Phase
Developer writes Java code:
```java
public class MyGrid extends AgGridEnterprise<MyGrid> {
    public MyGrid() {
        enableCharts();
        enableRangeSelection();
        sideBarFiltersAndColumns();
    }
}
```

### 2. Boot Phase (PageConfigurator)
When JWebMP application starts:
- GuicedEE discovers `AgGridEnterprisePageConfigurator` (implements `IPageConfigurator`)
- Configurator is invoked before Angular code generation
- Action: Adds `ag-grid-enterprise` npm dependency to `package.json`
- Action: Ensures `AllEnterpriseModule` is registered in generated TS index

### 3. Code Generation Phase
JWebMP generates TypeScript:
- Includes import and registration of `AllEnterpriseModule` from `ag-grid-enterprise`
- Generated index.ts:
  ```typescript
  import { AllEnterpriseModule } from 'ag-grid-enterprise';
  ModuleRegistry.registerModules([AllEnterpriseModule]);
  ```

### 4. Angular Bootstrap Phase
Angular app starts:
- Module Registry loads `AllEnterpriseModule`
- Enterprise providers registered (Charts, Range Selection, Row Grouping, etc.)
- Theme plugins loaded

### 5. Grid Instantiation Phase
Component tree renders:
- `AgGridEnterpriseOptions` serialized to JSON by Jackson
- Passed to `ag-grid-angular` component as input bindings
- Angular sends JSON to AG Grid JS engine

### 6. Runtime Phase (Browser)
AG Grid JS engine:
- Deserializes options JSON
- Activates enterprise API
- Charts engine initialized
- Range selection enabled
- Row grouping available
- Server-Side Row Model ready if configured

### 7. Interaction
User interacts with fully functional enterprise grid:
- Creates charts from data
- Selects cell ranges
- Groups rows
- Filters via enterprise filters
- Uses side bar

---

## Error Scenarios

### Missing npm dependency
- If `ag-grid-enterprise` not installed: enterprise features not available (graceful degradation)
- Configurator should log warning

### Module not registered
- If `AllEnterpriseModule` not in TS index: feature providers not available
- Grid still renders with community features

### JSON serialization error
- Malformed options → Jackson errors logged at build time
- Recommend schema validation in tests

