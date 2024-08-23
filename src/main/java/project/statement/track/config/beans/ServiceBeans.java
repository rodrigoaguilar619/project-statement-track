package project.statement.track.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lib.base.backend.modules.catalog.interaface.CatalogDefinition;
import project.statement.track.config.catalog.CatalogStatementTrackDefinition;

@Configuration
public class ServiceBeans {

	@Bean
	public CatalogDefinition buildCatalogDefinition() {
		return new CatalogStatementTrackDefinition();
	}
}
