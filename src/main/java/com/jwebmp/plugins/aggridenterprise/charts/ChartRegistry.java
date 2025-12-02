package com.jwebmp.plugins.aggridenterprise.charts;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registry and factory for managing AG Charts Enterprise instances linked to AG Grid data.
 *
 * Responsibilities:
 * - Register charts by unique ID
 * - Manage chart configuration and state
 * - Coordinate data flow from grid to charts
 * - Handle chart-to-grid interactions (cross-filtering, selection sync)
 *
 * Thread-safe for concurrent access from multiple components.
 */
public class ChartRegistry
{
    private static final ChartRegistry INSTANCE = new ChartRegistry();

    private final Map<String, ChartConfiguration> charts = new ConcurrentHashMap<>();
    private final Map<String, IChartDataBridge<?>> bridges = new ConcurrentHashMap<>();
    private final Map<String, List<String>> gridChartMappings = new ConcurrentHashMap<>();
    private final List<ChartRegistryListener> listeners = Collections.synchronizedList(new ArrayList<>());

    /**
     * Get the singleton registry instance.
     *
     * @return global ChartRegistry
     */
    public static ChartRegistry getInstance()
    {
        return INSTANCE;
    }

    /**
     * Register a chart configuration.
     * Each chart must have a unique ID within its grid context.
     *
     * @param chartId unique identifier for the chart
     * @param config chart configuration (type, data source, etc.)
     */
    public void registerChart(String chartId, ChartConfiguration config)
    {
        charts.put(chartId, config);
        notifyListeners(listener -> listener.onChartRegistered(chartId, config));
    }

    /**
     * Unregister a chart by ID.
     *
     * @param chartId chart identifier to remove
     */
    public void unregisterChart(String chartId)
    {
        ChartConfiguration removed = charts.remove(chartId);
        if (removed != null)
        {
            notifyListeners(listener -> listener.onChartUnregistered(chartId));
        }
    }

    /**
     * Get a registered chart configuration.
     *
     * @param chartId chart identifier
     * @return ChartConfiguration or null if not found
     */
    public ChartConfiguration getChart(String chartId)
    {
        return charts.get(chartId);
    }

    /**
     * Get all registered charts.
     *
     * @return collection of all registered configurations
     */
    public Collection<ChartConfiguration> getAllCharts()
    {
        return new ArrayList<>(charts.values());
    }

    /**
     * Register a data bridge linking a grid to charts.
     * The bridge handles data sync between grid and chart(s).
     *
     * @param bridgeId unique identifier for the bridge
     * @param bridge data bridge implementation
     */
    public void registerDataBridge(String bridgeId, IChartDataBridge<?> bridge)
    {
        bridges.put(bridgeId, bridge);
        notifyListeners(listener -> listener.onBridgeRegistered(bridgeId));
    }

    /**
     * Get a registered data bridge.
     *
     * @param bridgeId bridge identifier
     * @return IChartDataBridge or null if not found
     */
    public IChartDataBridge<?> getDataBridge(String bridgeId)
    {
        return bridges.get(bridgeId);
    }

    /**
     * Link a grid to a set of charts.
     * Used to establish the relationship for data sync and cross-filtering.
     *
     * @param gridId identifier of the grid
     * @param chartIds identifiers of charts to link to this grid
     */
    public void linkChartsToGrid(String gridId, List<String> chartIds)
    {
        gridChartMappings.put(gridId, new ArrayList<>(chartIds));
        notifyListeners(listener -> listener.onChartsLinkedToGrid(gridId, chartIds));
    }

    /**
     * Get all charts linked to a grid.
     *
     * @param gridId grid identifier
     * @return list of chart IDs linked to this grid
     */
    public List<String> getLinkedCharts(String gridId)
    {
        return new ArrayList<>(gridChartMappings.getOrDefault(gridId, new ArrayList<>()));
    }

    /**
     * Remove the link between a grid and its charts.
     *
     * @param gridId grid identifier
     */
    public void unlinkChartsFromGrid(String gridId)
    {
        gridChartMappings.remove(gridId);
        notifyListeners(listener -> listener.onChartsUnlinkedFromGrid(gridId));
    }

    /**
     * Add a listener for registry events (chart registration, linking, etc.).
     *
     * @param listener callback to invoke on events
     */
    public void addListener(ChartRegistryListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Remove a previously added listener.
     *
     * @param listener callback to remove
     */
    public void removeListener(ChartRegistryListener listener)
    {
        listeners.remove(listener);
    }

    /**
     * Clear all registrations (charts, bridges, mappings).
     * Use with caution; typically only in test or cleanup scenarios.
     */
    public void clear()
    {
        charts.clear();
        bridges.clear();
        gridChartMappings.clear();
        notifyListeners(ChartRegistryListener::onRegistryCleared);
    }

    private void notifyListeners(java.util.function.Consumer<ChartRegistryListener> action)
    {
        listeners.forEach(action);
    }

    /**
     * Listener for chart registry events.
     * Implementations can react to chart registration/unregistration
     * and linking events to maintain UI or application state.
     */
    public interface ChartRegistryListener
    {
        /**
         * Called when a chart is registered.
         *
         * @param chartId chart identifier
         * @param config chart configuration
         */
        void onChartRegistered(String chartId, ChartConfiguration config);

        /**
         * Called when a chart is unregistered.
         *
         * @param chartId chart identifier
         */
        void onChartUnregistered(String chartId);

        /**
         * Called when a data bridge is registered.
         *
         * @param bridgeId bridge identifier
         */
        void onBridgeRegistered(String bridgeId);

        /**
         * Called when charts are linked to a grid.
         *
         * @param gridId grid identifier
         * @param chartIds chart identifiers
         */
        void onChartsLinkedToGrid(String gridId, List<String> chartIds);

        /**
         * Called when charts are unlinked from a grid.
         *
         * @param gridId grid identifier
         */
        void onChartsUnlinkedFromGrid(String gridId);

        /**
         * Called when the registry is cleared.
         */
        void onRegistryCleared();
    }
}
