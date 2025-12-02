package com.jwebmp.plugins.aggridenterprise.charts;

import java.util.List;
import java.util.Map;

/**
 * Bridge interface for linking AG Grid data to AG Charts Enterprise.
 *
 * Provides abstraction for synchronizing data, selections, and events
 * between an AG Grid and one or more AG Charts Enterprise instances.
 *
 * Implementation patterns:
 * - One-way: Grid → Charts (charts consume grid data)
 * - Two-way: Grid ↔ Charts (selection sync, cross-filtering)
 * - Event-driven: subscriptions to grid changes propagate to charts
 *
 * @param <T> Row data type (typically a map or POJO representing a grid row)
 */
public interface IChartDataBridge<T>
{
    /**
     * Get the grid row data to be consumed by charts.
     * Charts typically use this to populate series or categories.
     *
     * @return list of row objects from the grid
     */
    List<T> getGridRowData();

    /**
     * Notify the bridge that grid data has changed (add/update/remove rows).
     * Implementations may trigger chart re-rendering or updates.
     *
     * @param updatedData new/updated row list
     */
    void onGridDataChanged(List<T> updatedData);

    /**
     * Notify the bridge that grid selection has changed.
     * This may trigger cross-filtering in linked charts.
     *
     * @param selectedRows list of selected row objects
     */
    void onGridSelectionChanged(List<T> selectedRows);

    /**
     * Notify the bridge that a chart selection or interaction occurred.
     * This may trigger grid filtering or selection updates.
     *
     * @param chartId identifier of the chart that triggered the event
     * @param dataPoints selected/highlighted data points from the chart
     */
    void onChartInteraction(String chartId, List<Map<String, Object>> dataPoints);

    /**
     * Register a listener for chart-to-grid updates.
     * Called when a chart interaction should affect the grid.
     *
     * @param listener callback invoked on chart events
     */
    void addChartInteractionListener(ChartInteractionListener listener);

    /**
     * Remove a previously registered chart interaction listener.
     *
     * @param listener callback to remove
     */
    void removeChartInteractionListener(ChartInteractionListener listener);

    /**
     * Get field mapping between grid columns and chart data properties.
     * Allows defining how grid fields map to chart series/categories.
     *
     * Example:
     * "region" -> "x" (category axis)
     * "sales" -> "y" (numeric axis)
     * "productId" -> "id" (key for cross-filtering)
     *
     * @return map of grid field names to chart property names
     */
    Map<String, String> getFieldMapping();

    /**
     * Set field mapping for grid-to-chart data projection.
     *
     * @param fieldMapping mapping of grid field → chart property
     */
    void setFieldMapping(Map<String, String> fieldMapping);

    /**
     * Listener for chart interactions that may affect the grid.
     * Implementations decide whether to propagate changes to the grid.
     */
    interface ChartInteractionListener
    {
        /**
         * Called when a chart interaction occurs.
         *
         * @param chartId source chart identifier
         * @param dataPoints selected/highlighted data points
         */
        void onChartInteraction(String chartId, List<Map<String, Object>> dataPoints);
    }
}
