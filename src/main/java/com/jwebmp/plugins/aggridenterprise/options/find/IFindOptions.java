package com.jwebmp.plugins.aggridenterprise.options.find;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * FindOptions contract for AG Grid Enterprise Find feature.
 * CRTP-enabled to follow project fluent API rules.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface IFindOptions<J extends IFindOptions<J>> {

    Boolean getCurrentPageOnly();

    J setCurrentPageOnly(Boolean currentPageOnly);

    Boolean getCaseSensitive();

    J setCaseSensitive(Boolean caseSensitive);

    Boolean getSearchDetail();

    J setSearchDetail(Boolean searchDetail);
}
