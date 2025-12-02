package com.jwebmp.plugins.aggridenterprise.options.modules;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AG Grid Enterprise: SideBar and StatusBar Configuration Module
 *
 * Encapsulates sidebar panels, tool panels, and status bar configuration.
 *
 * @param <J> Generic type for fluent builder pattern (CRTP)
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SideBarAndStatusBarOptions<J extends SideBarAndStatusBarOptions<J>>
{
    private J parent;

    /**
     * Allow dragging columns from the columns tool panel to the grid to show/hide them.
     */
    @JsonProperty("allowDragFromColumnsToolPanel")
    private Boolean allowDragFromColumnsToolPanel;

    /**
     * Status bar configuration. Defines status bar panels and their configuration.
     * Enterprise feature for showing aggregates and row information.
     */
    @JsonProperty("statusBar")
    private Object statusBar;

    /**
     * Sidebar configuration. Defines which panels appear in the sidebar and their configuration.
     */
    @JsonProperty("sideBar")
    private Object sideBar;

    // ===== Constructors & Parent Management =====

    public SideBarAndStatusBarOptions()
    {
    }

    public SideBarAndStatusBarOptions(J parent)
    {
        this.parent = parent;
    }

    /**
     * Get the parent AgGridEnterpriseOptions instance for fluent chaining.
     *
     * @return parent options object
     */
    public J parent()
    {
        return parent;
    }

    public J getParent()
    {
        return parent;
    }

    public SideBarAndStatusBarOptions<J> setParent(J parent)
    {
        this.parent = parent;
        return this;
    }

    // ===== Property Getters & Fluent Setters =====

    public Boolean getAllowDragFromColumnsToolPanel()
    {
        return allowDragFromColumnsToolPanel;
    }

    public SideBarAndStatusBarOptions<J> setAllowDragFromColumnsToolPanel(Boolean allowDragFromColumnsToolPanel)
    {
        this.allowDragFromColumnsToolPanel = allowDragFromColumnsToolPanel;
        return this;
    }

    public Object getStatusBar()
    {
        return statusBar;
    }

    public SideBarAndStatusBarOptions<J> setStatusBar(Object statusBar)
    {
        this.statusBar = statusBar;
        return this;
    }

    public Object getSideBar()
    {
        return sideBar;
    }

    public SideBarAndStatusBarOptions<J> setSideBar(Object sideBar)
    {
        this.sideBar = sideBar;
        return this;
    }
}
