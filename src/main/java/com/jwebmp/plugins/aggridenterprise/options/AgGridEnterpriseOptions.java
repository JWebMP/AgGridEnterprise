package com.jwebmp.plugins.aggridenterprise.options;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.jwebmp.plugins.aggrid.options.AgGridColumnDef;
import com.jwebmp.plugins.aggrid.options.AgGridOptions;
import com.jwebmp.plugins.aggridenterprise.options.mapping.AgGridColDefEnterpriseMapper;
import com.jwebmp.plugins.aggridenterprise.options.modules.AdvancedFilteringOptions;
import com.jwebmp.plugins.aggridenterprise.options.modules.AggregationOptions;
import com.jwebmp.plugins.aggridenterprise.options.modules.ChartsOptions;
import com.jwebmp.plugins.aggridenterprise.options.modules.PivotingOptions;
import com.jwebmp.plugins.aggridenterprise.options.modules.RangeSelectionOptions;
import com.jwebmp.plugins.aggridenterprise.options.modules.RowGroupingOptions;
import com.jwebmp.plugins.aggridenterprise.options.modules.ServerSideRowModelOptions;
import com.jwebmp.plugins.aggridenterprise.options.modules.SideBarAndStatusBarOptions;
import org.jspecify.annotations.Nullable;

/**
 * Enterprise-only options layered on top of community AgGridOptions
 */
