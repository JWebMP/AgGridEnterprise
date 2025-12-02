# Dynamic Series Coloring Guide

## Overview

Dynamic series coloring enables sophisticated visual styling of chart data points and series based on data values, without requiring stacked bar chart workarounds. This feature supports five distinct coloring strategies, from simple solid colors to complex custom callback functions.

## Quick Start

The simplest way to color a chart series by value is using value gradients:

```java
CreateRangeChartParams chartParams = new CreateRangeChartParams()
    .setChartType(GridChartType.COLUMN)
    .setCellRange(cellRange)
    .setValueGradientColors("#FFE5E5", "#FF0000");  // Light red to dark red
```

Or use the full configuration approach:

```java
SeriesColorConfiguration colorConfig = new SeriesColorConfiguration()
    .setValueGradientColors("#0066CC", "#FF6600");  // Blue to orange gradient

CreateRangeChartParams chartParams = new CreateRangeChartParams()
    .setChartType(GridChartType.BAR)
    .setCellRange(cellRange)
    .setSeriesColorConfig(colorConfig);
```

## Coloring Strategies

### 1. SOLID - Single Color Per Series

Use a single, uniform color for all data points in a series. This is the default AG Charts behavior.

```java
SeriesColorConfiguration colorConfig = new SeriesColorConfiguration(SeriesColorStrategy.SOLID)
    .setColorPalette(Arrays.asList("#1F77B4", "#FF7F0E", "#2CA02C"));

CreateRangeChartParams chartParams = new CreateRangeChartParams()
    .setChartType(GridChartType.BAR)
    .setCellRange(cellRange)
    .setSeriesColorConfig(colorConfig);
```

**When to use:**
- Standard charts where each series has a distinct, permanent color
- Dashboard visualizations with consistent color schemes
- When value-based coloring is not needed

---

### 2. VALUE_GRADIENT - Smooth Gradient Based on Values

Colors smoothly transition from a minimum color to a maximum color across the data range. Perfect for heatmaps and trend visualization.

```java
SeriesColorConfiguration colorConfig = new SeriesColorConfiguration()
    .setValueGradientColors("#FFFFFF", "#FF0000");  // White to red

CreateRangeChartParams chartParams = new CreateRangeChartParams()
    .setChartType(GridChartType.COLUMN)
    .setCellRange(cellRange)
    .setValueRangeColors(colorConfig);
```

**Configuration options:**
```java
SeriesColorConfiguration gradient = new SeriesColorConfiguration()
    .setStrategy(SeriesColorStrategy.VALUE_GRADIENT)
    .setMinColor("#FFFFFF")      // Color for minimum values
    .setMaxColor("#FF0000")      // Color for maximum values
    .setMinValue(0.0)            // Optional: explicit min (auto-calculated if omitted)
    .setMaxValue(1000.0);        // Optional: explicit max (auto-calculated if omitted)
```

**When to use:**
- Sales/revenue heatmaps (white = low, red = high)
- Performance metrics (green = good, red = poor)
- Temperature or intensity visualizations
- Continuous value ranges

**Example - Revenue by Quarter:**
```java
SeriesColorConfiguration revenueGradient = new SeriesColorConfiguration()
    .setValueGradientColors("#E8F4F8", "#006B96");  // Light cyan to dark blue

// Users will see increasing shade of blue as revenue increases
CreateRangeChartParams quarterlyChart = new CreateRangeChartParams()
    .setChartType(GridChartType.COLUMN)
    .setCellRange(quarterlyRevenueCellRange)
    .setSeriesColorConfig(revenueGradient);
```

---

### 3. VALUE_RANGE - Threshold-Based Coloring

Assign different colors to different value ranges. Useful for alerting, performance indicators, or categorical thresholds.

