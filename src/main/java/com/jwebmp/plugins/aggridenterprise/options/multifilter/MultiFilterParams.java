package com.jwebmp.plugins.aggridenterprise.options.multifilter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jwebmp.plugins.aggridenterprise.options.setfilter.SetFilterButton;

import java.util.List;

/**
 * Concrete implementation of Multi Filter parameters.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class MultiFilterParams implements IMultiFilterParams<MultiFilterParams> {

    @JsonProperty("filters")
    private List<IMultiFilterDef<?>> filters;

    @JsonProperty("readOnly")
    private Boolean readOnly;

    @JsonProperty("buttons")
    private List<SetFilterButton> buttons;

    @JsonProperty("closeOnApply")
    private Boolean closeOnApply;

    @Override
    public List<IMultiFilterDef<?>> getFilters() {
        return filters;
    }

    @Override
    public MultiFilterParams setFilters(List<IMultiFilterDef<?>> filters) {
        this.filters = filters;
        return this;
    }

    @Override
    public Boolean getReadOnly() {
        return readOnly;
    }

    @Override
    public MultiFilterParams setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }

    @Override
    public List<SetFilterButton> getButtons() {
        return buttons;
    }

    @Override
    public MultiFilterParams setButtons(List<SetFilterButton> buttons) {
        this.buttons = buttons;
        return this;
    }

    @Override
    public Boolean getCloseOnApply() {
        return closeOnApply;
    }

    @Override
    public MultiFilterParams setCloseOnApply(Boolean closeOnApply) {
        this.closeOnApply = closeOnApply;
        return this;
    }
}