@SuppressWarnings("unchecked")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgGridEnterpriseOptions<J extends AgGridEnterpriseOptions<J>> extends AgGridOptions<J>
{

    @Override
    public AgGridEnterpriseColumnDef<?> getDefaultColDef()
    {
        // Retrieve base definition (may be community or enterprise). If none, return null for parity
        // with community behavior and to avoid introducing side-effects.
        AgGridColumnDef<?> base = super.getDefaultColDef();
        if (base == null)
        {
										super.setDefaultColDef(new AgGridEnterpriseColumnDef<>());
										base = super.getDefaultColDef();
        }

        AgGridEnterpriseColumnDef<?> ent;
        if (base instanceof AgGridEnterpriseColumnDef<?> enterprise)
        {
            ent = enterprise;
        }
        else
        {
            // Convert community defaultColDef into an enterprise one using MapStruct mapper
            ent = AgGridColDefEnterpriseMapper.INSTANCE.toEnterpriseColDef(base);
        }

        // Defensive copy: return a new instance so callers cannot mutate internal state
        try
        {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            byte[] json = mapper.writeValueAsBytes(ent);
            return mapper.readValue(json, AgGridEnterpriseColumnDef.class);
        }
        catch (Exception e)
        {
            return new AgGridEnterpriseColumnDef<>();
        }
    }

    // Backwards-compatibility convenience getters/setters have been removed.
    // Use module APIs directly, e.g. configureRowGrouping().setSuppressAggFuncInHeader(true)

    // ===== PHASE 2: MODULAR ENTERPRISE OPTIONS (8 Modules) =====
    // Using @JsonUnwrapped for backward-compatible JSON serialization (all properties flatten to parent)

    /**
     * Integrated Charts Configuration Module
     * Encapsulates all chart-related options (enableCharts, chartThemes, suppressChartToolPanelsButton, etc.)
     */
    @JsonUnwrapped
    private ChartsOptions<?> charts = new ChartsOptions<>();

    /**
     * Server-Side Row Model Configuration Module
     * Encapsulates all SSRM options (serverSideDatasource, cacheBlockSize, suppressServerSideInfiniteScroll, etc.)
     */
    @JsonUnwrapped
    private ServerSideRowModelOptions<?> serverSideRowModel = new ServerSideRowModelOptions<>();

    // Note: Row Grouping is provided by the community superclass via @JsonUnwrapped.
    // No duplicate field is declared here.

    /**
     * Aggregation Functions Configuration Module
     * Encapsulates aggregation configuration (aggFuncs, aggregateOnlyChangedColumns, groupAggFiltering, etc.)
     */
    @JsonUnwrapped
    private AggregationOptions<?> aggregation = new AggregationOptions<>();

    /**
     * Pivot Mode Configuration Module
     * Encapsulates all pivot options (pivotMode, pivotRowTotals, processPivotResultColDef, etc.)
     */
    @JsonUnwrapped
    private PivotingOptions<?> pivoting = new PivotingOptions<>();

    /**
     * Advanced Filtering Configuration Module
     * Encapsulates advanced filter builder and cell selection options
     */
    @JsonUnwrapped
    private AdvancedFilteringOptions<?> advancedFiltering = new AdvancedFilteringOptions<>();

    /**
     * SideBar & StatusBar Configuration Module
     * Encapsulates UI panel configuration (sideBar, statusBar, allowDragFromColumnsToolPanel)
     */
    @JsonUnwrapped
    private SideBarAndStatusBarOptions<?> sideBarAndStatusBar = new SideBarAndStatusBarOptions<>();

    /**
     * Range Selection Configuration Module
     * Encapsulates range selection options (enableRangeSelection)
     */
    @JsonUnwrapped
    private RangeSelectionOptions<?> rangeSelection = new RangeSelectionOptions<>();

    // ===== Module Convenience Accessor Methods =====

    /**
     * Configure integrated charts options.
     *
     * @return ChartsOptions module for fluent chaining
     */
    public ChartsOptions<?> configureCharts()
    {
        return charts;
    }

    /**
     * Configure server-side row model options.
     *
     * @return ServerSideRowModelOptions module for fluent chaining
     */
    public ServerSideRowModelOptions<?> serverSideRowModelOptions()
    {
        return serverSideRowModel;
    }

    // Row grouping options are available via the superclass methods:
    // - getRowGrouping()
    // - configureRowGrouping()

    /**
     * Configure aggregation function options.
     *
     * @return AggregationOptions module for fluent chaining
     */
    public AggregationOptions<?> configureAggregation()
    {
        return aggregation;
    }

    /**
     * Configure pivot mode options.
     *
     * @return PivotingOptions module for fluent chaining
     */
    public PivotingOptions<?> configurePivoting()
    {
        return pivoting;
    }

    /**
     * Configure advanced filtering options.
     *
     * @return AdvancedFilteringOptions module for fluent chaining
     */
    public AdvancedFilteringOptions<?> configureAdvancedFiltering()
    {
        return advancedFiltering;
    }

    /**
     * Configure sidebar and statusbar options.
     *
     * @return SideBarAndStatusBarOptions module for fluent chaining
     */
    public SideBarAndStatusBarOptions<?> configureSideBarAndStatusBar()
    {
        return sideBarAndStatusBar;
    }

    /**
     * Configure range selection options.
     *
     * @return RangeSelectionOptions module for fluent chaining
     */
    public RangeSelectionOptions<?> configureRangeSelection()
    {
        return rangeSelection;
    }

    // ===== END PHASE 2 MODULAR OPTIONS =====
    // All 83 enterprise properties replaced by 8 modular components above
    // JSON serialization remains identical via @JsonUnwrapped pattern

    // ===== Inner Classes & Enums (extracted to separate files) =====

    /**
     * Strict object type for chartToolPanelsDef.
     * Mirrors AG Grid structure allowing panel ordering and default panel selection.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ChartToolPanelsDef
    {
        @JsonProperty("panels")
        private java.util.List<String> panels;

        @JsonProperty("defaultToolPanel")
        private String defaultToolPanel;

        @JsonProperty("settingsPanel")
        private SettingsPanel settingsPanel;

        @JsonProperty("dataPanel")
        private DataPanel dataPanel;

        @JsonProperty("formatPanel")
        private FormatPanel formatPanel;
        
        public java.util.List<String> getPanels()
        {
            return panels;
        }

        public SettingsPanel getSettingsPanel() {return settingsPanel;}

        public ChartToolPanelsDef setSettingsPanel(SettingsPanel settingsPanel)
        {
            this.settingsPanel = settingsPanel;
            return this;
        }

        public DataPanel getDataPanel() {return dataPanel;}

        public ChartToolPanelsDef setDataPanel(DataPanel dataPanel)
        {
            this.dataPanel = dataPanel;
            return this;
        }

        public FormatPanel getFormatPanel() {return formatPanel;}

        public ChartToolPanelsDef setFormatPanel(FormatPanel formatPanel)
        {
            this.formatPanel = formatPanel;
            return this;
        }

        /**
         * Backward-compatible: accept a list of ToolPanel objects but store only their id strings for serialization.
         */
        public ChartToolPanelsDef setPanels(java.util.List<ToolPanel> panels)
        {
            if (panels == null)
            {
                this.panels = null;
            }
            else
            {
                java.util.ArrayList<String> ids = new java.util.ArrayList<>(panels.size());
                for (ToolPanel p : panels)
                {
                    if (p != null && p.getId() != null)
                    {
                        ids.add(p.getId());
                    }
                }
                this.panels = ids;
            }
            return this;
        }

        /**
         * Preferred: set panels by their string ids directly, e.g., ['settings','data','format'].
         */
        public ChartToolPanelsDef setPanelsByIds(java.util.List<String> ids)
        {
            this.panels = ids;
            return this;
        }

        /**
         * Convenience: set panels by enum ids.
         */
        public ChartToolPanelsDef setPanelsByEnumIds(java.util.List<ToolPanelId> panelIds)
        {
            if (panelIds == null)
            {
                this.panels = null;
            }
            else
            {
                java.util.ArrayList<String> ids = new java.util.ArrayList<>(panelIds.size());
                for (ToolPanelId id : panelIds)
                {
                    if (id != null)
                    {
                        ids.add(id.getJson());
                    }
                }
                this.panels = ids;
            }
            return this;
        }

        public String getDefaultToolPanel()
        {
            return defaultToolPanel;
        }

        public ChartToolPanelsDef setDefaultToolPanel(ToolPanelId id)
        {
            this.defaultToolPanel = id == null ? null : id.getJson();
            return this;
        }

        public ChartToolPanelsDef setDefaultToolPanel(String id)
        {
            this.defaultToolPanel = id;
            return this;
        }

        /**
         * Definition of a single tool panel. You can provide custom labels/icons if desired.
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class ToolPanel
        {
            @JsonProperty("id")
            private String id;

            @JsonProperty("labelDefault")
            private String labelDefault;

            @JsonProperty("labelKey")
            private String labelKey;

            @JsonProperty("icon")
            private String icon;

            public String getId() {return id;}

            public ToolPanel setId(ToolPanelId id)
            {
                this.id = id == null ? null : id.getJson();
                return this;
            }

            public ToolPanel setId(String id)
            {
                this.id = id;
                return this;
            }

            public String getLabelDefault() {return labelDefault;}

            public ToolPanel setLabelDefault(String labelDefault)
            {
                this.labelDefault = labelDefault;
                return this;
            }

            public String getLabelKey() {return labelKey;}

            public ToolPanel setLabelKey(String labelKey)
            {
                this.labelKey = labelKey;
                return this;
            }

            public String getIcon() {return icon;}

            public ToolPanel setIcon(String icon)
            {
                this.icon = icon;
                return this;
            }
        }

        // ===== Nested panel configurations =====
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class SettingsPanel
        {
            @JsonProperty("chartGroupsDef")
            private ChartGroupsDef chartGroupsDef;

            public ChartGroupsDef getChartGroupsDef() {return chartGroupsDef;}

            public SettingsPanel setChartGroupsDef(ChartGroupsDef def)
            {
                this.chartGroupsDef = def;
                return this;
            }
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class ChartGroupsDef
        {
            // Use string lists to keep flexible and aligned with AG Grid string keys
            @JsonProperty("columnGroup")
            private java.util.List<String> columnGroup;
            @JsonProperty("barGroup")
            private java.util.List<String> barGroup;
            @JsonProperty("pieGroup")
            private java.util.List<String> pieGroup;
            @JsonProperty("lineGroup")
            private java.util.List<String> lineGroup;
            @JsonProperty("areaGroup")
            private java.util.List<String> areaGroup;
            @JsonProperty("scatterGroup")
            private java.util.List<String> scatterGroup;
            @JsonProperty("combinationGroup")
            private java.util.List<String> combinationGroup;
            @JsonProperty("polarGroup")
            private java.util.List<String> polarGroup;
            @JsonProperty("statisticalGroup")
            private java.util.List<String> statisticalGroup;
            @JsonProperty("hierarchicalGroup")
            private java.util.List<String> hierarchicalGroup;
            @JsonProperty("specializedGroup")
            private java.util.List<String> specializedGroup;
            @JsonProperty("funnelGroup")
            private java.util.List<String> funnelGroup;

            public java.util.List<String> getColumnGroup() {return columnGroup;}

            public ChartGroupsDef setColumnGroup(java.util.List<String> v)
            {
                this.columnGroup = v;
                return this;
            }

            public java.util.List<String> getBarGroup() {return barGroup;}

            public ChartGroupsDef setBarGroup(java.util.List<String> v)
            {
                this.barGroup = v;
                return this;
            }

            public java.util.List<String> getPieGroup() {return pieGroup;}

            public ChartGroupsDef setPieGroup(java.util.List<String> v)
            {
                this.pieGroup = v;
                return this;
            }

            public java.util.List<String> getLineGroup() {return lineGroup;}

            public ChartGroupsDef setLineGroup(java.util.List<String> v)
            {
                this.lineGroup = v;
                return this;
            }

            public java.util.List<String> getAreaGroup() {return areaGroup;}

            public ChartGroupsDef setAreaGroup(java.util.List<String> v)
            {
                this.areaGroup = v;
                return this;
            }

            public java.util.List<String> getScatterGroup() {return scatterGroup;}

            public ChartGroupsDef setScatterGroup(java.util.List<String> v)
            {
                this.scatterGroup = v;
                return this;
            }

            public java.util.List<String> getCombinationGroup() {return combinationGroup;}

            public ChartGroupsDef setCombinationGroup(java.util.List<String> v)
            {
                this.combinationGroup = v;
                return this;
            }

            public java.util.List<String> getPolarGroup() {return polarGroup;}

            public ChartGroupsDef setPolarGroup(java.util.List<String> v)
            {
                this.polarGroup = v;
                return this;
            }

            public java.util.List<String> getStatisticalGroup() {return statisticalGroup;}

            public ChartGroupsDef setStatisticalGroup(java.util.List<String> v)
            {
                this.statisticalGroup = v;
                return this;
            }

            public java.util.List<String> getHierarchicalGroup() {return hierarchicalGroup;}

            public ChartGroupsDef setHierarchicalGroup(java.util.List<String> v)
            {
                this.hierarchicalGroup = v;
                return this;
            }

            public java.util.List<String> getSpecializedGroup() {return specializedGroup;}

            public ChartGroupsDef setSpecializedGroup(java.util.List<String> v)
            {
                this.specializedGroup = v;
                return this;
            }

            public java.util.List<String> getFunnelGroup() {return funnelGroup;}

            public ChartGroupsDef setFunnelGroup(java.util.List<String> v)
            {
                this.funnelGroup = v;
                return this;
            }
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class DataPanel
        {
            @JsonProperty("groups")
            private java.util.List<DataGroup> groups;

            public java.util.List<DataGroup> getGroups() {return groups;}

            public DataPanel setGroups(java.util.List<DataGroup> groups)
            {
                this.groups = groups;
                return this;
            }

            @JsonInclude(JsonInclude.Include.NON_NULL)
            public static class DataGroup
            {
                @JsonProperty("type")
                private String type;
                @JsonProperty("isOpen")
                private Boolean isOpen;

                public String getType() {return type;}

                public DataGroup setType(String type)
                {
                    this.type = type;
                    return this;
                }

                public Boolean getIsOpen() {return isOpen;}

                public DataGroup setIsOpen(Boolean isOpen)
                {
                    this.isOpen = isOpen;
                    return this;
                }
            }
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class FormatPanel
        {
            @JsonProperty("groups")
            private java.util.List<FormatGroup> groups;

            public java.util.List<FormatGroup> getGroups() {return groups;}

            public FormatPanel setGroups(java.util.List<FormatGroup> groups)
            {
                this.groups = groups;
                return this;
            }

            @JsonInclude(JsonInclude.Include.NON_NULL)
            public static class FormatGroup
            {
                @JsonProperty("type")
                private String type;
                @JsonProperty("isOpen")
                private Boolean isOpen;

                public String getType() {return type;}

                public FormatGroup setType(String type)
                {
                    this.type = type;
                    return this;
                }

                public Boolean getIsOpen() {return isOpen;}

                public FormatGroup setIsOpen(Boolean isOpen)
                {
                    this.isOpen = isOpen;
                    return this;
                }
            }
        }
    }

    // ===== Internal helpers & enums for this options class =====
    // (All enums extracted to separate files for better organization)

    /**
     * Simple wrapper to serialize raw JavaScript using the existing JavascriptFunction infrastructure.
     */
    static class RawJsFunction extends com.jwebmp.core.htmlbuilder.javascript.JavascriptFunction<RawJsFunction>
    {
        private final String raw;

        RawJsFunction(String raw) {this.raw = raw;}

        @Override
        public String renderFunction()
        {
            return raw == null ? "null" : raw;
        }
    }

    // Row Grouping options are defined in the superclass module. No duplicates here.

    // ===== Enums for new options (extracted to separate files) =====

    // ===== Getters / Setters for new options =====

    // All group-related fields and accessors are available via AgGridOptions rowGrouping module.

    // All Server-Side Row Model and Charts options are provided via their respective modules.

    // Pivoting is available exclusively via the PivotingOptions module (configurePivoting()).

    // Advanced Filter settings are provided by AdvancedFilteringOptions module.

    // Charts-specific settings are provided by ChartsOptions module.
}
