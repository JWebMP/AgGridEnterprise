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
 * AG Grid Enterprise SideBar definition. Use this to configure the columns and filters tool panels.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Accessors(chain = true)
public class SideBarDef
{
    /** Available panels in the sidebar */
    @JsonProperty("toolPanels")
    private List<SideBarToolPanelDef> toolPanels;

    /** Id of the default (initially open) tool panel */
    @JsonProperty("defaultToolPanel")
    private String defaultToolPanel;

    /** Optional: 'left' or 'right' */
    @JsonProperty("position")
    private String position;

    public List<SideBarToolPanelDef> getToolPanels()
    {
        if (toolPanels == null)
        {
            toolPanels = new ArrayList<>();
        }
        return toolPanels;
    }

    public SideBarDef setToolPanels(List<SideBarToolPanelDef> toolPanels)
    {
        this.toolPanels = toolPanels;
        return this;
    }

    public SideBarDef addToolPanel(SideBarToolPanelDef panel)
    {
        getToolPanels().add(panel);
        return this;
    }

    public String getDefaultToolPanel()
    {
        return defaultToolPanel;
    }

    public SideBarDef setDefaultToolPanel(String defaultToolPanel)
    {
        this.defaultToolPanel = defaultToolPanel;
        return this;
    }

    public String getPosition()
    {
        return position;
    }

    public SideBarDef setPosition(String position)
    {
        this.position = position;
        return this;
    }
}
