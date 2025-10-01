package com.jwebmp.plugins.aggridenterprise.options.find;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Concrete implementation of FindOptions.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class FindOptions implements IFindOptions<FindOptions> {

    @JsonProperty("currentPageOnly")
    private Boolean currentPageOnly;

    @JsonProperty("caseSensitive")
    private Boolean caseSensitive;

    @JsonProperty("searchDetail")
    private Boolean searchDetail;

    @Override
    public Boolean getCurrentPageOnly() {
        return currentPageOnly;
    }

    @Override
    public FindOptions setCurrentPageOnly(Boolean currentPageOnly) {
        this.currentPageOnly = currentPageOnly;
        return this;
    }

    @Override
    public Boolean getCaseSensitive() {
        return caseSensitive;
    }

    @Override
    public FindOptions setCaseSensitive(Boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
        return this;
    }

    @Override
    public Boolean getSearchDetail() {
        return searchDetail;
    }

    @Override
    public FindOptions setSearchDetail(Boolean searchDetail) {
        this.searchDetail = searchDetail;
        return this;
    }
}