```java
Map<Number, String> thresholds = new LinkedHashMap<>();
thresholds.put(0, "#00AA00");      // Green: 0 to next threshold
thresholds.put(500, "#FFAA00");    // Orange: 500 to next threshold
thresholds.put(1000, "#FF0000");   // Red: 1000+

SeriesColorConfiguration colorConfig = new SeriesColorConfiguration()
    .setStrategy(SeriesColorStrategy.VALUE_RANGE)
    .setThresholds(thresholds);

CreateRangeChartParams chartParams = new CreateRangeChartParams()
    .setChartType(GridChartType.BAR)
    .setCellRange(cellRange)
    .setSeriesColorConfig(colorConfig);
```

**Fluent API:**
```java
SeriesColorConfiguration rangeColors = new SeriesColorConfiguration()
    .addThreshold(0, "#00AA00")           // Green
    .addThreshold(500, "#FFAA00")         // Orange
    .addThreshold(1000, "#FF0000")        // Red
    .setStrategy(SeriesColorStrategy.VALUE_RANGE);

CreateRangeChartParams chart = new CreateRangeChartParams()
    .setChartType(GridChartType.COLUMN)
    .setValueRangeColors(new LinkedHashMap<>(Map.of(
        0, "#00AA00",
        500, "#FFAA00",
        1000, "#FF0000"
    )));
```

**When to use:**
- KPI dashboards (bad/warning/good ranges)
- SLA monitoring (below target/on target/exceeds target)
- Risk indicators (low/medium/high risk)
- Service level tracking (poor/acceptable/excellent)

**Example - Sales Performance Alert System:**
```java
Map<Number, String> performanceThresholds = new LinkedHashMap<>();
performanceThresholds.put(0, "#CC0000");        // Red: Below target
performanceThresholds.put(10000, "#FFAA00");    // Orange: At target
performanceThresholds.put(15000, "#00AA00");    // Green: Exceeding target

SeriesColorConfiguration performanceColors = new SeriesColorConfiguration()
    .setStrategy(SeriesColorStrategy.VALUE_RANGE)
    .setThresholds(performanceThresholds);

// Sales below $10k: Red, $10-15k: Orange, Above $15k: Green
CreateRangeChartParams salesChart = new CreateRangeChartParams()
    .setChartType(GridChartType.COLUMN)
    .setValueRangeColors(performanceThresholds);
```

---

### 4. POSITIVE_NEGATIVE - Diverging Colors for Sign

Automatically apply different colors to positive and negative values. Excellent for financial, profit/loss, and variance data.

```java
SeriesColorConfiguration divColor = new SeriesColorConfiguration()
    .setPositiveNegativeColors(
        "#00AA00",  // Green for positive values
        "#FF0000",  // Red for negative values
        "#CCCCCC"   // Optional: gray for zero
    );

CreateRangeChartParams chart = new CreateRangeChartParams()
    .setChartType(GridChartType.COLUMN)
    .setCellRange(cellRange)
    .setSeriesColorConfig(divColor);
```

**Configuration options:**
```java
SeriesColorConfiguration posNeg = new SeriesColorConfiguration()
    .setStrategy(SeriesColorStrategy.POSITIVE_NEGATIVE)
    .setPositiveColor("#00AA00")  // Color for values > 0
    .setNegativeColor("#FF0000")  // Color for values < 0
    .setZeroColor("#CCCCCC");     // Optional: color for values == 0
```

**When to use:**
- Profit and loss statements
- Budget variance analysis (over/under budget)
- Growth rates (positive/negative growth)
- Temperature deltas (above/below baseline)
- Score differentials (win/loss records)

**Example - Quarterly Profit/Loss:**
```java
SeriesColorConfiguration profitLoss = new SeriesColorConfiguration()
    .setPositiveNegativeColors("#0066CC", "#CC0000", "#CCCCCC");

// Positive quarterly profits: Blue, losses: Red, break-even: Gray
CreateRangeChartParams pnlChart = new CreateRangeChartParams()
    .setChartType(GridChartType.COLUMN)
    .setCellRange(quarterlyProfitLossCellRange)
    .setSeriesColorConfig(profitLoss);
```

