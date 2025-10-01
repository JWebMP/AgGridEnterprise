package com.jwebmp.plugins.aggridenterprise.options;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Status bar panel definition for AG Grid Enterprise.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Accessors(chain = true)
public class StatusBarPanelDef
{
    /** Built-in panel key or custom component reference (string), e.g. 'agAggregationComponent' */
    @JsonProperty("statusPanel")
    private String statusPanel;

    /** Optional alignment: left | center | right */
    @JsonProperty("align")
    private String align;

    /** Optional unique key */
    @JsonProperty("key")
    private String key;

    /** Optional params passed to the panel */
    @JsonProperty("statusPanelParams")
    private Object statusPanelParams;

    public String getStatusPanel()
    {
        return statusPanel;
    }

    public StatusBarPanelDef setStatusPanel(String statusPanel)
    {
        this.statusPanel = statusPanel;
        return this;
    }

    public String getAlign()
    {
        return align;
    }

    public StatusBarPanelDef setAlign(String align)
    {
        this.align = align;
        return this;
    }

    public String getKey()
    {
        return key;
    }

    public StatusBarPanelDef setKey(String key)
    {
        this.key = key;
        return this;
    }

    public Object getStatusPanelParams()
    {
        return statusPanelParams;
    }

    public StatusBarPanelDef setStatusPanelParams(Object statusPanelParams)
    {
        this.statusPanelParams = statusPanelParams;
        return this;
    }
}
