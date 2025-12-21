package com.jwebmp.plugins.aggridenterprise.options;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
class AgGridEnterpriseOptionsTest
{
		
		@Test
		void getSuppressAggFuncInHeader()
		{
				AgGridEnterpriseOptions<?> agGridEnterpriseOptions = new AgGridEnterpriseOptions<>();
				
				agGridEnterpriseOptions
					.configureRowGrouping()
					.setGroupDefaultExpanded(2);
				
				agGridEnterpriseOptions
					.configureRowGrouping()
					.setRowGroupPanelShow("onlyWhenGrouping")
				;
				assertEquals(2, agGridEnterpriseOptions.getRowGrouping().getGroupDefaultExpanded());
				assertEquals("onlyWhenGrouping", agGridEnterpriseOptions.getRowGrouping().getRowGroupPanelShow());
				System.out.println("options - " + agGridEnterpriseOptions.toJson());
				assertTrue(agGridEnterpriseOptions.toJson().contains("onlyWhenGrouping"));
				
				agGridEnterpriseOptions.configurePivoting().setPivotMode(true);
				String json = agGridEnterpriseOptions.toJson();
				System.out.println("options - " + json);
				assertTrue(json.contains("pivotMode"));
				// ensure no duplicate keys for pivotMode
				assertEquals(json.indexOf("\"pivotMode\""), json.lastIndexOf("\"pivotMode\""));
				
		}
}