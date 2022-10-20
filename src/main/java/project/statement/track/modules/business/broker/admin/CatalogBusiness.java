package project.statement.track.modules.business.broker.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.exception.BaseException;
import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.persistance.GenericPersistence;
import lib.base.backend.pojo.catalog.CatalogDataPojo;
import lib.base.backend.pojo.catalog.CatalogRequestPojo;
import lib.base.backend.pojo.catalog.CatalogRespPojo;
import lib.base.backend.utils.CatalogUtil;
import project.statement.track.app.beans.entity.BrokerAccount;

@Component
public class CatalogBusiness {

	@SuppressWarnings("rawtypes")
	@Autowired
	protected GenericPersistence genericCustomPersistance;
	
	@Autowired
	CatalogUtil catalogUtil;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CatalogDataPojo> getCatalog(String catalogName) throws BaseException {
		
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
		
		List<CatalogDataPojo> catalogListPojo = catalogUtil.getCatalog(clazz, catalogList);
		
		return catalogListPojo;
	}
	
	@Transactional
	public CatalogRespPojo executeGetCatalog(CatalogRequestPojo requestPojo) throws BaseException {
		
		List<CatalogDataPojo> catalogListPojo = getCatalog(requestPojo.getCatalogName());
		
		CatalogRespPojo responsePojo = new CatalogRespPojo();
		responsePojo.setCatalogs(catalogListPojo);
		
		return responsePojo;
	}
}
