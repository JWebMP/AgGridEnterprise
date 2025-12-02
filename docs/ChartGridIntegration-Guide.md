# AG Grid + AG Charts Enterprise Integration Guide

## Overview

The JWebMP AG Grid Enterprise plugin now includes comprehensive support for linking AG Grid data to AG Charts Enterprise, enabling:

- **Data Synchronization**: Charts automatically consume grid data
- **Cross-Filtering**: Selections in charts filter grid rows
- **Selection Sync**: Grid selections highlight chart data points
- **Chart Registry**: Centralized management of chart instances

## Key Components

### 1. IChartDataBridge<T>
Bridge interface for synchronizing data between grid and charts.

**Responsibilities:**
- Provide grid row data to charts
- Handle grid data changes (add/update/remove)
- Coordinate grid/chart selection events
- Manage field mapping for data projection

**Usage:**
```java
// Implement or use a data bridge to sync grid with charts
IChartDataBridge<RowData> bridge = new GridChartDataBridge<>(gridData);
bridge.setFieldMapping(Map.of(
    "region", "x",      // grid column → chart category
    "sales", "y",       // grid column → chart value
    "productId", "id"   // grid column → chart key (for cross-filtering)
));
```

### 2. ChartConfiguration
Configuration metadata for chart instances linked to a grid.

**Key Properties:**
- `chartId`: Unique chart identifier
- `chartType`: Chart type (bar, line, pie, scatter, etc.)
- `dataBridgeId`: Reference to data bridge
- `linkedGridId`: Grid this chart is linked to
- `fieldMapping`: Grid field → chart property mapping
- `enableCrossFiltering`: Allow chart selections to filter grid
- `enableSelectionSync`: Sync grid/chart selections
- `themes`: Applied chart themes
- `customOptions`: Direct AG Charts options

**Usage:**
```java
ChartConfiguration barChart = new ChartConfiguration("sales-chart", "bar")
    .setTitle("Sales by Region")
    .setLinkedGridId("main-grid")
    .setDataBridgeId("grid-bridge")
    .setEnableCrossFiltering(true)
    .setEnableSelectionSync(true)
    .setThemes(List.of("ag-default"))
    .setFieldMapping(Map.of(
        "region", "x",
        "sales", "y"
    ));
```

### 3. ChartRegistry
Singleton registry for managing chart instances and grid-chart relationships.

**Responsibilities:**
- Register/unregister chart configurations
- Manage chart registry listeners
- Maintain grid-chart mappings
- Store data bridges

**Usage:**
```java
ChartRegistry registry = ChartRegistry.getInstance();

// Register a chart
registry.registerChart("sales-chart", barChart);

// Link charts to a grid
registry.linkChartsToGrid("main-grid", List.of("sales-chart", "region-chart"));

// Get linked charts for a grid
List<String> chartIds = registry.getLinkedCharts("main-grid");

// Listen to registry events
registry.addListener(new ChartRegistry.ChartRegistryListener() {
    @Override
    public void onChartRegistered(String chartId, ChartConfiguration config) {
        System.out.println("Chart registered: " + chartId);
    }
    
    @Override
    public void onChartsLinkedToGrid(String gridId, List<String> chartIds) {
        System.out.println("Charts " + chartIds + " linked to grid " + gridId);
    }
    
    // ... other methods
});
```

## Integration Patterns

### Pattern 1: Simple Chart Linking
Link a grid to pre-configured charts for basic data display.

```java
AgGridEnterprise<AgGridEnterprise<?>> grid = new AgGridEnterprise<>("salesGrid")
    .setRowData(rowDataList)
    .enableCharts()
    .linkCharts("bar-chart", "pie-chart");  // Link by chart IDs
```

### Pattern 2: Register and Link
Create and register chart configuration in one step.

```java
AgGridEnterprise<AgGridEnterprise<?>> grid = new AgGridEnterprise<>("salesGrid")
    .setRowData(rowDataList)
    .enableCharts()
    .registerAndLinkChart(new ChartConfiguration("sales-chart", "bar")
        .setTitle("Sales by Region")
        .setFieldMapping(Map.of("region", "x", "sales", "y"))
        .setEnableCrossFiltering(true));
```

### Pattern 3: Advanced - Custom Data Bridge
Implement custom logic for data sync and filtering.

