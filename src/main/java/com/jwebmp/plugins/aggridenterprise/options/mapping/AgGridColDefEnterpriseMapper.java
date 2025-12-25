package com.jwebmp.plugins.aggridenterprise.options.mapping;

import com.jwebmp.plugins.aggrid.options.AgGridColumnDef;
import com.jwebmp.plugins.aggrid.options.AgGridOptions;
import com.jwebmp.plugins.aggridenterprise.options.AgGridEnterpriseColumnDef;
import com.jwebmp.plugins.aggridenterprise.options.AgGridEnterpriseOptions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MapStruct mapper to convert a community AgGridColumnDef to an enterprise AgGridEnterpriseColumnDef.
 *
 * One-to-one mapping: do not ignore any properties. MapStruct will copy all matching properties
 * by name and compatible type from the community definition into the enterprise definition.
 * For properties with overloaded getters (e.g., String vs raw component types), we explicitly
 * select the raw-typed getters via expressions.
 */
@Mapper
@SuppressWarnings("rawtypes")
public interface AgGridColDefEnterpriseMapper
{
    AgGridColDefEnterpriseMapper INSTANCE = Mappers.getMapper(AgGridColDefEnterpriseMapper.class);

    @Mappings({
            @Mapping(target = "cellRenderer", expression = "java(source.getCellRenderer(true))"),
            @Mapping(target = "headerComponent", expression = "java(source.getHeaderComponent(true))"),
            @Mapping(target = "type", expression = "java(source.getType())"),
            @Mapping(target = "editable", expression = "java(source.getEditable())"),
            @Mapping(target = "cellStyle", expression = "java(source.getCellStyle())"),
            @Mapping(target = "rowSpan", expression = "java(source.getRowSpan())"),
            @Mapping(target = "aggFunc", expression = "java(source.getAggFunc())"),
            @Mapping(target = "rowDrag", expression = "java(source.getRowDrag())"),
            @Mapping(target = "dndSource", expression = "java(source.getDndSource())"),
            @Mapping(target = "toolPanelClass", expression = "java(source.getToolPanelClass())"),
            @Mapping(target = "colSpan", expression = "java(source.getColSpan())"),
            @Mapping(target = "filter", expression = "java(source.getFilter())"),
            @Mapping(target = "cellRendererParams", expression = "java(source.getCellRendererParams())"),
            @Mapping(target = "initialAggFunc", ignore = true),
            @Mapping(target = "enableValue", ignore = true),
            @Mapping(target = "enableRowGroup", ignore = true),
            @Mapping(target = "enablePivot", ignore = true),
            @Mapping(target = "pivot", ignore = true),
            @Mapping(target = "cellDataType", ignore = true),
            @Mapping(target = "allowedAggFuncs", ignore = true),
            @Mapping(target = "defaultAggFunc", ignore = true),
            @Mapping(target = "groupTotalRow", ignore = true),
            @Mapping(target = "groupTotalRowRawCallback", ignore = true),
            @Mapping(target = "grandTotalRow", ignore = true),
            @Mapping(target = "suppressAggFuncInHeader", ignore = true),
            @Mapping(target = "aggregateOnlyChangedColumns", ignore = true),
            @Mapping(target = "suppressAggFilteredOnly", ignore = true),
            @Mapping(target = "groupAggFiltering", ignore = true),
            @Mapping(target = "groupSuppressBlankHeader", ignore = true),
            @Mapping(target = "suppressStickyTotalRow", ignore = true),
            @Mapping(target = "alwaysAggregateAtRootLevel", ignore = true),
            @Mapping(target = "getGroupRowAgg", ignore = true)
    })
    AgGridEnterpriseColumnDef toEnterpriseColDef(AgGridColumnDef source);
    List<AgGridEnterpriseColumnDef> toEnterpriseColDefs(List<AgGridColumnDef> source);
				
				AgGridEnterpriseOptions toEnterpriseOptions(AgGridOptions source);
}
