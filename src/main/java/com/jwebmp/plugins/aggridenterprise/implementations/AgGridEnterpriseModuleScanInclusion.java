package com.jwebmp.plugins.aggridenterprise.implementations;

import com.guicedee.client.services.config.IGuiceScanModuleInclusions;

import java.util.Set;

public class AgGridEnterpriseModuleScanInclusion implements IGuiceScanModuleInclusions<AgGridEnterpriseModuleScanInclusion>
{
    @Override
    public Set<String> includeModules()
    {
        return Set.of("com.jwebmp.plugins.aggridenterprise");
    }
}
