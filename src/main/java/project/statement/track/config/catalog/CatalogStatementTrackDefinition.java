package project.statement.track.config.catalog;

import java.util.LinkedHashMap;
import java.util.Map;

import lib.base.backend.modules.catalog.interaface.CatalogDefinition;
import project.statement.track.app.beans.entity.BrokerAccount;

public class CatalogStatementTrackDefinition implements CatalogDefinition {

	@SuppressWarnings("rawtypes")
	Map<String, Class> catalogs = new LinkedHashMap<>();
	
	public CatalogStatementTrackDefinition() {
		
		catalogs.put("brokerAccount", BrokerAccount.class);
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Class> getCatalogsDefinition() {
		return catalogs;
	}

}
