package project.statement.track.modules.business.catalog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.exception.BaseException;
import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.persistance.GenericPersistence;
import lib.base.backend.pojo.catalog.CatalogPojo;
import lib.base.backend.pojo.catalog.CatalogRequestPojo;
import lib.base.backend.pojo.catalog.CatalogDataPojo;
import lib.base.backend.utils.CatalogUtil;
import project.statement.track.app.beans.entity.BrokerAccount;
import project.statement.track.app.utils.BuildPojoToEntityUtil;

@Component
public class CatalogBusiness {

	@SuppressWarnings("rawtypes")
	@Autowired
	protected GenericPersistence genericCustomPersistance;
	
	@Autowired
	CatalogUtil catalogUtil;
	
	@Autowired
	BuildPojoToEntityUtil buildPojoToEntityUtil;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CatalogPojo> getCatalog(String catalogName) throws BaseException {
		
		Class clazz = null;
		
		switch (catalogName) {
		case "brokerAccount":
			clazz = BrokerAccount.class;
			break;
		default:
			clazz = null;
			break;
		}
		
		if (clazz == null)
			throw new BusinessException("Catalog " + catalogName + " not found");
		
		List<?> catalogList = genericCustomPersistance.findAll(clazz);
		
		return catalogUtil.getCatalog(clazz, catalogList);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public CatalogDataPojo executeGetCatalog(CatalogRequestPojo requestPojo) throws BaseException {
		
		List<CatalogPojo> catalogListPojo = getCatalog(requestPojo.getCatalogName());
		
		CatalogDataPojo responsePojo = new CatalogDataPojo();
		responsePojo.setCatalogs(catalogListPojo);
		
		return responsePojo;
	}
}
