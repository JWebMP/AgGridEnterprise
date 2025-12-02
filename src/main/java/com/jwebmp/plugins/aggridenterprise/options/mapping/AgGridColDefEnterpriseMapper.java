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
@SuppressWarnings("rawtypes")
public interface AgGridColDefEnterpriseMapper
{
    AgGridColDefEnterpriseMapper INSTANCE = Mappers.getMapper(AgGridColDefEnterpriseMapper.class);

    @Mappings({
            @Mapping(target = "cellRenderer", expression = "java(source.getCellRenderer(true))"),
            @Mapping(target = "headerComponent", expression = "java(source.getHeaderComponent(true))"),
            @Mapping(target = "chartDataType", ignore = true),
            @Mapping(target = "getFindText", ignore = true),
            @Mapping(target = "valueGetterExpression", ignore = true),
            @Mapping(target = "valueGetterRaw", ignore = true),
            @Mapping(target = "filterValueGetterExpression", ignore = true),
            @Mapping(target = "filterValueGetterRaw", ignore = true),
            @Mapping(target = "suppressKeyboardEventRaw", ignore = true),
            @Mapping(target = "contextMenuItemsRaw", ignore = true),
            @Mapping(target = "aggFunc", ignore = true),
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
}
