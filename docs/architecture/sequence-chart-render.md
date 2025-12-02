# Sequence Diagram — Enterprise Charts Rendering

Flow from enabling charts to chart display in the grid.

```mermaid
sequenceDiagram
    actor User as User/Developer
    participant Grid as AgGridEnterprise<br/>Component
    participant Opts as ChartOptions<br/>POJO
    participant Mapper as ChartMapper<br/>MapStruct
    participant Jackson as Jackson<br/>Serializer
    participant Angular as Angular<br/>Component Tree
    participant GridJS as ag-grid-angular<br/>Component
    participant Engine as AG Grid<br/>Enterprise Engine
    participant ChartEngine as Chart Engine<br/>ag-charts-enterprise
    participant Browser as DOM<br/>Canvas/SVG

    User->>Grid: enableCharts()
    activate Grid
    Grid->>Opts: Create ChartOptions<br/>set themes, toolbar, etc.
    activate Opts
    
    Grid->>Mapper: Map domain→JSON contract
    activate Mapper
    Mapper->>Opts: Read chartThemes<br/>chartThemeOverrides<br/>chartToolPanelsDef
    Mapper->>Jackson: Prepare for serialization
    deactivate Mapper
    
    Grid->>Jackson: serialize ChartOptions
    activate Jackson
    Jackson->>Jackson: @JsonAutoDetect field detection<br/>@JsonInclude NON_NULL filtering
    Jackson-->>Grid: JSON object
    {
      "enableCharts": true,
      "chartThemes": ["ag-default"],
      "chartToolPanelsDef": {...},
      "suppressChartToolPanelsOverlay": false
    }
    deactivate Jackson
    deactivate Opts
    deactivate Grid
    
    Angular->>GridJS: Bind options to component<br/>[gridOptions]="chartOptions"
    activate GridJS
    
    GridJS->>Engine: Initialize AG Grid<br/>with chart options
    activate Engine
    Engine->>ChartEngine: Register chart provider<br/>AllEnterpriseModule active
    activate ChartEngine
    ChartEngine-->>Engine: Charts ready
    deactivate ChartEngine
    
    Engine-->>GridJS: Grid initialized
    deactivate Engine
    
    Note over User,GridJS: User loads row data
    
    User->>Grid: setRowData(List of data)
    Grid->>GridJS: Update [rowData]
    GridJS->>Engine: rowData changed event
    
    activate Engine
    Engine->>ChartEngine: Trigger chart creation<br/>based on chartThemes config
    activate ChartEngine
    
    ChartEngine->>ChartEngine: Aggregate row data<br/>per column/series config
    ChartEngine->>ChartEngine: Apply theme<br/>(ag-default colors, fonts, etc.)
    
    ChartEngine->>Browser: Render chart to DOM<br/>Canvas/SVG elements
    activate Browser
    Browser-->>ChartEngine: Chart rendered
    deactivate Browser
    
    ChartEngine-->>Engine: Chart display complete
    deactivate ChartEngine
    deactivate Engine
    
    Engine->>GridJS: Tool panel available<br/>toggle charts, themes, etc.
    GridJS-->>User: Interactive chart displayed<br/>in Grid side panel or modal
    deactivate GridJS
    
    Note over User,Browser: User can interact with chart<br/>zoom, pan, export, theme change
```

## Charts Rendering Flow Details

### 1. Enable Charts (Java Side)
Developer calls on AgGridEnterprise:
```java
enableCharts();
// or with custom config:
ChartOptions chartOpts = new ChartOptions()
    .setChartThemes(List.of(ChartTheme.AG_DEFAULT, ChartTheme.AG_VIVID))
    .setChartThemeOverrides(Map.of(...));
getOptions().setChartOptions(chartOpts);
```

### 2. MapStruct Transformation
ChartMapper converts domain model → AG Grid contract:
```java
@Mapper
public interface ChartMapper {
    // Maps List<ChartTheme> → List<String> ("ag-default", "ag-vivid")
    @Mapping(target = "chartThemes", source = "chartThemes")
    Map<String, Object> toChartConfig(ChartOptions opts);
}
```

### 3. Jackson Serialization
Configured with:
- `@JsonAutoDetect(fieldVisibility = Visibility.ANY)` — detects all fields
- `@JsonInclude(Include.NON_NULL)` — skips null values for clean JSON
- Result: compact JSON sent to frontend

### 4. Angular Binding
Grid component receives options:
```typescript
<ag-grid-angular 
  [gridOptions]="gridOptions"
  [rowData]="rowData">
</ag-grid-angular>
```

### 5. AG Grid Engine (Browser)
AG Grid JS deserializes options:
- Recognizes `enableCharts: true`
- Loads chart toolbar and tool panel
- Registers event handlers

### 6. Chart Creation Trigger
When rowData provided:
- User creates chart from grid menu/toolbar
- Chart Engine aggregates data per chart config
- Applies theme (colors, fonts, axes)
- Renders to Canvas or SVG

### 7. User Interaction
User can:
- Change chart type (bar, line, scatter, etc.)
- Switch theme (ag-vivid, ag-material, etc.)
- Export chart image
- Interact with legend, axes, data series

## Configuration Example

```java
public class SalesGrid extends AgGridEnterprise<SalesGrid> {
    public SalesGrid() {
        enableCharts();
        
        ChartOptions chartOpts = new ChartOptions();
        chartOpts.setChartThemes(List.of(
            ChartTheme.AG_DEFAULT,
            ChartTheme.AG_VIVID
        ));
        
        // Custom toolbar items
        chartOpts.setChartToolbarItems(List.of(
            "chartDownload", "chartOpenChartToolPanel"
        ));
        
        getOptions().setChartOptions(chartOpts);
        
        // Define columns for data
        getOptions().setColumnDefs(List.of(
            new AgGridColumnDef<>("country"),
            new AgGridColumnDef<>("sales"),
            new AgGridColumnDef<>("year")
        ));
    }
}
```

## ChartTheme Mapping

| Java Enum | AG Grid String |
|-----------|---|
| `ChartTheme.AG_DEFAULT` | `"ag-default"` |
| `ChartTheme.AG_VIVID` | `"ag-vivid"` |
| `ChartTheme.AG_MATERIAL` | `"ag-material"` |
| `ChartTheme.AG_SHEETS` | `"ag-sheets"` |
| `ChartTheme.POLYCHROMA` | `"polychroma"` |

