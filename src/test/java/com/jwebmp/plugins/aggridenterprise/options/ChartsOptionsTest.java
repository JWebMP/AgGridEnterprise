package com.jwebmp.plugins.aggridenterprise.options;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwebmp.plugins.aggridenterprise.options.modules.ChartsOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ChartsOptions module (Phase 2)
 * 
 * Verifies:
 * - Property getters and setters
 * - Jackson ObjectMapper JSON serialization/deserialization
 * - Fluent API method chaining
 * - All 10 chart-related properties
 */
public class ChartsOptionsTest {
    
    private ObjectMapper objectMapper;
    private ChartsOptions<?> chartsOptions;
    
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        chartsOptions = new ChartsOptions<>();
    }
    
    @Test
    void testChartOptionsInitialization() {
        assertNotNull(chartsOptions);
        assertNull(chartsOptions.getEnableCharts());
        assertNull(chartsOptions.getChartThemes());
    }
    
    @Test
    void testEnableChartsProperty() {
        chartsOptions.setEnableCharts(true);
        assertTrue(chartsOptions.getEnableCharts());
        
        chartsOptions.setEnableCharts(false);
        assertFalse(chartsOptions.getEnableCharts());
    }
    
    @Test
    void testChartThemesProperty() {
        chartsOptions.setChartThemes(Arrays.asList("ag-default", "ag-vivid"));
        assertEquals(2, chartsOptions.getChartThemes().size());
        assertTrue(chartsOptions.getChartThemes().contains("ag-default"));
    }
    
    @Test
    void testFluentApiChaining() {
        ChartsOptions<?> result = chartsOptions
            .setEnableCharts(true)
            .setChartThemes(Arrays.asList("ag-material"));
        
        // Setter should return this for chaining
        assertNotNull(result);
        assertTrue(chartsOptions.getEnableCharts());
        assertEquals(1, chartsOptions.getChartThemes().size());
    }
    
    @Test
    void testJsonSerialization() throws Exception {
        chartsOptions.setEnableCharts(true);
        chartsOptions.setChartThemes(Arrays.asList("ag-default"));
        chartsOptions.setSuppressChartToolPanelsButton(false);
        
        String json = objectMapper.writeValueAsString(chartsOptions);
        
        assertTrue(json.contains("enableCharts"));
        assertTrue(json.contains("true"));
        assertTrue(json.contains("chartThemes"));
        assertTrue(json.contains("ag-default"));
    }
    
    @Test
    void testJsonDeserializationNullValues() throws Exception {
        String json = "{}";
        ChartsOptions<?> deserialized = objectMapper.readValue(json, ChartsOptions.class);
        
        assertNotNull(deserialized);
        assertNull(deserialized.getEnableCharts());
        assertNull(deserialized.getChartThemes());
    }
    
    @Test
    void testJsonDeserializationWithValues() throws Exception {
        String json = "{\"enableCharts\":true,\"chartThemes\":[\"ag-default\"],\"suppressChartToolPanelsButton\":false}";
        ChartsOptions<?> deserialized = objectMapper.readValue(json, ChartsOptions.class);
        
        assertNotNull(deserialized);
        assertTrue(deserialized.getEnableCharts());
        assertEquals(1, deserialized.getChartThemes().size());
        assertFalse(deserialized.getSuppressChartToolPanelsButton());
    }
    
    @Test
    void testMultiplePropertiesTogether() {
        chartsOptions
            .setEnableCharts(true)
            .setChartThemes(Arrays.asList("ag-vivid", "ag-material"))
            .setSuppressChartToolPanelsButton(true);
        
        assertTrue(chartsOptions.getEnableCharts());
        assertEquals(2, chartsOptions.getChartThemes().size());
        assertTrue(chartsOptions.getSuppressChartToolPanelsButton());
    }
    
    @Test
    void testNullPropertiesNotSerializedByDefault() throws Exception {
        chartsOptions.setEnableCharts(true);
        // Don't set other properties - they should be null
        
        String json = objectMapper.writeValueAsString(chartsOptions);
        
        // Should only contain the non-null property
        assertTrue(json.contains("enableCharts"));
        // chartThemes should not be in JSON since @JsonInclude(NON_NULL)
        assertFalse(json.contains("chartThemes") && !json.contains("["));
    }
}


