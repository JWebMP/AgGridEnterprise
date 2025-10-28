package com.jwebmp.plugins.aggridenterprise;

import com.jwebmp.core.base.angular.client.annotations.boot.NgBootConstructorBody;
import com.jwebmp.core.base.angular.client.annotations.boot.NgBootImportReference;
import com.jwebmp.core.base.angular.client.annotations.typescript.TsDependency;
import com.jwebmp.core.plugins.PluginInformation;
import com.jwebmp.core.plugins.PluginStatus;
import com.jwebmp.core.services.IPage;
import com.jwebmp.core.services.IPageConfigurator;
import lombok.Getter;
import lombok.Setter;

@PluginInformation(
        pluginName = "AG Grid Enterprise",
        pluginUniqueName = "ag-grid-enterprise",
        pluginDescription = "AG Grid Enterprise extension for JWebMP providing integrated charts, row grouping, server-side row model and more.",
        pluginVersion = "34.2.0",
        pluginCategories = "grid, table, data, ui, web ui, enterprise",
        pluginSubtitle = "Enterprise features for AG Grid",
        pluginSourceUrl = "https://www.ag-grid.com/",
        pluginWikiUrl = "https://github.com/JWebMP/JWebMP-AgGrid/wiki",
        pluginGitUrl = "https://github.com/JWebMP/JWebMP",
        pluginIconUrl = "",
        pluginIconImageUrl = "",
        pluginOriginalHomepage = "https://www.ag-grid.com/",
        pluginDownloadUrl = "https://mvnrepository.com/artifact/com.jwebmp.plugins/aggrid-enterprise",
        pluginGroupId = "com.jwebmp.plugins",
        pluginArtifactId = "aggrid-enterprise",
        pluginModuleName = "com.jwebmp.plugins.aggridenterprise",
        pluginLastUpdatedDate = "2025-09-23",
        pluginStatus = PluginStatus.Released
)

@TsDependency(value = "ag-grid-enterprise", version = "^34.2.0")
@TsDependency(value = "ag-charts-enterprise", version = "^12.2.0")
@NgBootImportReference(value = "AllEnterpriseModule", reference = "ag-grid-enterprise")
@NgBootImportReference(value = "ModuleRegistry", reference = "ag-grid-community")
@NgBootImportReference(value = "LicenseManager", reference = "ag-grid-enterprise")

public class AgGridEnterprisePageConfigurator implements IPageConfigurator<AgGridEnterprisePageConfigurator>
{
    @Getter
    @Setter
    private static String AG_GRID_LICENSE_KEY = "";

    @Override
    public IPage<?> configure(IPage<?> page)
    {
        // Angular handles resource loading via dependencies
        return page;
    }

    /**
     * Configure the Angular-compiled index by injecting the AG Grid Enterprise license key into the window
     * so the boot constructor can apply it via LicenseManager.
     */
    @Override
    public IPage<?> configureAngular(IPage<?> page)
    {
        try
        {
            String key = AG_GRID_LICENSE_KEY;
            if (key == null || key.isBlank())
            {
                key = System.getProperty("ag.grid.license", System.getenv("AG_GRID_LICENSE"));
            }
            if (key != null && !key.isBlank())
            {
                com.jwebmp.core.base.html.Script<?, ?> script = new com.jwebmp.core.base.html.Script<>()
                        .setText("window.AG_GRID_LICENSE_KEY = '" + key.replace("\\", "\\\\")
                                                                       .replace("'", "\\'") + "';");
                page.getHead()
                    .add(script);
            }
        }
        catch (Throwable ignored)
        {
            // Do not fail Angular compilation due to license injection errors
        }
        return page;
    }

    @Override
    public boolean enabled()
    {
        return true;
    }
}
