package com.jwebmp.plugins.aggridenterprise.charts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for dynamic series coloring feature.
 * 
 * Tests cover:
 * - SeriesColorStrategy enum
 * - DataPointStyle class with fluent API and Jackson serialization
 * - SeriesColorConfiguration class with all 5 strategies
 * - CreateRangeChartParams integration with color configuration
 * - Convenience methods on CreateRangeChartParams
 * - JSON serialization/deserialization roundtrips
 */
public class DynamicSeriesColoringTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    // ============================================
    // SeriesColorStrategy Tests
    // ============================================

    @Test
    void testSeriesColorStrategyCount() {
        SeriesColorStrategy[] strategies = SeriesColorStrategy.values();
        assertEquals(5, strategies.length);
    }

    @Test
    void testSolidStrategy() {
        assertEquals("solid", SeriesColorStrategy.SOLID.getStrategyName());
        assertEquals("solid", SeriesColorStrategy.SOLID.toString());
    }

    @Test
    void testValueGradientStrategy() {
        assertEquals("valueGradient", SeriesColorStrategy.VALUE_GRADIENT.getStrategyName());
    }

    @Test
    void testValueRangeStrategy() {
        assertEquals("valueRange", SeriesColorStrategy.VALUE_RANGE.getStrategyName());
    }

    @Test
    void testPositiveNegativeStrategy() {
        assertEquals("positiveNegative", SeriesColorStrategy.POSITIVE_NEGATIVE.getStrategyName());
    }

    @Test
    void testCustomCallbackStrategy() {
        assertEquals("customCallback", SeriesColorStrategy.CUSTOM_CALLBACK.getStrategyName());
    }

    @Test
    void testStrategyToString() {
        for (SeriesColorStrategy strategy : SeriesColorStrategy.values()) {
            assertNotNull(strategy.toString());
            assertFalse(strategy.toString().isEmpty());
        }
    }

    // ============================================
    // DataPointStyle Tests
    // ============================================

    @Test
    void testDataPointStyleDefaultConstructor() {
        DataPointStyle style = new DataPointStyle();
        assertNotNull(style);
        assertNull(style.getFill());
        assertNull(style.getStroke());
        assertNull(style.getOpacity());
        assertNull(style.getStrokeWidth());
        assertNull(style.getSize());
    }

    @Test
    void testDataPointStyleConstructorWithFill() {
        DataPointStyle style = new DataPointStyle("#FF0000");
        assertEquals("#FF0000", style.getFill());
        assertNull(style.getStroke());
    }

    @Test
    void testDataPointStyleFillProperty() {
        DataPointStyle style = new DataPointStyle();
        style.setFill("#0066CC");
        assertEquals("#0066CC", style.getFill());
    }

    @Test
    void testDataPointStyleStrokeProperty() {
        DataPointStyle style = new DataPointStyle();
        style.setStroke("#000000");
        assertEquals("#000000", style.getStroke());
    }

    @Test
    void testDataPointStyleOpacityProperty() {
        DataPointStyle style = new DataPointStyle();
        style.setOpacity(0.8);
        assertEquals(0.8, style.getOpacity());
    }

    @Test
    void testDataPointStyleStrokeWidthProperty() {
        DataPointStyle style = new DataPointStyle();
        style.setStrokeWidth(2);
        assertEquals(2, style.getStrokeWidth());
    }

    @Test
    void testDataPointStyleSizeProperty() {
        DataPointStyle style = new DataPointStyle();
        style.setSize(6);
        assertEquals(6, style.getSize());
    }

    @Test
    void testDataPointStyleFluentApi() {
        DataPointStyle style = new DataPointStyle()
            .setFill("#FF0000")
            .setStroke("#000000")
            .setOpacity(0.9)
            .setStrokeWidth(3)
            .setSize(8);

        assertEquals("#FF0000", style.getFill());
        assertEquals("#000000", style.getStroke());
        assertEquals(0.9, style.getOpacity());
        assertEquals(3, style.getStrokeWidth());
        assertEquals(8, style.getSize());
    }

    @Test
    void testDataPointStyleJsonSerialization() throws Exception {
        DataPointStyle style = new DataPointStyle()
            .setFill("#FF0000")
            .setStroke("#000000")
            .setOpacity(0.8)
            .setStrokeWidth(2)
            .setSize(6);

        String json = objectMapper.writeValueAsString(style);

        assertTrue(json.contains("FF0000"));
        assertTrue(json.contains("fill"));
        assertTrue(json.contains("stroke"));
        assertTrue(json.contains("opacity"));
        assertTrue(json.contains("strokeWidth"));
        assertTrue(json.contains("size"));
    }

    @Test
    void testDataPointStyleJsonDeserialization() throws Exception {
        String json = "{\"fill\":\"#FF0000\",\"stroke\":\"#000000\",\"opacity\":0.8,\"strokeWidth\":2,\"size\":6}";
        DataPointStyle style = objectMapper.readValue(json, DataPointStyle.class);

        assertEquals("#FF0000", style.getFill());
        assertEquals("#000000", style.getStroke());
        assertEquals(0.8, style.getOpacity());
        assertEquals(2, style.getStrokeWidth());
        assertEquals(6, style.getSize());
    }

    // ============================================
    // SeriesColorConfiguration Tests
    // ============================================

    @Test
    void testSeriesColorConfigurationDefaultConstructor() {
        SeriesColorConfiguration config = new SeriesColorConfiguration();
        assertNotNull(config);
        assertNull(config.getStrategy());
    }

    @Test
    void testSeriesColorConfigurationConstructorWithStrategy() {
        SeriesColorConfiguration config = new SeriesColorConfiguration(SeriesColorStrategy.VALUE_GRADIENT);
        assertEquals("valueGradient", config.getStrategy());
    }

    @Test
    void testSetStrategyEnum() {
        SeriesColorConfiguration config = new SeriesColorConfiguration();
        config.setStrategy(SeriesColorStrategy.SOLID);
        assertEquals("solid", config.getStrategy());
    }

    @Test
    void testSetStrategyString() {
        SeriesColorConfiguration config = new SeriesColorConfiguration();
        config.setStrategy("custom");
        assertEquals("custom", config.getStrategy());
    }

    @Test
    void testSolidStrategyConfiguration() {
        SeriesColorConfiguration config = new SeriesColorConfiguration(SeriesColorStrategy.SOLID)
            .setColorPalette(java.util.Arrays.asList("#0066CC", "#FF6600", "#00AA00"));

        assertEquals("solid", config.getStrategy());
        assertEquals(3, config.getColorPalette().size());
    }

    @Test
    void testValueGradientStrategyConfiguration() {
        SeriesColorConfiguration config = new SeriesColorConfiguration()
            .setValueGradientColors("#FFFFFF", "#FF0000");

        assertEquals("valueGradient", config.getStrategy());
        assertEquals("#FFFFFF", config.getMinColor());
        assertEquals("#FF0000", config.getMaxColor());
    }

    @Test
    void testValueGradientConvenienceMethod() {
        SeriesColorConfiguration config = new SeriesColorConfiguration()
            .setValueGradientColors("#0066CC", "#FF6600");

        assertEquals(SeriesColorStrategy.VALUE_GRADIENT.getStrategyName(), config.getStrategy());
        assertEquals("#0066CC", config.getMinColor());
        assertEquals("#FF6600", config.getMaxColor());
    }

    @Test
    void testValueRangeStrategyConfiguration() {
        Map<Number, String> thresholds = new LinkedHashMap<>();
        thresholds.put(0, "#00AA00");
        thresholds.put(500, "#FFAA00");
        thresholds.put(1000, "#FF0000");

        SeriesColorConfiguration config = new SeriesColorConfiguration()
            .setStrategy(SeriesColorStrategy.VALUE_RANGE)
            .setThresholds(thresholds);

        assertEquals("valueRange", config.getStrategy());
        assertEquals(3, config.getThresholds().size());
        assertEquals("#00AA00", config.getThresholds().get(0));
        assertEquals("#FF0000", config.getThresholds().get(1000));
    }

    @Test
    void testAddThresholdMethod() {
        SeriesColorConfiguration config = new SeriesColorConfiguration()
            .addThreshold(0, "#00AA00")
            .addThreshold(500, "#FFAA00")
            .addThreshold(1000, "#FF0000");

        assertEquals(3, config.getThresholds().size());
    }

    @Test
    void testValueRangeConvenienceMethod() {
        Map<Number, String> thresholds = new LinkedHashMap<>();
        thresholds.put(0, "#00AA00");
        thresholds.put(500, "#FFAA00");
        thresholds.put(1000, "#FF0000");

        SeriesColorConfiguration config = new SeriesColorConfiguration()
            .setValueRangeColors(thresholds);

        assertEquals(SeriesColorStrategy.VALUE_RANGE.getStrategyName(), config.getStrategy());
        assertEquals(3, config.getThresholds().size());
    }

    @Test
    void testPositiveNegativeStrategy2Params() {
        SeriesColorConfiguration config = new SeriesColorConfiguration()
            .setPositiveNegativeColors("#00AA00", "#FF0000");

        assertEquals("positiveNegative", config.getStrategy());
        assertEquals("#00AA00", config.getPositiveColor());
        assertEquals("#FF0000", config.getNegativeColor());
        assertNull(config.getZeroColor());
    }

    @Test
    void testPositiveNegativeStrategy3Params() {
        SeriesColorConfiguration config = new SeriesColorConfiguration()
            .setPositiveNegativeColors("#00AA00", "#FF0000", "#CCCCCC");

        assertEquals("positiveNegative", config.getStrategy());
        assertEquals("#00AA00", config.getPositiveColor());
        assertEquals("#FF0000", config.getNegativeColor());
        assertEquals("#CCCCCC", config.getZeroColor());
    }

    @Test
    void testCustomCallbackStrategyConfiguration() {
        String jsFunction = "(value) => value > 100 ? '#FF0000' : '#00AA00'";
        SeriesColorConfiguration config = new SeriesColorConfiguration()
            .setCustomColorFunction(jsFunction);

        assertEquals("customCallback", config.getStrategy());
        assertEquals(jsFunction, config.getColorFunctionRaw());
    }

    @Test
    void testSeriesColorConfigurationFluentApi() {
        SeriesColorConfiguration config = new SeriesColorConfiguration()
            .setStrategy(SeriesColorStrategy.VALUE_GRADIENT)
            .setMinColor("#FFFFFF")
            .setMaxColor("#FF0000")
            .setMinValue(0)
            .setMaxValue(1000);

        assertEquals("valueGradient", config.getStrategy());
        assertEquals("#FFFFFF", config.getMinColor());
        assertEquals("#FF0000", config.getMaxColor());
    }

    @Test
    void testSeriesColorConfigurationJsonSerialization() throws Exception {
        SeriesColorConfiguration config = new SeriesColorConfiguration()
            .setValueGradientColors("#FFFFFF", "#FF0000");

        String json = objectMapper.writeValueAsString(config);

        assertTrue(json.contains("valueGradient"));
        assertTrue(json.contains("FFFFFF"));
        assertTrue(json.contains("FF0000"));
        assertTrue(json.contains("minColor"));
        assertTrue(json.contains("maxColor"));
    }

    @Test
    void testSeriesColorConfigurationJsonDeserialization() throws Exception {
        String json = "{\"strategy\":\"valueGradient\",\"minColor\":\"#FFFFFF\",\"maxColor\":\"#FF0000\"}";
        SeriesColorConfiguration config = objectMapper.readValue(json, SeriesColorConfiguration.class);

        assertEquals("valueGradient", config.getStrategy());
        assertEquals("#FFFFFF", config.getMinColor());
        assertEquals("#FF0000", config.getMaxColor());
    }

    // ============================================
    // CreateRangeChartParams Integration Tests
    // ============================================

    @Test
    void testCreateRangeChartParamsSetGetSeriesColorConfig() {
        CreateRangeChartParams params = new CreateRangeChartParams();
        SeriesColorConfiguration config = new SeriesColorConfiguration(SeriesColorStrategy.SOLID);

        params.setSeriesColorConfig(config);
        assertNotNull(params.getSeriesColorConfig());
        assertEquals("solid", params.getSeriesColorConfig().getStrategy());
    }

    @Test
    void testCreateRangeChartParamsValueGradientConvenience() {
        CreateRangeChartParams params = new CreateRangeChartParams()
            .setValueGradientColors("#FFFFFF", "#FF0000");

        assertNotNull(params.getSeriesColorConfig());
        assertEquals("valueGradient", params.getSeriesColorConfig().getStrategy());
        assertEquals("#FFFFFF", params.getSeriesColorConfig().getMinColor());
        assertEquals("#FF0000", params.getSeriesColorConfig().getMaxColor());
    }

    @Test
    void testCreateRangeChartParamsValueRangeConvenience() {
        Map<Number, String> thresholds = new LinkedHashMap<>();
        thresholds.put(0, "#00AA00");
        thresholds.put(500, "#FFAA00");

        CreateRangeChartParams params = new CreateRangeChartParams()
            .setValueRangeColors(thresholds);

        assertNotNull(params.getSeriesColorConfig());
        assertEquals("valueRange", params.getSeriesColorConfig().getStrategy());
        assertEquals(2, params.getSeriesColorConfig().getThresholds().size());
    }

    @Test
    void testCreateRangeChartParamsPositiveNegativeConvenience2Params() {
        CreateRangeChartParams params = new CreateRangeChartParams()
            .setPositiveNegativeColors("#00AA00", "#FF0000");

        assertNotNull(params.getSeriesColorConfig());
        assertEquals("positiveNegative", params.getSeriesColorConfig().getStrategy());
        assertEquals("#00AA00", params.getSeriesColorConfig().getPositiveColor());
        assertEquals("#FF0000", params.getSeriesColorConfig().getNegativeColor());
    }

    @Test
    void testCreateRangeChartParamsPositiveNegativeConvenience3Params() {
        CreateRangeChartParams params = new CreateRangeChartParams()
            .setPositiveNegativeColors("#00AA00", "#FF0000", "#CCCCCC");

        assertNotNull(params.getSeriesColorConfig());
        assertEquals("positiveNegative", params.getSeriesColorConfig().getStrategy());
        assertEquals("#CCCCCC", params.getSeriesColorConfig().getZeroColor());
    }

    @Test
    void testCreateRangeChartParamsCustomColorFunctionConvenience() {
        String jsFunc = "(value) => value > 100 ? '#FF0000' : '#00AA00'";
        CreateRangeChartParams params = new CreateRangeChartParams()
            .setCustomColorFunction(jsFunc);

        assertNotNull(params.getSeriesColorConfig());
        assertEquals("customCallback", params.getSeriesColorConfig().getStrategy());
        assertEquals(jsFunc, params.getSeriesColorConfig().getColorFunctionRaw());
    }

    @Test
    void testCreateRangeChartParamsFluentApiChaining() {
        CreateRangeChartParams params = new CreateRangeChartParams()
            .setChartType(GridChartType.GROUPED_COLUMN)
            .setValueGradientColors("#FFFFFF", "#FF0000")
            .setSuppressChartRanges(true);

        assertNotNull(params.getChartType());
        assertNotNull(params.getSeriesColorConfig());
        assertTrue(params.getSuppressChartRanges());
    }

    @Test
    void testCreateRangeChartParamsAutoCreationOfConfig() {
        CreateRangeChartParams params1 = new CreateRangeChartParams();
        assertNull(params1.getSeriesColorConfig());

        params1.setValueGradientColors("#FFFFFF", "#FF0000");
        assertNotNull(params1.getSeriesColorConfig());
    }

    @Test
    void testCreateRangeChartParamsJsonSerializationWithChartParams() throws Exception {
        CreateRangeChartParams params = new CreateRangeChartParams()
            .setChartType(GridChartType.GROUPED_COLUMN)
            .setValueGradientColors("#FFFFFF", "#FF0000");

        String json = objectMapper.writeValueAsString(params);

        assertTrue(json.contains("chartType"));
        assertTrue(json.contains("groupedColumn"));
        assertTrue(json.contains("seriesColorConfig"));
        assertTrue(json.contains("valueGradient"));
    }

    @Test
    void testCreateRangeChartParamsJsonDeserializationWithChartParams() throws Exception {
        String json = "{\"chartType\":\"groupedColumn\",\"seriesColorConfig\":{\"strategy\":\"valueGradient\",\"minColor\":\"#FFFFFF\",\"maxColor\":\"#FF0000\"}}";
        CreateRangeChartParams params = objectMapper.readValue(json, CreateRangeChartParams.class);

        assertEquals("groupedColumn", params.getChartType());
        assertNotNull(params.getSeriesColorConfig());
        assertEquals("valueGradient", params.getSeriesColorConfig().getStrategy());
    }

    // ============================================
    // Real-World Scenarios
    // ============================================

    @Test
    void testScenarioSalesRevenueGradient() {
        CreateRangeChartParams chart = new CreateRangeChartParams()
            .setChartType(GridChartType.GROUPED_COLUMN)
            .setValueGradientColors("#E8F4F8", "#0066CC");

        assertNotNull(chart.getSeriesColorConfig());
        assertEquals(SeriesColorStrategy.VALUE_GRADIENT.getStrategyName(), 
            chart.getSeriesColorConfig().getStrategy());
    }

    @Test
    void testScenarioKPIPerformanceThresholds() {
        Map<Number, String> performanceRanges = new LinkedHashMap<>();
        performanceRanges.put(0, "#CC0000");
        performanceRanges.put(60, "#FFAA00");
        performanceRanges.put(80, "#00AA00");

        CreateRangeChartParams chart = new CreateRangeChartParams()
            .setChartType(GridChartType.GROUPED_BAR)
            .setValueRangeColors(performanceRanges);

        assertNotNull(chart.getSeriesColorConfig());
        assertEquals(3, chart.getSeriesColorConfig().getThresholds().size());
    }

    @Test
    void testScenarioFinancialProfitLoss() {
        CreateRangeChartParams chart = new CreateRangeChartParams()
            .setChartType(GridChartType.GROUPED_COLUMN)
            .setPositiveNegativeColors("#0066CC", "#CC0000", "#999999");

        assertEquals("positiveNegative", chart.getSeriesColorConfig().getStrategy());
        assertEquals("#0066CC", chart.getSeriesColorConfig().getPositiveColor());
        assertEquals("#CC0000", chart.getSeriesColorConfig().getNegativeColor());
    }

    @Test
    void testScenarioMonitoringCustomSLA() {
        String slaFunction = "(value) => {\n" +
            "  if (value < 100) return '#00AA00';\n" +
            "  if (value < 500) return '#FFAA00';\n" +
            "  return '#FF0000';\n" +
            "}";

        CreateRangeChartParams chart = new CreateRangeChartParams()
            .setChartType(GridChartType.GROUPED_COLUMN)
            .setCustomColorFunction(slaFunction);

        assertEquals("customCallback", chart.getSeriesColorConfig().getStrategy());
        assertTrue(chart.getSeriesColorConfig().getColorFunctionRaw().contains("value < 100"));
    }

    @Test
    void testCompleteChartParamsRendering() throws Exception {
        ChartParamsCellRange cellRange = new ChartParamsCellRange()
            .setRowStartIndex(0)
            .setRowEndIndex(10)
            .setColumns(java.util.Arrays.asList("sales", "region"));

        CreateRangeChartParams params = new CreateRangeChartParams()
            .setChartType(GridChartType.GROUPED_COLUMN)
            .setCellRange(cellRange)
            .setValueGradientColors("#FFFFFF", "#FF0000")
            .setSuppressChartRanges(false);

        String json = objectMapper.writeValueAsString(params);

        assertTrue(json.contains("chartType"));
        assertTrue(json.contains("cellRange"));
        assertTrue(json.contains("seriesColorConfig"));
        assertTrue(json.contains("groupedColumn"));
        assertTrue(json.contains("valueGradient"));
    }

    // ============================================
    // Edge Cases
    // ============================================

    @Test
    void testNullStrategyInConstructor() {
        SeriesColorConfiguration config = new SeriesColorConfiguration(null);
        assertNull(config.getStrategy());
    }

    @Test
    void testOpacityEdgeValues() {
        DataPointStyle style = new DataPointStyle()
            .setOpacity(0.0)  
            .setOpacity(1.0);

        assertEquals(1.0, style.getOpacity());
    }

    @Test
    void testNegativeThresholds() {
        Map<Number, String> thresholds = new LinkedHashMap<>();
        thresholds.put(-1000, "#FF0000");
        thresholds.put(0, "#CCCCCC");
        thresholds.put(1000, "#00AA00");

        SeriesColorConfiguration config = new SeriesColorConfiguration()
            .setThresholds(thresholds);

        assertEquals(3, config.getThresholds().size());
    }
}