**Example - Budget Variance:**
```java
SeriesColorConfiguration budgetVariance = new SeriesColorConfiguration()
    .setPositiveNegativeColors(
        "#00AA00",  // Green: Under budget (good)
        "#FF0000",  // Red: Over budget (bad)
        "#999999"   // Gray: On budget
    );

CreateRangeChartParams budgetChart = new CreateRangeChartParams()
    .setChartType(GridChartType.BAR)
    .setCellRange(departmentBudgetVarianceCellRange)
    .setSeriesColorConfig(budgetVariance);
```

---

### 5. CUSTOM_CALLBACK - Custom JavaScript Function

Use a custom function to compute colors based on any custom logic. Enables unlimited flexibility for color determination.

```java
String colorFunction = "(value, index, series) => {\n" +
    "  if (value > 1000) return '#FF0000';\n" +
    "  if (value > 500) return '#FFAA00';\n" +
    "  return '#00AA00';\n" +
    "}";

SeriesColorConfiguration customColor = new SeriesColorConfiguration()
    .setCustomColorFunction(colorFunction);

CreateRangeChartParams chart = new CreateRangeChartParams()
    .setChartType(GridChartType.COLUMN)
    .setCellRange(cellRange)
    .setSeriesColorConfig(customColor);
```

**Function Signature:**
```typescript
(value: number, index: number, series: SeriesOptions): string => {
    // Return a valid CSS color string (#RRGGBB, rgb(), named color, etc.)
}
```

**Parameters:**
- `value` - The current data point value
- `index` - The data point index within the series
- `series` - The full series configuration object

**Return:** Valid CSS color string

**When to use:**
- Complex, rule-based coloring logic
- Business-specific color schemes
- Dynamic color calculation based on multiple factors
- Integration with external color systems
- Advanced styling requirements

**Example - Complex SLA Metrics:**
```java
String slaColorFunction = "(value, index, series) => {\n" +
    "  // Value is response time in milliseconds\n" +
    "  if (value < 100) return '#00CC00';        // Excellent\n" +
    "  if (value < 500) return '#00AA00';        // Good\n" +
    "  if (value < 1000) return '#FFAA00';       // Warning\n" +
    "  if (value < 2000) return '#FF6600';       // Poor\n" +
    "  return '#CC0000';                         // Critical\n" +
    "}";

SeriesColorConfiguration slaColors = new SeriesColorConfiguration()
    .setCustomColorFunction(slaColorFunction);

CreateRangeChartParams slaChart = new CreateRangeChartParams()
    .setChartType(GridChartType.COLUMN)
    .setCellRange(slaResponseTimeCellRange)
    .setSeriesColorConfig(slaColors);
```

**Example - Seasonal Patterns:**
```java
String seasonalColorFunction = "(value, index, series) => {\n" +
    "  const month = index % 12;  // Assuming data ordered by month\n" +
    "  if (month >= 5 && month <= 8) return '#FF9900';  // Summer: Orange\n" +
    "  return '#0099FF';                                 // Other: Blue\n" +
    "}";

CreateRangeChartParams seasonalChart = new CreateRangeChartParams()
    .setCustomColorFunction(seasonalColorFunction);
```

---

## DataPointStyle - Individual Point Styling

Beyond series colors, use `DataPointStyle` to customize rendering of individual data points:

```java
DataPointStyle customStyle = new DataPointStyle()
    .setFill("#FF0000")       // Fill color
    .setStroke("#000000")     // Border color
    .setOpacity(0.8)          // Transparency (0-1)
    .setStrokeWidth(2)        // Border thickness in pixels
    .setSize(6);              // Point size in pixels

// For advanced use cases combining coloring with styling:
SeriesColorConfiguration colorConfig = new SeriesColorConfiguration()
    .setStrategy(SeriesColorStrategy.VALUE_GRADIENT)
    .setMinColor("#FFFFFF")
    .setMaxColor("#FF0000");

CreateRangeChartParams chartParams = new CreateRangeChartParams()
    .setChartType(GridChartType.SCATTER)
    .setCellRange(cellRange)
    .setSeriesColorConfig(colorConfig);
    // DataPointStyle can be applied at the series level in AG Charts
```

---

## Complete Examples

### Example 1: Sales Dashboard with Multiple Strategies

