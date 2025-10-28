package com.jwebmp.plugins.aggridenterprise.options.mapping;

import com.jwebmp.plugins.aggrid.options.AgGridColumnDef;
import com.jwebmp.plugins.aggridenterprise.options.AgGridEnterpriseColumnDef;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper to convert a community AgGridColumnDef to an enterprise AgGridEnterpriseColumnDef.
 *
 * One-to-one mapping: do not ignore any properties. MapStruct will copy all matching properties
 * by name and compatible type from the community definition into the enterprise definition.
 * For properties with overloaded getters (e.g., String vs raw component types), we explicitly
 * select the raw-typed getters via expressions.
 */
@Mapper
public interface AgGridColDefEnterpriseMapper
{
    AgGridColDefEnterpriseMapper INSTANCE = Mappers.getMapper(AgGridColDefEnterpriseMapper.class);

    @Mappings({
            @Mapping(target = "cellRenderer", expression = "java(source.getCellRenderer(true))"),
            @Mapping(target = "headerComponent", expression = "java(source.getHeaderComponent(true))")
    })
    AgGridEnterpriseColumnDef toEnterpriseColDef(AgGridColumnDef source);
}
