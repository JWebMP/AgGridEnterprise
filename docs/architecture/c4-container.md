# C4 Container Diagram — AgGridEnterprise

Containers/major subsystems within the AgGridEnterprise plugin.

```mermaid
graph TB
    subgraph Java["Java Module (Server/Build-Time)"]
        CoreAPI["<b>Enterprise Options API</b><br/>AgGridEnterpriseOptions<br/>Fluent setters, typed DTOs<br/>Charts, Range, SideBar, StatusBar"]
        Enums["<b>Enums & Models</b><br/>RowModelType, PanelShow<br/>ChartTheme, SideBarDef<br/>StatusBarDef, etc."]
        ConfigProv["<b>Page Configurator</b><br/>AgGridEnterprisePageConfigurator<br/>Boots enterprise npm deps<br/>Registers AllEnterpriseModule"]
        ModuleScan["<b>Module Scanner</b><br/>AgGridEnterpriseModuleScanInclusion<br/>GuicedEE discovery<br/>@Service registration"]
        Mappers["<b>MapStruct Mappers</b><br/>Domain ↔ JSON contracts<br/>Options transformation"]
    end
    
    subgraph TSGen["TypeScript Code Generation"]
        TSIndex["<b>TS Index Generator</b><br/>npm dependency: ag-grid-enterprise<br/>ModuleRegistry.register<br/>AllEnterpriseModule"]
        ClientDeps["<b>Client Dependencies</b><br/>Package.json updates<br/>ag-grid-enterprise version"]
    end
    
    subgraph AngularRT["Angular Runtime"]
        GridComp["<b>ag-grid-angular</b><br/>Component host<br/>Input bindings"]
        ModuleReg["<b>Module Registry</b><br/>AllEnterpriseModule loaded<br/>Provider plugins, themes"]
        DataFlow["<b>Data & Config Flow</b><br/>Options → JSON<br/>Events ← Grid"]
    end
    
    subgraph Browser["Browser / AG Grid JS"]
        GridEngine["<b>AG Grid Engine</b><br/>Enterprise API active<br/>Charts, Range, Groups, SSM"]
        ChartEngine["<b>Chart Engine</b><br/>Data aggregation<br/>Theme rendering"]
    end
    
    CoreAPI --> Mappers
    Enums --> CoreAPI
    ConfigProv -.->|discovers| ModuleScan
    ConfigProv -->|adds npm dep| ClientDeps
    ConfigProv -->|registers module| TSIndex
    
    TSIndex -->|generates TS| TSGen
    ClientDeps -->|installs| Browser
    
    Mappers -->|transforms| GridComp
    CoreAPI -->|options via| GridComp
    GridComp -->|binds to| GridEngine
    
    ModuleReg -->|provides| GridEngine
    ModuleReg -->|theme config| ChartEngine
    
    GridEngine -->|renders| Browser
    ChartEngine -->|renders charts| Browser
```

## Container Responsibilities

### Java Module (Server/Build-Time)

**Enterprise Options API**
- Typed setters for enterprise-only features
- Fluent builder pattern (CRTP): `new AgGridEnterprise<T>().enableCharts().sideBarFiltersAndColumns()`
- Extends `AgGridOptions` from community plugin

**Enums & Models**
- `RowModelType` — clientSide, serverSide, viewport, infinite
- `PanelShow` — rowGroupPanelShow, pivotPanelShow (always/onlyWhenGrouping/never)
- `ChartTheme` — ag-default, ag-vivid, etc.
- POJOs — `SideBarDef`, `StatusBarDef`, `SideBarToolPanelDef`, `StatusBarPanelDef`

**Page Configurator**
- IPageConfigurator implementation
- Boots enterprise npm dependency (`ag-grid-enterprise`)
- Registers `AllEnterpriseModule` with Angular ModuleRegistry
- Declared via Java @Service; auto-discovered by GuicedEE

**Module Scanner**
- GuicedEE `IGuiceScanModuleInclusions` implementation
- Enables DI and classpath scanning for enterprise features

**MapStruct Mappers**
- Transforms domain POJOs → AG Grid JSON contracts
- Handles nested objects (Charts config, SideBar panels, etc.)
- Ensures serialization correctness

### TypeScript Code Generation

**TS Index Generator**
- Auto-generates `index.ts` that imports and registers `AllEnterpriseModule`
- Pulled from `ag-grid-enterprise` npm package
- Executed at build time by Page Configurator

**Client Dependencies**
- Adds `ag-grid-enterprise` to `package.json`
- Managed by ConfigProv; installed at frontend build time

### Angular Runtime

**ag-grid-angular Component**
- Receives `AgGridEnterpriseOptions` as input bindings
- Two-way data flow: options → grid, events ← grid

**Module Registry**
- Loads `AllEnterpriseModule` from TS Index
- Provider plugins register Charts, Range, Row Groups, etc.
- Theme configuration applied

**Data & Config Flow**
- Options serialized via Jackson → JSON
- Grid events emitted via RxJS observables

### Browser / AG Grid JS

**AG Grid Engine**
- Enterprise API active (Charts, Range Selection, Row Grouping, Server-Side Model)
- Deserializes JSON options
- Handles grid lifecycle

**Chart Engine**
- Aggregates row data per chart config
- Renders themed charts via AG Grid Charts library

## Key Data Flows

1. **Options Path** — Java EnterpriseOptions → MapStruct → JSON → Angular binding → AG Grid JS
2. **Module Path** — Page Configurator (boot) → npm dep added → TS Index generated → ModuleRegistry → Angular loads enterprise features
3. **Event Path** — AG Grid JS events → Angular observable → Java callback/listener

