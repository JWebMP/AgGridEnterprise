# Entity-Relationship Diagram — Enterprise Options Model

Structure of the Enterprise Options class hierarchy and relationships.

```mermaid
erDiagram
    GRID_OPTIONS ||--|| ENTERPRISE_OPTIONS : extends
    
    ENTERPRISE_OPTIONS ||--|{ CHART_OPTIONS : contains
    ENTERPRISE_OPTIONS ||--|{ RANGE_OPTIONS : contains
    ENTERPRISE_OPTIONS ||--|{ SIDEBAR_OPTIONS : contains
    ENTERPRISE_OPTIONS ||--|{ STATUSBAR_OPTIONS : contains
    ENTERPRISE_OPTIONS ||--|{ ROWGROUPING_OPTIONS : contains
    ENTERPRISE_OPTIONS ||--|{ SERVERSIDE_OPTIONS : contains
    
    CHART_OPTIONS ||--o{ CHART_THEME : uses
    CHART_OPTIONS ||--o| CHART_THEME_OVERRIDES : contains
    CHART_OPTIONS ||--o{ STRING : chartToolbarItems
    
    SIDEBAR_OPTIONS ||--o| SIDEBAR_DEF : contains
    SIDEBAR_DEF ||--o{ SIDEBAR_TOOL_PANEL_DEF : has
    
    STATUSBAR_OPTIONS ||--o| STATUSBAR_DEF : contains
    STATUSBAR_DEF ||--o{ STATUSBAR_PANEL_DEF : has
    
    ROWGROUPING_OPTIONS ||--o| PANEL_SHOW : uses
    ROWGROUPING_OPTIONS ||--o| GROUP_HIERARCHY_CONFIG : contains
    
    SERVERSIDE_OPTIONS ||--o| ROW_MODEL_TYPE : uses
    
    GRID_OPTIONS {
        string id
        list columnDefs
        list rowData
        boolean pagination
        int paginationPageSize
    }
    
    ENTERPRISE_OPTIONS {
        boolean enableCharts
        boolean enableRangeSelection
        object sideBar
        object statusBar
        string rowGroupPanelShow
        string pivotPanelShow
        string rowModelType
    }
    
    CHART_OPTIONS {
        list chartThemes
        object chartThemeOverrides
        object chartToolPanelsDef
        boolean suppressChartToolPanelsOverlay
        list chartToolbarItems
    }
    
    RANGE_OPTIONS {
        boolean enableRangeSelection
        boolean suppressCopyRowsToClipboard
        boolean suppressCopySingleCellRanges
    }
    
    SIDEBAR_OPTIONS {
        object sideBar
        boolean enableSideBar
        object toolPanels
    }
    
    SIDEBAR_DEF {
        list toolPanels
        string position
    }
    
    SIDEBAR_TOOL_PANEL_DEF {
        string id
        string labelKey
        string labelDefault
        string iconKey
    }
    
    STATUSBAR_OPTIONS {
        object statusBar
    }
    
    STATUSBAR_DEF {
        list statusPanels
    }
    
    STATUSBAR_PANEL_DEF {
        string statusPanelId
        string statusPanelClass
        object statusPanelComp
        string align
    }
    
    ROWGROUPING_OPTIONS {
        string rowGroupPanelShow
        string pivotPanelShow
        object groupHierarchyConfig
        boolean groupAllowUnbalanced
        string groupHideParentOfSingleChild
        boolean groupHideOpenParents
    }
    
    SERVERSIDE_OPTIONS {
        string rowModelType
        string serverSideStoreType
        int cacheBlockSize
        int maxBlocksInCache
        boolean purgeClosedRowNodes
    }
    
    CHART_THEME {
        string value
    }
    
    CHART_THEME_OVERRIDES {
        object styleOverrides
    }
    
    PANEL_SHOW {
        string value
    }
    
    GROUP_HIERARCHY_CONFIG {
        string key
        object config
    }
    
    ROW_MODEL_TYPE {
        string value
    }
    
    STRING {
        string value
    }
```

## Entity Relationships

### Inheritance Hierarchy
```
AgGridOptions (JWebMP Community)
    ↓
AgGridEnterpriseOptions (This plugin)
```

- `AgGridEnterpriseOptions` extends `AgGridOptions`
- Inherits all community features (basic grid options, columns, rows, pagination)
- Adds enterprise-only fields and methods

### Composition Structure

**AgGridEnterpriseOptions** contains:

