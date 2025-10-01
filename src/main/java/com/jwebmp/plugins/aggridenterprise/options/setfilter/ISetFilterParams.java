package com.jwebmp.plugins.aggridenterprise.options.setfilter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jwebmp.plugins.aggrid.options.filters.IFilterParams;

import java.util.List;

/**
 * CRTP interface describing Set Filter parameters (base set only).
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ISetFilterParams<J extends ISetFilterParams<J>> extends IFilterParams<J> {

    Boolean getApplyMiniFilterWhileTyping();
    J setApplyMiniFilterWhileTyping(Boolean applyMiniFilterWhileTyping);

    List<SetFilterButton> getButtons();
    J setButtons(List<SetFilterButton> buttons);

    Boolean getCaseSensitive();
    J setCaseSensitive(Boolean caseSensitive);

    Integer getCellHeight();
    J setCellHeight(Integer cellHeight);

    Boolean getCloseOnApply();
    J setCloseOnApply(Boolean closeOnApply);

    /**
     * Provide a custom renderer for values in the Filter List.
     * Raw JavaScript component/function reference. Example: "countryCellRenderer" or "(p)=>MyRenderer(p)".
     */
    String getCellRenderer();
    J setCellRenderer(String cellRendererRawJs);

    /**
     * Optional params for the custom filter cell renderer. Raw JavaScript object literal.
     */
    String getCellRendererParams();
    J setCellRendererParams(String cellRendererParamsRawJs);

    /**
     * Raw JavaScript comparator function. Example: "(a,b) => a==b?0:(a>b?1:-1)".
     */
    String getComparator();
    J setComparator(String comparatorRawJs);

    Integer getDebounceMs();
    J setDebounceMs(Integer debounceMs);

    Boolean getDefaultToNothingSelected();
    J setDefaultToNothingSelected(Boolean defaultToNothingSelected);

    ExcelMode getExcelMode();
    J setExcelMode(ExcelMode excelMode);

    /**
     * Function or Array for Set Filter values.
     * Use setValuesRaw for a raw JS callback or array literal; or setValues(List<String>) for convenience.
     */
    String getValues();
    J setValuesRaw(String valuesRawJs);
    J setValues(List<String> values);

    /**
     * Raw JavaScript keyCreator function. Example: "params => String(params.value?.id)".
     */
    String getKeyCreator();
    J setKeyCreator(String keyCreatorRawJs);

    Boolean getReadOnly();
    J setReadOnly(Boolean readOnly);

    Boolean getRefreshValuesOnOpen();
    J setRefreshValuesOnOpen(Boolean refreshValuesOnOpen);

    Boolean getShowTooltips();
    J setShowTooltips(Boolean showTooltips);

    Boolean getSuppressClearModelOnRefreshValues();
    J setSuppressClearModelOnRefreshValues(Boolean suppressClearModelOnRefreshValues);

    Boolean getSuppressMiniFilter();
    J setSuppressMiniFilter(Boolean suppressMiniFilter);

    Boolean getSuppressSelectAll();
    J setSuppressSelectAll(Boolean suppressSelectAll);

    Boolean getSuppressSorting();
    J setSuppressSorting(Boolean suppressSorting);

    /**
     * Raw JavaScript text formatter function for mini filter. Example: "text => text.normalize('NFD').replace(/\\p{Diacritic}/gu,'')".
     */
    String getTextFormatter();
    J setTextFormatter(String textFormatterRawJs);

    Boolean getTreeList();
    J setTreeList(Boolean treeList);

    /**
     * Raw JavaScript treeList formatter function.
     */
    String getTreeListFormatter();
    J setTreeListFormatter(String treeListFormatterRawJs);

    /**
     * Raw JavaScript tree path getter function.
     */
    String getTreeListPathGetter();
    J setTreeListPathGetter(String treeListPathGetterRawJs);

    /**
     * Raw JavaScript value formatter for filter list items.
     */
    String getValueFormatter();
    J setValueFormatter(String valueFormatterRawJs);
}
