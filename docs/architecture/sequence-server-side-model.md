# Sequence Diagram — Server-Side Row Model Data Exchange

Flow for lazy-loading row data via Server-Side Row Model (enterprise feature).

```mermaid
sequenceDiagram
    actor User as User
    participant Grid as AG Grid<br/>Enterprise
    participant Backend as Java Backend<br/>REST Service
    participant DB as Database
    participant Engine as AG Grid JS<br/>ServerSideRowModel
    participant Browser as Browser<br/>DOM Rendering

    Note over User,Browser: Initial Grid Load
    User->>Grid: Page loads, grid initialized
    activate Grid
    Grid->>Engine: Initialize with<br/>rowModelType: serverSide
    activate Engine
    Engine->>Engine: Create ServerSideDataSource
    deactivate Engine
    
    Note over User,Browser: User scrolls or sorts/filters
    User->>Browser: Scroll grid / Sort / Filter
    activate Browser
    Browser->>Engine: onServerSideGetRows(params)
    activate Engine
    
    Engine->>Backend: POST /api/grid/rows
    {
      "startRow": 0,
      "endRow": 100,
      "rowGroupCols": [],
      "valueCols": [{"field": "sales", "aggFunc": "sum"}],
      "pivotCols": [],
      "pivotMode": false,
      "filterModel": {"country": {"values": ["USA"]}},
      "sortModel": [{"colId": "sales", "sort": "desc"}]
    }
    activate Backend
    
    Backend->>DB: Query with sort/filter/pagination
    {
      SELECT * FROM sales
      WHERE country IN ('USA')
      ORDER BY sales DESC
      LIMIT 100 OFFSET 0
    }
    activate DB
    DB-->>Backend: Rows data
    deactivate DB
    
    Backend->>Backend: Transform to AG Grid format
    {
      "successStatus": 0,
      "rowData": [
        {"id": 1, "country": "USA", "sales": 5000},
        {"id": 2, "country": "USA", "sales": 4500},
        ...
      ],
      "rowCount": 250
    }
    Backend-->>Engine: Success response + rowCount
    deactivate Backend
    
    Engine->>Engine: Cache blocks<br/>cacheBlockSize: 100
    Engine->>Engine: Update displayed rows<br/>handle sorting/filtering locally if enabled
    Engine->>Browser: Render rows to DOM
    deactivate Engine
    Browser-->>User: Grid displays new rows
    deactivate Browser
    
    Note over User,Browser: User groups rows by country
    User->>Engine: Apply row grouping<br/>groupBy: country
    activate Engine
    
    Engine->>Backend: POST /api/grid/rows<br/>(rowGroupCols: [{"field": "country"}])
    {
      "startRow": 0,
      "endRow": 100,
      "rowGroupCols": [{"field": "country", "aggFunc": null}],
      "pivotCols": [],
      "groupKeys": [],
      "sortModel": []
    }
    activate Backend
    
    Backend->>DB: Query for distinct countries
    DB-->>Backend: ["USA", "Canada", "Mexico"]
    deactivate DB
    
    Backend-->>Engine: Group response
    {
      "successStatus": 0,
      "rowData": [
        {"country": "USA", "group": true, "rowCount": 100},
        {"country": "Canada", "group": true, "rowCount": 50},
        ...
      ]
    }
    deactivate Backend
    
    Engine->>Engine: Render group rows
    Engine->>Browser: Display grouped structure
    deactivate Engine
    Browser-->>User: Grouped view with expandable rows
    
    Note over User,Browser: User expands USA group
    User->>Engine: Expand group key "USA"
    activate Engine
    
    Engine->>Backend: POST /api/grid/rows<br/>(groupKeys: ["USA"])
    {
      "startRow": 0,
      "endRow": 50,
      "rowGroupCols": [{"field": "country"}],
      "groupKeys": ["USA"],
      "sortModel": []
    }
    activate Backend
    
    Backend->>DB: Query children for country=USA
    DB-->>Backend: Detail rows for USA
    deactivate DB
    
    Backend-->>Engine: Detail rows + childRowCount
    deactivate Backend
    
    Engine->>Browser: Expand group, render children
    deactivate Engine
    Browser-->>User: USA group expanded, detail rows visible
    
    Note over User,Browser: User changes column visibility / reorders
    User->>Engine: Column visibility or order change
    activate Engine
    Engine->>Engine: Update columnState
    Engine->>Backend: Refresh with new column layout
    activate Backend
    Backend->>DB: Query (column order affects aggregation)
    DB-->>Backend: Rows in new order
    deactivate DB
    Backend-->>Engine: Rows response
    deactivate Backend
    Engine->>Browser: Re-render grid
    deactivate Engine
    Browser-->>User: Updated grid layout
```

