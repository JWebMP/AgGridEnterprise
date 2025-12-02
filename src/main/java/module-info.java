import com.guicedee.client.services.config.IGuiceScanModuleInclusions;
import com.jwebmp.core.services.IPageConfigurator;
import com.jwebmp.plugins.aggridenterprise.AgGridEnterprisePageConfigurator;
import com.jwebmp.plugins.aggridenterprise.implementations.AgGridEnterpriseModuleScanInclusion;

module com.jwebmp.plugins.aggridenterprise {
    exports com.jwebmp.plugins.aggridenterprise;
    exports com.jwebmp.plugins.aggridenterprise.options;
    exports com.jwebmp.plugins.aggridenterprise.options.modules;
    exports com.jwebmp.plugins.aggridenterprise.options.mapping;
    exports com.jwebmp.plugins.aggridenterprise.options.find;
    exports com.jwebmp.plugins.aggridenterprise.options.setfilter;
    exports com.jwebmp.plugins.aggridenterprise.options.multifilter;
    exports com.jwebmp.plugins.aggridenterprise.options.cellselection;
    exports com.jwebmp.plugins.aggridenterprise.options.advancedfilter;
    exports com.jwebmp.plugins.aggridenterprise.options.enums;
    exports com.jwebmp.plugins.aggridenterprise.charts;

    requires transitive com.jwebmp.plugins.aggrid;
    requires transitive com.jwebmp.plugins.agchartsenterprise;

    requires com.jwebmp.client;
    requires com.jwebmp.core;
    requires com.jwebmp.core.angular;
    requires com.guicedee.guicedinjection;
    requires com.fasterxml.jackson.databind;
    requires static lombok;

    requires org.mapstruct;

    provides IPageConfigurator with AgGridEnterprisePageConfigurator;
    provides IGuiceScanModuleInclusions with AgGridEnterpriseModuleScanInclusion;

    //uses IPageConfigurator;

    opens com.jwebmp.plugins.aggridenterprise to com.google.guice, com.fasterxml.jackson.databind, com.jwebmp.core, org.mapstruct;
    opens com.jwebmp.plugins.aggridenterprise.charts to com.google.guice, com.fasterxml.jackson.databind, com.jwebmp.core, org.mapstruct;
    opens com.jwebmp.plugins.aggridenterprise.options to com.fasterxml.jackson.databind, com.jwebmp.core, com.google.guice, org.mapstruct;
    opens com.jwebmp.plugins.aggridenterprise.options.modules to com.fasterxml.jackson.databind, com.jwebmp.core, com.google.guice, org.mapstruct;
    opens com.jwebmp.plugins.aggridenterprise.options.mapping to com.fasterxml.jackson.databind, com.jwebmp.core, com.google.guice, org.mapstruct;
    opens com.jwebmp.plugins.aggridenterprise.options.find to com.fasterxml.jackson.databind, com.jwebmp.core, com.google.guice, org.mapstruct;
    opens com.jwebmp.plugins.aggridenterprise.options.setfilter to com.fasterxml.jackson.databind, com.jwebmp.core, com.google.guice, org.mapstruct;
    opens com.jwebmp.plugins.aggridenterprise.options.multifilter to com.fasterxml.jackson.databind, com.jwebmp.core, com.google.guice, org.mapstruct;
    opens com.jwebmp.plugins.aggridenterprise.options.cellselection to com.fasterxml.jackson.databind, com.jwebmp.core, com.google.guice, org.mapstruct;
    opens com.jwebmp.plugins.aggridenterprise.options.advancedfilter to com.fasterxml.jackson.databind, com.jwebmp.core, com.google.guice, org.mapstruct;
}