```java
public class SalesDashboard {
    
    public CreateRangeChartParams createRevenueTrendChart(ChartParamsCellRange cellRange) {
        // Use gradient to show increasing intensity as revenue grows
        return new CreateRangeChartParams()
            .setChartType(GridChartType.COLUMN)
            .setCellRange(cellRange)
            .setValueGradientColors("#E8F4F8", "#0066CC");
    }
    
    public CreateRangeChartParams createPerformanceChart(ChartParamsCellRange cellRange) {
        // Use thresholds to show performance tiers
        Map<Number, String> performanceRanges = new LinkedHashMap<>();
        performanceRanges.put(0, "#CC0000");        // Red: Poor
        performanceRanges.put(60, "#FFAA00");       // Orange: Average
        performanceRanges.put(80, "#00AA00");       // Green: Good
        
        return new CreateRangeChartParams()
            .setChartType(GridChartType.BAR)
            .setCellRange(cellRange)
            .setValueRangeColors(performanceRanges);
    }
    
    public CreateRangeChartParams createProfitLossChart(ChartParamsCellRange cellRange) {
        // Use positive/negative to distinguish profitability
        return new CreateRangeChartParams()
            .setChartType(GridChartType.COLUMN)
            .setCellRange(cellRange)
            .setPositiveNegativeColors("#0066CC", "#CC0000", "#999999");
    }
}
```

### Example 2: Financial Dashboard with Custom Logic

```java
public class FinancialDashboard {
    
    public CreateRangeChartParams createCreditRiskChart(ChartParamsCellRange cellRange) {
        // Custom function for credit risk scoring
        String riskColorFunction = "(value) => {\n" +
            "  if (value >= 800) return '#00AA00';      // Excellent credit\n" +
            "  if (value >= 670) return '#00CC00';      // Good credit\n" +
            "  if (value >= 580) return '#FFAA00';      // Fair credit\n" +
            "  if (value >= 500) return '#FF6600';      // Poor credit\n" +
            "  return '#CC0000';                        // Very poor credit\n" +
            "}";
        
        return new CreateRangeChartParams()
            .setChartType(GridChartType.BAR)
            .setCellRange(cellRange)
            .setCustomColorFunction(riskColorFunction);
    }
    
    public CreateRangeChartParams createInterestRateChart(ChartParamsCellRange cellRange) {
        // Positive/negative for rate changes
        return new CreateRangeChartParams()
            .setChartType(GridChartType.COLUMN)
            .setCellRange(cellRange)
            .setPositiveNegativeColors("#CC0000", "#00AA00");  // Red=higher rates, Green=lower
    }
}
```

### Example 3: Operations & Monitoring Dashboard

```java
public class OperationsDashboard {
    
    public CreateRangeChartParams createResponseTimeChart(ChartParamsCellRange cellRange) {
        // Response time with SLA thresholds
        Map<Number, String> slaRanges = new LinkedHashMap<>();
        slaRanges.put(0, "#00AA00");        // Green: < 200ms (excellent)
        slaRanges.put(200, "#FFAA00");      // Orange: 200-500ms (acceptable)
        slaRanges.put(500, "#FF6600");      // Dark orange: 500-1000ms (warning)
        slaRanges.put(1000, "#CC0000");     // Red: > 1000ms (critical)
        
        return new CreateRangeChartParams()
            .setChartType(GridChartType.COLUMN)
            .setCellRange(cellRange)
            .setValueRangeColors(slaRanges);
    }
    
    public CreateRangeChartParams createErrorRateChart(ChartParamsCellRange cellRange) {
        // Custom function for error rate escalation
        String errorColorFunction = "(value) => {\n" +
            "  const percent = Math.min(100, value);\n" +
            "  // Start with blue (0%), end with red (100%)\n" +
            "  const hue = Math.round((1 - percent/100) * 240);\n" +
            "  return `hsl(${hue}, 100%, 50%)`;\n" +
            "}";
        
        return new CreateRangeChartParams()
            .setChartType(GridChartType.COLUMN)
            .setCellRange(cellRange)
            .setCustomColorFunction(errorColorFunction);
    }
    
    public CreateRangeChartParams createUptimeChart(ChartParamsCellRange cellRange) {
        // Gradient for uptime percentage visualization
        return new CreateRangeChartParams()
            .setChartType(GridChartType.COLUMN)
            .setCellRange(cellRange)
            .setValueGradientColors("#FF0000", "#00AA00");  // Red (0%) to Green (100%)
    }
}
```

