package com.jwebmp.plugins.aggridenterprise.options;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * AG Grid Enterprise Status Bar definition.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Accessors(chain = true)
public class StatusBarDef
{
    @JsonProperty("statusPanels")
    private List<StatusBarPanelDef> statusPanels;

    public List<StatusBarPanelDef> getStatusPanels()
    {
        if (statusPanels == null)
        {
            statusPanels = new ArrayList<>();
        }
        return statusPanels;
    }

    public StatusBarDef setStatusPanels(List<StatusBarPanelDef> statusPanels)
    {
        this.statusPanels = statusPanels;
        return this;
    }

    public StatusBarDef addPanel(StatusBarPanelDef panel)
    {
        getStatusPanels().add(panel);
        return this;
    }
}