## Server-Side Row Model Flow

### 1. Grid Initialization
Developer configures:
```java
AgGridEnterpriseOptions opts = getOptions();
opts.setRowModelType(RowModelType.SERVER_SIDE);
opts.setServerSideStoreType("partial");  // lazy-load on scroll
opts.setCacheBlockSize(100);             // load 100 rows at a time
opts.setMaxBlocksInCache(10);            // keep up to 10 blocks in memory
```

### 2. Grid Ready Event
AG Grid JS initializes with ServerSideDataSource:
- Waits for user scroll/sort/filter actions
- No initial data load until user interacts

### 3. User Interaction Triggers Request
When user:
- Scrolls to new area
- Sorts by column
- Filters data
- Groups rows
- Expands group

AG Grid JS calls: `dataSource.getRows(params)`

### 4. Request Object (params)
```typescript
{
  startRow: number,           // 0-based
  endRow: number,             // exclusive
  rowGroupCols: ColumnVO[],   // columns being grouped by
  valueCols: ColumnVO[],      // columns being aggregated
  pivotCols: ColumnVO[],      // pivot columns
  pivotMode: boolean,         // pivot/grouping mode
  filterModel: FilterModel,   // active filters per column
  sortModel: SortVO[],        // active sorts
  groupKeys: string[]         // for nested groups
}
```

### 5. Backend Processing
Java REST endpoint receives params and:
- Parses sort, filter, pagination, group keys
- Builds SQL query (or equivalent for non-SQL)
- Executes query
- Transforms results to AG Grid format

Response:
```java
{
  "successStatus": 0,         // 0 = success, 1 = error
  "rowData": [...],           // rows for this block
  "rowCount": 1000            // total rows (after filter)
}
```

### 6. Caching
AG Grid caches blocks (e.g., rows 0-99, 100-199):
- Reduces backend calls when scrolling within cached blocks
- Cache size controlled by `maxBlocksInCache`
- Purges oldest blocks when cache full

### 7. Row Grouping
When user groups by column:
- First request has `rowGroupCols: [field]`, `groupKeys: []` (top-level groups)
- Backend returns distinct group values
- Grid displays group rows

When user expands group:
- Request includes `groupKeys: ["USA"]` (child rows for that group)
- Backend returns detail rows for that group key

### 8. Pivot Mode
Similar to grouping but with value aggregation:
- `valueCols` includes aggregation functions (sum, count, avg, etc.)
- Backend aggregates values per pivot cell
- Grid renders pivot table

## Configuration Example

```java
public class LargeDataGrid extends AgGridEnterprise<LargeDataGrid> {
    public LargeDataGrid() {
        setID("largeDataGrid");
        
        // Server-Side Configuration
        getOptions().setRowModelType(RowModelType.SERVER_SIDE);
        getOptions().setServerSideStoreType("partial");
        getOptions().setCacheBlockSize(100);
        getOptions().setMaxBlocksInCache(5);
        getOptions().setPurgeClosedRowNodes(true);  // free memory when groups closed
        
        // Columns with grouping capability
        getOptions().setColumnDefs(List.of(
            new AgGridColumnDef<>("country")
                .setRowGroup(true)
                .setRowGroupIndex(0),
            new AgGridColumnDef<>("sales")
                .setAggFunc("sum"),  // for pivot aggregation
            new AgGridColumnDef<>("quantity")
                .setAggFunc("count")
        ));
        
        // Enable grouping UI
        showRowGroupPanel();
    }
}
```

## Performance Considerations

| Setting | Impact |
|---------|--------|
| `cacheBlockSize` | Larger = fewer requests, more memory |
| `maxBlocksInCache` | Larger = better scroll performance, more memory |
| `purgeClosedRowNodes` | true = frees memory but slower re-expand |
| `suppressAggFuncInHeader` | true = cleaner headers, less visual clutter |

## Error Handling

If backend returns `"successStatus": 1`:
```json
{
  "successStatus": 1,
  "errorMessage": "Failed to load rows"
}
```

AG Grid displays error message and retries.