---

## Integration with CreateRangeChartParams

All convenience methods on `CreateRangeChartParams` handle initialization:

```java
// Auto-creates SeriesColorConfiguration if needed
CreateRangeChartParams chart = new CreateRangeChartParams()
    .setChartType(GridChartType.COLUMN)
    .setCellRange(cellRange)
    .setValueGradientColors("#FFFFFF", "#FF0000");

// Equivalent to:
SeriesColorConfiguration config = new SeriesColorConfiguration()
    .setValueGradientColors("#FFFFFF", "#FF0000");

CreateRangeChartParams chart2 = new CreateRangeChartParams()
    .setChartType(GridChartType.COLUMN)
    .setCellRange(cellRange)
    .setSeriesColorConfig(config);
```

---

## Rendering to AG Charts

The classes serialize to AG Charts-compatible JSON configuration:

```java
CreateRangeChartParams chartParams = new CreateRangeChartParams()
    .setValueGradientColors("#0066CC", "#FF6600");

// Serializes to:
// {
//   "seriesColorConfig": {
//     "strategy": "valueGradient",
//     "minColor": "#0066CC",
//     "maxColor": "#FF6600"
//   }
// }
```

This JSON is passed directly to `api.createRangeChart(params)` in AG Charts Enterprise.

---

## Best Practices

### 1. Choose the Right Strategy
- **SOLID**: Standard dashboards
- **VALUE_GRADIENT**: Heatmaps, intensity visualizations
- **VALUE_RANGE**: KPI dashboards, status indicators
- **POSITIVE_NEGATIVE**: Financial/variance data
- **CUSTOM_CALLBACK**: Complex business logic

### 2. Color Selection
- Use accessible color combinations (WCAG AA contrast ratios)
- Test with colorblind vision simulators
- Maintain consistency across related charts
- Document your color schemes for team reference

### 3. Performance
- Gradient and threshold strategies are optimized in AG Charts
- Custom callbacks execute per data point - keep logic simple
- For large datasets (>10k points), test callback performance

### 4. User Experience
- Provide a legend explaining the color scheme
- Include tooltips explaining what colors represent
- Consider animation when values change over time
- Ensure colors are intuitive (red=bad, green=good, etc.)

### 5. Testing
- Test edge cases: min/max values, zero, negative numbers
- Verify colors across different browsers
- Test with different data distributions
- Validate accessibility with screen readers

---

## Troubleshooting

**Chart appears but colors don't change:**
- Verify `SeriesColorStrategy` is set correctly
- Check that data values are within expected ranges
- Ensure threshold values in VALUE_RANGE are in ascending order

**Custom callback not working:**
- Verify JavaScript syntax is correct
- Check function signature matches expectations
- Test callback in browser console for debugging
- Ensure function returns valid CSS color strings

**Colors look wrong or washed out:**
- Check hex color values are valid (#RRGGBB format)
- Verify min/max values are set for gradients
- Test with named colors (red, blue) to isolate issues
- Check for CSS color conflicts in page

**Performance issues with custom callbacks:**
- Simplify callback logic
- Use VALUE_RANGE instead of CUSTOM_CALLBACK if possible
- Consider pre-computing colors server-side
- Profile callback execution in browser DevTools

---

## Related Documentation

- [AG Grid Enterprise Guide](./AgGridEnterprise-Guide.md)
- [Chart Integration Guide](./ChartGridIntegration-Guide.md)
- [AG Charts Documentation](https://www.ag-grid.com/javascript-charts/)
- [AG Grid API Reference](https://www.ag-grid.com/javascript-data-grid/api/)