```java
public class CustomGridChartBridge implements IChartDataBridge<SalesData> {
    private List<SalesData> gridData;
    private Map<String, String> fieldMapping;
    private List<ChartInteractionListener> listeners = new ArrayList<>();
    
    @Override
    public List<SalesData> getGridRowData() {
        return new ArrayList<>(gridData);
    }
    
    @Override
    public void onGridDataChanged(List<SalesData> updatedData) {
        this.gridData = new ArrayList<>(updatedData);
        // Notify charts of data changes
    }
    
    @Override
    public void onChartInteraction(String chartId, List<Map<String, Object>> dataPoints) {
        // Handle cross-filtering: apply chart selection to grid
        List<String> selectedKeys = dataPoints.stream()
            .map(dp -> (String) dp.get("id"))
            .toList();
        
        List<SalesData> filtered = gridData.stream()
            .filter(row -> selectedKeys.contains(row.getProductId()))
            .toList();
        
        // Update grid with filtered data
        listeners.forEach(l -> l.onChartInteraction(chartId, dataPoints));
    }
    
    @Override
    public Map<String, String> getFieldMapping() {
        return fieldMapping;
    }
    
    @Override
    public void setFieldMapping(Map<String, String> mapping) {
        this.fieldMapping = new HashMap<>(mapping);
    }
    
    @Override
    public void addChartInteractionListener(ChartInteractionListener listener) {
        listeners.add(listener);
    }
    
    @Override
    public void removeChartInteractionListener(ChartInteractionListener listener) {
        listeners.remove(listener);
    }
}

// Usage
CustomGridChartBridge bridge = new CustomGridChartBridge();
bridge.setFieldMapping(Map.of("region", "x", "sales", "y"));

ChartRegistry.getInstance().registerDataBridge("custom-bridge", bridge);
ChartRegistry.getInstance().linkChartsToGrid("main-grid", List.of("chart1"));
```

### Pattern 4: Enable Cross-Filtering
Coordinate selections between grid and charts.

```java
AgGridEnterprise<AgGridEnterprise<?>> grid = new AgGridEnterprise<>("salesGrid")
    .setRowData(rowDataList)
    .enableCharts()
    .registerAndLinkChart(new ChartConfiguration("region-chart", "pie")
        .setEnableCrossFiltering(true))
    .enableChartCrossFiltering();  // Enable bidirectional filtering
```

### Pattern 5: Selection Synchronization
Sync selections between grid and charts.

```java
AgGridEnterprise<AgGridEnterprise<?>> grid = new AgGridEnterprise<>("salesGrid")
    .setRowData(rowDataList)
    .enableCharts()
    .registerAndLinkChart(new ChartConfiguration("trends-chart", "line")
        .setEnableSelectionSync(true))
    .enableChartSelectionSync();  // Selecting rows highlights chart data
```

## Helper Methods on AgGridEnterprise

### linkCharts(String... chartIds)
Link pre-registered charts to this grid.

```java
grid.linkCharts("bar-chart", "pie-chart", "line-chart");
```

### registerAndLinkChart(ChartConfiguration config)
Create and link a new chart in one step.

```java
grid.registerAndLinkChart(new ChartConfiguration("new-chart", "scatter")
    .setTitle("Sales Distribution")
    .setFieldMapping(Map.of("unitsSold", "x", "revenue", "y")));
```

### enableChartCrossFiltering()
Enable chart selections to filter grid rows.

```java
grid.enableChartCrossFiltering();
```

### enableChartSelectionSync()
Enable grid selections to highlight chart data.

```java
grid.enableChartSelectionSync();
```

### getChartRegistry()
Access the global chart registry for advanced management.

```java
ChartRegistry registry = AgGridEnterprise.getChartRegistry();
```

## Data Flow Architecture

```
┌──────────────────────────────────────────────────────────────┐
│                         AG Grid Enterprise                     │
│  ┌────────────┐                        ┌─────────────────┐   │
│  │  Row Data  │                        │  Chart Linking  │   │
│  └────────────┘                        └─────────────────┘   │
│        │                                        │              │
│        └────────────────┬─────────────────────┘               │
│                         │                                      │
│                    ┌────▼────┐                                │
│                    │ Selection│                               │
│                    └────┬────┘                                │
└─────────────────────────┼──────────────────────────────────────┘
                          │
        ┌─────────────────┴──────────────────┐
        │                                    │
    ┌───▼────────────────┐   ┌─────────────▼───┐
    │ IChartDataBridge   │   │ ChartRegistry   │
    │ - Data sync        │   │ - Chart mgmt    │
    │ - Field mapping    │   │ - Grid links    │
    │ - Event coord      │   │ - Listeners     │
    └───┬────────────────┘   └─────────────┬───┘
        │                                   │
        └───────────────┬───────────────────┘
                        │
        ┌───────────────┴──────────────────┐
        │                                  │
    ┌───▼──────────────┐   ┌──────────────▼───┐
    │ AG Charts #1     │   │ AG Charts #2     │
    │ - Series data    │   │ - Series data    │
    │ - Interactions   │   │ - Interactions   │
    └──────────────────┘   └──────────────────┘
```