1. **ChartOptions**
   - `enableCharts: Boolean`
   - `chartThemes: List<ChartTheme>` (enum values)
   - `chartThemeOverrides: Object` (custom CSS/styling)
   - `chartToolPanelsDef: Object` (tool panel config)
   - `chartToolbarItems: List<String>` (toolbar items: "chartDownload", "chartOpenChartToolPanel")

2. **RangeSelectionOptions**
   - `enableRangeSelection: Boolean`
   - `suppressCopyRowsToClipboard: Boolean`
   - `suppressCopySingleCellRanges: Boolean`

3. **SideBarOptions**
   - `sideBar: SideBarDef` (composite POJO)
     - `toolPanels: List<SideBarToolPanelDef>`
       - `id: String` (e.g., "columns", "filters")
       - `labelKey: String` (i18n key)
       - `labelDefault: String` (default label)
       - `iconKey: String` (icon identifier)
     - `position: String` ("left" or "right")

4. **StatusBarOptions**
   - `statusBar: StatusBarDef` (composite POJO)
     - `statusPanels: List<StatusBarPanelDef>`
       - `statusPanelId: String`
       - `statusPanelClass: String`
       - `statusPanelComp: Object` (component reference)
       - `align: String` ("left", "center", "right")

5. **RowGroupingOptions**
   - `rowGroupPanelShow: PanelShow` (enum: ALWAYS, ONLY_WHEN_GROUPING, NEVER)
   - `pivotPanelShow: PanelShow` (enum: ALWAYS, NEVER)
   - `groupHierarchyConfig: Map<String, Object>` (custom hierarchy definitions)
   - `groupAllowUnbalanced: Boolean`
   - `groupHideParentOfSingleChild: String` (true/"leafGroupsOnly"/false)
   - `groupHideOpenParents: Boolean`

6. **ServerSideOptions**
   - `rowModelType: RowModelType` (enum: CLIENT_SIDE, SERVER_SIDE, VIEWPORT, INFINITE)
   - `serverSideStoreType: String` ("full" or "partial")
   - `cacheBlockSize: Integer` (rows per block)
   - `maxBlocksInCache: Integer` (max blocks in memory)
   - `purgeClosedRowNodes: Boolean` (free memory when groups closed)

## Enum Types

### ChartTheme
- `AG_DEFAULT` → `"ag-default"`
- `AG_VIVID` → `"ag-vivid"`
- `AG_MATERIAL` → `"ag-material"`
- `AG_SHEETS` → `"ag-sheets"`
- `POLYCHROMA` → `"polychroma"`

### PanelShow
- `ALWAYS` → `"always"`
- `ONLY_WHEN_GROUPING` → `"onlyWhenGrouping"`
- `NEVER` → `"never"`

### RowModelType
- `CLIENT_SIDE` → `"clientSide"`
- `SERVER_SIDE` → `"serverSide"`
- `VIEWPORT` → `"viewport"`
- `INFINITE` → `"infinite"`

## JSON Serialization Example

When serialized to JSON:

```json
{
  "enableCharts": true,
  "chartThemes": ["ag-default", "ag-vivid"],
  "enableRangeSelection": true,
  "sideBar": {
    "toolPanels": [
      {
        "id": "columns",
        "labelKey": "ag.sideBar.columns",
        "labelDefault": "Columns",
        "iconKey": "columns"
      },
      {
        "id": "filters",
        "labelKey": "ag.sideBar.filters",
        "labelDefault": "Filters",
        "iconKey": "filter"
      }
    ],
    "position": "left"
  },
  "statusBar": {
    "statusPanels": [
      {
        "statusPanelId": "agTotalAndFilteredRowCountComponent",
        "align": "left"
      },
      {
        "statusPanelId": "agSelectedRowCountComponent",
        "align": "center"
      }
    ]
  },
  "rowGroupPanelShow": "always",
  "rowModelType": "serverSide",
  "serverSideStoreType": "partial",
  "cacheBlockSize": 100,
  "maxBlocksInCache": 5
}
```

## Cardinality Notes

- **1-to-1 (||--||}）** — Each enterprise options instance has exactly one chart/range/sidebar/statusbar/grouping/serverside option container
- **1-to-Many (||--|{}）** — SideBarDef has many ToolPanelDef; StatusBarDef has many StatusBarPanelDef
- **0-to-1 (||--o|}）** — Optional enum or config (may be null)
- **0-to-Many (||--o{}）** — Optional list

