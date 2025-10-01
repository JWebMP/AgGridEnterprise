package com.jwebmp.plugins.aggridenterprise.options.setfilter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

import java.util.List;

/**
 * Concrete implementation of Set Filter parameters (base subset).
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class SetFilterParams implements ISetFilterParams<SetFilterParams> {

    @JsonProperty("applyMiniFilterWhileTyping")
    private Boolean applyMiniFilterWhileTyping;

    @JsonProperty("buttons")
    private List<SetFilterButton> buttons;

    @JsonProperty("caseSensitive")
    private Boolean caseSensitive;

    @JsonProperty("cellHeight")
    private Integer cellHeight;

    @JsonProperty("closeOnApply")
    private Boolean closeOnApply;

    /**
     * Custom renderer for items in the filter list.
     */
    @JsonProperty("cellRenderer")
    @JsonRawValue
    private String cellRenderer;

    /**
     * Optional params for the custom filter list renderer.
     */
    @JsonProperty("cellRendererParams")
    @JsonRawValue
    private String cellRendererParams;

    @JsonProperty("comparator")
    @JsonRawValue
    private String comparator;

    @JsonProperty("debounceMs")
    private Integer debounceMs;

    @JsonProperty("defaultToNothingSelected")
    private Boolean defaultToNothingSelected;

    @JsonProperty("excelMode")
    private ExcelMode excelMode;

    /**
     * Values for filter list. Can be an array literal or a callback function.
     */
    @JsonProperty("values")
    @JsonRawValue
    private String values;

    @JsonProperty("keyCreator")
    @JsonRawValue
    private String keyCreator;

    @JsonProperty("readOnly")
    private Boolean readOnly;

    @JsonProperty("refreshValuesOnOpen")
    private Boolean refreshValuesOnOpen;

    @JsonProperty("showTooltips")
    private Boolean showTooltips;

    @JsonProperty("suppressClearModelOnRefreshValues")
    private Boolean suppressClearModelOnRefreshValues;

    @JsonProperty("suppressMiniFilter")
    private Boolean suppressMiniFilter;

    @JsonProperty("suppressSelectAll")
    private Boolean suppressSelectAll;

    @JsonProperty("suppressSorting")
    private Boolean suppressSorting;

    @JsonProperty("textFormatter")
    @JsonRawValue
    private String textFormatter;

    @JsonProperty("treeList")
    private Boolean treeList;

    @JsonProperty("treeListFormatter")
    @JsonRawValue
    private String treeListFormatter;

    @JsonProperty("treeListPathGetter")
    @JsonRawValue
    private String treeListPathGetter;

    @JsonProperty("valueFormatter")
    @JsonRawValue
    private String valueFormatter;

    @Override
    public Boolean getApplyMiniFilterWhileTyping() {
        return applyMiniFilterWhileTyping;
    }

    @Override
    public SetFilterParams setApplyMiniFilterWhileTyping(Boolean applyMiniFilterWhileTyping) {
        this.applyMiniFilterWhileTyping = applyMiniFilterWhileTyping;
        return this;
    }

    @Override
    public List<SetFilterButton> getButtons() {
        return buttons;
    }

    @Override
    public SetFilterParams setButtons(List<SetFilterButton> buttons) {
        this.buttons = buttons;
        return this;
    }

    @Override
    public Boolean getCaseSensitive() {
        return caseSensitive;
    }

    @Override
    public SetFilterParams setCaseSensitive(Boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
        return this;
    }

    @Override
    public Integer getCellHeight() {
        return cellHeight;
    }

    @Override
    public SetFilterParams setCellHeight(Integer cellHeight) {
        this.cellHeight = cellHeight;
        return this;
    }

    @Override
    public Boolean getCloseOnApply() {
        return closeOnApply;
    }

    @Override
    public SetFilterParams setCloseOnApply(Boolean closeOnApply) {
        this.closeOnApply = closeOnApply;
        return this;
    }

    @Override
    public String getCellRenderer() {
        return cellRenderer;
    }

    @Override
    public SetFilterParams setCellRenderer(String cellRendererRawJs) {
        this.cellRenderer = cellRendererRawJs;
        return this;
    }

    @Override
    public String getCellRendererParams() {
        return cellRendererParams;
    }

    @Override
    public SetFilterParams setCellRendererParams(String cellRendererParamsRawJs) {
        this.cellRendererParams = cellRendererParamsRawJs;
        return this;
    }

    @Override
    public String getComparator() {
        return comparator;
    }

    @Override
    public SetFilterParams setComparator(String comparatorRawJs) {
        this.comparator = comparatorRawJs;
        return this;
    }

    @Override
    public Integer getDebounceMs() {
        return debounceMs;
    }

    @Override
    public SetFilterParams setDebounceMs(Integer debounceMs) {
        this.debounceMs = debounceMs;
        return this;
    }

    @Override
    public Boolean getDefaultToNothingSelected() {
        return defaultToNothingSelected;
    }

    @Override
    public SetFilterParams setDefaultToNothingSelected(Boolean defaultToNothingSelected) {
        this.defaultToNothingSelected = defaultToNothingSelected;
        return this;
    }

    @Override
    public ExcelMode getExcelMode() {
        return excelMode;
    }

    @Override
    public SetFilterParams setExcelMode(ExcelMode excelMode) {
        this.excelMode = excelMode;
        return this;
    }

    @Override
    public String getValues() {
        return values;
    }

    @Override
    public SetFilterParams setValuesRaw(String valuesRawJs) {
        this.values = valuesRawJs;
        return this;
    }

    private static String toJsArray(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (String v : list) {
            if (!first) sb.append(',');
            first = false;
            if (v == null) {
                sb.append("null");
            } else {
                String escaped = v.replace("\\", "\\\\").replace("\"", "\\\"");
                sb.append('"').append(escaped).append('"');
            }
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public SetFilterParams setValues(List<String> valuesList) {
        this.values = toJsArray(valuesList);
        return this;
    }

    @Override
    public String getKeyCreator() {
        return keyCreator;
    }

    @Override
    public SetFilterParams setKeyCreator(String keyCreatorRawJs) {
        this.keyCreator = keyCreatorRawJs;
        return this;
    }

    @Override
    public Boolean getReadOnly() {
        return readOnly;
    }

    @Override
    public SetFilterParams setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }

    @Override
    public Boolean getRefreshValuesOnOpen() {
        return refreshValuesOnOpen;
    }

    @Override
    public SetFilterParams setRefreshValuesOnOpen(Boolean refreshValuesOnOpen) {
        this.refreshValuesOnOpen = refreshValuesOnOpen;
        return this;
    }

    @Override
    public Boolean getShowTooltips() {
        return showTooltips;
    }

    @Override
    public SetFilterParams setShowTooltips(Boolean showTooltips) {
        this.showTooltips = showTooltips;
        return this;
    }

    @Override
    public Boolean getSuppressClearModelOnRefreshValues() {
        return suppressClearModelOnRefreshValues;
    }

    @Override
    public SetFilterParams setSuppressClearModelOnRefreshValues(Boolean suppressClearModelOnRefreshValues) {
        this.suppressClearModelOnRefreshValues = suppressClearModelOnRefreshValues;
        return this;
    }

    @Override
    public Boolean getSuppressMiniFilter() {
        return suppressMiniFilter;
    }

    @Override
    public SetFilterParams setSuppressMiniFilter(Boolean suppressMiniFilter) {
        this.suppressMiniFilter = suppressMiniFilter;
        return this;
    }

    @Override
    public Boolean getSuppressSelectAll() {
        return suppressSelectAll;
    }

    @Override
    public SetFilterParams setSuppressSelectAll(Boolean suppressSelectAll) {
        this.suppressSelectAll = suppressSelectAll;
        return this;
    }

    @Override
    public Boolean getSuppressSorting() {
        return suppressSorting;
    }

    @Override
    public SetFilterParams setSuppressSorting(Boolean suppressSorting) {
        this.suppressSorting = suppressSorting;
        return this;
    }

    @Override
    public String getTextFormatter() {
        return textFormatter;
    }

    @Override
    public SetFilterParams setTextFormatter(String textFormatterRawJs) {
        this.textFormatter = textFormatterRawJs;
        return this;
    }

    @Override
    public Boolean getTreeList() {
        return treeList;
    }

    @Override
    public SetFilterParams setTreeList(Boolean treeList) {
        this.treeList = treeList;
        return this;
    }

    @Override
    public String getTreeListFormatter() {
        return treeListFormatter;
    }

    @Override
    public SetFilterParams setTreeListFormatter(String treeListFormatterRawJs) {
        this.treeListFormatter = treeListFormatterRawJs;
        return this;
    }

    @Override
    public String getTreeListPathGetter() {
        return treeListPathGetter;
    }

    @Override
    public SetFilterParams setTreeListPathGetter(String treeListPathGetterRawJs) {
        this.treeListPathGetter = treeListPathGetterRawJs;
        return this;
    }

    @Override
    public String getValueFormatter() {
        return valueFormatter;
    }

    @Override
    public SetFilterParams setValueFormatter(String valueFormatterRawJs) {
        this.valueFormatter = valueFormatterRawJs;
        return this;
    }
}