## Field Mapping Reference

The `fieldMapping` property controls how grid columns map to chart data properties:

```java
Map<String, String> mapping = Map.ofEntries(
    // Category/X-axis
    Map.entry("region", "x"),           // String field → category
    Map.entry("date", "x"),             // Date field → time category
    
    // Numeric values
    Map.entry("sales", "y"),            // Numeric → Y value
    Map.entry("profit", "size"),        // For bubble/scatter: size
    Map.entry("marketShare", "color"),  // For heatmaps: color value
    
    // Keys for cross-filtering
    Map.entry("productId", "id"),       // Unique key for selection tracking
    Map.entry("customerId", "id"),      // Another unique key option
    
    // Multi-series support
    Map.entry("quarter", "x"),
    Map.entry("productLine", "series"), // For grouped data
    Map.entry("revenue", "y")
);
```

## Chart Types and Typical Field Mappings

### Bar Chart
```java
Map.of("category", "x", "value", "y", "series", "series")
```

### Line/Area Chart
```java
Map.of("date", "x", "value", "y", "metric", "series")
```

### Pie Chart
```java
Map.of("label", "label", "value", "value")
```

### Scatter Chart
```java
Map.of("xValue", "x", "yValue", "y", "size", "size", "category", "id")
```

### Bubble Chart
```java
Map.of("xValue", "x", "yValue", "y", "size", "size", "label", "label")
```

## Best Practices

1. **Unique Chart IDs**: Always use unique, meaningful chart IDs within your application
2. **Field Mapping**: Define field mappings upfront to avoid runtime issues
3. **Grid ID**: Explicitly set grid IDs for multi-grid scenarios
4. **Data Bridge**: Implement custom bridges for complex data transformations
5. **Event Listeners**: Register registry listeners to track chart lifecycle
6. **Theme Consistency**: Use the same themes across related charts for visual cohesion
7. **Performance**: For large datasets, consider server-side aggregation before charting

## Troubleshooting

**Charts not appearing**: Verify `enableCharts()` is called and charts are registered.

**Cross-filtering not working**: Ensure `setEnableCrossFiltering(true)` and field mapping includes an `id` field.

**Selection sync fails**: Check that grid selection events are properly wired to chart interaction listeners.

**Data not updating**: Verify the `IChartDataBridge` is properly receiving grid data changes via `onGridDataChanged()`.

## Examples

### Complete Sales Dashboard
```java
public class SalesDashboard {
    public void setupGrid() {
        AgGridEnterprise<AgGridEnterprise<?>> grid = new AgGridEnterprise<>("salesGrid")
            .setRowData(loadSalesData())
            .enableCharts()
            .enableRangeSelection()
            
            // Register sales by region chart
            .registerAndLinkChart(new ChartConfiguration("regionChart", "pie")
                .setTitle("Sales by Region")
                .setEnableCrossFiltering(true)
                .setFieldMapping(Map.of(
                    "region", "label",
                    "sales", "value",
                    "regionId", "id"
                )))
            
            // Register sales trends chart
            .registerAndLinkChart(new ChartConfiguration("trendsChart", "line")
                .setTitle("Sales Trends")
                .setEnableSelectionSync(true)
                .setFieldMapping(Map.of(
                    "month", "x",
                    "sales", "y",
                    "productLine", "series",
                    "monthId", "id"
                )))
            
            // Enable cross-filtering between grid and charts
            .enableChartCrossFiltering()
            .enableChartSelectionSync();
    }
    
    private List<Map<String, Object>> loadSalesData() {
        // Load your data here
        return List.of(/* ... */);
    }
}
```

## Related Documentation

- [AG Grid Enterprise Guide](./docs/AgGridEnterprise-Guide.md)
- [AG Charts Enterprise Rules](./rules/generative/frontend/jwebmp/agcharts-enterprise/README.md)
- [AG Grid API Reference](https://www.ag-grid.com/javascript-data-grid/api/)
- [AG Charts API Reference](https://www.ag-grid.com/javascript-charts/api/)
