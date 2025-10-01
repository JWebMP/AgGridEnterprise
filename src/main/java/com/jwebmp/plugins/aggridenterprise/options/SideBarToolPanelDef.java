package com.jwebmp.plugins.aggridenterprise.options;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Definition for a single entry inside the AG Grid Enterprise Side Bar
 * e.g. Columns or Filters panels.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Accessors(chain = true)
public class SideBarToolPanelDef
{
    /** Unique id of the tool panel, e.g. "columns" or "filters" */
    @JsonProperty("id")
    private String id;

    /** Localised label, optional */
    @JsonProperty("labelDefault")
    private String labelDefault;

    /** Optional icon key, e.g. "columns" */
    @JsonProperty("iconKey")
    private String iconKey;

    /** The built-in panel reference, e.g. "agColumnsToolPanel" or "agFiltersToolPanel" */
    @JsonProperty("toolPanel")
    private String toolPanel;

    @JsonProperty("minWidth")
    private Integer minWidth;

    @JsonProperty("width")
    private Integer width;

    @JsonProperty("maxWidth")
    private Integer maxWidth;

    public String getId()
    {
        return id;
    }

    public SideBarToolPanelDef setId(String id)
    {
        this.id = id;
        return this;
    }

    public String getLabelDefault()
    {
        return labelDefault;
    }

    public SideBarToolPanelDef setLabelDefault(String labelDefault)
    {
        this.labelDefault = labelDefault;
        return this;
    }

    public String getIconKey()
    {
        return iconKey;
    }

    public SideBarToolPanelDef setIconKey(String iconKey)
    {
        this.iconKey = iconKey;
        return this;
    }

    public String getToolPanel()
    {
        return toolPanel;
    }

    public SideBarToolPanelDef setToolPanel(String toolPanel)
    {
        this.toolPanel = toolPanel;
        return this;
    }

    public Integer getMinWidth()
    {
        return minWidth;
    }

    public SideBarToolPanelDef setMinWidth(Integer minWidth)
    {
        this.minWidth = minWidth;
        return this;
    }

    public Integer getWidth()
    {
        return width;
    }

    public SideBarToolPanelDef setWidth(Integer width)
    {
        this.width = width;
        return this;
    }

    public Integer getMaxWidth()
    {
        return maxWidth;
    }

    public SideBarToolPanelDef setMaxWidth(Integer maxWidth)
    {
        this.maxWidth = maxWidth;
        return this;
    }
}
